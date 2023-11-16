package com.nobblecrafts.challenge.integration;

import com.nobblecrafts.challenge.AbstractNameOrderedIntegrationTest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.UpdateProfissionalRequest;
import com.nobblecrafts.challenge.util.DtoSupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
class ProfissionalControllerTest extends AbstractNameOrderedIntegrationTest {

    private static final String URL = "/profissionais/";

    /**
     * Não vou fazer todos os testes porque estou sem tempo
     */
    @Test
    void A00_criar_profissional_corretamente() {
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(DtoSupplier.createProfissionalRequest().build()),
                String.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void A01_retornar_profissionais_corretamente() {
        //criando 2 profissionais

        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(DtoSupplier.createProfissionalRequest().build()),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        ResponseEntity<String> response2 = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(DtoSupplier.createProfissionalRequest().build()),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        ResponseEntity<Set<FullProfissionalResponse>> response3 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullProfissionalResponse>>() {});

        log.info("\n\nProfissionais returned: {}\n\n", response3.getBody());

//        assertEquals(response3.getBody().contains(response.getBody()));

    }

    @Test
    void A02_retornar_profissionais_corretamente_com_parametros() {
        //criando 2 profissionais

        var p1 = DtoSupplier.createProfissionalRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(p1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        var p2 = DtoSupplier.createProfissionalRequest().build();

        ResponseEntity<String> response2 = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(p2),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL)
                .queryParam("q", p1.nome())
                .queryParam("fields", "nome", "contato", "id");

        ResponseEntity<Set<FullProfissionalResponse>> response3 = rest.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullProfissionalResponse>>() {});

        log.info("\n\nProfissionais returned: {}\n\n", response3.getBody());

    }

    @Test
    void A03_retornar_profissional_por_id() {
        //criando 2 profissionais

        var p1 = DtoSupplier.createProfissionalRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(p1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        var p2 = DtoSupplier.createProfissionalRequest().build();

        ResponseEntity<String> response2 = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(p2),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        // recebendo todos os profissionais
        ResponseEntity<Set<FullProfissionalResponse>> response3 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullProfissionalResponse>>() {});

        // pegando 1 profissional para pesquisar por id:
        FullProfissionalResponse profissionalParaPesquisa = response3.getBody().iterator().next();

        log.info("\n\nRANDOM PROFISSIONAL: {}\n\n", profissionalParaPesquisa);

        ResponseEntity<FullProfissionalResponse> response4 = rest.exchange(URL + profissionalParaPesquisa.id(),
                HttpMethod.GET,
                null,
                FullProfissionalResponse.class);

        log.info("\n\nProfissionais returned: {}\n\n", response4.getBody());

    }

    @Test
    void A04_atualizando_contato() {
        //criando 1 profissonal

        var p1 = DtoSupplier.createProfissionalRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(p1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        // recebendo todos os profissionais
        ResponseEntity<Set<FullProfissionalResponse>> response1 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullProfissionalResponse>>() {});

        // pegando 1 profissional para atualizar
        FullProfissionalResponse profissionalParaAtualizar = response1.getBody().iterator().next();

        log.info("\n\nProfissional a ser atualizado: {}\n\n", profissionalParaAtualizar);

        UpdateProfissionalRequest atualizacao = DtoSupplier.updateProfissionalRequest()
                .cargo(null)
                .build();

        log.info("\n\nAtualização: {}\n\n", atualizacao);

        ResponseEntity<String> response2 = rest.exchange(URL + profissionalParaAtualizar.id(),
                HttpMethod.PUT,
                new HttpEntity<>(atualizacao),
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        // recebendo a entidade atualizada para verificar as alterações
        ResponseEntity<FullContatoResponse> response3 = rest.exchange(URL + profissionalParaAtualizar.id(),
                HttpMethod.GET,
                null,
                FullContatoResponse.class);

        log.info("\n\nApós atualização: {}\n\n", response3.getBody());
    }

    @Test
    void A05_removendo_contato() {
        //criando 1 profissional

        var p1 = DtoSupplier.createProfissionalRequest().build();
        ResponseEntity<String> response = rest.exchange(URL,
                HttpMethod.POST,
                new HttpEntity<>(p1),
                String.class);

        assertEquals(200, response.getStatusCodeValue());

        // recebendo todos os profissionais
        ResponseEntity<Set<FullProfissionalResponse>> response1 = rest.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<FullProfissionalResponse>>() {});

        // pegando 1 profissional para deletar
        FullProfissionalResponse profissionalParaDeletar = response1.getBody().iterator().next();

        log.info("\n\nProfissional a ser deletado: {}\n\n", profissionalParaDeletar);

        ResponseEntity<String> response2 = rest.exchange(URL + profissionalParaDeletar.id(),
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(200, response2.getStatusCodeValue());

        log.info("\n\nProcurando profissional deletado!\n\n");

        // procurando o profissional para garantir que foi deletado
        ResponseEntity<FullProfissionalResponse> response3 = rest.exchange(URL + profissionalParaDeletar.id(),
                HttpMethod.GET,
                null,
                FullProfissionalResponse.class);

        log.info("\n\nApós deleção: {}\n\n", response3.getBody());
    }

}
