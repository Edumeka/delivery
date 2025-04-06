<x-plantilla-admin>
    <h2>Lista de Empresas</h2>
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
            <tr>
                <th>N</th>
                <th>Empresa</th>
                <th>RTN</th>
                <th>Costo del envío</th>
                <th>Imagen</th>
                <th>Administrador</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody id="tablaEmpresas">
        </tbody>
    </table>
</x-plantilla-admin>

<script>
    $(document).ready(function() {
        obtenerEmpresas();
    });

    function obtenerEmpresas() {
        const token = getCookie("jwt");

        $.ajax({
            url: "http://localhost:8080/delivery/v1/empresas/obtenerEmpresas",
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function(empresas) {
                mostrarEmpresas(empresas);
            },
            error: function(e) {
                console.error("Error al obtener las empresas:", e);
            }
        });
    }

    function mostrarEmpresas(empresas) {
        let html = '';
        let numero = 1;

        empresas.forEach(empresa => {
            let imagen = empresa.imagen ? `<img src="${empresa.imagen}" width="50">` : '';

            html += `<tr>
                        <td>${numero++}</td>
                        <td>${empresa.empresa}</td>
                        <td>${empresa.rtn}</td>
                        <td>${empresa.costoEnvio}</td>
                        <td>${imagen}</td>
                        <td>${empresa.administradorEmpresa.nombre}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="editarEmpresa(${empresa.idEmpresa}, '${empresa.empresa}', '${empresa.rtn}', '${empresa.costoEnvio}', '${empresa.administradorEmpresa.nombre}')">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarEmpresa(${empresa.idEmpresa})">Eliminar</button>
                        </td>
                    </tr>`;
        });

        $("#tablaEmpresas").html(html);
    }
    function editarEmpresa(id, nombre, rtn, costoEnvio, administradorIdActual) {
    fetch('http://localhost:8080/delivery/v1/clientes/obtenerUsuarios') // Asegúrate que esta ruta es correcta y accesible
        .then(response => response.json())
        .then(usuarios => {
            const opcionesUsuarios = usuarios.map(usuario => {
                const selected = usuario.idUsuario === administradorIdActual ? 'selected' : '';
                return `<option value="${usuario.idUsuario}" ${selected}>${usuario.nombre}</option>`;
            }).join('');

            Swal.fire({
                title: 'Editar Empresa',
                html: `
                <h6>Nombre de la Empresa</h6>
                    <input id="nombreEmpresa" class="swal2-input" value="${nombre}" placeholder="Nombre de la empresa" />
                    <h6>RTN de la Empresa</h6>
                    <input id="rtnEmpresa" class="swal2-input" value="${rtn}" placeholder="RTN" />
                    <h6>Costo de Envio de la Empresa</h6>
                    <input id="costoEnvioEmpresa" type="number" class="swal2-input" value="${costoEnvio}" placeholder="Costo del envío" />
                    <h6>Administrador de la empresa</h6>
                    <select id="administradorEmpresa" class="swal2-input">
                        ${opcionesUsuarios}
                    </select>
                `,
                focusConfirm: false,
                preConfirm: () => {
                    const nombreEmpresa = document.getElementById('nombreEmpresa').value;
                    const rtnEmpresa = document.getElementById('rtnEmpresa').value;
                    const costoEnvioEmpresa = parseFloat(document.getElementById('costoEnvioEmpresa').value);
                    const administradorId = parseInt(document.getElementById('administradorEmpresa').value);

                    if (!nombreEmpresa || !rtnEmpresa || isNaN(costoEnvioEmpresa)) {
                        Swal.showValidationMessage("Todos los campos son obligatorios y el costo debe ser numérico.");
                        return false;
                    }
                    actualizarEmpresa(id, nombreEmpresa, rtnEmpresa, costoEnvioEmpresa, administradorId);
                }
            });
        })
        .catch(error => {
            console.error('Error al obtener usuarios:', error);
            Swal.fire('Error', 'No se pudieron cargar los usuarios.', 'error');
        });
}


    function actualizarEmpresa(id, nombre, rtn, costoEnvio, administradorId) {
        const token = getCookie("jwt");

        const empresaDTO = {
            idEmpresa: id,
            empresa: nombre,
            rtn: rtn,
            costoEnvio: costoEnvio,
            administradorEmpresa: {
                idUsuario: administradorId
            }
        };

        $.ajax({
            url: "http://localhost:8080/delivery/v1/empresas/editarEmpresa",
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            data: JSON.stringify(empresaDTO),
            success: function() {
                Swal.fire('Éxito', 'Empresa actualizada correctamente', 'success');
                obtenerEmpresas();
            },
            error: function() {
                Swal.fire('Error', 'No se pudo actualizar la empresa', 'error');
            }
        });
    }

    function eliminarEmpresa(id) {
        const token = getCookie("jwt");

        Swal.fire({
            title: '¿Estás seguro?',
            text: "Esta acción no se puede deshacer.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: `http://localhost:8080/delivery/v1/empresas/eliminarEmpresa/${id}`,
                    method: "DELETE",
                    headers: {
                        "Authorization": `Bearer ${token}`
                    },
                    success: function() {
                        Swal.fire('Eliminado', 'Empresa eliminada correctamente', 'success');
                        obtenerEmpresas();
                    },
                    error: function() {
                        Swal.fire('Error', 'No se pudo eliminar la empresa', 'error');
                    }
                });
            }
        });
    }

    function getCookie(name) {
        const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
        if (match) return match[2];
    }
</script>
