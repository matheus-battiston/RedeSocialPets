package br.com.cwi.instapet.security.repository;

import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    @Query("select distinct u from Usuario u join fetch u.amigos amigos where (u != :usuario AND (u NOT IN :listaAmigos )  AND (u.email like CONCAT('%', :texto, '%') or u.nome like CONCAT('%', :texto, '%')))")
    List<Usuario> procurarUsuarioPorNomeOuEmail(@Param("usuario") Usuario usuario, @Param("listaAmigos") List<Usuario> amigos, @Param("texto") String texto);

    List<Usuario> findByEmailContainingIgnoreCaseOrNomeContainingIgnoreCase(String email, String nome);
}
