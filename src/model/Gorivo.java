/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Avalg
 */
@Entity
@Table
public class Gorivo extends Entitet implements Serializable {

    @OneToMany(mappedBy = "gorivo")
    private List<NarudzbaGorivo> narudzbaGoriva;

    public List<NarudzbaGorivo> getNarudzbaGoriva() {
        return narudzbaGoriva;
    }

    public void setNarudzbaGoriva(List<NarudzbaGorivo> narudzbaGoriva) {
        this.narudzbaGoriva = narudzbaGoriva;
    }

    private String vrsta;
    private BigDecimal prodajnaCijena, kapacitetSpremnika;

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public BigDecimal getProdajnaCijena() {
        return prodajnaCijena;
    }

    public void setProdajnaCijena(BigDecimal prodajnaCijena) {
        this.prodajnaCijena = prodajnaCijena;
    }

    public BigDecimal getKapacitetSpremnika() {
        return kapacitetSpremnika;
    }

    public void setKapacitetSpremnika(BigDecimal kapacitetSpremnika) {
        this.kapacitetSpremnika = kapacitetSpremnika;
    }

    @Override
    public String toString() {
        return "Gorivo (" + vrsta + ')';
    }

}
