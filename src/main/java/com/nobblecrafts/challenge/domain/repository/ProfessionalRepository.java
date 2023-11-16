package com.nobblecrafts.challenge.domain.repository;

import com.nobblecrafts.challenge.domain.service.dto.*;

import java.util.Optional;
import java.util.Set;

public interface ProfessionalRepository {
    Set<FullProfissionalResponse> getProfissionais(String q, Set<String> fields);

    Optional<FullProfissionalResponse> getProfissionalById(Long id);

    FullProfissionalResponse createProfissional(CreateProfissionalRequest profissional);

    void updateProfissional(Long id, UpdateProfissionalRequest profissional);

    void deleteProfissional(Long id);
}
