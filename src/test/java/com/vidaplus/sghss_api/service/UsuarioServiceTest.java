package com.vidaplus.sghss_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vidaplus.sghss_api.dto.UsuarioDTO;
import com.vidaplus.sghss_api.dto.UsuarioResponseDTO;
import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.model.enums.TipoUsuario;
import com.vidaplus.sghss_api.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;
	
    @Mock 
    private PasswordEncoder passwordEncoder;
    
	@InjectMocks
	private UsuarioService usuarioService;

	@Test
	@DisplayName("Deve lançar exceção ao tentar criar usuário com e-mail duplicado")
	void usuarioEmailDuplicado() {

		UsuarioDTO dtoEmailDuplicado = new UsuarioDTO();
		dtoEmailDuplicado.setEmail("email@teste.com");
		dtoEmailDuplicado.setSenha("senha123");
		dtoEmailDuplicado.setTipoUsuario(TipoUsuario.PACIENTE);

		when(usuarioRepository.findByEmail("email@teste.com")).thenReturn(Optional.of(new Usuario()));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			usuarioService.criarUsuario(dtoEmailDuplicado);
		});

		assertEquals("Este e-mail já está em uso.", exception.getMessage());
	}

	@Test
	@DisplayName("Deve criar um usuário com sucesso quando o e-mail for novo")
	void usuarioEmailNovo() {

		UsuarioDTO dtoEmailNovo = new UsuarioDTO();
		dtoEmailNovo.setEmail("email.novo@teste.com");
		dtoEmailNovo.setSenha("123456");
		dtoEmailNovo.setTipoUsuario(TipoUsuario.PACIENTE);

		Usuario usuarioSalvo = new Usuario();
		usuarioSalvo.setId(1L);
		usuarioSalvo.setEmail("email.novo@teste.com");

		when(usuarioRepository.findByEmail("email.novo@teste.com")).thenReturn(Optional.empty());
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);
		
		when(passwordEncoder.encode(any(String.class))).thenReturn("senha_criptografada_mock");

		UsuarioResponseDTO resultado = usuarioService.criarUsuario(dtoEmailNovo);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("email.novo@teste.com", resultado.getEmail());
	}
}