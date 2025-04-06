<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard de Administrador</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome (para íconos) -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Chart.js (para gráficos) -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            background-color: #212529; /* Fondo oscuro para toda la página */
            color: #e4e6f0; /* Texto claro para contraste */
        }

        .sidebar {
            min-height: 100vh;
            background-color: #343a40; /* Barra lateral más oscura */
            color: #ffffff;
        }

        .sidebar .nav-link {
            color: #adb5bd;
        }

        .sidebar .nav-link.active {
            background-color: #007bff;
            color: white;
        }

        .sidebar .nav-link:hover {
            background-color: #495057;
        }

        .card {
            background-color: #495057; /* Fondo de las tarjetas oscuro */
            border: none;
            color: #f8f9fa;
        }

        .card-title {
            color: #ffffff; /* Títulos de las tarjetas en blanco */
        }

        .card-body {
            background-color: #6c757d; /* Fondo de las tarjetas con un toque más suave */
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }

        .dashboard-card {
            background-color: #343a40;
        }

        .badge-warning {
            background-color: #ffc107;
        }

        .badge-success {
            background-color: #28a745;
        }

        .badge-danger {
            background-color: #dc3545;
        }

        .content {
            flex-grow: 1;
            padding: 2rem;
        }

        .navbar, .nav-item, .nav-link {
            background-color: #343a40;
        }
    </style>
</head>
<body>
    <div class="d-flex">
        <!-- Barra lateral -->
        <div class="sidebar p-3 shadow-sm">
            <h4 class="text-center mb-4 text-light">Administrador</h4>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" href="#">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-box"></i> Pedidos
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-cogs"></i> Configuración
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-users"></i> Usuarios
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-chart-line"></i> Reportes
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-danger" href="#">
                        <i class="fas fa-sign-out-alt"></i> Cerrar sesión
                    </a>
                </li>
            </ul>
        </div>

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
</body>
</html>
