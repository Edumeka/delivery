<x-plantilla-admin>


    <!-- Contenido principal -->
    <div class="content">
        <h2 class="mb-4 ">Dashboard del Administrador</h2>

        <!-- Estadísticas y métricas -->
        <div class="row">
            <div class="col-md-4">
                <div class="card dashboard-card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Pedidos Hoy</h5>
                        <p class="card-text" id='pedidosHoy'>25 Pedidos</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card dashboard-card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title ">Total Ventas</h5>
                        <p class="card-text" id='lblTotalVentas'>$1,250.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card dashboard-card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Usuarios Activos</h5>
                        <p class="card-text" id='lblUsuariosActivos'>102 Usuarios</p>
                        <a href="{{route('admin.usuarios')}}" class="btn btn-primary">Ver detalles</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Gráficos -->
        <div class="row mt-4">
            <div class="col-md-8" hidden>
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Ventas Mensuales</h5>
                        <canvas id="salesChart"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">Pedidos por Estado</h5>
                        <canvas id="orderStatusChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>

    <script>
        verPedidosDelDia();
        verTotalVendido();
        verUsuariosActivos();

        function verPedidosDelDia() {
            fetch('http://localhost:8080/delivery/v1/pedidos/hoy') // Cambia a tu endpoint real
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    $('#pedidosHoy').html(data + " pedidos");
                })
                .catch(error => {
                    console.error("Error al obtener pedidos de hoy:", error);
                });
        }

        function verTotalVendido() {
            fetch('http://localhost:8080/delivery/v1/pedidos/totalVendido') // Cambia a tu endpoint real
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    $('#lblTotalVentas').html("L. " + data.toFixed(2));
                })
                .catch(error => {
                    console.error("Error al obtener total vendido :", error);
                });
        }

        function verUsuariosActivos() {
            fetch('http://localhost:8080/delivery/v1/clientes/usuariosActivos') // Cambia a tu endpoint real
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    $('#lblUsuariosActivos').html(data + " usuarios activos");
                })
                .catch(error => {
                    console.error("Error al obtener usuarios activos:", error);
                });
        }




 // Obtener los datos de pedidos por estado desde el backend
 fetch('http://localhost:8080/delivery/v1/pedidos/reporteEstadoPedidos')
        .then(response => response.json())
        .then(data => {
            // Extraer los datos
            const finalizados = data.finalizados; // Cambié el nombre para coincidir con el backend
            const pendientes = data.pendientes;
            const enProceso = data.enProceso; // Cambié el nombre para coincidir con el backend

            // Gráfico de pedidos por estado
            const ctx2 = document.getElementById('orderStatusChart').getContext('2d');
            const orderStatusChart = new Chart(ctx2, {
                type: 'pie',
                data: {
                    labels: ['Finalizado', 'Pendientes', 'En Proceso'], // Coincide con los datos
                    datasets: [{
                        data: [finalizados, pendientes, enProceso],
                        backgroundColor: ['#28a745', '#ffc107', '#dc3545']
                    }]
                },
                options: {
                    responsive: true
                }
            });
        })
        .catch(error => console.error('Error al cargar los datos de los pedidos:', error));
    </script>
    
</x-plantilla-admin>
