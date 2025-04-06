<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery CCDE Administrador</title>
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
    <!-- SweetAlert2 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.css" rel="stylesheet">

    <!-- SweetAlert2 JS -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.js"></script>
    <style>
        <link rel="icon" type="image/png" href="{{ asset('img/logo_delivery.png') }}"><style>body {
            background-color: #212529;
            /* Fondo oscuro para toda la página */
            color: #e4e6f0;
            /* Texto claro para contraste */
        }

        .sidebar {
            min-height: 100vh;
            background-color: #343a40;
            /* Barra lateral más oscura */
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
            background-color: #495057;
            /* Fondo de las tarjetas oscuro */
            border: none;
            color: #f8f9fa;
        }

        .card-title {
            color: #ffffff;
            /* Títulos de las tarjetas en blanco */
        }

        .card-body {
            background-color: #6c757d;
            /* Fondo de las tarjetas con un toque más suave */
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

        .navbar,
        .nav-item,
        .nav-link {
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
                    <a class="nav-link active" href="{{route('admin')}}">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="{{ route('admin.pedidos') }}">
                        <i class="fas fa-box"></i> Pedidos
                    </a>
                </li>
                <li class="nav-item" hidden>
                    <a class="nav-link" href="#">
                        <i class="fas fa-cogs"></i> Configuración
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="{{ route('admin.usuarios') }}">
                        <i class="fas fa-users"></i> Usuarios
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-chart-line"></i> Reportes
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-danger" id="btnCerrarSesion" href="#">
                        <i class="fas fa-sign-out-alt"></i> Cerrar sesión
                    </a>
                </li>
            </ul>
        </div>
        <!-- Contenido principal -->
        <div class="main-content">
            <div class="content-wrapper">
                {{ $slot }}
            </div>
        </div>
    </div>
</body>

</html>
<script>
    function getCookie(name) {
        const cookies = document.cookie.split("; ");

        for (let cookie of cookies) {
            let [key, value] = cookie.split("=");

            if (key === name) {
                return decodeURIComponent(value);
            }
        }
        return null;
    }

    // Función para verificar si la cookie 'jwt' existe
    function checkAuth() {
        const jwtToken = getCookie("jwt");

        if (jwtToken) {

            $('#btnCerrarSesion').show(); // Mostrar el botón de cerrar sesión
            $('#liCarrito').show();
        } else {
            // Si no existe la cookie JWT, mostrar 'Iniciar sesión'
            $('#btnCerrarSesion').hide(); // Mostrar el botón de cerrar sesión
            document.getElementById('auth-link').textContent = 'Iniciar sesión';
            document.getElementById('auth-link').setAttribute('href',
                '{{ route('login') }}'); // Redirige a la página de login

            $('#liCarrito').hide();
        }
    }

    // Llamamos a la función para actualizar el enlace según el estado de la cookie
    checkAuth();




    function cerrarSesion() {
        fetch("http://localhost:8080/delivery/v1/auth/logout", {
                method: "POST",
                credentials: "include" // Enviar cookies en la petición
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al cerrar sesión");
                }
                return response.text();
            })
            .then(() => {
                console.log("Sesión cerrada correctamente");
                localStorage.removeItem('bearerToken'); // Elimina el token de localStorage
                window.location.href = "{{ route('bienvenida') }}"; // Redirigir a la página de inicio de sesión

            })
            .catch(error => {
                console.error("Error:", error);
            });
    }



    $("#btnCerrarSesion").click(function() {
        confirmarCerrarSesion();
    });


    function confirmarCerrarSesion() {
        Swal.fire({
            title: "¿Cerrar sesión?",
            text: "¿Estás seguro de que deseas cerrar sesión?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Sí, cerrar sesión",
            cancelButtonText: "Cancelar"
        }).then((result) => {
            if (result.isConfirmed) {
                cerrarSesion();
            }
        });
    }
</script>
