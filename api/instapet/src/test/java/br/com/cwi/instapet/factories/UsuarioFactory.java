package br.com.cwi.instapet.factories;

import br.com.cwi.instapet.security.domain.Usuario;

import java.time.LocalDate;

public class UsuarioFactory {
    public static Usuario get(Long id) {
        Usuario usuario = new Usuario();
        usuario.setApelido("Apelido" + id);
        usuario.setNome("Nome" + id);;
        usuario.setId(id);
        usuario.setEmail("matheus.battiston@cwi.com.br");
        usuario.setUrlFotoPerfil("URL_FOTO_PERFIL");
        usuario.setDataNascimento(LocalDate.of(1997,9,17));
        return usuario;
    }
}
