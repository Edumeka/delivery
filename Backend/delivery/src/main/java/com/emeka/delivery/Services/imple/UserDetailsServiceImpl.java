package com.emeka.delivery.Services.imple;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emeka.delivery.models.Rol;
import com.emeka.delivery.models.Usuario;
import com.emeka.delivery.Repositories.UsuarioRepository;
import jakarta.transaction.Transactional;


@Service("userDetailService")
@Transactional
public class UserDetailsServiceImpl  implements UserDetailsService {
     @Autowired
    private UsuarioRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario user = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado!"));
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        Rol rol = user.getRol(); // Obtiene un solo Rol

        if (rol != null) {
            roles.add(new SimpleGrantedAuthority(rol.getRol())); // Añade el Rol a la lista
        }

        return new User(user.getCorreo() , user.getContrasenia(), roles);
    }
}
