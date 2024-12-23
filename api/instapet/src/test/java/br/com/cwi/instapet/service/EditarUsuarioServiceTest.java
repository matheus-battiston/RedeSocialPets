package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.request.EditarUsuarioRequest;
import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.mapper.DetalharUsuarioMapper;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class EditarUsuarioServiceTest {
    @InjectMocks
    private EditarUsuarioService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve editar um usuario")
    void editarUsuario(){
        Long usuarioId = 1L;
        String apelido = "Apelido";
        String setNome = "Nome";
        String URL = "URL_FOTO_PERFIL";
        Usuario usuario = UsuarioFactory.get(usuarioId);

        Usuario usuarioEsperado = UsuarioFactory.get(usuarioId);
        usuarioEsperado.setApelido(apelido);
        usuarioEsperado.setNome(setNome);
        usuarioEsperado.setUrlFotoPerfil(URL);

        DetalhesUsuarioResponse responseEsperado = DetalharUsuarioMapper.toResponse(usuarioEsperado);

        EditarUsuarioRequest request = new EditarUsuarioRequest();
        request.setApelido(apelido);
        request.setNome(setNome);
        request.setUrlFotoPerfil(URL);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);

        DetalhesUsuarioResponse response = tested.editar(usuarioId, request);

        verify(usuarioRepository).save(usuarioEsperado);
        assertEquals(responseEsperado.getApelido(), response.getApelido());
        assertEquals(responseEsperado.getNome(), response.getNome());
        assertEquals(responseEsperado.getUrlFotoPerfil(), response.getUrlFotoPerfil());
    }

    @Test
    @DisplayName("Deve editar um usuario sem alteração de apelido")
    void editarUsuarioSemApelido(){
        Long usuarioId = 1L;
        String setNome = "Nome";
        String URL = "URL_FOTO_PERFIL";
        Usuario usuario = UsuarioFactory.get(usuarioId);

        Usuario usuarioEsperado = UsuarioFactory.get(usuarioId);
        usuarioEsperado.setNome(setNome);
        usuarioEsperado.setUrlFotoPerfil(URL);

        DetalhesUsuarioResponse responseEsperado = DetalharUsuarioMapper.toResponse(usuarioEsperado);

        EditarUsuarioRequest request = new EditarUsuarioRequest();
        request.setNome(setNome);
        request.setUrlFotoPerfil(URL);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);

        DetalhesUsuarioResponse response = tested.editar(usuarioId, request);

        verify(usuarioRepository).save(usuarioEsperado);
        assertEquals(responseEsperado.getApelido(), response.getApelido());
        assertEquals(responseEsperado.getNome(), response.getNome());
        assertEquals(responseEsperado.getUrlFotoPerfil(), response.getUrlFotoPerfil());
    }

    @Test
    @DisplayName("Nao deve editar usuario inexistente")
    void naoAlterarUsuarioInexistente(){
        Long idUsuario = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(idUsuario);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.editar(idUsuario, new EditarUsuarioRequest());
        });

        verify(usuarioRepository, never()).save(any());
    }
}
