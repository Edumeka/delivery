<x-plantilla>
    <div class="container d-flex justify-content-center align-items-center mt-1">
        <div class='row'>
            <div class="col-md-6">

            
        <div class="card shadow-lg p-4 rounded" style="width: 500px; background: #2C2F33; color: white;">
            <div class="text-center">
                <h2 class="mb-3">Crear Cuenta</h2>
            </div>
            <form id="registerForm">
                <div class='row'>
                    <div class="col-md-6">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Escriba su Nombre" required>
                    </div>
                    <div class="col-md-6">
                        <label for="apellido" class="form-label">Apellido</label>
                        <input type="text" class="form-control" id="apellido" name="apellido" placeholder="Escriba su apellido" required>
                    </div>
                </div>
               
                <div class="mb-3">
                    <label for="email" class="form-label">Correo Electrónico</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="usuario@ejemplo.com" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="********" required>
                </div>
                <div class="mb-3">
                    <label for="password_confirmation" class="form-label">Confirmar Contraseña</label>
                    <input type="password" class="form-control" id="password_confirmation" name="password_confirmation" placeholder="********" required>
                </div>
                <div class="mb-6">
                    <label for="dni" class="form-label">DNI</label>
                    <input type="text" class="form-control" id="dni" name="dni" placeholder="xxxx-xxxx-xxxxx" required>
                </div>
                <div class="mb-6">
                    <label for="telefono" class="form-label">Telefono</label>
                    <input type="text" class="form-control" id="telefono" name="telefono" placeholder="Escriba su telefono" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Registrar</button>
            </form>

            <div class="mt-3 text-center">
                <p class="text-light">¿Ya tienes cuenta? <a href="{{ route('login') }}" class="text-decoration-none text-primary">Inicia sesión aquí</a></p>
            </div>
        </div>
    </div>
</div></div>

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
