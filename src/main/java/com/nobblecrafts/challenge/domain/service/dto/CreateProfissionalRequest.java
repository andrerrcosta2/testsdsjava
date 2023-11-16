package com.nobblecrafts.challenge.domain.service.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
public record CreateProfissionalRequest(@NotNull String nome,
                                        @NotNull String cargo,
                                        @NotNull Date nascimento) {
}
