package com.nobblecrafts.challenge.domain.service;

import com.nobblecrafts.challenge.dataaccess.profissional.adapter.ProfissionalDaoRepository;
import com.nobblecrafts.challenge.domain.core.exception.EntityNotFoundException;
import com.nobblecrafts.challenge.domain.service.dto.CreateProfissionalRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateProfissionalRequest;
import com.nobblecrafts.challenge.shared.data.transaction.OptimisticLockMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service("profissional-service-imp")
@RequiredArgsConstructor
public class ProfissionalServiceImp implements ProfissionalService {

    private final ProfissionalDaoRepository repository;

    @Transactional(readOnly = true)
    @Override
    @OptimisticLockMode
    public Set<FullProfissionalResponse> getProfissionais(String q, List<String> fields) {
        HashSet<String> set = fields == null || fields.isEmpty() ? null : new HashSet<>(fields);
        return repository.getProfissionais(q, set);
    }

    @Transactional(readOnly = true)
    @Override
    @OptimisticLockMode
    public FullProfissionalResponse getProfissionalById(Long id) {
        Optional<FullProfissionalResponse> optional = repository.getProfissionalById(id);

        if (optional.isEmpty()) {
            log.info("Profissional com ID '{}' não encontrado", id);
            throw new EntityNotFoundException(String.format("O profissional '%d' não pode ser encontrado", id));
        }

        return optional.get();
    }

    @Transactional
    @Override
    @OptimisticLockMode
    public Long createProfissional(CreateProfissionalRequest profissional) {
        try {
            FullProfissionalResponse createdProfissional = repository.createProfissional(profissional);
            return createdProfissional.id();
        } catch (DataAccessException e) {
            log.error("Ocorreu um erro ao tentar salvar o profissional: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar o profissional", e);
        }
    }

    @Transactional
    @Override
    @OptimisticLockMode
    public void updateProfissional(Long id, UpdateProfissionalRequest profissional) {
        try {
            repository.updateProfissional(id, profissional);
        } catch (EntityNotFoundException e) {
            log.error("Profissional não encontrado ao tentar atualizar: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Ocorreu um erro ao tentar atualizar o profissional: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o profissional", e);
        }
    }

    @Transactional
    @Override
    @OptimisticLockMode
    public void deleteProfissional(Long id) {
        try {
            repository.deleteProfissional(id);
        } catch (EntityNotFoundException e) {
            log.error("Profissional não encontrado ao tentar deletar: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Ocorreu um erro ao tentar deletar o profissional: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar o profissional", e);
        }
    }
}
