package gouv.sn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gouv.sn.domain.enumeration.Type;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Commune.
 */
@Entity
@Table(name = "commune")
public class Commune implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "addresse")
    private String addresse;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "faxe")
    private String faxe;

    @Column(name = "logo")
    private String logo;

    @Column(name = "sigle")
    private String sigle;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "urlsiteweb")
    private String urlsiteweb;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "communes" }, allowSetters = true)
    private TypeAutoriteContractante typeautorite;

    @ManyToOne
    @JsonIgnoreProperties(value = { "communes" }, allowSetters = true)
    private Departement department;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commune id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddresse() {
        return this.addresse;
    }

    public Commune addresse(String addresse) {
        this.setAddresse(addresse);
        return this;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public Commune denomination(String denomination) {
        this.setDenomination(denomination);
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getFaxe() {
        return this.faxe;
    }

    public Commune faxe(String faxe) {
        this.setFaxe(faxe);
        return this;
    }

    public void setFaxe(String faxe) {
        this.faxe = faxe;
    }

    public String getLogo() {
        return this.logo;
    }

    public Commune logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSigle() {
        return this.sigle;
    }

    public Commune sigle(String sigle) {
        this.setSigle(sigle);
        return this;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Commune telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUrlsiteweb() {
        return this.urlsiteweb;
    }

    public Commune urlsiteweb(String urlsiteweb) {
        this.setUrlsiteweb(urlsiteweb);
        return this;
    }

    public void setUrlsiteweb(String urlsiteweb) {
        this.urlsiteweb = urlsiteweb;
    }

    public Type getType() {
        return this.type;
    }

    public Commune type(Type type) {
        this.setType(type);
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TypeAutoriteContractante getTypeautorite() {
        return this.typeautorite;
    }

    public void setTypeautorite(TypeAutoriteContractante typeAutoriteContractante) {
        this.typeautorite = typeAutoriteContractante;
    }

    public Commune typeautorite(TypeAutoriteContractante typeAutoriteContractante) {
        this.setTypeautorite(typeAutoriteContractante);
        return this;
    }

    public Departement getDepartment() {
        return this.department;
    }

    public void setDepartment(Departement departement) {
        this.department = departement;
    }

    public Commune department(Departement departement) {
        this.setDepartment(departement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commune)) {
            return false;
        }
        return id != null && id.equals(((Commune) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commune{" +
            "id=" + getId() +
            ", addresse='" + getAddresse() + "'" +
            ", denomination='" + getDenomination() + "'" +
            ", faxe='" + getFaxe() + "'" +
            ", logo='" + getLogo() + "'" +
            ", sigle='" + getSigle() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", urlsiteweb='" + getUrlsiteweb() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
