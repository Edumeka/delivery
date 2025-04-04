$(document).ready(function () {

    verDireccionesDelCliente();
    const token = document.cookie.split(';').find(cookie => cookie.trim().startsWith('jwt='));



    if (!token) {
        $("#direccionContainer").html(`
            <div class="alert alert-warning text-center">
                No has iniciado sesión. <a href="/login" class="alert-link">Inicia sesión</a> para continuar.
            </div>
        `);
        return;
    }
// Seleccionar dirección
$(document).on("click", ".seleccionarDireccion", function () {
    let direccionId = $(this).closest(".direccion-card").data("id");
    let direccion = $(this).closest(".direccion-card").data("direccion");

    // Guardar la dirección en localStorage
    localStorage.setItem('idDireccion', direccionId);  // Guarda el id  

    // Mostrar el SweetAlert con el botón "Aceptar"
    Swal.fire({
        title: "Dirección seleccionada",
        text: "Has seleccionado la dirección: " + direccion,
        icon: "success",
        confirmButtonText: "Aceptar"
    }).then((result) => {
        if (result.isConfirmed) {
            // Crear el formulario oculto
            var form = $('<form>', {
                method: 'POST',
                action: empresasCercanasAlCliente,  // Ruta para enviar los datos
                style: 'display:none;'  // Asegurarnos de que el formulario no sea visible
            });

            // Agregar el token CSRF
            form.append($('<input>', {
                type: 'hidden',
                name: '_token',
                value: $('meta[name="csrf-token"]').attr('content')  // Obtenemos el token CSRF
            }));

            // Agregar el idDireccion al formulario
            form.append($('<input>', {
                type: 'hidden',
                name: 'idDireccion',
                value: direccionId  // El idDireccion que se enviará
            }));

            // Agregar el formulario al body y enviarlo
            $('body').append(form);
            form.submit();  // Enviar el formulario
        }
    });
});



    
});

function verDireccionesDelCliente() {

    // Enviar los datos a tu API
    const token = getCookie("jwt");

    $.ajax({
        url: "http://localhost:8080/delivery/v1/clientes/obtenerDireccionCliente",
        type: "GET",
        headers: { "Authorization": "Bearer " + token },
        success: function (data) {

            let listaDirecciones = $("#listaDirecciones");
            listaDirecciones.empty();

            if (data.length > 0) {
                data.forEach((direccion, index) => {
                    let card = `
                        <div class="col-md-6">
                            <div class="card shadow-sm p-3 mb-3 direccion-card" data-id="${direccion.idDireccion}"  data-direccion="${direccion.direccion}">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <i class="bi bi-geo-alt-fill text-primary"></i> Dirección ${index + 1}
                                    </h5>
                                    <p class="card-text" >
                                        ${direccion.direccion}<br>
                                        <strong>Referencia:</strong> ${direccion.descripcion}
                                    </p>
                                    <button class="btn btn-primary seleccionarDireccion">
                                        Seleccionar
                                    </button>
                                </div>
                            </div>
                        </div>
                    `;
                    listaDirecciones.append(card);
                });
            } else {
                listaDirecciones.html(`
                    <div class="text-center">
                        <p class="text-muted">No tienes direcciones registradas.</p>
                       
                    </div>
                `);
            }
        },
        error: function (xhr, status, error) {
            console.error("Error al obtener direcciones:", error);
            $("#direccionContainer").html(`
                <div class="alert alert-danger text-center">
                    Ocurrió un error al cargar las direcciones. Intenta nuevamente.
                </div>
            `);
        }
    });
}

