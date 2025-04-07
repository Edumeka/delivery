
<script>
    function comprobarLogueo() {
    const jwtCookie = document.cookie.split(';').find(cookie => cookie.trim().startsWith('jwt='));

    if (jwtCookie) {
        window.location.href = "{{ route('inicio') }}";  
    }

}

comprobarLogueo();
</script>
<x-plantilla>
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="text-center welcome-container bg-dark bg-opacity-75 p-5 rounded shadow">
            <h1 class="display-4 text-light mb-3">Bienvenido a <span class="text-primary">Delivery CCDE</span></h1>
            <p class="lead text-light font-weight-normal mb-4">Tu plataforma de pedidos confiable y eficiente.</p>
            <a href="{{ route('login') }}" class="btn btn-primary btn-lg rounded-pill">Iniciar Sesión</a>
        </div>
    </div>
</x-plantilla>