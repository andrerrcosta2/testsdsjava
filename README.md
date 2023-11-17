Para rodar o projeto:

1. mvn clean install
2. docker-compose-up

comentários:
* eu não estou muito certo do porque está tendo problemas de conexão entre a aplicação
e o postgres quando os dois serviços estão containerizados. só está conectando aqui quando eu uso o
postgres no docker e a aplicação pelo java local. o problema é que é a primeira vez que eu uso
o docker no windows. eu não sei se tem alguma coisa a ver. mas está tendo um conflito entre
localhost e <container-name>. o meu linux está a um mês estragado porque eu troquei o home
para um hd externo mas deu algum problema no meu computador que esse hd está falhando.
então não consigo programar no linux que é onde eu programo.
* pode ter ficado faltando algumas funcionalidades como validação de
data nos dtos, javax.validation em geral e controller-advice para exceções em geral.
Infelizmente eu não tive tempo.