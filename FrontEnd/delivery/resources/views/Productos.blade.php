<x-plantilla>

    <input type="text" id="idEmpresa" value="{{ $idEmpresa }}" hidden>

    <div id="contenedorProductos" class="row mt-4"></div>

</x-plantilla>

<script>
    var defaultImageUrl = "{{ asset('img/productos/pizza.png') }}";
    $(document).ready(function() {

        const idEmpresa = $('#idEmpresa').val();
        // Enviar los datos a tu API
        const token = getCookie("jwt");
        $.ajax({
            url: "http://localhost:8080/delivery/v1/productos/" + idEmpresa,
            method: 'GET',
            headers: {
                "Authorization": "Bearer " + token
            },
            success: function(response) {
                if (response.length > 0) {
                    $('#contenedorProductos').html('');

                    response.forEach(function(producto) {
                        let productoHtml = `
    <div class="col-md-4 mb-4">
        <div class="card h-100">
            <img src="${producto.imagen || defaultImageUrl}" class="card-img-top" alt="${producto.producto}" style="width: 50px;">
            <div class="card-body">
                <h5 class="card-title">${producto.producto}</h5>
                <p class="card-text">Precio: L. ${producto.precio}</p>
                <p class="card-text">${producto.descripcion}</p>
               <button class="btn btn-success agregar-carrito" 
        data-id="${producto.idProducto}" 
        data-nombre="${producto.producto}" 
        data-precio="${producto.precio}"
        data-descripcion="${producto.descripcion}"
        data-imagen="${producto.imagen}">
    Agregar
</button>

            </div>
        </div>
    </div>
`;
                        $('#contenedorProductos').append(productoHtml);

                    });
                } else {
                    $('#contenedorProductos').html(
                        '<p>No hay productos disponibles para esta empresa.</p>');
                }
            },
            error: function(error) {
                console.error("Error al obtener productos:", error);
                $('#contenedorProductos').html('<p>Error al cargar productos.</p>');
            }
        });
    });


    let carrito = [];

    $(document).on('click', '.agregar-carrito', function() {
        const id = $(this).data('id');
        const nombre = $(this).data('nombre');
        const precio = $(this).data('precio');
        const descripcion = $(this).data('descripcion');
        const imagen = $(this).data('imagen');

        Swal.fire({
            title: `¿Cuántos deseas agregar de "${nombre}"?`,
            input: 'number',
            inputAttributes: {
                min: 1
            },
            inputValue: 1,
            showCancelButton: true,
            confirmButtonText: 'Agregar',
            cancelButtonText: 'Cancelar',
            preConfirm: (cantidad) => {
                if (cantidad <= 0 || isNaN(cantidad)) {
                    Swal.showValidationMessage('Ingresa una cantidad válida');
                    return false;
                }
                return parseInt(cantidad);
            }
        }).then((result) => {
            if (result.isConfirmed) {
                const cantidad = result.value;

                const token = getCookie("jwt");
                const productoDTO = {
                    idProducto: id,
                    producto: nombre,
                    descripcion: descripcion,
                    precio: precio

                };

                $.ajax({
                    url: "http://localhost:8080/delivery/v1/carritos/guardarCarrito",
                    method: "POST",
                    contentType: "application/json",
                    headers: {
                        "Authorization": `Bearer ${token}`
                    },
                    data: JSON.stringify({
                        producto: productoDTO,
                        cantidad: cantidad
                    }),
                    success: function(response) {
                        Swal.fire('Agregado',
                            `${nombre} x${cantidad} fue agregado al carrito.`, 'success'
                        );
                        console.log("Carrito actual:", response);
                        actualizarContadorCarrito();
                    },
                    error: function(err) {
                        console.error("Error al guardar en carrito:", err);
                        Swal.fire('Error', 'No se pudo agregar el producto al carrito.',
                            'error');
                    }
                });
            }
        });
    });
</script>
