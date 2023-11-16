package com.nobblecrafts.challenge.domain.service.dto;

import lombok.Builder;

@Builder
public record UpdateContatoRequest(String nome,
                                   String contato) {
}
