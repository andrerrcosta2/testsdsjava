package com.nobblecrafts.challenge.domain.service.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record UpdateProfissionalRequest(String nome,
                                        String cargo) {

}
