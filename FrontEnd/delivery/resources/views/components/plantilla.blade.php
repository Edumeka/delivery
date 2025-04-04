<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery CCDE</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="icon" type="image/png" href="{{ asset('img/logo_delivery.png') }}">



    <style>
        body {
            background: linear-gradient(to right, #1f1c2c, #928DAB);
        }

        .welcome-container {
            background: #2C2F33;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
            color: white;
        }

        .navbar-brand {
            font-weight: bold;
            font-size: 1.5rem;
            color: #fff !important;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            font-weight: bold;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
    </style>
    <!-- SweetAlert2 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.css" rel="stylesheet">

    <!-- SweetAlert2 JS -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.js"></script>

</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
        <div class="container">
            <a class="navbar-brand" href="{{ route('bienvenida') }}"><img src="{{ asset('img/logo_delivery.png') }}"
                    width="70"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" id="auth-link" href="#">


                        </a>
                    </li>
                    <li class="nav-item" id="liCarrito">
                        <a class="nav-link position-relative" href="/carrito">
                            <img src="{{ asset('img/carrito.png') }}" width="50">
                            <span id="carrito-count"
                                class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                0
                            </span>
                        </a>
                    </li>

                    <li>
                        <a class="nav-link" title="Cerrar Sesión" id="btnCerrarSesion" href="#">

                            Cerrar Sesión
                        </a>
                    <li>
                </ul>
            </div>
        </div>
    </nav>
    <main>
        {{ $slot }}
    </main>
    <script>
        actualizarContadorCarrito();

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

        function actualizarContadorCarrito() {
            const token = getCookie("jwt");
            if (!token) {
                document.getElementById("carrito-count").textContent = "0";
                return;
            }

            $.ajax({
                url: "http://localhost:8080/delivery/v1/carritos/verCarrito",
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                },
                success: function(carrito) {
                    console.log("Carrito:", carrito);
                    let totalCantidad = 0;
                    carrito.forEach(item => {
                        totalCantidad++;
                    });

                    document.getElementById("carrito-count").textContent = totalCantidad;
                },
                error: function(e) {
                    console.error("Error al obtener el carrito:", e);
                    document.getElementById("carrito-count").textContent = "0";
                }
            });
        }
    </script>
</body>

</html>
