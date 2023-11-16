package com.nobblecrafts.challenge.dataaccess.profissional.mapper;

import com.nobblecrafts.challenge.dataaccess.contato.entity.ContatoJpaEntity;
import com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity;
import com.nobblecrafts.challenge.domain.service.dto.CreateProfissionalRequest;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse;
import com.nobblecrafts.challenge.domain.service.dto.UpdateProfissionalRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProfissionalDaoMapper {

    public static final ProfissionalDaoMapper INSTANCE = Mappers.getMapper(ProfissionalDaoMapper.class);

    @Named("toFullProfissionalResponse")
    @Mapping(target = "contatos", qualifiedByName = "toFullContatoResponseSet")
    public abstract FullProfissionalResponse toFullProfissionalResponse(ProfissionalJpaEntity entity);

    @Named("toFullContatoResponseSet")
    @IterableMapping(qualifiedByName = "toFullContatoResponse")
    public abstract Set<FullContatoResponse> toFullContatoResponseSet(Set<ContatoJpaEntity> entities);

    @Named("toFullContatoResponse")
    @Mapping(target = "profissional", qualifiedByName = "profissionalToString")
    public abstract FullContatoResponse toFullContatoResponse(ContatoJpaEntity entity);

    @IterableMapping(qualifiedByName = "toFullProfissionalResponse")
    public abstract Set<FullProfissionalResponse> toFullProfissionalResponseSet(Set<ProfissionalJpaEntity> entities);

    @Named("profissionalToString")
    public String profissionalToString(ProfissionalJpaEntity profissional) {
        return profissional != null ? profissional.toString() : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract ProfissionalJpaEntity toProfissionalJpaEntity(CreateProfissionalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract ProfissionalJpaEntity updateEntity(UpdateProfissionalRequest request,
                                                       @MappingTarget ProfissionalJpaEntity entity);

    public Set<FullProfissionalResponse> fromSpecifiedFieldsToFullProfissionalResponse(Iterable<ProfissionalJpaEntity> entities, Set<String> fields) {
        Set<FullProfissionalResponse> result = new HashSet<>();

        if (fields != null && !fields.isEmpty()) {
            for (ProfissionalJpaEntity entity : entities) {
                result.add(buildFullProfissionalResponseBySpecifiedFields(entity, fields));
            }
        } else {
            for (ProfissionalJpaEntity entity : entities) {
                result.add(toFullProfissionalResponse(entity));
            }
        }
        return result;
    }

    private FullProfissionalResponse buildFullProfissionalResponseBySpecifiedFields(ProfissionalJpaEntity entity, Set<String> fields) {
        ProfissionalJpaEntity holder = new ProfissionalJpaEntity();
        for (String field : fields) {
            switch (field) {
                case "id":
                    holder.setId(entity.getId());
                    break;
                case "nome":
                    holder.setNome(entity.getNome());
                    break;
                case "cargo":
                    holder.setCargo(entity.getCargo());
                    break;
                case "nascimento":
                    holder.setNascimento(entity.getNascimento());
                    break;
                case "contatos":
                    holder.setContatos(entity.getContatos());
                    break;
                default:
                    break;
            }
        }
        return toFullProfissionalResponse(holder);
    }

}
