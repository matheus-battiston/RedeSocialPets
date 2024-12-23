package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.request.ComentarioRequest;
import br.com.cwi.instapet.controller.response.ComentarioResponse;
import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.domain.Comentario;
import br.com.cwi.instapet.factories.ComentarioFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class ComentarioMapperTest {
    @Test
    @DisplayName("Deve transformar um request em entidade")
    void transformarRequestEmEntidade(){
        String textoComentario = "Comentario teste";
        ComentarioRequest request = new ComentarioRequest();
        request.setComentario(textoComentario);

        Comentario comentario = Comentario.builder()
                .comentario(textoComentario)
                .build();

        Comentario response = ComentarioMapper.toEntity(request);

        assertEquals(comentario, response);
    }

    @Test
    @DisplayName("Deve transformar uma entidade em um response")
    void transformarEntityEmResponse(){
        Long usuarioId = 1L;
        Long idComentario = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        DetalhesUsuarioResponse detalhesUsuarioResponse = DetalharUsuarioMapper.toResponse(usuario);
        Comentario comentario = ComentarioFactory.get(idComentario);
        comentario.setUsuario(usuario);

        ComentarioResponse esperado = ComentarioResponse.builder()
                .comentario(comentario.getComentario())
                .horario(comentario.getHorario())
                .usuario(detalhesUsuarioResponse)
                .build();

        ComentarioResponse response = ComentarioMapper.toResponse(comentario);

        assertEquals(response.getComentario(), esperado.getComentario());
        assertEquals(response.getHorario(), esperado.getHorario());
        assertEquals(response.getUsuario().getId(), esperado.getUsuario().getId());


    }
}
