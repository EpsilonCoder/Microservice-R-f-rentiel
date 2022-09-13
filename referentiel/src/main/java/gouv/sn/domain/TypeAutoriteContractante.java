package gouv.sn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A TypeAutoriteContractante.
 */
@Entity
@Table(name = "type_autorite_contractante")
public class TypeAutoriteContractante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "ordre")
    private Integer ordre;

    @OneToMany(mappedBy = "typeautorite")
    @JsonIgnoreProperties(value = { "typeautorite", "department" }, allowSetters = true)
    private Set<Commune> communes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeAutoriteContractante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TypeAutoriteContractante code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeAutoriteContractante description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public TypeAutoriteContractante libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getOrdre() {
        return this.ordre;
    }

    public TypeAutoriteContractante ordre(Integer ordre) {
        this.setOrdre(ordre);
        return this;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Set<Commune> getCommunes() {
        return this.communes;
    }

    public void setCommunes(Set<Commune> communes) {
        if (this.communes != null) {
            this.communes.forEach(i -> i.setTypeautorite(null));
        }
        if (communes != null) {
            communes.forEach(i -> i.setTypeautorite(this));
        }
        this.communes = communes;
    }

    public TypeAutoriteContractante communes(Set<Commune> communes) {
        this.setCommunes(communes);
        return this;
    }

    public TypeAutoriteContractante addCommune(Commune commune) {
        this.communes.add(commune);
        commune.setTypeautorite(this);
        return this;
    }

    public TypeAutoriteContractante removeCommune(Commune commune) {
        this.communes.remove(commune);
        commune.setTypeautorite(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeAutoriteContractante)) {
            return false;
        }
        return id != null && id.equals(((TypeAutoriteContractante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeAutoriteContractante{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", ordre=" + getOrdre() +
            "}";
    }
}
