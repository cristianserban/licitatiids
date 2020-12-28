package ro.ctce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Garantie.
 */
@Entity
@Table(name = "garantie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Garantie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "garantie", precision = 21, scale = 2)
    private BigDecimal garantie;

    @Column(name = "tarif", precision = 21, scale = 2)
    private BigDecimal tarif;

    @Column(name = "garantie_depusa", precision = 21, scale = 2)
    private BigDecimal garantieDepusa;

    @ManyToOne
    @JsonIgnoreProperties(value = "garanties", allowSetters = true)
    private Firma firma;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGarantie() {
        return garantie;
    }

    public Garantie garantie(BigDecimal garantie) {
        this.garantie = garantie;
        return this;
    }

    public void setGarantie(BigDecimal garantie) {
        this.garantie = garantie;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public Garantie tarif(BigDecimal tarif) {
        this.tarif = tarif;
        return this;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }

    public BigDecimal getGarantieDepusa() {
        return garantieDepusa;
    }

    public Garantie garantieDepusa(BigDecimal garantieDepusa) {
        this.garantieDepusa = garantieDepusa;
        return this;
    }

    public void setGarantieDepusa(BigDecimal garantieDepusa) {
        this.garantieDepusa = garantieDepusa;
    }

    public Firma getFirma() {
        return firma;
    }

    public Garantie firma(Firma firma) {
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
        if (!(o instanceof Garantie)) {
            return false;
        }
        return id != null && id.equals(((Garantie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Garantie{" +
            "id=" + getId() +
            ", garantie=" + getGarantie() +
            ", tarif=" + getTarif() +
            ", garantieDepusa=" + getGarantieDepusa() +
            "}";
    }
}
