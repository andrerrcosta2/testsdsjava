package com.nobblecrafts.challenge.dataaccess.profissional.adapter;

import com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity;
import com.nobblecrafts.challenge.dataaccess.profissional.mapper.ProfissionalDaoMapper;
import com.nobblecrafts.challenge.dataaccess.profissional.repository.ProfissionalJpaRepository;
import com.nobblecrafts.challenge.domain.core.exception.EntityNotFoundException;
import com.nobblecrafts.challenge.domain.repository.ProfessionalRepository;
import com.nobblecrafts.challenge.domain.service.dto.CreateProfissionalRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateProfissionalRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfissionalDaoRepository implements ProfessionalRepository {

    private final ProfissionalJpaRepository repository;
    private final ProfissionalDaoMapper mapper;

    // Este método poderia ter sido resolvido tranquilamente com a CriteriaApi ou o JHipster
    // Porém, como é só uma query simples eu optei por fazer isso manualmente. Demora menos
    // e gasta menos processamento
    @Override
    public Set<FullProfissionalResponse> getProfissionais(String q, Set<String> fields) {
        Iterable<ProfissionalJpaEntity> entities = q != null ?
                repository.findByNomeContainingOrCargoContaining(q, q) :
                repository.findAll();
        return mapper.fromSpecifiedFieldsToFullProfissionalResponse(entities, fields);

    }

    @Override
    public Optional<FullProfissionalResponse> getProfissionalById(Long id) {
        return repository.findById(id)
                .map(mapper::toFullProfissionalResponse);
    }

    @Override
    public FullProfissionalResponse createProfissional(CreateProfissionalRequest profissional) {
        return mapper.toFullProfissionalResponse(repository.save(mapper.toProfissionalJpaEntity(profissional)));
    }

    @Override
    public void updateProfissional(Long id, UpdateProfissionalRequest profissional) {
        Optional<ProfissionalJpaEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            log.info("Profissional com ID '{}' não encontrado", id);
            throw new EntityNotFoundException(String.format("O profissional '%d' não pode ser encontrado", id));
        }

        ProfissionalJpaEntity entity = optional.get();
        mapper.updateEntity(profissional, entity);
        repository.save(entity);
    }

    @Override
    public void deleteProfissional(Long id) {
        if (!repository.existsById(id)) {
            log.info("Profissional com ID '{}' não encontrado", id);
            throw new EntityNotFoundException(String.format("O profissional '%d' não pode ser encontrado", id));
        }

        repository.deleteById(id);
    }
}
