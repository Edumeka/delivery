package com.emeka.delivery.Services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emeka.delivery.DTO.TrabajoRealizadoDTO;
import com.emeka.delivery.DTO.UsuarioDTO;
import com.emeka.delivery.Repositories.DireccionRepository;
import com.emeka.delivery.Repositories.EstadoRepository;
import com.emeka.delivery.Repositories.PedidoRepository;
import com.emeka.delivery.Repositories.RolRepository;
import com.emeka.delivery.Repositories.TrabajoRealizadoRepository;
import com.emeka.delivery.Repositories.UsuarioRepository;
import com.emeka.delivery.Repositories.VehiculoRepository;
import com.emeka.delivery.models.Direccion;
import com.emeka.delivery.models.Estado;
import com.emeka.delivery.models.Pedido;
import com.emeka.delivery.models.Rol;
import com.emeka.delivery.models.TrabajoRealizado;
import com.emeka.delivery.models.Usuario;
import com.emeka.delivery.models.Vehiculo;

@Service
public class UsuarioService {

        @Autowired
        private UsuarioRepository usuarioRepository;
        @Autowired
        private EstadoRepository estadoRepository;
        @Autowired
        private VehiculoService vehiculoService;
        @Autowired
        private RolRepository rolRepository;
        @Autowired
        private PedidoRepository pedidoRepository;
        @Autowired
        private VehiculoRepository vehiculoRepository;
        @Autowired
        private DireccionRepository direccionRepository;
        @Autowired
        private TrabajoRealizadoRepository trabajoRealizadoRepository;
        @Autowired
        private ModelMapper modelMapper;



