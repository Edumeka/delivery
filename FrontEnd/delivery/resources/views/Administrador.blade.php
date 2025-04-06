<x-plantilla-admin>
   

        <!-- Contenido principal -->
        <div class="content">
            <h2 class="mb-4 text-light">Dashboard</h2>

            <!-- Estadísticas y métricas -->
            <div class="row">
                <div class="col-md-4">
                    <div class="card dashboard-card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Pedidos Hoy</h5>
                            <p class="card-text">25 Pedidos</p>
                            <a href="#" class="btn btn-primary">Ver detalles</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card dashboard-card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Total Ventas</h5>
                            <p class="card-text">$1,250.00</p>
                            <a href="#" class="btn btn-primary">Ver detalles</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card dashboard-card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Usuarios Activos</h5>
                            <p class="card-text">102 Usuarios</p>
                            <a href="#" class="btn btn-primary">Ver detalles</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Gráficos -->
            <div class="row mt-4">
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Ventas Mensuales</h5>
                            <canvas id="salesChart"></canvas>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
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

    <!-- Script de gráficos -->
    <script>
        // Gráfico de ventas mensuales
        const ctx1 = document.getElementById('salesChart').getContext('2d');
        const salesChart = new Chart(ctx1, {
            type: 'line',
            data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun'],
                datasets: [{
                    label: 'Ventas ($)',
                    data: [500, 600, 700, 800, 950, 1200],
                    borderColor: 'rgba(0, 123, 255, 1)',
                    backgroundColor: 'rgba(0, 123, 255, 0.1)',
                    fill: true
                }]
            }
        });

        // Gráfico de pedidos por estado
        const ctx2 = document.getElementById('orderStatusChart').getContext('2d');
        const orderStatusChart = new Chart(ctx2, {
            type: 'pie',
            data: {
                labels: ['Completados', 'Pendientes', 'Cancelados'],
                datasets: [{
                    data: [40, 30, 30],
                    backgroundColor: ['#28a745', '#ffc107', '#dc3545']
                }]
            }
        });
    </script>
</x-plantilla-admin>