package com.emeka.delivery.Services;

import org.springframework.http.ResponseEntity;

import com.emeka.delivery.DTO.LoginDTO;
import com.emeka.delivery.DTO.RegisterDTO;
import com.emeka.delivery.DTO.UsuarioDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
     public UsuarioDTO register(RegisterDTO registerDto);

   public ResponseEntity<?> login(LoginDTO loginDto, HttpServletResponse response); 

   UsuarioDTO getLoguedUser(HttpServletRequest request);
}
