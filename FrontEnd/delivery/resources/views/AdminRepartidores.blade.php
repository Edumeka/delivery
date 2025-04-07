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
    <div class="modal fade" id="historialModal" tabindex="-1" role="dialog" aria-labelledby="historialModalLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document"> <!-- modal-lg para que sea más ancho -->
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="historialModalLabel">Historial de Trabajo del Repartidor</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <h5 id='lblKmRecorridos'></h5>
                    <h3 id='lblGananciaTotal'></h3>
                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Fecha</th>
                                <th>Km Recorrido</th>
                                <th>Ganancia</th>
                            </tr>
                        </thead>
                        <tbody id="tablaHistorial">
                            <!-- Aquí se insertan las filas del historial -->
                        </tbody>

                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
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
                let kmTotal=0;
                data.forEach(h => {
                    gananciaTotal += h.ganancia;
                    kmTotal+=h.kmRecorrido;
                    html += `<tr>
                            <td>${i++}</td>
                            <td>${h.fecha}</td>
                            
                            <td>${h.kmRecorrido}</td>
                            <td>${h.ganancia}</td>
                         </tr>`;
                });
                $('#lblGananciaTotal').html("Ganancia: L " + gananciaTotal.toFixed(2));
                $('#lblKmRecorridos').html("Km recorridos: " + kmTotal.toFixed(2)+" km");
                
                document.getElementById('tablaHistorial').innerHTML = html;
                $('#historialModal').modal('show');
            })
            .catch(error => {
                console.error('Error al obtener el historial:', error);
                alert('No se pudo cargar el historial.');
            });
    }
</script>