function getCookie(name) {
    const cookies = document.cookie.split("; ");

    for (let cookie of cookies) {
        let [key, value] = cookie.split("=");

        if (key === name) {
            return decodeURIComponent(value);
        }
    }
    return null;
}
// Función para cargar los países
function verPaises() {
    const token = getCookie("jwt");
    $.ajax({
        url: "http://localhost:8080/delivery/v1/direcciones/paises",
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": "Bearer " + token // Enviar el token en los headers
        },
        success: function (data) {
            let selectPais = $("#pais");
            selectPais.empty();
            selectPais.append('<option value="">Seleccione un país</option>');

            data.forEach(pais => {
                selectPais.append(`<option value="${pais.idLugar}">${pais.lugar}</option>`);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error al cargar los países:", error);
        }
    });
}

// Función para cargar los departamentos según el país seleccionado
$("#pais").change(function () {
    const idPais = $(this).val();
    if (idPais) {
        cargarDepartamentos(idPais);
    } else {
        $("#departamento").empty().append('<option value="">Seleccione un departamento</option>');
        $("#municipio").empty().append('<option value="">Seleccione un municipio</option>');
    }
});

// Función para cargar los departamentos
function cargarDepartamentos(idPais) {

    const token = getCookie("jwt");
    $.ajax({
        url: `http://localhost:8080/delivery/v1/direcciones/departamentos/${idPais}`,
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": "Bearer " + token // Enviar el token en los headers
        },
        success: function (data) {

            let selectDepartamento = $("#departamento");
            selectDepartamento.empty();
            selectDepartamento.append('<option value="">Seleccione un departamento</option>');

            data.forEach(departamento => {
                selectDepartamento.append(`<option value="${departamento.idLugar}">${departamento.lugar}</option>`);
            });

            $("#municipio").empty().append('<option value="">Seleccione un municipio</option>'); // Limpiar municipios
        },
        error: function (xhr, status, error) {
            console.log(xhr);
            console.error("Error al cargar los departamentos:", error);
        }
    });
}

// Función para cargar los municipios según el departamento seleccionado
$("#departamento").change(function () {
    const idDepartamento = $(this).val();
    if (idDepartamento) {
        cargarMunicipios(idDepartamento);
    } else {
        $("#municipio").empty().append('<option value="">Seleccione un municipio</option>');
    }
});

// Función para cargar los municipios
function cargarMunicipios(idDepartamento) {
    const token = getCookie("jwt");
    $.ajax({
        url: `http://localhost:8080/delivery/v1/direcciones/municipios/${idDepartamento}`,
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": "Bearer " + token // Enviar el token en los headers
        },
        success: function (data) {
            let selectMunicipio = $("#municipio");
            selectMunicipio.empty();
            selectMunicipio.append('<option value="">Seleccione un municipio</option>');

            data.forEach(municipio => {
                selectMunicipio.append(`<option value="${municipio.idLugar}">${municipio.lugar}</option>`);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error al cargar los municipios:", error);
        }
    });
}



// Manejar el envío del formulario
$('#formAgregarDireccion').on('submit', function (e) {
    e.preventDefault();

    // Recoger los datos del formulario
    const direccionExacta = $('#direccionExacta').val();
    const descripcion = $('#descripcion').val();
    const ubicacion = $('#ubicacion').val(); // Ubicación como un objeto JSON

    // Recoger los datos de los selectores de país, departamento y municipio
    const pais = $('#pais').val();
    const departamento = $('#departamento').val();
    const municipio = $('#municipio').val();

    // Crear el objeto DireccionDTO
    const direccionDTO = {
        direccion: direccionExacta,
        descripcion: descripcion,
        ubicacion: JSON.parse(ubicacion), // Convertir el JSON de la ubicación
        lugar: {
            idLugar: municipio // Suponiendo que el municipio tiene el idLugar
        },
    };

    // Enviar los datos a tu API
    const token = getCookie("jwt");

    $.ajax({
        url: "http://localhost:8080/delivery/v1/clientes/crearDireccionCliente",
        method: "POST",
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + token
        },
        data: JSON.stringify(direccionDTO),
        success: function (response) {

            if (response == "La dirección ya está registrada para este usuario") {
                alert("La dirección ya está registrada para este usuario");
            } else {
                $("#modalAgregarDireccion").modal("toggle");
                verDireccionesDelCliente();

            }
        },
        error: function (xhr, status, error) {
            // Log de los detalles del error
            console.error("Status: " + status);  // Muestra el estado de la solicitud (ej. 'error', 'timeout')
            console.error("HTTP Status Code: " + xhr.status);  // Muestra el código de estado HTTP (ej. 400, 404, 500)
            console.error("Response Text: " + xhr.responseText);  // Muestra la respuesta del servidor

            // Si el error tiene mensaje, lo mostramos
            if (error) {
                console.error("Error: " + error);  // Muestra el error específico, si lo hay
            }

            // Si la respuesta tiene algún mensaje adicional, lo mostramos
            try {
                let response = JSON.parse(xhr.responseText);
                if (response.message) {
                    console.error("Mensaje del servidor: " + response.message);
                }
            } catch (e) {
                // Si no es un JSON válido, no hacemos nada
            }
        }
    });
});

