package ro.ctce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Firma.
 */
@Entity
@Table(name = "firma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Firma implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nume_firma")
    private String numeFirma;

    @Column(name = "drept_preemtiune")
    private Boolean dreptPreemtiune;

    @Column(name = "volum", precision = 21, scale = 2)
    private BigDecimal volum;

    @OneToMany(mappedBy = "firma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Garantie> garanties = new HashSet<>();

    @OneToMany(mappedBy = "firma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Oferta> ofertas = new HashSet<>();

    @ManyToMany(mappedBy = "firmas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Licitatie> licitaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeFirma() {
        return numeFirma;
    }

    public Firma numeFirma(String numeFirma) {
        this.numeFirma = numeFirma;
        return this;
    }

    public void setNumeFirma(String numeFirma) {
        this.numeFirma = numeFirma;
    }

    public Boolean isDreptPreemtiune() {
        return dreptPreemtiune;
    }

    public Firma dreptPreemtiune(Boolean dreptPreemtiune) {
        this.dreptPreemtiune = dreptPreemtiune;
        return this;
    }

    public void setDreptPreemtiune(Boolean dreptPreemtiune) {
        this.dreptPreemtiune = dreptPreemtiune;
    }

    public BigDecimal getVolum() {
        return volum;
    }

    public Firma volum(BigDecimal volum) {
        this.volum = volum;
        return this;
    }

    public void setVolum(BigDecimal volum) {
        this.volum = volum;
    }

    public Set<Garantie> getGaranties() {
        return garanties;
    }

    public Firma garanties(Set<Garantie> garanties) {
        this.garanties = garanties;
        return this;
    }

    public Firma addGarantie(Garantie garantie) {
        this.garanties.add(garantie);
        garantie.setFirma(this);
        return this;
    }

    public Firma removeGarantie(Garantie garantie) {
        this.garanties.remove(garantie);
        garantie.setFirma(null);
        return this;
    }

    public void setGaranties(Set<Garantie> garanties) {
        this.garanties = garanties;
    }

    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public Firma ofertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
        return this;
    }

    public Firma addOferta(Oferta oferta) {
        this.ofertas.add(oferta);
        oferta.setFirma(this);
        return this;
    }

    public Firma removeOferta(Oferta oferta) {
        this.ofertas.remove(oferta);
        oferta.setFirma(null);
        return this;
    }

    public void setOfertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public Set<Licitatie> getLicitaties() {
        return licitaties;
    }

    public Firma licitaties(Set<Licitatie> licitaties) {
        this.licitaties = licitaties;
        return this;
    }

    public Firma addLicitatie(Licitatie licitatie) {
        this.licitaties.add(licitatie);
        licitatie.getFirmas().add(this);
        return this;
    }

    public Firma removeLicitatie(Licitatie licitatie) {
        this.licitaties.remove(licitatie);
        licitatie.getFirmas().remove(this);
        return this;
    }

    public void setLicitaties(Set<Licitatie> licitaties) {
        this.licitaties = licitaties;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Firma)) {
            return false;
        }
        return id != null && id.equals(((Firma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Firma{" +
            "id=" + getId() +
            ", numeFirma='" + getNumeFirma() + "'" +
            ", dreptPreemtiune='" + isDreptPreemtiune() + "'" +
            ", volum=" + getVolum() +
            "}";
    }
}
