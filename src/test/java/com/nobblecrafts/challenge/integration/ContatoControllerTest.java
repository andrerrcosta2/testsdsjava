package com.nobblecrafts.challenge.integration;

import com.nobblecrafts.challenge.AbstractNameOrderedIntegrationTest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;
import com.nobblecrafts.challenge.util.DtoSupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
//@ActiveProfiles("test")
class ContatoControllerTest extends AbstractNameOrderedIntegrationTest {

    private static final String URL = "/contatos/";

    /**
     * Não vou fazer todos os testes porque estou sem tempo
     */
    @Test
    void A00_criar_contato_corretamente() {
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(DtoSupplier.createContatoRequest().build()),
                String.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void A01_retornar_contatos_corretamente() {
        //criando 2 contatos

        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(DtoSupplier.createContatoRequest().build()),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        ResponseEntity<String> response2 = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(DtoSupplier.createContatoRequest().build()),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        ResponseEntity<Set<FullContatoResponse>> response3 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullContatoResponse>>() {});

        log.info("\n\nContatos returned: {}\n\n", response3.getBody());

//        assertEquals(response3.getBody().contains(response.getBody()));

    }

    @Test
    void A02_retornar_contatos_corretamente_com_parametros() {
        //criando 2 contatos

        var contato1 = DtoSupplier.createContatoRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(contato1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        var contato2 = DtoSupplier.createContatoRequest().build();

        ResponseEntity<String> response2 = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(contato2),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("q", contato1.nome())
                .queryParam("fields", "nome", "contato", "id");

        ResponseEntity<Set<FullContatoResponse>> response3 = rest.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullContatoResponse>>() {});

        log.info("\n\nContatos returned: {}\n\n", response3.getBody());

    }

    @Test
    void A03_retornar_contato_por_id() {
        //criando 2 contatos

        var contato1 = DtoSupplier.createContatoRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(contato1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        var contato2 = DtoSupplier.createContatoRequest().build();

        ResponseEntity<String> response2 = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(contato2),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        // recebendo todos os contatos
        ResponseEntity<Set<FullContatoResponse>> response3 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullContatoResponse>>() {});

        // pegando 1 contato para pesquisar por id:
        FullContatoResponse contatoParaPesquisa = response3.getBody().iterator().next();

        log.info("\n\nRANDOM CONTATO: {}\n\n", contatoParaPesquisa);

        ResponseEntity<FullContatoResponse> response4 = rest.exchange(URL + contatoParaPesquisa.id(),
                HttpMethod.GET,
                null,
                FullContatoResponse.class);

        log.info("\n\nContatos returned: {}\n\n", response4.getBody());

    }

    @Test
    void A04_atualizando_contato() {
        //criando 1 contato

        var contato1 = DtoSupplier.createContatoRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(contato1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        // recebendo todos os contatos
        ResponseEntity<Set<FullContatoResponse>> response1 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullContatoResponse>>() {});

        // pegando 1 contato para atualizar
        FullContatoResponse contatoParaAtualizar = response1.getBody().iterator().next();

        log.info("\n\nContato a ser atualizado: {}\n\n", contatoParaAtualizar);

        UpdateContatoRequest atualizacao = DtoSupplier.updateContatoRequest()
                .contato(null)
                .build();

        log.info("\n\nAtualização: {}\n\n", atualizacao);

        ResponseEntity<String> response2 = rest.exchange(URL + contatoParaAtualizar.id(),
                HttpMethod.PUT,
                new HttpEntity<>(atualizacao),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        // recebendo a entidade atualizada para verificar as alterações
        ResponseEntity<FullContatoResponse> response3 = rest.exchange(URL + contatoParaAtualizar.id(),
                HttpMethod.GET,
                null,
                FullContatoResponse.class);

        log.info("\n\nApós atualização: {}\n\n", response3.getBody());
    }

    @Test
    void A05_removendo_contato() {
        //criando 1 contato

        var contato1 = DtoSupplier.createContatoRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(contato1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        // recebendo todos os contatos
        ResponseEntity<Set<FullContatoResponse>> response1 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullContatoResponse>>() {});

        // pegando 1 contato para deletar
        FullContatoResponse contatoParaDeletar = response1.getBody().iterator().next();

        log.info("\n\nContato a ser deletado: {}\n\n", contatoParaDeletar);

        ResponseEntity<String> response2 = rest.exchange(URL + contatoParaDeletar.id(),
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        log.info("\n\nProcurando contato deletado!\n\n");

        // procurando o contato para garantir que foi deletado
        ResponseEntity<FullContatoResponse> response3 = rest.exchange(URL + contatoParaDeletar.id(),
                HttpMethod.GET,
                null,
                FullContatoResponse.class);

        log.info("\n\nApós deleção: {}\n\n", response3.getBody());
    }

}
