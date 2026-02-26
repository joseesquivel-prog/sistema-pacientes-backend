package com.gine.p.controller;

import com.gine.p.dto.AuthRequest;
import com.gine.p.dto.AuthResponse;
import com.gine.p.model.Usuario;
import com.gine.p.security.JwtService;
import com.gine.p.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        UserDetails user = usuarioService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        
        Usuario usuario = usuarioService.buscarPorUsername(request.getUsername());
        
        return ResponseEntity.ok(new AuthResponse(token, usuario.getUsername(), usuario.getRol()));
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));
    }
}