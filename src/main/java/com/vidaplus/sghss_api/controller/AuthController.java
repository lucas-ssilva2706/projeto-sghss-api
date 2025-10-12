package com.vidaplus.sghss_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidaplus.sghss_api.dto.AuthDTO;
import com.vidaplus.sghss_api.dto.TokenDTO;
import com.vidaplus.sghss_api.dto.UsuarioDTO;
import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.service.AuthService;
import com.vidaplus.sghss_api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final UsuarioService usuarioService;

	public AuthController(AuthService authService, UsuarioService usuarioService) {
		this.authService = authService;
		this.usuarioService = usuarioService;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@Valid @RequestBody AuthDTO authDTO) {
		String token = authService.login(authDTO);
		return ResponseEntity.ok(new TokenDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity<Usuario> register(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		Usuario novoUsuario = usuarioService.criarUsuario(usuarioDTO);
		return ResponseEntity.ok(novoUsuario);
	}
}