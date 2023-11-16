package com.nobblecrafts.challenge.dataaccess.contato.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nobblecrafts.challenge.dataaccess.profissional.entity.ProfissionalJpaEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "contatos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String contato;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * É estranho poder possuir o profissional nulo.
     * porém, o endpoint passado no teste não associa nenhum id
     * ao contato.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "profissional-contato")
    @JoinColumn(name = "profissional_id")
    private ProfissionalJpaEntity profissional;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContatoJpaEntity that = (ContatoJpaEntity) o;
        return id.equals(that.id) && profissional.equals(that.profissional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profissional);
    }

    @Override
    public String toString() {
        return "ContatoJpaEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", contato='" + contato + '\'' +
                ", createdDate=" + createdDate +
                ", profissional=" + (profissional != null ? profissional.getNome() : "null") +
                ", version=" + version +
                '}';
    }
}
