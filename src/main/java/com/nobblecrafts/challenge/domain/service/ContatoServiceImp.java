package com.nobblecrafts.challenge.domain.service;

import com.nobblecrafts.challenge.dataaccess.contato.adapter.ContatoDaoRepository;
import com.nobblecrafts.challenge.domain.core.exception.EntityNotFoundException;
import com.nobblecrafts.challenge.domain.service.dto.CreateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;
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
@Service("contato-service-imp")
@RequiredArgsConstructor
public class ContatoServiceImp implements ContatoService {

    private final ContatoDaoRepository repository;

    @Override
    @Transactional(readOnly = true)
    @OptimisticLockMode
    public Set<FullContatoResponse> getContatos(String q, List<String> fields) {
        HashSet<String> set = fields == null || fields.isEmpty() ? null : new HashSet<>(fields);
        return repository.getContatos(q, set);
    }

    @Override
    @Transactional(readOnly = true)
    @OptimisticLockMode
    public FullContatoResponse getContatoById(Long id) {
        Optional<FullContatoResponse> optional = repository.getContatoById(id);

        if (optional.isEmpty()) {
            log.info("Contato com ID '{}' não encontrado", id);
            throw new EntityNotFoundException(String.format("O contato %d não pode ser encontrado", id));
        }

        return optional.get();
    }

    @Override
    @Transactional
    @OptimisticLockMode
    public Long createContato(CreateContatoRequest contato) {
        try {
            FullContatoResponse createdContato = repository.createContato(contato);
            return createdContato.id();
        } catch (DataAccessException e) {
            log.error("Ocorreu um erro ao tentar salvar o contato: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar o contato", e);
        }
    }

    @Override
    @Transactional
    @OptimisticLockMode
    public void updateContato(Long id, UpdateContatoRequest contato) {
        try {
            repository.updateContato(id, contato);
        } catch (EntityNotFoundException e) {
            log.error("Contato não encontrado ao tentar atualizar: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Ocorreu um erro ao tentar atualizar o contato: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o contato", e);
        }
    }

    @Override
    @Transactional
    @OptimisticLockMode
    public void deleteContato(Long id) {
        try {
            repository.deleteContato(id);
        } catch (EntityNotFoundException e) {
            log.error("Contato não encontrado ao tentar deletar: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Ocorreu um erro ao tentar deletar o contato: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar o contato", e);
        }
    }
}
