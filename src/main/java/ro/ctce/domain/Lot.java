package ro.ctce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ro.ctce.domain.enumeration.StareLot;

/**
 * A Lot.
 */
@Entity
@Table(name = "lot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lot implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nr_fisa")
    private Integer nrFisa;

    @Enumerated(EnumType.STRING)
    @Column(name = "stare")
    private StareLot stare;

    @Column(name = "pret_pornire", precision = 21, scale = 2)
    private BigDecimal pretPornire;

    @Column(name = "volum_brut", precision = 21, scale = 2)
    private BigDecimal volumBrut;

    @Column(name = "volum_net", precision = 21, scale = 2)
    private BigDecimal volumNet;

    @Column(name = "volum_coaja", precision = 21, scale = 2)
    private BigDecimal volumCoaja;

    @OneToMany(mappedBy = "lot")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Oferta> ofertas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "lots", allowSetters = true)
    private Ocol ocol;

    @ManyToMany(mappedBy = "lots")
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

    public Integer getNrFisa() {
        return nrFisa;
    }

    public Lot nrFisa(Integer nrFisa) {
        this.nrFisa = nrFisa;
        return this;
    }

    public void setNrFisa(Integer nrFisa) {
        this.nrFisa = nrFisa;
    }

    public StareLot getStare() {
        return stare;
    }

    public Lot stare(StareLot stare) {
        this.stare = stare;
        return this;
    }

    public void setStare(StareLot stare) {
        this.stare = stare;
    }

    public BigDecimal getPretPornire() {
        return pretPornire;
    }

    public Lot pretPornire(BigDecimal pretPornire) {
        this.pretPornire = pretPornire;
        return this;
    }

    public void setPretPornire(BigDecimal pretPornire) {
        this.pretPornire = pretPornire;
    }

    public BigDecimal getVolumBrut() {
        return volumBrut;
    }

    public Lot volumBrut(BigDecimal volumBrut) {
        this.volumBrut = volumBrut;
        return this;
    }

    public void setVolumBrut(BigDecimal volumBrut) {
        this.volumBrut = volumBrut;
    }

    public BigDecimal getVolumNet() {
        return volumNet;
    }

    public Lot volumNet(BigDecimal volumNet) {
        this.volumNet = volumNet;
        return this;
    }

    public void setVolumNet(BigDecimal volumNet) {
        this.volumNet = volumNet;
    }

    public BigDecimal getVolumCoaja() {
        return volumCoaja;
    }

    public Lot volumCoaja(BigDecimal volumCoaja) {
        this.volumCoaja = volumCoaja;
        return this;
    }

    public void setVolumCoaja(BigDecimal volumCoaja) {
        this.volumCoaja = volumCoaja;
    }

    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public Lot ofertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
        return this;
    }

    public Lot addOferta(Oferta oferta) {
        this.ofertas.add(oferta);
        oferta.setLot(this);
        return this;
    }

    public Lot removeOferta(Oferta oferta) {
        this.ofertas.remove(oferta);
        oferta.setLot(null);
        return this;
    }

    public void setOfertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public Ocol getOcol() {
        return ocol;
    }

    public Lot ocol(Ocol ocol) {
        this.ocol = ocol;
        return this;
    }

    public void setOcol(Ocol ocol) {
        this.ocol = ocol;
    }

    public Set<Licitatie> getLicitaties() {
        return licitaties;
    }

    public Lot licitaties(Set<Licitatie> licitaties) {
        this.licitaties = licitaties;
        return this;
    }

    public Lot addLicitatie(Licitatie licitatie) {
        this.licitaties.add(licitatie);
        licitatie.getLots().add(this);
        return this;
    }

    public Lot removeLicitatie(Licitatie licitatie) {
        this.licitaties.remove(licitatie);
        licitatie.getLots().remove(this);
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
        if (!(o instanceof Lot)) {
            return false;
        }
        return id != null && id.equals(((Lot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lot{" +
            "id=" + getId() +
            ", nrFisa=" + getNrFisa() +
            ", stare='" + getStare() + "'" +
            ", pretPornire=" + getPretPornire() +
            ", volumBrut=" + getVolumBrut() +
            ", volumNet=" + getVolumNet() +
            ", volumCoaja=" + getVolumCoaja() +
            "}";
    }
}
