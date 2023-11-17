package com.nobblecrafts.challenge.dataaccess.contato.adapter;

import com.nobblecrafts.challenge.dataaccess.contato.entity.ContatoJpaEntity;
import com.nobblecrafts.challenge.dataaccess.contato.mapper.ContatoDaoMapper;
import com.nobblecrafts.challenge.dataaccess.contato.repository.ContatoJpaRepository;
import com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity;
import com.nobblecrafts.challenge.domain.core.exception.EntityNotFoundException;
import com.nobblecrafts.challenge.domain.repository.ContatoRepository;
import com.nobblecrafts.challenge.domain.service.dto.CreateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContatoDaoRepository implements ContatoRepository {

    private final ContatoJpaRepository repository;
    private final ContatoDaoMapper mapper;

    // Este método poderia ter sido resolvido tranquilamente com a CriteriaApi ou o JHipster
    // Porém, como é só uma query simples eu optei por fazer isso manualmente. Demora menos
    // e gasta menos processamento
    @Override
    public Set<FullContatoResponse> getContatos(String q, Set<String> fields) {
        log.info("\n\nWhat a hell? {}\n\n", q);
        Iterable<ContatoJpaEntity> entities = q != null ? repository.findByNomeContainingOrContatoContaining(q, q) : repository.findAll();
        return mapper.fromSpecifiedFieldsToFullContatoResponse(entities, fields);
    }

    @Override
    public Optional<FullContatoResponse> getContatoById(Long id) {
        return repository.findById(id)
                .map(mapper::toFullContatoResponse);
    }

    /**
     * Eu só fiz o que foi pedido. nesse caso o contato nunca vai ser associado
     * ao profissional em sua criação.
     * @param contato
     * @return
     */
    @Override
    public FullContatoResponse createContato(CreateContatoRequest contato) {
        var tosave = mapper.toContatoJpaEntity(contato);
        log.info("\n\nSAVING THE CONTATO: {}\n\n", tosave);
        return mapper.toFullContatoResponse(repository.save(tosave));
    }

    @Override
    public void updateContato(Long id, UpdateContatoRequest record) {
        Optional<ContatoJpaEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            log.info("Contato com ID '{}' não encontrado", id);
            throw new EntityNotFoundException(String.format("O contato %d não pode ser encontrado", id));
        }

        ContatoJpaEntity entity = optional.get();
        mapper.updateEntity(record, entity);
        repository.save(entity);
    }


    @Override
    public void deleteContato(Long id) {
        if (!repository.existsById(id)) {
            log.info("Contato com ID '{}' não encontrado", id);
            throw new EntityNotFoundException(String.format("O contato %d não pode ser encontrado", id));
        }

        repository.deleteById(id);
    }

}
