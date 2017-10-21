/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Gorivo;

/**
 *
 * @author Avalg
 */
public class ObradaGorivo {

    private PreparedStatement izraz;
    private Connection veza;
    private ResultSet rs;

    public ObradaGorivo() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            veza = DriverManager.getConnection("jdbc:mysql://127.0.0.1/benzinskacrpka", "edunova", "edunova");

        } catch (Exception e) {

        }
    }

    public void zatvoriVezu() {
        try {
            veza.close();
        } catch (SQLException ex) {

        }
    }

    public List<Gorivo> getGoriva() {
        List<Gorivo> goriva = new ArrayList<>();
        try {
            izraz = veza.prepareStatement("select * from gorivo where obrisano=false");
            rs = izraz.executeQuery();
            Gorivo g;
            while (rs.next()) {
                g = new Gorivo();
                g.setSifra(rs.getLong("sifra"));
                g.setVrsta(rs.getString("vrsta"));
                g.setProdajnaCijena(rs.getBigDecimal("prodajna_cijena"));
                g.setKapacitetSpremnika(rs.getBigDecimal("kapacitet_spremnika"));

                goriva.add(g);
            }
            rs.close();
            izraz.close();
        } catch (Exception e) {

        }

        return goriva;
    }

    public void kreirajGorivo(Gorivo g) {
        try {
            izraz = veza.prepareStatement("insert into gorivo (vrsta, prodajna_cijena, kapacitet_spremnika) values (?,?,?)");
            izraz.setString(1, g.getVrsta());
            izraz.setBigDecimal(2, g.getProdajnaCijena());
            izraz.setBigDecimal(3, g.getKapacitetSpremnika());
            izraz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void promjeniGorivo(Gorivo g) {
        try {
            izraz = veza.prepareStatement("update gorivo set vrsta =?, prodajna_cijena=?, kapacitet_spremnika=? where sifra=?");
            izraz.setString(1, g.getVrsta());
            izraz.setBigDecimal(2, g.getProdajnaCijena());
            izraz.setBigDecimal(3, g.getKapacitetSpremnika());
            izraz.setLong(4, g.getSifra());
            izraz.execute();
        } catch (Exception e) {

        }
    }

    public void obrisiGorivo(Gorivo g) {
        try {
            izraz = veza.prepareStatement("update gorivo set obrisano=true where sifra=?");
            izraz.setLong(1, g.getSifra());
            izraz.execute();
        } catch (Exception e) {

        }
    }

}
