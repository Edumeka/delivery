<x-plantilla-admin>
    <h2>Lista de Clientes</h2>
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
                    <h5 class="modal-title" id="historialModalLabel">Historial de Compra del cliente</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
                        aria-label="Cerrar"></button>
                </div>

                <div class="modal-body">
                  
                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>#</th>
                                <th>Producto</th>
                                <th>Descripcion</th>     
                                <th>Empresa</th>                               
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
            url: "http://localhost:8080/delivery/v1/clientes/obtenerClientes", // Asegúrate de que la URL coincida con tu endpoint en Spring Boot
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
                            <button class="btn btn-info btn-sm" onclick="verHistorialCliente(${usuario.idUsuario})">Ver Historial</button>
                        </td>
                    </tr>`;
        });

        $("#tablaUsuarios").html(html);
    }

    function verHistorialCliente(idUsuario) {
        fetch(`http://localhost:8080/delivery/v1/clientes/historialCliente/${idUsuario}`)
            .then(res => res.json())
            .then(data => {
                let html = '';
                let i = 1;
                data.forEach(h => {                    
                
                    html += `<tr>
                            <td>${i++}</td>
                            <td>${h.producto}</td>
                            
                            <td>${(h.descripcion)}</td>
                            <td>${h.empresa.empresa}</td>
                         </tr>`;
                });
                
                document.getElementById('tablaHistorial').innerHTML = html;
                $('#historialModal').modal('show');
            })
            .catch(error => {
                console.error('Error al obtener el historial:', error);
                alert('No se pudo cargar el historial.');
            });
    }
</script>
