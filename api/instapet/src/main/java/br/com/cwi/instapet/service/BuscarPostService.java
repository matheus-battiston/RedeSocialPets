package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class BuscarPostService {
    public static final String NAO_ENCONTRADO = "Post nao encontrado";

    @Autowired
    private PostRepository postRepository;

    public Post porId(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, NAO_ENCONTRADO));
    }
}
