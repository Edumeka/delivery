
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
    <div class="container d-flex justify-content-center align-items-center mt-5">
        <div class="text-center welcome-container">
            <h1 class="text-light">Bienvenido a <span class="text-primary">Delivery CCDE</span></h1>
            <p class="lead text-light">Tu plataforma de pedidos confiable y eficiente.</p>
            <a href="{{ route('login') }}" class="btn btn-primary btn-lg">Iniciar Sesión</a>
        </div>
    </div>


</x-plantilla>