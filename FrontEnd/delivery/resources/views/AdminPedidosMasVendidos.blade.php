<x-plantilla-admin>
    <h2>Lista de Productos mas vendidos</h2>
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
            <tr>
                <th>N</th>
                <th>Producto</th>
                <th>Descripcion</th>
                <th>Precio</th>
                <th>Cantidad Vendida</th>
                <th>Empresa</th>
                <th>Total de ingresos</th>
            </tr>
        </thead>
        <tbody>
            <!-- Los datos de los pedidos se cargarán aquí -->
        </tbody>
    </table>

</x-plantilla-admin>


<script>
    $(document).ready(function() {
        obtenerProductosMasVendidos();
    });

    function obtenerProductosMasVendidos() {
        const token = getCookie("jwt");

        $.ajax({
            url: "http://localhost:8080/delivery/v1/productos/productosMasVendidos", 
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function(productos) {
             
                mostrarProductosMasVendidos(productos);
            },
            error: function(e) {
                console.error("Error al obtener los pedidos:", e);
            }
        });
    }

    function mostrarProductosMasVendidos(productos) {
        let html = '';
        let numero=1;
        productos.forEach(producto => {
            html += `<tr>
                    <td>${numero++}</td>
                     <td>${producto.producto.producto}</td>
                     <td>${producto.producto.descripcion}</td>
                     <td>${producto.producto.precio}</td>
                     <td>${producto.cantidadVendida}</td>
                     <td style="color:blue">${producto.producto.empresa.empresa}</td>
                    <td>${producto.totalIngresos.toFixed(2)}</td>  <!-- Formato de monto -->
                </tr>`;
        });

        // Supón que tienes una tabla con el ID "tablaPedidos"
        $("table tbody").html(html);
    }
</script>
