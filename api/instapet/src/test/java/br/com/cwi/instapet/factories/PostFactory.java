package br.com.cwi.instapet.factories;

import br.com.cwi.instapet.domain.Post;

public class PostFactory {
    public static Post get(){
        Post post = new Post();
        post.setId(1L);
        post.setNumeroLikes(0);
        return post;
    }
}