        public String crearRepartidor(String correo) {
                List<Usuario> usuariosDelSistema = usuarioRepository.findAll();

                // Extraer los ids de los usuarios
                List<Integer> idsDeUsuarios = usuariosDelSistema.stream()
                                .map(Usuario::getIdUsuario)                                                            
                                .collect(Collectors.toList());

                // Obtener un índice aleatorio entre 0 y la lista de idsDeUsuarios.size() - 1
                int indiceAleatorio = new Random().nextInt(idsDeUsuarios.size());
                // Obtener un índice aleatorio entre 0 y la lista de idsDeUsuarios.size() - 1
                int indiceAleatorio2 = new Random().nextInt(idsDeUsuarios.size());

                // Obtener el idUsuario aleatorio
                Integer idUsuarioAleatorio = idsDeUsuarios.get(indiceAleatorio);

                // Obtener un nombre aleatorio del usuario
                Usuario usuarioNombre = usuarioRepository.findById(idUsuarioAleatorio)                                                                                       
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "No se encontró el usuario"));
                String nombreRepartidor = usuarioNombre.getNombre();

                // 5. Obtener un apellido aleatorio del usuario numeroAleatorio
                Usuario usuarioApellido = usuarioRepository.findById(indiceAleatorio2) 
                                                                                      
                                                                                         
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "No se encontró el usuario "));
                String apellidoRepartidor = usuarioApellido.getApellido();

                // 6. Crear un nuevo usuario repartidor con el nombre y apellido aleatorio
                Usuario nuevoRepartidor = new Usuario();
                nuevoRepartidor.setNombre(nombreRepartidor);
                nuevoRepartidor.setApellido(apellidoRepartidor);
                nuevoRepartidor.setCorreo("repartidor_" + System.currentTimeMillis() + "@gmail.com"); // Crear correo
                                                                                                      // único
                nuevoRepartidor.setContrasenia("repartidor_" + System.currentTimeMillis());
                nuevoRepartidor.setTelefono("+504-" + System.currentTimeMillis());

                Optional<Rol> rol = Optional.ofNullable(rolRepository.findByRol("REPARTIDOR")
                                .orElseGet(() -> {
                                        Rol nuevoRol = new Rol();
                                        nuevoRol.setRol("DISPONIBLE");
                                        return rolRepository.save(nuevoRol); // Guarda el nuevo estado en la base de
                                                                             // datos
                                }));

                nuevoRepartidor.setRol(rol.get());

                Optional<Estado> estadoDisponible = Optional.ofNullable(estadoRepository.findByEstado("DISPONIBLE")
                                .orElseGet(() -> {
                                        Estado nuevoEstado = new Estado();
                                        nuevoEstado.setEstado("DISPONIBLE");
                                        return estadoRepository.save(nuevoEstado); // Guarda el nuevo estado en la base
                                                                                   // de datos
                                }));

                nuevoRepartidor.setEstado(estadoDisponible.get()); // Asignar el estado DISPONIBLE al repartidor

                // 7. Guardar al nuevo repartidor en la base de datos
                usuarioRepository.save(nuevoRepartidor);

                vehiculoService.guardarVehiculo(nuevoRepartidor);
                // 12. Retornar un mensaje de éxito
                return "Repartidor creado con nombre aleatorio y asignado al pedido con éxito";
        }

        public String tiempoDeEspera(String correo) {

                // 1. Buscar al usuario comprador
                Usuario comprador = usuarioRepository.findByCorreo(correo)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Usuario no encontrado"));

                // 2. Buscar el estado "EN PROCESO"
                Estado estadoPendiente = estadoRepository.findByEstado("PENDIENTE")
                                .orElseThrow(
                                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                "Estado 'PENDIENTE' no encontrado"));

                // 3. Buscar el último pedido del comprador con estado "EN PROCESO"
                Pedido pedido = pedidoRepository
                                .findTopByCompradorAndEstadoOrderByFechaPedidoDesc(comprador, estadoPendiente)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "No hay pedidos PENDIENTES"));

                Optional<Direccion> direccionComprador = direccionRepository.findFirstByUsuario(comprador);
                Optional<Direccion> direccionEmpresa = direccionRepository.findByEmpresa(pedido.getEmpresa());

                double distancia = calcularDistancia(direccionComprador.get().getUbicacion(),
                                direccionEmpresa.get().getUbicacion());

                // Obtener el repartidor del pedido
                Usuario repartidor = pedido.getRepartidor();

                // Obtener el vehículo del repartidor
                Vehiculo vehiculo = vehiculoRepository.findByRepartidor(repartidor);

                // Calcular el tiempo de espera en horas
                double tiempoEsperaHoras = distancia / vehiculo.getVelocidad();

                // Convertir el tiempo de espera a minutos
                double tiempoEsperaMinutos = tiempoEsperaHoras * 60;

                // Convertir el tiempo de espera a segundos
                double tiempoEsperaSegundos = tiempoEsperaMinutos * 60;

                // Imprimir el resultado en consola (si es necesario para depuración)
                System.out.println("Tiempo de espera en segundos: " + tiempoEsperaSegundos);

                // Devolver el mensaje con el tiempo de espera en minutos y segundos
                return "La Distancia del Cliente a la empresa es de: " + distancia
                                + "km.\n La velocidad del vehiculo del Repartidor es de: " + vehiculo.getVelocidad()
                                + "km/h. \nEste es el tiempo de Espera: "
                                + String.format("%.2f", tiempoEsperaMinutos) + " minutos ";

        }

        /**
         * Método para calcular la distancia entre dos puntos geográficos.
         *
         * @param ubicacionUsuario Ubicación del usuario.
         * @param ubicacionEmpresa Ubicación de la empresa.
         * @return Distancia en kilómetros entre los dos puntos.
         */
        private double calcularDistancia(Point ubicacionUsuario, Point ubicacionEmpresa) {
                // Implementa aquí la lógica para calcular la distancia entre dos puntos
                // geográficos.
                // Puedes usar la fórmula de Haversine o alguna otra fórmula adecuada.

                // Ejemplo de implementación (reemplaza esto con tu lógica real):
                double lat1 = ubicacionUsuario.getY();
                double lon1 = ubicacionUsuario.getX();
                double lat2 = ubicacionEmpresa.getY();
                double lon2 = ubicacionEmpresa.getX();

                // Convertir grados a radianes
                lat1 = Math.toRadians(lat1);
                lon1 = Math.toRadians(lon1);
                lat2 = Math.toRadians(lat2);
                lon2 = Math.toRadians(lon2);

                // Diferencia de latitud y longitud
                double dlat = lat2 - lat1;
                double dlon = lon2 - lon1;

                // Fórmula de Haversine
                double a = Math.pow(Math.sin(dlat / 2), 2)
                                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

                // Radio de la Tierra en kilómetros (aproximadamente)
                double radioTierra = 6371;

                // Calcular la distancia
                return radioTierra * c;
        }

        public String esAdmin(String correo) {
                // Buscar al usuario por correo
                Usuario usuario = usuarioRepository.findByCorreo(correo)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Usuario no encontrado"));

                // Verificar si el rol del usuario es "ADMIN"
                if (usuario.getRol().getRol().equalsIgnoreCase("ADMINISTRADOR")) {
                        return "ADMIN";
                } else {
                        return "El usuario no es administrador.";
                }
        }


        public List<UsuarioDTO> obtenerUsuarios() {
                List<Usuario> usuarios = usuarioRepository.findAll();
                List<UsuarioDTO> usuariosDTO =   usuarios.stream()
                        .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                        .collect(Collectors.toList());
                        return usuariosDTO;
        }

        public String eliminarUsuario(int idUsuario) {
                Usuario usuarioAEliminar = usuarioRepository.findById(idUsuario)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Usuario no encontrado"));

                usuarioRepository.delete(usuarioAEliminar);

                return "Usuario eliminado con éxito";
        }

        public String editarUsuario(UsuarioDTO usuarioDTO) {
                Usuario usuarioSeleccionado = usuarioRepository.findById(usuarioDTO.getIdUsuario())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Usuario no encontrado"));

                usuarioSeleccionado.setNombre(usuarioDTO.getNombre());
                usuarioSeleccionado.setApellido(usuarioDTO.getApellido());
                usuarioSeleccionado.setCorreo(usuarioDTO.getCorreo());
                Rol nuevoRol = this.modelMapper.map(usuarioDTO.getRol(),Rol.class);
                usuarioSeleccionado.setRol(nuevoRol);
                Estado nuevoEstado = this.modelMapper.map(usuarioDTO.getEstado(), Estado.class);
                usuarioSeleccionado.setEstado(nuevoEstado);

                usuarioRepository.save(usuarioSeleccionado);

                return "Usuario editado con éxito";
        }

        public List<UsuarioDTO> obtenerRepartidores() {
                List<Usuario> usuarios = usuarioRepository.findAll();
                List<UsuarioDTO> repartidoresDTO = usuarios.stream()
                        .filter(usuario -> usuario.getRol().getRol().equalsIgnoreCase("REPARTIDOR"))
                        .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                        .collect(Collectors.toList());
                return repartidoresDTO;
        }
        
        public List<TrabajoRealizadoDTO> historialRepartidor(int idUsuario) {
                Usuario repartidorSeleccionado = usuarioRepository.findById(idUsuario)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Repartidor no encontrado"));

                // Obtener todos los trabajos realizados por el repartidor
                List<TrabajoRealizado> trabajosRealizados = trabajoRealizadoRepository.findByRepartidor(repartidorSeleccionado);

                // Convertir la lista de trabajos realizados a DTOs
                List<TrabajoRealizadoDTO> trabajosRealizadosDTO = trabajosRealizados.stream()
                                .map(trabajo -> modelMapper.map(trabajo, TrabajoRealizadoDTO.class))
                                .collect(Collectors.toList());

                return trabajosRealizadosDTO;
        }
}
