<x-plantilla>
    <div class="container d-flex justify-content-center align-items-center mt-5">
        <div class="card shadow-lg p-4 rounded" style="width: 350px; background: #2C2F33; color: white;">
            <div class="text-center">
                <h2 class="mb-3">Iniciar Sesión</h2>
            </div>
            <form id="loginForm" onsubmit="event.preventDefault(); iniciarSesion();">
                <div class="mb-3">
                    <label for="email" class="form-label">Correo Electrónico</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="usuario@ejemplo.com" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="********" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Acceder</button>
            </form>

            <!-- Enlace para registro -->
            <div class="mt-3 text-center">
                <p class="text-light">¿No tienes cuenta? <a href="{{ route('register') }}" class="text-decoration-none text-primary">Regístrate aquí</a></p>
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
