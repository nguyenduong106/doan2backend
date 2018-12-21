package com.travelapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A RateType.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "rate_type")
public class RateType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate")
    private String rate;
    @OneToMany(mappedBy = "rateType")
    private Set<Rate> rates=new HashSet<>();
    public RateType addRate(Rate rate){
        this.rates.add(rate);
        rate.setRateType(this);
        return this;
    }
    public RateType removeRate(Rate rate){
        this.rates.remove(rate);
        rate.setRateType(null);
        return this;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public RateType rate(String rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateType rateType = (RateType) o;
        if (rateType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rateType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
