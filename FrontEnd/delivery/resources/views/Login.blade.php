<x-plantilla>
    <style>
         .form-control::placeholder {
            color: #6c757d !important;
        }
    </style>

<div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card shadow-md p-4 rounded-md border border-secondary border-opacity-25" style="width: 350px; background:rgb(59, 59, 63); color: white;">
        <div class="text-center mb-4">
            <h2 class="text-light">Iniciar Sesión</h2>
        </div>
        <form id="loginForm" onsubmit="event.preventDefault(); iniciarSesion();">
            <div class="form-floating mb-3">
                <input type="email" class="form-control rounded-sm bg-light text-dark" id="email" name="email" placeholder="usuario@ejemplo.com" required>
                <label for="email" class="form-label text-light">Correo Electrónico</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control rounded-sm bg-light text-dark" id="password" name="password" placeholder="********" required>
                <label for="password" class="form-label text-light">Contraseña</label>
            </div>
            <button type="submit" class="btn btn-primary w-100 rounded-pill">Acceder</button>
        </form>

        <div class="mt-3 text-center">
            <p class="text-light">¿No tienes cuenta? <a href="{{ route('register') }}" class="text-decoration-none text-info">Regístrate aquí</a></p>
        </div>
    </div>
</div>

    <script>
        function iniciarSesion() {
            // Obtener los valores del formulario
            var valores = {
                correo: document.getElementById("email").value,
                contrasenia: document.getElementById("password").value
            };

            // Realizar la solicitud al backend para iniciar sesión
            fetch("http://localhost:8080/delivery/v1/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(valores),
                credentials: "include"  // Asegura que las cookies se envíen con la solicitud
            })
            .then(response => {
                if (response.ok) {
                    Swal.fire({
                        title: "Inicio de sesión exitoso",
                        text: "Bienvenido al sistema",
                        icon: "success",
                        timer: 2000,
                        showConfirmButton: false
                    }).then(() => {
                        // Redirigir a la página principal si el inicio de sesión es exitoso
                       window.location.href = "{{ route('inicio') }}";
                    });
                } else {
                    throw new Error("Credenciales incorrectas");
                }
            })
            .catch(error => {
                console.log(error);
                Swal.fire({
                    title: "Error",
                    text: error.message,
                    icon: "error"
                });
            });
        }
    </script>
</x-plantilla>