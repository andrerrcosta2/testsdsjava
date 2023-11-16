package com.nobblecrafts.challenge.dataaccess.contato.repository;

import com.nobblecrafts.challenge.dataaccess.contato.entity.ContatoJpaEntity;
import com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity;
import com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface ContatoJpaRepository extends JpaRepository<ContatoJpaEntity, Long> {

//    @Query("SELECT NEW map(" +
//            "CASE WHEN :fields IS NULL OR 'id' IN :fields THEN c.id ELSE NULL END as id, " +
//            "CASE WHEN :fields IS NULL OR 'nome' IN :fields THEN c.nome ELSE NULL END as nome, " +
//            "CASE WHEN :fields IS NULL OR 'contato' IN :fields THEN c.contato ELSE NULL END as contato) " +
//            "FROM com.nobblecrafts.challenge.dataaccess.contato.entity.ContatoJpaEntity c " +
//            "WHERE (:q IS NULL OR " +
//            "LOWER(CONCAT(c.nome::text, ' ', c.contato::text)) LIKE LOWER(CONCAT('%', :q, '%')))")
//    Set<Map<String, Object>> findContatosByParams(@Param("q") String q, @Param("fields") Set<String> fields);

//    @Query("SELECT " +
//            "NEW com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse(" +
//            "CASE WHEN :fields IS NULL OR 'id' IN :fields THEN c.id ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'nome' IN :fields THEN c.nome ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'contato' IN :fields THEN c.contato ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'createdDate' IN :fields THEN c.createdDate ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'profissional' IN :fields THEN c.profissional.nome ELSE null END) " +
//            "FROM com.nobblecrafts.challenge.dataaccess.contato.entity.ContatoJpaEntity c " +
//            "WHERE (:q IS NULL OR " +
//            "LOWER(CONCAT(CAST(c.nome AS text), ' ', CAST(c.contato AS text))) LIKE LOWER(CONCAT('%', :q, '%')))")
//    Set<FullContatoResponse> findContatosByParams(@Param("q") String q, @Param("fields") Set<String> fields);


//    @Query("SELECT c FROM ContatoJpaEntity c " +
//            "WHERE (:q IS NULL OR " +
//            "LOWER(CONCAT(CAST(c.nome AS text), ' ', CAST(c.contato AS text))) LIKE LOWER(CONCAT('%', :q, '%')))")
//    Set<ContatoJpaEntity> findContatosByParams(@Param("q") String q);

    Set<ContatoJpaEntity> findByNomeContainingOrContatoContaining(String nome, String contato);

}
