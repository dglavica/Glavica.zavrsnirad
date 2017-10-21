/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 *
 * @author Avalg
 */
@MappedSuperclass
public abstract class Entitet {

    @Id
    @GeneratedValue

    private Long sifra;

    public Long getSifra() {
        return sifra;
    }

    public void setSifra(Long sifra) {
        this.sifra = sifra;
    }

    private boolean obrisan = false;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumKreiranja;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumPromjene;

    public boolean isObrisan() {
        return obrisan;
    }

    public void setObrisan(boolean obrisan) {
        this.obrisan = obrisan;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Date getDatumPromjene() {
        return datumPromjene;
    }

    public void setDatumPromjene(Date datumPromjene) {
        this.datumPromjene = datumPromjene;
    }

}
