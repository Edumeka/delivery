<script src="{{ asset('js/deliveryApp.js') }}"></script>
<script>
    var urlBienvenida = "{{ route('bienvenida') }}";
    comprobarLogueo();
</script>
<!-- Incluir CSS de Leaflet -->
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />

<!-- Incluir JS de Leaflet -->
<script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>

<x-plantilla>
    <div class="container mt-4">
        <h2 class="text-center mb-4">Selecciona una dirección de entrega</h2>

        <div id="direccionContainer">
            <div class="row" id="listaDirecciones">
                <div class="col-12 text-center">
                    <p class="text-muted">Cargando direcciones...</p>
                </div>
            </div>

            <!-- Botón para agregar nueva dirección -->
            <div class="text-center mt-4">
                <button class="btn btn-success" id="btnAgregarDireccion">
                    <i class="bi bi-plus-circle"></i> Agregar Dirección
                </button>
            </div>
        </div>
    </div>

    <!-- MODAL PARA AGREGAR DIRECCIÓN -->
    <div class="modal fade" id="modalAgregarDireccion" tabindex="-1" aria-labelledby="modalAgregarDireccionLabel"
        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content shadow-lg rounded-4">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="modalAgregarDireccionLabel">
                        <i class="bi bi-map"></i> Nueva Dirección
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
                        aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="formAgregarDireccion">

                        <!-- PAÍS -->
                        <div class="mb-3">
                            <label class="form-label"><i class="bi bi-globe"></i> País:</label>
                            <select class="form-control rounded-pill" id="pais" required>
                                <option value="">Seleccione un país</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label"><i class="bi bi-geo"></i> Departamento:</label>
                            <select class="form-control rounded-pill" id="departamento" required>
                                <option value="">Seleccione un departamento</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label"><i class="bi bi-house-door"></i> Municipio:</label>
                            <select class="form-control rounded-pill" id="municipio" required>
                                <option value="">Seleccione un municipio</option>
                            </select>
                        </div>
                        <!-- Mapa -->
                        <div id="map" style="height: 400px;"></div>

                        <!-- Campo de búsqueda -->
                        <div class="mb-3">
                            <label class="form-label"><i class="bi bi-search"></i> Buscar Dirección:</label>
                            <input type="text" class="form-control rounded-pill" id="buscarDireccion"
                                placeholder="Buscar dirección" />
                        </div>
                        <button type="button" class="btn btn-primary" id="btnBuscarDireccion">
                            <i class="bi bi-search"></i> Buscar
                        </button>

                        <!-- Campo oculto para la ubicación -->
                        <input type="text" id="ubicacion" name="ubicacion" />


                        <div class="mb-3">
                            <label class="form-label"><i class="bi bi-info-circle"></i> Direccion Exacta:</label>
                            <textarea class="form-control rounded-3" id="direccionExacta" rows="2"
                                placeholder="Col. La Kennedy frente al Seguro Social" required></textarea>
                        </div>

                        <div class="mb-3">
                            <label class="form-label"><i class="bi bi-info-circle"></i> Descripcion de la
                                direccion:</label>
                            <input class="form-control rounded-3" id="descripcion" rows="2"
                                placeholder="Trabajo o Casa" required></input>
                        </div>
                        <button type="submit" class="btn btn-success w-100 rounded-pill">
                            <i class="bi bi-save"></i> Guardar Dirección
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>



    <script src="{{ asset('js/direcciones.js') }}"></script>
    <script>
        $("#btnAgregarDireccion").click(function() {
            verPaises();
            limpiarModalDirecciones();
            $("#modalAgregarDireccion").modal("toggle");
            inicializarMapa(); // Iniciar el mapa
        });

        function limpiarModalDirecciones() {
            // Limpiar el <select> de país
            $('#pais').empty();
            $('#pais').append('<option value="">Seleccione un país</option>'); // Opción por defecto

            // Limpiar el <input> de departamento
            $('#departamento').val(''); // Vaciar el campo de texto del departamento

            // Limpiar cualquier otro campo que puedas tener en el modal (si hay más campos)
            // Ejemplo para un campo de municipio:
            $('#municipio').val('');
        }
    </script>
    <style>
        .direccion-card {
            border-radius: 10px;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .direccion-card:hover {
            transform: scale(1.02);
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2);
        }

        .seleccionarDireccion {
            width: 100%;
        }

        .direccion-card {
            border-radius: 10px;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .direccion-card:hover {
            transform: scale(1.02);
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2);
        }

        .seleccionarDireccion {
            width: 100%;
        }

        .modal-content {
            border-radius: 12px;
            border: none;
            overflow: hidden;
        }

        .modal-header {
            background: linear-gradient(135deg, #007bff, #0056b3);
            border-bottom: none;
        }

        .modal-header h5 {
            font-weight: bold;
        }

        .modal-body {
            padding: 20px;
        }

        .form-control {
            border-radius: 8px;
            border: 1px solid #ddd;
        }

        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        .btn {
            transition: all 0.3s ease-in-out;
        }

        .btn:hover {
            transform: scale(1.05);
        }
    </style>


</x-plantilla>
