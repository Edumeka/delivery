<x-plantilla-admin>
    <h2>Lista de Pedidos del Sistema</h2>
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
            <tr>
                <th>ID Pedido</th>
                <th>Fecha Pedido</th>
                <th>Monto Total</th>
                <th>Estado</th>
            </tr>
        </thead>
        <tbody>
            <!-- Los datos de los pedidos se cargarán aquí -->
        </tbody>
    </table>

</x-plantilla-admin>


<script>
    $(document).ready(function() {
        obtenerPedidos();
    });

    function obtenerPedidos() {
        const token = getCookie("jwt");

        $.ajax({
            url: "http://localhost:8080/delivery/v1/pedidos/listaDePedidos", // Asegúrate de que la URL coincida con tu endpoint en Spring Boot
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function(pedidos) {
                console.log(pedidos);
                // Aquí puedes manipular los datos de los pedidos y mostrarlos en la interfaz
                mostrarPedidos(pedidos);
            },
            error: function(e) {
                console.error("Error al obtener los pedidos:", e);
            }
        });
    }

    function mostrarPedidos(pedidos) {
        let html = '';
        pedidos.forEach(pedido => {
            html += `<tr>
                    <td>${pedido.idPedido}</td>
                    <td>${new Date(pedido.fechaPedido).toLocaleString()}</td>  <!-- Formato de fecha -->
                    <td>${pedido.montoTotalDeProductos.toFixed(2)}</td>  <!-- Formato de monto -->
                    <td>${pedido.estado.estado}</td>
                </tr>`;
        });

        // Supón que tienes una tabla con el ID "tablaPedidos"
        $("table tbody").html(html);
    }
</script>
