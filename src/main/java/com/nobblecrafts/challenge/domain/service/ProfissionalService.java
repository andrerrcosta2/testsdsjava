package com.nobblecrafts.challenge.domain.service;

import com.nobblecrafts.challenge.domain.service.dto.CreateProfissionalRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateProfissionalRequest;

import java.util.List;
import java.util.Set;

public interface ProfissionalService {
    Set<FullProfissionalResponse> getProfissionais(String q, List<String> fields);

    FullProfissionalResponse getProfissionalById(Long id);

    Long createProfissional(CreateProfissionalRequest profissional);

    void updateProfissional(Long id, UpdateProfissionalRequest profissional);

    void deleteProfissional(Long id);
}
