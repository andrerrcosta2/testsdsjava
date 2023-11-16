package com.nobblecrafts.challenge.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Date;
import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FullProfissionalResponse(Long id,
                                       String nome,
                                       String cargo,
                                       Date nascimento,
                                       Date createdDate,
                                       Set<FullContatoResponse> contatos) {
}