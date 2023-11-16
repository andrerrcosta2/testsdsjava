package com.nobblecrafts.challenge.domain.repository;

import com.nobblecrafts.challenge.domain.service.dto.CreateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;

import java.util.Optional;
import java.util.Set;

public interface ContatoRepository {
    Set<FullContatoResponse> getContatos(String q, Set<String> fields);

    Optional<FullContatoResponse> getContatoById(Long id);

    FullContatoResponse createContato(CreateContatoRequest contato);

    void updateContato(Long id, UpdateContatoRequest contato);

    void deleteContato(Long id);
}
