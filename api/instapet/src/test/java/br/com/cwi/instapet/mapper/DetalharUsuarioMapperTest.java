package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class DetalharUsuarioMapperTest {

    @Test
    @DisplayName("Deve transformar uma entidade em response")
    void transformarEmResponse(){
        Usuario usuario = UsuarioFactory.get(1L);
        DetalhesUsuarioResponse esperado = DetalhesUsuarioResponse.builder()
                .id(usuario.getId())
                .urlFotoPerfil(usuario.getUrlFotoPerfil())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .apelido(usuario.getApelido())
                .build();

        DetalhesUsuarioResponse response = DetalharUsuarioMapper.toResponse(usuario);

        Assertions.assertEquals(esperado.getId(), response.getId());
        Assertions.assertEquals(esperado.getApelido(), response.getApelido());
        Assertions.assertEquals(esperado.getEmail(), response.getEmail());
        Assertions.assertEquals(esperado.getUrlFotoPerfil(), response.getUrlFotoPerfil());
        Assertions.assertEquals(esperado.getNome(), response.getNome());
    }



}
