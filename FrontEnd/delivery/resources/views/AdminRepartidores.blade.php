<x-plantilla-admin>
    <h2>Lista de Repartidores</h2>
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
            <tr>
                <th>N</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Correo</th>
                <th>Telefono</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody id="tablaUsuarios">
            <!-- Los usuarios se cargarán aquí -->
        </tbody>
    </table>

    <!-- Modal -->
    <div class="modal fade" id="historialModal" tabindex="-1" aria-labelledby="historialModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content shadow-lg rounded-4 border-0">
                <div class="modal-header bg-primary text-white rounded-top-4">
                    <h5 class="modal-title" id="historialModalLabel">Historial de Trabajo del Repartidor</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
                        aria-label="Cerrar"></button>
                </div>

                <div class="modal-body">
                    <div class="mb-3">
                        <h6 class="fw-bold text-secondary">Resumen:</h6>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="alert alert-info p-2" role="alert">
                                    <strong>Kilómetros recorridos:</strong> <span id="lblKmRecorridos">0 km</span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="alert alert-success p-2" role="alert">
                                    <strong>Ganancia total:</strong> <span id="lblGananciaTotal">L 0.00</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>#</th>
                                <th>Fecha</th>
                                <th>Kilómetros Recorridos</th>
                                <th>Ganancia (Lps)</th>
                            </tr>
                        </thead>
                        <tbody id="tablaHistorial">
                            <!-- Aquí se insertan las filas dinámicamente -->
                        </tbody>
                    </table>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>



</x-plantilla-admin>
<script>
    $(document).ready(function() {
        obtenerUsuarios();
    });

    function obtenerUsuarios() {
        const token = getCookie("jwt");

        $.ajax({
            url: "http://localhost:8080/delivery/v1/clientes/obtenerRepartidores", // Asegúrate de que la URL coincida con tu endpoint en Spring Boot
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function(usuarios) {
                mostrarUsuarios(usuarios);
            },
            error: function(e) {
                console.error("Error al obtener los usuarios:", e);
            }
        });
    }

    function mostrarUsuarios(usuarios) {
        let html = '';
        let numero = 1;
        usuarios.forEach(usuario => {
            html += `<tr>
                        <td>${numero++}</td>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.apellido}</td>
                        <td>${usuario.correo}</td>
                                  <td>${usuario.telefono}</td>
                         
                        <td>
                            <button class="btn btn-info btn-sm" onclick="verHistorialRepartidor(${usuario.idUsuario})">Ver Historial</button>
                        </td>
                    </tr>`;
        });

        $("#tablaUsuarios").html(html);
    }

    function verHistorialRepartidor(idUsuario) {
        fetch(`http://localhost:8080/delivery/v1/clientes/buscarHistorialRepartidor/${idUsuario}`)
            .then(res => res.json())
            .then(data => {
                let html = '';
                let i = 1;
                let gananciaTotal = 0;
                let kmTotal = 0;
                data.forEach(h => {
                    gananciaTotal += h.ganancia;
                    kmTotal += h.kmRecorrido;
                    html += `<tr>
                            <td>${i++}</td>
                            <td>${h.fecha}</td>
                            
                            <td>${(h.kmRecorrido).toFixed(2)}</td>
                            <td>${h.ganancia}</td>
                         </tr>`;
                });
                $('#lblGananciaTotal').html(gananciaTotal.toFixed(2));
                $('#lblKmRecorridos').html(kmTotal.toFixed(2));

                document.getElementById('tablaHistorial').innerHTML = html;
                $('#historialModal').modal('show');
            })
            .catch(error => {
                console.error('Error al obtener el historial:', error);
                alert('No se pudo cargar el historial.');
            });
    }
</script>
