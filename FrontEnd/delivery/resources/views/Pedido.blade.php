<x-plantilla>

    <script>
        $(document).ready(function() {
            Swal.fire({
                title: '¡Pedido en proceso!',
                text: 'Estamos preparando tu pedido y buscando un repartidor para ti. 🚴‍♂️🍔',
                icon: 'info',
                confirmButtonText: 'Entendido',
                allowOutsideClick: false,
                backdrop: true
            }).then((result) => {
                if (result.isConfirmed) {
                    // Aquí colocas lo que quieres hacer cuando el usuario hace clic en "Entendido"
                    console.log('El usuario hizo clic en "Entendido"');
                    verUltimoPedido(); // Por ejemplo, llamar tu función después de que el usuario confirma
                }
            });
        });


        function verUltimoPedido() {
            const token = getCookie("jwt");

            $.ajax({
                url: "http://localhost:8080/delivery/v1/pedidos/buscarRepartidor",
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                },
                success: function(data) {
                    console.log(data);
                    // Aquí puedes agregar la lógica para manejar la respuesta
                    if (data && data.success) {
                        Swal.fire({
                            title: '¡Repartidor asignado!',
                            text: 'Tu repartidor está en camino. 🚗💨',
                            icon: 'success',
                            confirmButtonText: 'Genial!'
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
                    console.log(xhr);
                    console.error("Error en la solicitud:", error);
                    Swal.fire({
                        title: 'Error',
                        text: 'Hubo un error al intentar buscar un repartidor. Intenta de nuevo más tarde.',
                        icon: 'error',
                        confirmButtonText: 'Entendido'
                    });
                }
            });
        }
    </script>

</x-plantilla>
