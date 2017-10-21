/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Avalg
 */
@Entity
@Table
public class NarudzbaGorivo extends Entitet implements Serializable {

    
    
    private BigDecimal kolicina, nabavnaCijena;
    
    @ManyToOne
    private Gorivo gorivo;
    @ManyToOne
    private NarudzbaCisterne narudzbaCisterne;

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getNabavnaCijena() {
        return nabavnaCijena;
    }

    public void setNabavnaCijena(BigDecimal nabavnaCijena) {
        this.nabavnaCijena = nabavnaCijena;
    }

    public Gorivo getGorivo() {
        return gorivo;
    }

    public void setGorivo(Gorivo gorivo) {
        this.gorivo = gorivo;
    }

    public NarudzbaCisterne getNarudzbaCisterne() {
        return narudzbaCisterne;
    }

    public void setNarudzbaCisterne(NarudzbaCisterne narudzbaCisterne) {
        this.narudzbaCisterne = narudzbaCisterne;
    }

    @Override
    public String toString() {
        return "NarudzbaGorivo{" + "gorivo=" + gorivo + '}';
    }
    
   
    
}
