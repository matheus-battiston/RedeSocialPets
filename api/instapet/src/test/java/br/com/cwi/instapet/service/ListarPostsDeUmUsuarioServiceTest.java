package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesPostResponse;
import br.com.cwi.instapet.domain.PermissaoPost;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.PostFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ListarPostsDeUmUsuarioServiceTest {

    @InjectMocks
    private ListarPostsDeUmUsuarioService tested;
    @Mock
    private BuscarUsuarioService buscarUsuarioService;


    @Test
    @DisplayName("Deve listar todos os posts de um usuario se forem amigos")
    void listarTodos(){
        Long usuarioId = 1L;
        Long usuarioListadoId = 2L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario listado = UsuarioFactory.get(usuarioListadoId);
        usuario.getAmigos().add(listado);
        listado.getAmigos().add(usuario);
        Post postPrivado = PostFactory.get();
        postPrivado.setPermissao(PermissaoPost.PRIVADO);
        Post postPublico = PostFactory.get();
        postPublico.setId(2L);
        postPublico.setPermissao(PermissaoPost.PUBLICO);

        listado.getPosts().add(postPrivado);
        listado.getPosts().add(postPublico);


        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(buscarUsuarioService.porId(usuarioListadoId)).thenReturn(listado);

        List<DetalhesPostResponse> responseList = tested.listar(usuarioId, usuarioListadoId);

        Assertions.assertEquals(responseList.size(), 2);
        Assertions.assertEquals(responseList.get(0).getId(), postPrivado.getId());
        Assertions.assertEquals(responseList.get(1).getId(), postPublico.getId());
    }

    @Test
    @DisplayName("Deve listar um post por nao serem amigos")
    void listarApenasUm(){
        Long usuarioId = 1L;
        Long usuarioListadoId = 2L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario listado = UsuarioFactory.get(usuarioListadoId);

        Post postPrivado = PostFactory.get();
        postPrivado.setPermissao(PermissaoPost.PRIVADO);
        Post postPublico = PostFactory.get();
        postPublico.setId(2L);
        postPublico.setPermissao(PermissaoPost.PUBLICO);

        listado.getPosts().add(postPrivado);
        listado.getPosts().add(postPublico);


        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(buscarUsuarioService.porId(usuarioListadoId)).thenReturn(listado);

        List<DetalhesPostResponse> responseList = tested.listar(usuarioId, usuarioListadoId);

        Assertions.assertEquals(responseList.size(), 1);
        Assertions.assertEquals(responseList.get(0).getId(), postPublico.getId());


    }
}
