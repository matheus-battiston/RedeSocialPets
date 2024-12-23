package br.com.cwi.instapet.repository;

import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p where p.usuario IN :amigos OR p.usuario = :usuario")
    Page<Post> findTodosPostsDeAmigos(@Param("amigos") List<Usuario> amigos, @Param("usuario")Usuario usuario, Pageable pageable);
}
