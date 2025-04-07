<x-plantilla>
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card shadow-md p-4 rounded-md border border-secondary border-opacity-25" style="width: 500px; background:rgb(59, 59, 63); color: white;">
            <div class="text-center mb-4">
                <h2 class="text-light">Crear Cuenta</h2>
            </div>
            <form id="registerForm">
                <div class="row mb-3">
                    <div class="col-md-6 form-floating">
                        <input type="text" class="form-control rounded-sm bg-dark text-light" id="nombre" name="nombre" placeholder="Nombre" required>
                        <label for="nombre" class="form-label text-light">Nombre</label>
                    </div>
                    <div class="col-md-6 form-floating">
                        <input type="text" class="form-control rounded-sm bg-dark text-light" id="apellido" name="apellido" placeholder="Apellido" required>
                        <label for="apellido" class="form-label text-light">Apellido</label>
                    </div>
                </div>
    
                <div class="form-floating mb-3">
                    <input type="email" class="form-control rounded-sm bg-dark text-light" id="email" name="email" placeholder="usuario@ejemplo.com" required>
                    <label for="email" class="form-label text-light">Correo Electrónico</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control rounded-sm bg-dark text-light" id="password" name="password" placeholder="********" required>
                    <label for="password" class="form-label text-light">Contraseña</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control rounded-sm bg-dark text-light" id="password_confirmation" name="password_confirmation" placeholder="********" required>
                    <label for="password_confirmation" class="form-label text-light">Confirmar Contraseña</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-sm bg-dark text-light" id="dni" name="dni" placeholder="xxxx-xxxx-xxxxx" required>
                    <label for="dni" class="form-label text-light">DNI</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-sm bg-dark text-light" id="telefono" name="telefono" placeholder="Escriba su telefono" required>
                    <label for="telefono" class="form-label text-light">Teléfono</label>
                </div>
                <button type="submit" class="btn btn-primary w-100 rounded-pill">Registrar</button>
            </form>
    
            <div class="mt-3 text-center">
                <p class="text-light">¿Ya tienes cuenta? <a href="{{ route('login') }}" class="text-decoration-none text-info">Inicia sesión aquí</a></p>
            </div>
        </div>
    </div>

<script>
    $(document).ready(function () {
        $("#registerForm").on("submit", function (e) {
            e.preventDefault();

            // Recoger datos del formulario
            var nombre = $("#nombre").val();
            var apellido = $("#apellido").val();
            var email = $("#email").val();
            var password = $("#password").val();
            var password_confirmation = $("#password_confirmation").val();
            var dni = $("#dni").val();
            var telefono = $("#telefono").val();

            // Validación de campos
            if (nombre === "" || apellido === "" || email === "" || password === "" || password_confirmation === "" || dni === "") {
                alert("Por favor, completa todos los campos.");
                return;
            }

            // Verificación de contraseñas
            if (password !== password_confirmation) {
                alert("Las contraseñas no coinciden.");
                return;
            }

            // Crear objeto JSON para enviar
            var userData = {
                nombre: nombre,
                apellido: apellido,
                correo: email,
                contrasenia: password,
                dni: dni,
                telefono: telefono
            };

            // Enviar datos a Spring Boot con AJAX
            $.ajax({
                url: "http://localhost:8080/delivery/v1/auth/register",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(userData),
                success: function (response) {
                  
                 //   alert("Cuenta registrada exitosamente.");

                    // Llamar a la función para iniciar sesión con los mismos datos de usuario
                    iniciarSesion({
                        correo: email,
                        contrasenia: password
                    });
                },
                error: function (xhr, status, error) {
                    // Parsear la respuesta JSON
                    var response = JSON.parse(xhr.responseText);  // Convierte la respuesta JSON en un objeto

                    // Obtener el mensaje de error
                    var errorMessage = response.message || "Error desconocido"; // Si no hay mensaje, mostrar "Error desconocido"
                    
                    // Si el error es debido a un duplicado de DNI
                    if (errorMessage.includes("Duplicate entry")) {
                        errorMessage = "El DNI ya está registrado en el sistema.";
                    }

                    alert("Error al registrar la cuenta: " + errorMessage);
                }
            });
        });
    });

    function iniciarSesion(valores) {
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
            Swal.fire({
                title: "Error",
                text: error.message,
                icon: "error"
            });
        });
    }
</script>

</x-plantilla>
