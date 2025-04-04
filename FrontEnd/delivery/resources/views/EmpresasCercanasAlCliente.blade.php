<x-plantilla>
    <div class="container mt-5">
        <h2>Empresas Cercanas al Cliente</h2>

        <div id="empresasCercanas" class="row">
            <!-- Aquí se cargarán las empresas cercanas -->
            <p>Cargando empresas cercanas...</p>
        </div>
    </div>

    <script>
        function obtenerDireccionCliente() {
            // Recuperar los datos almacenados en localStorage
            let idDireccion = localStorage.getItem('idDireccion');

            // Verificar si hay un idDireccion
            if (idDireccion) {
                console.log("Dirección seleccionada: " + idDireccion);
                return idDireccion; // Retorna el idDireccion si existe
            } else {
                console.log("No se ha seleccionado ninguna dirección.");
                return null; // Retorna null si no se ha seleccionado ninguna dirección
            }
        }

        // Pasa la URL de la imagen predeterminada desde Blade a JavaScript
        var defaultImageUrl = "{{ asset('img/empresas/restaurante.png') }}";
        const rutaProductos = "{{ route('productos', ':id') }}";
        $(document).ready(function() {
            // Obtener el idDireccion del localStorage
            let direccionId = obtenerDireccionCliente();

            if (direccionId) {
                // Hacer la solicitud AJAX para obtener las empresas cercanas
                $.ajax({
                    url: 'http://localhost:8080/delivery/v1/empresas/obtenerEmpresas/' +
                        direccionId, // Llama a tu API
                    type: 'GET',
                    success: function(response) {
                        // Verificar si la respuesta es un array

                        // Limpiar el contenedor de empresas
                        $('#empresasCercanas').html('');

                        // Verificar si hay empresas
                        if (response.length > 0) {
                            response.forEach(function(empresa) {
                                let url = rutaProductos.replace(':id', empresa.idEmpresa);
                                // Crear el HTML de la tarjeta para cada empresa
                                let cardHtml = `
                                    <div class="col-md-4 mb-4">
                                        <div class="card">
                               <img src="${empresa.imagen || defaultImageUrl}" class="card-img-top" alt="${empresa.empresa}" style="width: 50px;">

                                            <div class="card-body">
                                                <h5 class="card-title">${empresa.empresa}</h5>
                                                <p class="card-text">Costo de Envio: ${empresa.costoEnvio}</p>
                                             <a href="${url}" class="btn btn-primary">Elegir</a>
                                            </div>
                                        </div>
                                    </div>
                                `;

                                // Agregar la tarjeta al contenedor de empresas
                                $('#empresasCercanas').append(cardHtml);
                            });
                        } else {
                            $('#empresasCercanas').html('<p>No se encontraron empresas cercanas.</p>');
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                        Swal.fire({
                            title: "Error",
                            text: "Hubo un problema al obtener las empresas.",
                            icon: "error",
                            confirmButtonText: "Aceptar"
                        });
                    }
                });
            } else {
                // Si no hay idDireccion, mostrar un mensaje de error o advertencia
                $('#empresasCercanas').html(
                    '<p>No se ha seleccionado ninguna dirección. Por favor, seleccione una dirección primero.</p>'
                );
            }
        });
    </script>
</x-plantilla>
