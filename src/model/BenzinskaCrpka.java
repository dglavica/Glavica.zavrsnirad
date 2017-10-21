/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
public class BenzinskaCrpka extends Entitet implements Serializable {

    private String naziv, adresa, oib, email;

    @OneToMany(mappedBy = "benzinskaCrpka")
    private List<Djelatnik> djelatnici;

    public List<Djelatnik> getDjelatnici() {
        return djelatnici;
    }

    public void setDjelatnici(List<Djelatnik> djelatnici) {
        this.djelatnici = djelatnici;
    }

    @OneToMany(mappedBy = "benzinskaCrpka")
    private List<NarudzbaCisterne> narudzbaCisterni;

    public List<NarudzbaCisterne> getNarudzbaCisterni() {
        return narudzbaCisterni;
    }

    public void setNarudzbaCisterni(List<NarudzbaCisterne> narudzbaCisterni) {
        this.narudzbaCisterni = narudzbaCisterni;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "BenzinskaCrpka (" + naziv + ')';
    }

}
