# Instapet

API para uma rede social voltada a pets
## Funcionalidades

- Incluir usuario: `POST /usuarios`
- Fazer um post: `POST /posts/postar`
- Login: `POST /login`
- Logout: `PUT /logout`
- Detalhes usuario logado: `GET /usuarios/me`
- Editar usuario : `PUT /usuarios/me/editar`
- Curtir um post : `POST /posts/curtir/{idPost}`
- Remover curtida : `POST /posts/remover-curtida/{idPost}`
- Pedido de amizade : `POST /usuarios/nova-amizade/{idUsuario}`
- Aceitar amizade : `PUT /usuarios/aceitar-amizade/{idPedidoAmizade}`
- Recusar amizade: `PUT /usuarios/recusar-amizade/{idPedidoAmizade}`
- Pesquisar usuarios que nao sao amigos : `GET /usuarios/buscar-usuarios`
- Listar amigos : `GET /usuarios/listar-amigos`
- listar pedidos de amizade : `GET /pedidos-amizade-pendente`
- Comentar post : `POST /comentarios/comentar/{idPost}`
- Listar comentarios: `GET /comentarios/listar/{idUsuario}`
- Listar posts de amigos: `GET /posts/listar-posts-home`
- Desfazer amizade: `POST usuarios/desfazer-amizade/{idUsuario}`
- Listar posts de um usuario: `POST usuarios/2/posts` 


## Dados

### Schema

```
    Arquivo em /data/schema.sql
```


