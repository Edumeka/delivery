<x-plantilla>
    <div class="container mt-4">
        <h2 class="mb-3">🛒 Tu carrito</h2>
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Producto</th>
                    <th>Descripción</th>
                    <th>Precio Unitario</th>
                    <th>Cantidad</th>
                    <th>Subtotal</th>
                </tr>
            </thead>
            <tbody id="carrito-body">
                <!-- Se llenará con jQuery -->
            </tbody>
        </table>
    </div>

</x-plantilla>
<script>
    $(document).ready(function() {
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

                let html = "";
                data.forEach(item => {
                    html += `
                        <tr>
                            <td>${item.producto.producto}</td>
                            <td>${item.producto.descripcion}</td>
                            <td>L. ${item.precioUnitario.toFixed(2)}</td>
                            <td>${item.cantidad}</td>
                            <td>L. ${(item.precioUnitario * item.cantidad).toFixed(2)}</td>
                        </tr>`;
                });

                $('#carrito-body').html(html);
            },
            error: function(xhr) {
                console.error("Error al obtener el carrito:", xhr);
                $('#carrito-body').html(
                    `<tr><td colspan="5" class="text-center text-danger">Error al cargar el carrito</td></tr>`
                );
            }
        });
    });
</script>
