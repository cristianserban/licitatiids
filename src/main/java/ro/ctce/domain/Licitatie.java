package ro.ctce.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ro.ctce.domain.enumeration.TipLicitatie;

/**
 * A Licitatie.
 */
@Entity
@Table(name = "licitatie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Licitatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_licitatie")
    private LocalDate dataLicitatie;

    @Column(name = "activa")
    private Boolean activa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_licitatie")
    private TipLicitatie tipLicitatie;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "licitatie_firma",
        joinColumns = @JoinColumn(name = "licitatie_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "firma_id", referencedColumnName = "id")
    )
    private Set<Firma> firmas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "licitatie_lot",
        joinColumns = @JoinColumn(name = "licitatie_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "lot_id", referencedColumnName = "id")
    )
    private Set<Lot> lots = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataLicitatie() {
        return dataLicitatie;
    }

    public Licitatie dataLicitatie(LocalDate dataLicitatie) {
        this.dataLicitatie = dataLicitatie;
        return this;
    }

    public void setDataLicitatie(LocalDate dataLicitatie) {
        this.dataLicitatie = dataLicitatie;
    }

    public Boolean isActiva() {
        return activa;
    }

    public Licitatie activa(Boolean activa) {
        this.activa = activa;
        return this;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public TipLicitatie getTipLicitatie() {
        return tipLicitatie;
    }

    public Licitatie tipLicitatie(TipLicitatie tipLicitatie) {
        this.tipLicitatie = tipLicitatie;
        return this;
    }

    public void setTipLicitatie(TipLicitatie tipLicitatie) {
        this.tipLicitatie = tipLicitatie;
    }

    public Set<Firma> getFirmas() {
        return firmas;
    }

    public Licitatie firmas(Set<Firma> firmas) {
        this.firmas = firmas;
        return this;
    }

    public Licitatie addFirma(Firma firma) {
        this.firmas.add(firma);
        firma.getLicitaties().add(this);
        return this;
    }

    public Licitatie removeFirma(Firma firma) {
        this.firmas.remove(firma);
        firma.getLicitaties().remove(this);
        return this;
    }

    public void setFirmas(Set<Firma> firmas) {
        this.firmas = firmas;
    }

    public Set<Lot> getLots() {
        return lots;
    }

    public Licitatie lots(Set<Lot> lots) {
        this.lots = lots;
        return this;
    }

    public Licitatie addLot(Lot lot) {
        this.lots.add(lot);
        lot.getLicitaties().add(this);
        return this;
    }

    public Licitatie removeLot(Lot lot) {
        this.lots.remove(lot);
        lot.getLicitaties().remove(this);
        return this;
    }

    public void setLots(Set<Lot> lots) {
        this.lots = lots;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Licitatie)) {
            return false;
        }
        return id != null && id.equals(((Licitatie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Licitatie{" +
            "id=" + getId() +
            ", dataLicitatie='" + getDataLicitatie() + "'" +
            ", activa='" + isActiva() + "'" +
            ", tipLicitatie='" + getTipLicitatie() + "'" +
            "}";
    }
}
