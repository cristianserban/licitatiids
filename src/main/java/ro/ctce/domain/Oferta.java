package ro.ctce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Oferta.
 */
@Entity
@Table(name = "oferta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Oferta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "castigatoare")
    private Boolean castigatoare;

    @Column(name = "pas", precision = 21, scale = 2)
    private BigDecimal pas;

    @Column(name = "pret", precision = 21, scale = 2)
    private BigDecimal pret;

    @ManyToOne
    @JsonIgnoreProperties(value = "ofertas", allowSetters = true)
    private Lot lot;

    @ManyToOne
    @JsonIgnoreProperties(value = "ofertas", allowSetters = true)
    private Firma firma;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCastigatoare() {
        return castigatoare;
    }

    public Oferta castigatoare(Boolean castigatoare) {
        this.castigatoare = castigatoare;
        return this;
    }

    public void setCastigatoare(Boolean castigatoare) {
        this.castigatoare = castigatoare;
    }

    public BigDecimal getPas() {
        return pas;
    }

    public Oferta pas(BigDecimal pas) {
        this.pas = pas;
        return this;
    }

    public void setPas(BigDecimal pas) {
        this.pas = pas;
    }

    public BigDecimal getPret() {
        return pret;
    }

    public Oferta pret(BigDecimal pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(BigDecimal pret) {
        this.pret = pret;
    }

    public Lot getLot() {
        return lot;
    }

    public Oferta lot(Lot lot) {
        this.lot = lot;
        return this;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Firma getFirma() {
        return firma;
    }

    public Oferta firma(Firma firma) {
        this.firma = firma;
        return this;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Oferta)) {
            return false;
        }
        return id != null && id.equals(((Oferta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Oferta{" +
            "id=" + getId() +
            ", castigatoare='" + isCastigatoare() + "'" +
            ", pas=" + getPas() +
            ", pret=" + getPret() +
            "}";
    }
}
