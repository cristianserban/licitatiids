package ro.ctce.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ocol.
 */
@Entity
@Table(name = "ocol")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ocol implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nume_ocol")
    private String numeOcol;

    @Column(name = "ordine")
    private Integer ordine;

    @OneToMany(mappedBy = "ocol")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Lot> lots = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeOcol() {
        return numeOcol;
    }

    public Ocol numeOcol(String numeOcol) {
        this.numeOcol = numeOcol;
        return this;
    }

    public void setNumeOcol(String numeOcol) {
        this.numeOcol = numeOcol;
    }

    public Integer getOrdine() {
        return ordine;
    }

    public Ocol ordine(Integer ordine) {
        this.ordine = ordine;
        return this;
    }

    public void setOrdine(Integer ordine) {
        this.ordine = ordine;
    }

    public Set<Lot> getLots() {
        return lots;
    }

    public Ocol lots(Set<Lot> lots) {
        this.lots = lots;
        return this;
    }

    public Ocol addLot(Lot lot) {
        this.lots.add(lot);
        lot.setOcol(this);
        return this;
    }

    public Ocol removeLot(Lot lot) {
        this.lots.remove(lot);
        lot.setOcol(null);
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
        if (!(o instanceof Ocol)) {
            return false;
        }
        return id != null && id.equals(((Ocol) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ocol{" +
            "id=" + getId() +
            ", numeOcol='" + getNumeOcol() + "'" +
            ", ordine=" + getOrdine() +
            "}";
    }
}
