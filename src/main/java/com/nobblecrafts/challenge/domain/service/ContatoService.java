package com.nobblecrafts.challenge.domain.service;

import com.nobblecrafts.challenge.domain.service.dto.CreateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

public interface ContatoService {
    Set<FullContatoResponse> getContatos(String q, List<String> fields);

    FullContatoResponse getContatoById(Long id);

    Long createContato(@Valid CreateContatoRequest contato);

    void updateContato(Long id, UpdateContatoRequest contato);

    void deleteContato(Long id);
}
