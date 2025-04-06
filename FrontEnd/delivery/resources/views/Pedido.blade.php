<x-plantilla>

    <script>
        $(document).ready(function() {
            // Notificación inicial de que el pedido está en proceso
            Swal.fire({
                title: '¡Pedido en proceso!',
                text: 'Estamos preparando tu pedido y buscando un repartidor para ti. 🚴‍♂️🍔',
                icon: 'info',
                confirmButtonText: 'Entendido',
                allowOutsideClick: false,
                backdrop: true
            }).then((result) => {
                if (result.isConfirmed) {
                    // Llamar a la función para buscar un repartidor
                    buscarRepartidor();
                }
            });
        });

        function buscarRepartidor() {
            const token = getCookie("jwt");

            $.ajax({
                url: "http://localhost:8080/delivery/v1/pedidos/buscarRepartidor",
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                },
                success: function(data) {
                    console.log(data);
                    // Si se encuentra un repartidor y se asigna
                    if (data) {
                        Swal.fire({
                            title: '¡Repartidor asignado!',
                            text: 'Tu repartidor ' + data.nombre + ' ' + data.apellido +
                                ' está en camino. 🚗💨',
                            icon: 'info',
                            allowOutsideClick: false,
                            backdrop: true,
                            confirmButtonText: 'Genial!'
                        }).then((result) => {
                            // Esta acción se ejecuta cuando el usuario hace clic en "Genial"
                            if (result.isConfirmed) {
                                tiempoDeEspera();
                            }
                        });
                    } else {
                        Swal.fire({
                            title: 'Error',
                            text: 'No se pudo asignar un repartidor, por favor intenta más tarde.',
                            icon: 'error',
                            confirmButtonText: 'Entendido'
                        });
                    }
                },
                error: function(xhr, status, error) {
                    console.log(xhr.status);
                    console.error("Error en la solicitud:", error);

                    // Si el error es un 404, mostramos un mensaje específico
                    if (xhr.status === 404) {
                        Swal.fire({
                            title: 'No hay pedidos en proceso',
                            text: 'No se pudo encontrar un pedido en proceso. Por favor, verifica el estado de tus pedidos.',
                            icon: 'error',
                            allowOutsideClick: false,
                            backdrop: true,
                            confirmButtonText: 'Entendido'
                        }).then((result) => {
                                if (result.isConfirmed) {
                                    redirigirAInicio();
                                }
                            }


                        );
                    } else if (xhr.status === 400) { // Cambié `elseif` por `else if`
                        Swal.fire({
                            title: 'No hay repartidores disponibles',
                            text: 'En estos momentos no hay repartidores disponibles. Intenta de nuevo más tarde.',
                            icon: 'error',
                            allowOutsideClick: false,
                            backdrop: true,
                            confirmButtonText: 'Intentar nuevamente'
                        }).then((result) => {
                            // Si el usuario hace clic en "Intentar nuevamente", vuelve a buscar el repartidor
                            if (result.isConfirmed) {
                                crearRepartidor(); // Llama la función crearRepartidor
                            }
                        });
                    } else {
                        Swal.fire({
                            title: 'Error',
                            text: 'Hubo un error al intentar buscar un repartidor. Intenta de nuevo más tarde.',
                            icon: 'error',
                            confirmButtonText: 'Entendido'
                        });
                    }

                }
            });
        }

        // Función para actualizar el estado del pedido
        function actualizarEstadoPedido() {
            const token = getCookie("jwt");

            $.ajax({
                url: "http://localhost:8080/delivery/v1/pedidos/actualizarEstadoDelPedido", // Asegúrate de que esta URL exista en tu backend
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },

                success: function(response) {
                    console.log("Estado actualizado: ", response);
                    Swal.fire({
                        title: 'Pedido entregado',
                        text: '¡Tu pedido ha sido entregado con éxito! 🎉\nGracias por usar nuestros servicios.',
                        icon: 'success',
                        allowOutsideClick: false,
                        backdrop: true,
                        confirmButtonText: '¡Genial!'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            redirigirAInicio();
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.log("Error al actualizar estado:", error);
                    Swal.fire({
                        title: 'Error al actualizar estado',
                        text: 'Hubo un problema al actualizar el estado de tu pedido. Intenta más tarde.',
                        icon: 'error',
                        confirmButtonText: 'Entendido'
                    });
                }
            });
        }

        function redirigirAInicio() {
            window.location.href =
                "{{ route('inicio') }}"; // Redirige a la página de pedidos (ajusta la URL según lo necesites)
        }



        function crearRepartidor() {
            const token = getCookie("jwt");

            $.ajax({
                url: "http://localhost:8080/delivery/v1/clientes/crearRepartidor", // Asegúrate de que esta URL apunte a la correcta en tu backend
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                },
                success: function(response) {
                    // Si la creación del repartidor fue exitosa
                    Swal.fire({
                        title: 'Se ha encontrado un Repartidor',
                        text: 'Un repartidor ha sido elegido. 🏍️',
                        icon: 'info',
                        allowOutsideClick: false,
                        backdrop: true,
                        confirmButtonText: '¡Genial!'
                    }).then((result) => {
                        // Si el usuario hace clic en "¡Genial!", buscamos el repartidor nuevamente
                        if (result.isConfirmed) {
                            buscarRepartidor(); // Llamamos la función para buscar el repartidor
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.log(xhr);
                    // Si hay un error en la solicitud
                    console.error("Error al crear el repartidor:", error);
                    Swal.fire({
                        title: 'Error al crear el repartidor',
                        text: 'Hubo un problema al registrar el repartidor. Por favor, intenta de nuevo más tarde.',
                        icon: 'error',
                        confirmButtonText: 'Entendido'
                    });
                }
            });
        }


        function tiempoDeEspera() {
            const token = getCookie("jwt");

            $.ajax({
                url: "http://localhost:8080/delivery/v1/clientes/tiempoDeEspera", // Asegúrate de que esta URL apunte a la correcta en tu backend
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                },
                success: function(response) {
                    // Si la creación del repartidor fue exitosa
                    Swal.fire({
                        title: 'Tiempo de Espera Aproximado',
                        text: response,
                        icon: 'info',
                        allowOutsideClick: false,
                        backdrop: true,
                        confirmButtonText: 'Entendido'
                    }).then((result) => {
                        // Si el usuario hace clic en "¡Genial!", buscamos el repartidor nuevamente
                        if (result.isConfirmed) {
                            actualizarEstadoPedido();
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.log(xhr);
                    // Si hay un error en la solicitud
                    console.error("Error al obtener el tiempo de espera:", error);
                    Swal.fire({
                        title: 'Error al obtener el tiempo de espera',
                        text: 'Hubo un problema al obtener el tiempo de espera. Por favor, intenta de nuevo más tarde.',
                        icon: 'error',
                        confirmButtonText: 'Entendido'
                    });
                }
            });
        }
    </script>

</x-plantilla>
