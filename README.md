# Mini iFood - Atividade 02 

Projeto Spring Boot com domínio inspirado no iFood, usando JPA, DTOs, validação, relacionamentos avançados e auditoria.
Projeto criado para a disciplina de Desenvolvimento Web II

## Como rodar no Windows

.\mvnw.cmd spring-boot:run

Depois acesse a interface abrindo o arquivo:

index.html

A API roda em:

http://localhost:8080
Mas, como não há página inicial, dará uma mensagem de erro. Desse modo, você pode acessar com:
http://localhost:8080/clientes
http://localhost:8080/restaurantes
http://localhost:8080/produtos
http://localhost:8080/pedidos
Ou,
Abrindo o index.html


## Bancos H2

O projeto usa duas bases H2:

- Base A / PRINCIPAL:** `jdbc:h2:file:./data/principal_db`
- Base B / AUDITORIA:** `jdbc:h2:file:./data/audit_db`

Console H2:
http://localhost:8080/h2-console

Usuário: `sa`  
Senha: deixar em branco.

## Domínio
O domínio escolhido é um sistema de delivery inspirado no iFood, com:
- Cliente
- Restaurante
- Produto
- Pedido
- Log de Auditoria

## Relacionamentos
- Cliente 1:N Pedido
- Restaurante 1:N Produto
- Pedido N:N Produto

## Cascata
- Cliente -> Pedido: `CascadeType.ALL`
- Restaurante -> Produto: `CascadeType.ALL`
- Pedido -> Produto: `CascadeType.PERSIST` e `CascadeType.MERGE`

## Auditoria
Ao criar, atualizar ou excluir registros principais, o sistema registra logs na base de auditoria.

Tabela de auditoria:
LOGS_AUDITORIA

## Queries 
- JPQL: busca de cliente por nome e pedido por status
- SQL nativo: busca de restaurante por categoria e produto por preço mínimo
- JOIN FETCH: busca de pedido com produtos e produtos com restaurante

## Observação
A pasta `target/` deve ser gerada automaticamente pelo Maven.
