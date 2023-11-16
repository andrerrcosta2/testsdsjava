package com.nobblecrafts.challenge.dataaccess.contato.mapper;

import com.nobblecrafts.challenge.dataaccess.contato.entity.ContatoJpaEntity;
import com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity;
import com.nobblecrafts.challenge.dataaccess.profissional.mapper.ProfissionalDaoMapper;
import com.nobblecrafts.challenge.domain.service.dto.CreateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ContatoDaoMapper {

    public static final ContatoDaoMapper INSTANCE = Mappers.getMapper(ContatoDaoMapper.class);

    @Named("toFullContatoResponse")
    @Mapping(target = "profissional", qualifiedByName = "profissionalToString")
    public abstract FullContatoResponse toFullContatoResponse(ContatoJpaEntity entity);

    @Named("profissionalToString")
    public String profissionalToString(ProfissionalJpaEntity profissional) {
        return profissional != null ? profissional.toString() : null;
    }

    @Named("toFullContatoResponseSet")
    @IterableMapping(qualifiedByName = "toFullContatoResponse")
    public abstract Set<FullContatoResponse> toFullContatoResponseSet(Set<ContatoJpaEntity> entities);

    public Set<FullContatoResponse> fromSpecifiedFieldsToFullContatoResponse(Iterable<ContatoJpaEntity> entities, Set<String> fields) {
        Set<FullContatoResponse> result = new HashSet<>();

        if (fields != null && !fields.isEmpty()) {
            for (ContatoJpaEntity entity : entities) {
                result.add(buildFullContatoResponseBySpecifiedFields(entity, fields));
            }
        } else {
            for (ContatoJpaEntity entity : entities) {
                result.add(toFullContatoResponse(entity));
            }
        }
        return result;
    }

    private FullContatoResponse buildFullContatoResponseBySpecifiedFields(ContatoJpaEntity entity, Set<String> fields) {
        ContatoJpaEntity holder = new ContatoJpaEntity();
        for (String field : fields) {
            switch (field) {
                case "id":
                    holder.setId(entity.getId());
                    break;
                case "nome":
                    holder.setNome(entity.getNome());
                    break;
                case "contato":
                    holder.setContato(entity.getContato());
                    break;
                case "profissional":
                    holder.setProfissional(entity.getProfissional());
                    break;
                default:
                    break;
            }
        }
        return toFullContatoResponse(holder);
    }

    public Set<FullContatoResponse> setMapToFullContatoResponseSet(Set<Map<String, Object>> entities) {
        return entities.stream()
                .map(this::toFullContatoResponse)
                .collect(Collectors.toSet());
    }

    private FullContatoResponse toFullContatoResponse(Map<String, Object> entity) {

        return FullContatoResponse.builder()
                .id((Long) entity.get("id"))
                .nome((String) entity.get("nome"))
                .contato((String) entity.get("contato"))
                .createdDate((Date) entity.get("createdDate"))
                .profissional(Optional.ofNullable((ProfissionalJpaEntity) entity.get("profissional"))
                        .map(ProfissionalJpaEntity::getNome)
                        .orElse(null))
                .build();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    public abstract ContatoJpaEntity toContatoJpaEntity(CreateContatoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract void updateEntity(UpdateContatoRequest request, @MappingTarget ContatoJpaEntity entity);

}
