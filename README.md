Para rodar o projeto:

1. mvn clean install
2. docker-compose-up

comentários:
* Acredito que agora esteja funcionando o docker da aplicação e a comunicação com o postgres.
meu windows está gerando a mesma imagem mesmo após remover os containers as imagens e os volumes
por isso não estou conseguindo testar.
Caso não funcione a aplicação completa com o docker-compose up, rode o sdtp-application manualmente
e rode apenas o postgres no docker: 

`docker-compose up postgres-sdtp-service`

* pode ter ficado faltando algumas funcionalidades como validação de
data nos dtos, javax.validation em geral e controller-advice para exceções em geral.
Infelizmente eu não tive tempo.