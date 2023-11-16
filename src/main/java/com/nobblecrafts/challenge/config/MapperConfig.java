package com.nobblecrafts.challenge.config;

import com.nobblecrafts.challenge.dataaccess.contato.mapper.ContatoDaoMapper;
import com.nobblecrafts.challenge.dataaccess.profissional.mapper.ProfissionalDaoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ContatoDaoMapper contatoDaoMapper() {
        return ContatoDaoMapper.INSTANCE;
    }

    @Bean
    public ProfissionalDaoMapper profissionalDaoMapper() {
        ProfissionalDaoMapper mapper = ProfissionalDaoMapper.INSTANCE;
        return mapper;
    }
}
