package com.nobblecrafts.challenge.domain.service.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record CreateContatoRequest(@NotNull String nome,
                                   @NotNull String contato) {
}
