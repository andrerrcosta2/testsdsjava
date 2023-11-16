package com.nobblecrafts.challenge.util;

import com.nobblecrafts.challenge.domain.service.dto.CreateContatoRequest;
import com.github.javafaker.Faker;
import com.nobblecrafts.challenge.domain.service.dto.CreateProfissionalRequest;
import com.nobblecrafts.challenge.domain.service.dto.UpdateContatoRequest;
import com.nobblecrafts.challenge.domain.service.dto.UpdateProfissionalRequest;

public class DtoSupplier {

    private static Faker faker;

    static {
        faker = new Faker();
    }

    public static CreateContatoRequest.CreateContatoRequestBuilder createContatoRequest() {
        return CreateContatoRequest.builder()
                .contato(faker.address().fullAddress())
                .nome(faker.address().firstName());
    }

    public static UpdateContatoRequest.UpdateContatoRequestBuilder updateContatoRequest() {
        return UpdateContatoRequest.builder()
                .contato(faker.address().fullAddress())
                .nome(faker.address().firstName());
    }

    public static CreateProfissionalRequest.CreateProfissionalRequestBuilder createProfissionalRequest() {
        return CreateProfissionalRequest.builder()
                .cargo(faker.company().profession())
                .nome(faker.name().name())
                .nascimento(faker.date().birthday());
    }

    public static UpdateProfissionalRequest.UpdateProfissionalRequestBuilder updateProfissionalRequest() {
        return UpdateProfissionalRequest.builder()
                .nome(faker.name().name())
                .cargo(faker.company().profession());
    }
}
