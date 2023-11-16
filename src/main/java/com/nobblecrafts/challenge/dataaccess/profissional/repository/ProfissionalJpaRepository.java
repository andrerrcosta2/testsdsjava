package com.nobblecrafts.challenge.dataaccess.profissional.repository;

import com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProfissionalJpaRepository extends JpaRepository<ProfissionalJpaEntity, Long> {

//    @Query("SELECT NEW com.nobblecrafts.challenge.domain.service.dto.FullProfissionalResponse(" +
//            "CASE WHEN :fields IS NULL OR 'id' IN :fields THEN p.id ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'nome' IN :fields THEN p.nome ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'cargo' IN :fields THEN p.cargo ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'nascimento' IN :fields THEN p.nascimento ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'createdDate' IN :fields THEN p.createdDate ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'contatos' IN :fields THEN " +
//            "NEW com.nobblecrafts.challenge.domain.service.dto.FullContatoResponse(" +
//            "CASE WHEN :fields IS NULL OR 'id' IN :fields THEN c.id ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'nome' IN :fields THEN c.nome ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'contato' IN :fields THEN c.contato ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'createdDate' IN :fields THEN c.createdDate ELSE null END, " +
//            "CASE WHEN :fields IS NULL OR 'profissional' IN :fields THEN c.profissional.nome ELSE null END) " +
//            "ELSE null END) " +
//            "FROM com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity p " +
//            "LEFT JOIN p.contatos c " +
//            "WHERE (:q IS NULL OR " +
//            "LOWER(CONCAT(CAST(p.nome AS text), ' ', CAST(p.cargo AS text), ' ', CAST(p.nascimento AS text), ' ', CAST(c.nome AS text))) LIKE LOWER(CONCAT('%', :q, '%')))")
//    Set<FullProfissionalResponse> findProfissionaisByParams(@Param("q") String q, @Param("fields") Set<String> fields);

//    @Query("SELECT p FROM ProfissionalJpaEntity p " +
//            "LEFT JOIN FETCH p.contatos c " +
//            "WHERE (:q IS NULL OR " +
//            "LOWER(CONCAT(CAST(p.nome AS text), ' ', CAST(p.cargo AS text), ' ', CAST(p.nascimento AS text), ' ', CAST(c.nome AS text))) " +
//            "LIKE LOWER(CONCAT('%', :q, '%')))")
//    Set<ProfissionalJpaEntity> findProfissionaisByParams(@Param("q") String q);

    Set<ProfissionalJpaEntity> findByNomeContainingOrCargoContaining(String nome, String cargo);


}
