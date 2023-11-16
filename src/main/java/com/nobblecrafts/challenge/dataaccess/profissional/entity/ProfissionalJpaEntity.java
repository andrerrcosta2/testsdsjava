package com.nobblecrafts.challenge.dataaccess.profissional.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nobblecrafts.challenge.dataaccess.contato.entity.ContatoJpaEntity;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "profissionais")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cargo;

    @Builder.Default
    @JsonManagedReference(value = "profissional-contato")
    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContatoJpaEntity> contatos = new HashSet<>();

    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

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
        ProfissionalJpaEntity that = (ProfissionalJpaEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addContato(ContatoJpaEntity contato) {
        this.contatos.add(contato);
        contato.setProfissional(this);
    }

    public void removeContato(ContatoJpaEntity contato) {
        contato.setProfissional(null);
        this.contatos.remove(contato);
    }

    public void removeAllContatos() {
        var iterator = this.contatos.iterator();
        while (iterator.hasNext()) {
            var contato = iterator.next();
            contato.setProfissional(null);
            iterator.remove();
        }
    }

    @Override
    public String toString() {
        return "ProfissionalJpaEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", contatos=" + contatos +
                ", nascimento=" + nascimento +
                ", createdDate=" + createdDate +
                ", version=" + version +
                '}';
    }
}
