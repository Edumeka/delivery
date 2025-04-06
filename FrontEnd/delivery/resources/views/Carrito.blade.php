<x-plantilla>
    <div class="container mt-4">
        <h2 class="mb-3">🛒 Tu carrito</h2>
        <div class="text-end me-3 mb-4">
            <button class="btn btn-success" id="btnIrAPagar">Ir a Pagar</button>
        </div>

        <div class="text-end me-3 mb-4" hidden>
            <button class="btn btn-success" id="btn-finalizar-pedido">Finalizar Pedido</button>
        </div>
        <h6 id='lblCostoEnvio' style="color: aliceblue"></h6>
        <input type="text" id="txtDistancia" hidden>
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
    const urlPedido = "{{ route('pedido') }}";

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
                    $('#lblCostoEnvio').text('Costo de Envio: L. ' + item.producto.empresa
                        .costoEnvio.toFixed(2));

                    console.log("Distancia: ", obtenerDistanciaDelClienteALaTienda(item.producto
                        .empresa.idEmpresa));
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
        verCarrito(); // Llama a la función para cargar el carrito al cargar la página
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
                            verCarrito
                                (); // Llama a la función para cargar el carrito al cargar la página
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

    $('#btn-finalizar-pedido').on('click', function() {
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
                    success: function(response) {
                        Swal.fire({
                            icon: 'success',
                            title: 'Pedido guardado',
                            text: response ||
                                'Tu pedido fue registrado exitosamente.',
                            timer: 2000,
                            showConfirmButton: false
                        });
                        verCarrito(); // Recargar el carrito
                    },
                    error: function(xhr) {
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



    $('#btnIrAPagar').on('click', function() {
        const totalTexto = $('#total-carrito').text().trim();
        const totalLimpio = totalTexto.replace('L. ', '');
        const costoEnvioTexto = $('#lblCostoEnvio').text().trim();
        const costoEnvioLimpio = costoEnvioTexto.replace('Costo de Envio: L. ', '');

        const costoRepartidor = parseFloat($('#txtDistancia').val() * 5) || 0;
        const costoEnvio = parseFloat(costoEnvioLimpio) || 0;
        const total = parseFloat(totalLimpio) || 0;

        const totalConEnvio = (total + costoEnvio + costoRepartidor);

        console.log("Total con envío:", totalConEnvio);


        $.ajax({
            url: "http://localhost:8080/delivery/v1/metodopago/obtenerMetodoPago",
            method: "GET",
            success: function(response) {
                if (!response || response.length === 0) {
                    Swal.fire({
                        icon: 'warning',
                        title: 'Sin métodos de pago',
                        text: 'No hay métodos de pago disponibles.'
                    });
                    return;
                }

                let opciones =
                    `<option value="" disabled selected>Selecciona una forma de pago</option>`;
                response.forEach(metodo => {
                    opciones +=
                        `<option value="${metodo.idMetodoPago}">${metodo.metodoPago}</option>`;
                });

                Swal.fire({
                    title: '<strong>Selecciona forma de pago</strong>',
                    html: `
                      <div class="text-start mb-2">Costo de Envio:</div>
                    <div class="fs-4 fw-bold text-success mb-3">L. ${costoEnvio.toFixed(2)}</div>
                     <div class="text-start mb-2">Cobro del Repartidor:</div>
                    <div class="fs-4 fw-bold text-success mb-3">L. ${costoRepartidor.toFixed(2)}</div>
                       <div class="text-start mb-2">Sub total de productos:</div>
                    <div class="fs-4 fw-bold text-success mb-3">L. ${totalLimpio}</div>
                    <div class="text-start mb-2">Total a pagar:</div>
                    <div class="fs-4 fw-bold text-success mb-3">L. ${totalConEnvio.toFixed(2)}</div>
                    <select id="selectMetodoPago" class="form-select mb-2" style="width:100%">
                        ${opciones}
                    </select>
                    <div class="form-text text-muted">Este será el método con el que pagarás tu pedido.</div>
                `,
                    showCancelButton: true,
                    confirmButtonText: 'Aceptar',
                    cancelButtonText: 'Cancelar',
                    focusConfirm: false,
                    customClass: {
                        popup: 'shadow rounded-4',
                        confirmButton: 'btn btn-success',
                        cancelButton: 'btn btn-secondary me-2'
                    },
                    buttonsStyling: false,
                    preConfirm: () => {
                        const metodo = $('#selectMetodoPago').val();
                        if (!metodo) {
                            Swal.showValidationMessage(
                                'Debes seleccionar una forma de pago');
                        }
                        return metodo;
                    }
                }).then((result) => {
                    if (result.isConfirmed) {
                        const metodoSeleccionado = result.value;
                        const token = getCookie("jwt");
                        const pagoDTO = {
                            factura: generarFacturaAleatoria(), // puedes cambiar esto
                            totalFactura: (totalConEnvio.toFixed(2)),
                            costoEnvio: (costoEnvio.toFixed(2)),
                            gananciaRepartidor: (costoRepartidor.toFixed(2)),
                            kmRecorridos: $('#txtDistancia').val(),
                            metodoPago: {
                                idMetodoPago: parseInt(metodoSeleccionado)
                            }
                        };

                        $.ajax({
                            url: "http://localhost:8080/delivery/v1/pagos/pagar",
                            method: "POST",
                            data: JSON.stringify(pagoDTO),
                            contentType: "application/json",
                            headers: {
                                "Authorization": "Bearer " + token
                            },
                            success: function(resp) {
                                window.location.href = urlPedido;

                                Swal.fire({
                                    icon: 'success',
                                    title: '¡Pago realizado!',
                                    text: resp
                                });
                            },
                            error: function(xhr) {
                                const mensaje = xhr.responseText ||
                                    'Error al enviar el pago.';
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: mensaje
                                });
                                console.error("Error al enviar el pago:", xhr);
                            }
                        });
                    }
                });
            },
            error: function(xhr) {
                const mensaje = xhr.responseText || 'No se pudo obtener los métodos de pago.';
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: mensaje
                });
                console.error("Error al obtener los métodos de pago:", xhr);
            }
        });
    });

    /*
     * Función para obtener el valor de una cookie por su nombre
     * @param {string} name - Nombre de la cookie
     * @returns {string|null} - Valor de la cookie o null si no existe
     */
    function generarFacturaAleatoria() {
        const random = Math.floor(1000 + Math.random() * 9000);
        return `FAC-${random}`;
    }

    function obtenerDistanciaDelClienteALaTienda(idEmpresa) {
        const token = getCookie("jwt");
        $.ajax({
            url: "http://localhost:8080/delivery/v1/empresas/obtenerDistancia/" + idEmpresa,
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function(data) {
                $('#txtDistancia').val(data);
                console.log("Distancia:", data);
            },
            error: function(xhr) {
                console.error("Error al obtener la distancia:", xhr);
            }
        });
    }
</script>
