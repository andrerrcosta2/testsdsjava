package com.nobblecrafts.challenge.app.rest;

import com.nobblecrafts.challenge.domain.service.ProfissionalService;
import com.nobblecrafts.challenge.domain.service.dto.CreateProfissionalRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateProfissionalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import io.swagger.annotations.*;

@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
@Api(value = "Profissional Controller", tags = {"Profissional"})
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @ApiOperation(value = "Get Profissionais", notes = "Lista de profissionais com base nos critérios definidos em Params")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FullProfissionalResponse.class, responseContainer = "Set")
    })
    @GetMapping
    public ResponseEntity<Set<FullProfissionalResponse>> getProfissionais(
            @ApiParam(value = "Filtro para buscar profissionais que contenham o texto em qualquer um de seus atributos")
            @RequestParam(required = false) String q,
            @ApiParam(value = "Quando presente apenas os campos listados em fields deverão ser retornados", allowMultiple = true)
            @RequestParam(required = false) List<String> fields) {
        Set<FullProfissionalResponse> profissionais = profissionalService.getProfissionais(q, fields);
        return ResponseEntity.ok(profissionais);
    }

    @ApiOperation(value = "Get Profissional by ID", notes = "Todos os dados do profissional que possui o ID passado na URL")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FullProfissionalResponse.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FullProfissionalResponse> getProfissionalById(
            @ApiParam(value = "ID do Profissional", required = true)
            @PathVariable Long id) {
        FullProfissionalResponse profissional = profissionalService.getProfissionalById(id);
        return ResponseEntity.ok(profissional);
    }

    @ApiOperation(value = "Create Profissional", notes = "Insere no banco de dados os dados do profissional enviados via body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping
    public ResponseEntity<String> createProfissional(
            @ApiParam(value = "Dados do Profissional a serem criados", required = true)
            @RequestBody CreateProfissionalRequest profissional) {
        Long profissionalId = profissionalService.createProfissional(profissional);
        return ResponseEntity.ok("Sucesso: profissional com id " + profissionalId + " cadastrado");
    }

    @ApiOperation(value = "Update Profissional", notes = "Atualiza os dados do profissional que possui o ID passado via URL com os dados enviados no Body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfissional(
            @ApiParam(value = "ID do Profissional a ser atualizado", required = true)
            @PathVariable Long id,
            @ApiParam(value = "Dados do Profissional a serem atualizados", required = true)
            @RequestBody UpdateProfissionalRequest profissional) {
        profissionalService.updateProfissional(id, profissional);
        return ResponseEntity.ok("Sucesso: cadastro alterado");
    }

    @ApiOperation(value = "Delete Profissional", notes = "Exclui o profissional que possui o ID passado na URL. Importante! Este método deve realizar uma exclusão lógica do registro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfissional(
            @ApiParam(value = "ID do Profissional a ser excluído", required = true)
            @PathVariable Long id) {
        profissionalService.deleteProfissional(id);
        return ResponseEntity.ok("Sucesso: profissional excluído");
    }
}
