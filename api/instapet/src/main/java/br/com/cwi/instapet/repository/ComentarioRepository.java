package br.com.cwi.instapet.repository;

import br.com.cwi.instapet.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
