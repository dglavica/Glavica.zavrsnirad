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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Avalg
 */
@Entity
@Table
public class Djelatnik extends Entitet implements Serializable {

    private String ime, prezime, oib, iban, email;
    private BigDecimal placa;

    @OneToMany(mappedBy = "nadredjeni")
    private List<Djelatnik> podredjeni;

    public List<Djelatnik> getPodredjeni() {
        return podredjeni;
    }

    public void setPodredjeni(List<Djelatnik> podredjeni) {
        this.podredjeni = podredjeni;
    }

    public Djelatnik getNadredjeni() {
        return nadredjeni;
    }

    public void setNadredjeni(Djelatnik nadredjeni) {
        this.nadredjeni = nadredjeni;
    }

    @ManyToOne
    private BenzinskaCrpka benzinskaCrpka;
    @ManyToOne
    private Djelatnik nadredjeni;

    public BenzinskaCrpka getBenzinskaCrpka() {
        return benzinskaCrpka;
    }

    public void setBenzinskaCrpka(BenzinskaCrpka benzinskaCrpka) {
        this.benzinskaCrpka = benzinskaCrpka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getPlaca() {
        return placa;
    }

    public void setPlaca(BigDecimal placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        return getIme() + " " + getPrezime();

    }

}
