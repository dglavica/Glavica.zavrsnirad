/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Avalg
 */
@Entity
@Table
public class NarudzbaCisterne extends Entitet implements Serializable {

    @OneToMany(mappedBy = "narudzbaCisterne")
    private List<NarudzbaGorivo> narudzbaGoriva;

    

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vrijemeNarudzbe;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vrijemeIsporuke;

    public List<NarudzbaGorivo> getNarudzbaGoriva() {
        return narudzbaGoriva;
    }

    public void setNarudzbaGoriva(List<NarudzbaGorivo> narudzbaGoriva) {
        this.narudzbaGoriva = narudzbaGoriva;
    }
    private BigDecimal trosak;

    @ManyToOne
    private BenzinskaCrpka benzinskaCrpka;

    public Date getVrijemeNarudzbe() {
        return vrijemeNarudzbe;
    }

    public void setVrijemeNarudzbe(Date vrijemeNarudzbe) {
        this.vrijemeNarudzbe = vrijemeNarudzbe;
    }

    public Date getVrijemeIsporuke() {
        return vrijemeIsporuke;
    }

    public void setVrijemeIsporuke(Date vrijemeIsporuke) {
        this.vrijemeIsporuke = vrijemeIsporuke;
    }

    public BigDecimal getTrosak() {
        return trosak;
    }

    public void setTrosak(BigDecimal trosak) {
        this.trosak = trosak;
    }

    public BenzinskaCrpka getBenzinskaCrpka() {
        return benzinskaCrpka;
    }

    public void setBenzinskaCrpka(BenzinskaCrpka benzinskaCrpka) {
        this.benzinskaCrpka = benzinskaCrpka;
    }
    
    @Override
    public String toString() {
        return "NarudzbaCisterne{" + "narudzbaGoriva=" + narudzbaGoriva + ", benzinskaCrpka=" + benzinskaCrpka + '}';
    }

}
