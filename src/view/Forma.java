/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Obrada;
import javax.swing.JFrame;
import model.Entitet;
import model.NarudzbaGorivo;

/**
 *
 * @author Avalg
 */
public abstract class Forma<T extends Entitet> extends JFrame {

    protected abstract void ucitaj();
    protected Obrada<T> obrada;
    protected T entitet;

    protected void obrisi() {
        obrada.delete(entitet);
        ucitaj();
    }

    protected void spremi() {
        entitet = obrada.save(entitet);
        ucitaj();
    }
    
    protected void spremi(NarudzbaGorivo narudzbaGorivo) {
        obrada.save(narudzbaGorivo);
        ucitaj();
    }

}
