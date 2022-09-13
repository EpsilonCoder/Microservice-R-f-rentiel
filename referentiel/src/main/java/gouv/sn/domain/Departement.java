package gouv.sn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Departement.
 */
@Entity
@Table(name = "departement")
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "department")
    @JsonIgnoreProperties(value = { "typeautorite", "department" }, allowSetters = true)
    private Set<Commune> communes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Departement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Departement libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Commune> getCommunes() {
        return this.communes;
    }

    public void setCommunes(Set<Commune> communes) {
        if (this.communes != null) {
            this.communes.forEach(i -> i.setDepartment(null));
        }
        if (communes != null) {
            communes.forEach(i -> i.setDepartment(this));
        }
        this.communes = communes;
    }

    public Departement communes(Set<Commune> communes) {
        this.setCommunes(communes);
        return this;
    }

    public Departement addCommune(Commune commune) {
        this.communes.add(commune);
        commune.setDepartment(this);
        return this;
    }

    public Departement removeCommune(Commune commune) {
        this.communes.remove(commune);
        commune.setDepartment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departement)) {
            return false;
        }
        return id != null && id.equals(((Departement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departement{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
