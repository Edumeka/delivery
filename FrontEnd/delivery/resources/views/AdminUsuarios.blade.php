<x-plantilla-admin>
    <h2>Lista de Usuarios</h2>
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
            <tr>
                <th>N</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Correo</th>
                <th>Estado</th>
                <th>Rol</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody id="tablaUsuarios">
            <!-- Los usuarios se cargarán aquí -->
        </tbody>
    </table>
</x-plantilla-admin>

<script>
    $(document).ready(function() {
        obtenerUsuarios();
    });

    function obtenerUsuarios() {
        const token = getCookie("jwt");

        $.ajax({
            url: "http://localhost:8080/delivery/v1/clientes/obtenerUsuarios", // Asegúrate de que la URL coincida con tu endpoint en Spring Boot
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
                           <td>${usuario.estado.estado}</td>
                          <td>${usuario.rol.rol}</td>
                         
                        <td>
                   
    <button class="btn btn-warning btn-sm" onclick="editarUsuario('${usuario.idUsuario}', '${usuario.nombre}', '${usuario.apellido}', '${usuario.correo}', '${usuario.rol.rol}', '${usuario.estado.estado}')">Editar</button>

                            <button class="btn btn-danger btn-sm" onclick="eliminarUsuario(${usuario.idUsuario})">Eliminar</button>
                        </td>
                    </tr>`;
        });

        $("#tablaUsuarios").html(html);
    }

    function editarUsuario(id, nombre, apellido, correo, rol, estado) {
    $.when(
        $.ajax({ url: "http://localhost:8080/delivery/v1/roles/obtenerRoles", method: "GET" }),  // Roles
        $.ajax({ url: "http://localhost:8080/delivery/v1/estados/obtenerEstados", method: "GET" })   // Estados
    ).done(function(rolesResponse, estadosResponse) {
        const roles = rolesResponse[0]; // Lista de roles
        const estados = estadosResponse[0]; // Lista de estados

        // Abre un SweetAlert para editar el usuario
        Swal.fire({
            title: 'Editar Usuario',
            html: `
                <input id="nombre" class="swal2-input" value="${nombre}" placeholder="Nombre" />
                <input id="apellido" class="swal2-input" value="${apellido}" placeholder="Apellido" />
                <input id="correo" class="swal2-input" value="${correo}" placeholder="Correo" />
                <select id="estado" class="swal2-select">
                    ${estados.map(estadoOption => `
                        <option value="${estadoOption.estado}" 
                                data-estado='${JSON.stringify(estadoOption)}' 
                                ${estado === estadoOption.estado ? 'selected' : ''}>
                            ${estadoOption.estado}
                        </option>
                    `).join('')}
                </select>
                <select id="rol" class="swal2-select">
                    ${roles.map(rolOption => `
                        <option value="${rolOption.rol}" 
                                data-rol='${JSON.stringify(rolOption)}' 
                                ${rol === rolOption.rol ? 'selected' : ''}>
                            ${rolOption.rol}
                        </option>
                    `).join('')}
                </select>
            `,
            focusConfirm: false,
            preConfirm: () => {
                const nombre = document.getElementById('nombre').value;
                const apellido = document.getElementById('apellido').value;
                const correo = document.getElementById('correo').value;

                // Obtener el objeto completo de estado y rol a partir de los atributos data-*
                const estado = JSON.parse(document.getElementById('estado').selectedOptions[0].getAttribute('data-estado'));
                const rol = JSON.parse(document.getElementById('rol').selectedOptions[0].getAttribute('data-rol'));

                // Enviar los cambios al backend
                actualizarUsuario(id, nombre, apellido, correo, estado, rol);
            }
        });
    }).fail(function() {
        Swal.fire('Error', 'No se pudo obtener los roles o estados', 'error');
    });
}



function actualizarUsuario(id, nombre, apellido, correo, estado, rol) {
    const token = getCookie("jwt");
    // Crear el objeto usuarioDTO
    const usuarioDTO = {
        idUsuario: id,
        nombre: nombre,
        apellido: apellido,
        correo: correo,
        estado: estado,  // Asegúrate de enviar el estado como un objeto
        rol: rol           // Asegúrate de enviar el rol como un objeto
    };

    $.ajax({
        url: `http://localhost:8080/delivery/v1/clientes/editarUsuario`,
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"  // Indicamos que enviamos JSON
        },
        data: JSON.stringify(usuarioDTO),  // Convertimos el objeto usuarioDTO a JSON
        success: function() {
            Swal.fire('Éxito', 'Usuario actualizado correctamente', 'success');
            obtenerUsuarios();
        },
        error: function() {
            Swal.fire('Error', 'Hubo un problema al actualizar el usuario', 'error');
        }
    });
}


    function eliminarUsuario(id) {
        const token = getCookie("jwt");

        $.ajax({
            url: `http://localhost:8080/delivery/v1/clientes/eliminarUsuario/${id}`,
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function() {
                alert("Usuario eliminado");
                obtenerUsuarios(); // Recargar la lista de usuarios
            },
            error: function(e) {
                console.error("Error al eliminar el usuario:", e);
            }
        });
    }
</script>
