package com.nobblecrafts.challenge.app.rest;

import com.nobblecrafts.challenge.domain.service.ContatoService;
import com.nobblecrafts.challenge.domain.service.dto.CreateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
@Api(value = "Contato Controller", tags = {"Contato"})
public class ContatoController {

    private final ContatoService contatoService;

    @ApiOperation(value = "Get Contatos", notes = "Lista de contatos com base nos critérios definidos em Params")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FullContatoResponse.class, responseContainer = "Set")
    })
    @GetMapping
    public ResponseEntity<Set<FullContatoResponse>> getContatos(
            @ApiParam(value = "Filtro para buscar contatos que contenham o texto em qualquer um de seus atributos")
            @RequestParam(required = false) String q,
            @ApiParam(value = "Quando presente apenas os campos listados em fields deverão ser retornados", allowMultiple = true)
            @RequestParam(required = false) List<String> fields) {
        Set<FullContatoResponse> contatos = contatoService.getContatos(q, fields);
        return ResponseEntity.ok(contatos);
    }

    @ApiOperation(value = "Get Contato by ID", notes = "Todos os dados do contato que possui o ID passado na URL")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FullContatoResponse.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FullContatoResponse> getContatoById(
            @ApiParam(value = "ID do Contato", required = true)
            @PathVariable Long id) {
        FullContatoResponse contato = contatoService.getContatoById(id);
        return ResponseEntity.ok(contato);
    }

    @ApiOperation(value = "Create Contato", notes = "Insere no banco de dados os dados do contato enviados via body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping
    public ResponseEntity<String> createContato(
            @ApiParam(value = "Dados do Contato a serem criados", required = true)
            @RequestBody @Valid CreateContatoRequest contato) {
        Long contatoId = contatoService.createContato(contato);
        return ResponseEntity.ok("Sucesso: contato com id " + contatoId + " cadastrado");
    }

    @ApiOperation(value = "Update Contato", notes = "Atualiza os dados do contato que possui o ID passado via URL com os dados enviados no Body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContato(
            @ApiParam(value = "ID do Contato a ser atualizado", required = true)
            @PathVariable Long id,
            @ApiParam(value = "Dados do Contato a serem atualizados", required = true)
            @RequestBody @Valid UpdateContatoRequest contato) {
        contatoService.updateContato(id, contato);
        return ResponseEntity.ok("Sucesso: cadastro alterado");
    }

    @ApiOperation(value = "Delete Contato", notes = "Exclui o contato que possui o ID passado na URL")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContato(
            @ApiParam(value = "ID do Contato a ser excluído", required = true)
            @PathVariable Long id) {
        contatoService.deleteContato(id);
        return ResponseEntity.ok("Sucesso: contato excluído");
    }
}