function inicializarMapa() {
    // Crear el mapa centrado en una ubicación predeterminada
    var map = L.map('map').setView([14.076275, -87.194136], 13);  // Latitud y Longitud predeterminada

    // Agregar capa de mapa (OpenStreetMap)
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Agregar un marcador inicial
    var marker = L.marker([14.076275, -87.194136]).addTo(map);

    // Actualizar la ubicación al hacer clic en el mapa
    map.on('click', function (e) {
        var lat = e.latlng.lat;
        var lng = e.latlng.lng;

        // Mover el marcador a la nueva ubicación
        marker.setLatLng([lat, lng]);

        // Guardar la ubicación en el campo oculto como un objeto POINT
        document.getElementById('ubicacion').value = JSON.stringify([lng, lat]);
    });

    // Evento de búsqueda de dirección (solo cuando se presione el botón)
    $('#btnBuscarDireccion').on('click', function () {
        var direccion = $('#buscarDireccion').val();

        if (direccion.length > 3) {  // Solo hacer la búsqueda si la dirección tiene más de 3 caracteres
            buscarDireccionEnMapa(direccion, map, marker);
        } else {
            alert("Por favor ingrese al menos 4 caracteres para realizar la búsqueda.");
        }
    });
}

// Función de búsqueda de la dirección utilizando la API de Nominatim (OpenStreetMap)
function buscarDireccionEnMapa(direccion, map, marker) {
    var url = `https://nominatim.openstreetmap.org/search?format=json&q=${direccion}&addressdetails=1&limit=1`;

    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            if (data && data.length > 0) {
                var lat = data[0].lat;
                var lon = data[0].lon;

                // Centrar el mapa en las coordenadas obtenidas
                map.setView([lat, lon], 13);

                // Colocar un marcador en la nueva ubicación
                marker.setLatLng([lat, lon]);

                // Guardar las coordenadas en el campo oculto
                document.getElementById('ubicacion').value = JSON.stringify([lon, lat]);
            } else {
                alert("No se encontraron resultados para la dirección.");
            }
        },
        error: function (xhr, status, error) {
            console.error("Error al geocodificar la dirección", error);
        }
    });
}

function obtenerDireccionCliente() {
    // Recuperar los datos almacenados en localStorage
    let idDireccion = localStorage.getItem('idDireccion');

    // Verificar si hay un idDireccion
    if (idDireccion) {
        console.log("Dirección seleccionada: " + idDireccion);
        return idDireccion;  // Retorna el idDireccion si existe
    } else {
        console.log("No se ha seleccionado ninguna dirección.");
        return null;  // Retorna null si no se ha seleccionado ninguna dirección
    }
}


function comprobarDireccionDelCliente() {
    let direccionSeleccionada = obtenerDireccionCliente();

if (direccionSeleccionada) {
    console.log("La dirección seleccionada es: " + direccionSeleccionada);
} else {
    console.log("No se ha seleccionado ninguna dirección.");
}
    
}

function eliminarDireccion(idDireccion) {
    // Eliminar la dirección con la clave 'idDireccion' de localStorage
    localStorage.removeItem('idDireccion');
    console.log("Dirección con ID " + idDireccion + " eliminada.");
}

