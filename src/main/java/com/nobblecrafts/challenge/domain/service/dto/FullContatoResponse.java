package com.nobblecrafts.challenge.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FullContatoResponse(Long id,
                                  String nome,
                                  String contato,
                                  Date createdDate,
                                  String profissional) {
}
