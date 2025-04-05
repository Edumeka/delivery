<x-plantilla>
    <div class="container mt-4">
        <h2 class="mb-3">🛒 Tu carrito</h2><div class="text-end me-3 mb-4">
            <button class="btn btn-success" id="btn-finalizar-pedido">Finalizar Pedido</button>
        </div>
        
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
                <tr>
                    <th>N</th>
                    <th>Producto</th>
                    <th>Descripción</th>
                    <th>Precio Unitario</th>
                    <th>Cantidad</th>
                    <th>Subtotal</th>
                    <th>Accion</th>
                </tr>
            </thead>
            <tbody id="carrito-body">
                <!-- Se llenará con jQuery -->
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="5" class="text-end"><strong>Total:</strong></td>
                    <td colspan="2" id="total-carrito">L. 0.00</td>
                </tr>
            </tfoot>

        </table>
    </div>

</x-plantilla>
<script>
    function verCarrito() {
        const token = getCookie("jwt");

        $.ajax({
            url: "http://localhost:8080/delivery/v1/carritos/verCarrito",
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function(data) {
                if (data.length === 0) {
                    $('#carrito-body').html(
                        `<tr><td colspan="5" class="text-center">El carrito está vacío</td></tr>`
                    );
                    return;
                }
                let fila = 1;
                let html = '';
                let total = 0;
                data.forEach(item => {
                    const subtotal = item.precioUnitario * item.cantidad;
                    total += subtotal;

                    html += `
        <tr>
            <td>${fila++}</td>
            <td>${item.producto.producto}</td>
            <td>${item.producto.descripcion}</td>
            <td>L. ${item.precioUnitario.toFixed(2)}</td>
            <td>${item.cantidad}</td>
            <td>L. ${subtotal.toFixed(2)}</td>
            <td><button class="btn btn-danger btn-sm eliminar-item" data-id="${item.idCarrito}">Eliminar</button></td>
        </tr>`;
                });

                $('#carrito-body').html(html);
                $('#total-carrito').text('L. ' + total.toFixed(
                2)); // Asegúrate que este ID exista en el HTML

            },
            error: function(xhr) {
                console.error("Error al obtener el carrito:", xhr);
                $('#carrito-body').html(
                    `<tr><td colspan="5" class="text-center text-danger">Error al cargar el carrito</td></tr>`
                );
            }
        });
    }
    $(document).ready(function() {
        verCarrito() ;  // Llama a la función para cargar el carrito al cargar la página
    });

    $('#carrito-body').on('click', '.eliminar-item', function() {
        const idCarrito = $(this).data('id');
        Swal.fire({
            title: '¿Estás seguro?',
            text: "¡Esta acción eliminará el producto del carrito!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(`http://localhost:8080/delivery/v1/carritos/eliminarCarrito/${idCarrito}`, {
                        method: 'DELETE'
                    })
                    .then(res => {
                        if (res.ok) {
                            Swal.fire({
                                icon: 'success',
                                title: 'Eliminado',
                                text: 'El producto ha sido eliminado del carrito.',
                                timer: 1500,
                                showConfirmButton: false
                            });
                            verCarrito() ;  // Llama a la función para cargar el carrito al cargar la página
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'No se pudo eliminar el producto.'
                            });
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Ocurrió un error en la petición.'
                        });
                    });
            }
        });
    });

    $('#btn-finalizar-pedido').on('click', function () {
    const token = getCookie("jwt");

    Swal.fire({
        title: '¿Confirmar pedido?',
        text: "¿Deseas finalizar este pedido?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Sí, finalizar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {

            $.ajax({
                url: "http://localhost:8080/delivery/v1/pedidos/guardarPedido",
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                },
                success: function (response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Pedido guardado',
                        text: response || 'Tu pedido fue registrado exitosamente.',
                        timer: 2000,
                        showConfirmButton: false
                    });
                    verCarrito(); // Recargar el carrito
                },
                error: function (xhr) {
                    const mensaje = xhr.responseText || 'No se pudo guardar el pedido.';
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: mensaje
                    });
                    console.error("Error al guardar pedido:", xhr);
                }
            });

        }
    });
});






    
</script>
