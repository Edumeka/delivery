function comprobarLogueo() {
    const jwtCookie = document.cookie.split(';').find(cookie => cookie.trim().startsWith('jwt='));

    if (!jwtCookie) {
        // Redirige a la ruta "bienvenida" usando Laravel y JavaScript
        window.location.href = urlBienvenida;  // Usando Blade para inyectar la URL generada por Laravel
    }

}
