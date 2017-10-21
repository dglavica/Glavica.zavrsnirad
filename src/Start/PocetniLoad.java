/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Start;

import controller.Obrada;
import java.math.BigDecimal;
import model.BenzinskaCrpka;
import model.Djelatnik;
import model.Gorivo;

/**
 *
 * @author Avalg
 */
public class PocetniLoad {

    public static void main(String[] args) {

        Obrada<BenzinskaCrpka> bc = new Obrada<>();

        BenzinskaCrpka b1 = new BenzinskaCrpka();
        b1.setNaziv("INA");
        b1.setAdresa("Osijek,Vukovarska 306");
        b1.setOib("27759560625");
        b1.setEmail("ina@ina.hr");

        bc.save(b1);

        BenzinskaCrpka b2 = new BenzinskaCrpka();
        b2.setNaziv("Tifon");
        b2.setAdresa("Osijek,Vukovarska 49");
        b2.setOib("59648537792");
        b2.setEmail("info@tifon.hr");

        bc.save(b2);

        BenzinskaCrpka b3 = new BenzinskaCrpka();
        b3.setNaziv("Crodux");
        b3.setAdresa("Osijek,L.B.Mandica bb");
        b3.setOib("27958863341");
        b3.setEmail("info@crodux-derivati.hr");

        bc.save(b3);

        Obrada<Gorivo> go = new Obrada<>();

        Gorivo g1 = new Gorivo();
        g1.setVrsta("Eurosuper95");
        g1.setProdajnaCijena(new BigDecimal(9.46));
        g1.setKapacitetSpremnika(new BigDecimal(25000));

        go.save(g1);

        Gorivo g2 = new Gorivo();
        g2.setVrsta("Eurosuper98");
        g2.setProdajnaCijena(new BigDecimal(9.62));
        g2.setKapacitetSpremnika(new BigDecimal(25000));

        go.save(g2);

        Gorivo g3 = new Gorivo();
        g3.setVrsta("Eurosuper100");
        g3.setProdajnaCijena(new BigDecimal(9.92));
        g3.setKapacitetSpremnika(new BigDecimal(25000));

        go.save(g3);

        Gorivo g4 = new Gorivo();
        g4.setVrsta("Autoplin");
        g4.setProdajnaCijena(new BigDecimal(3.93));
        g4.setKapacitetSpremnika(new BigDecimal(25000));

        go.save(g4);

        Gorivo g5 = new Gorivo();
        g5.setVrsta("Eurodizel");
        g5.setProdajnaCijena(new BigDecimal(8.61));
        g5.setKapacitetSpremnika(new BigDecimal(25000));

        go.save(g5);

        Gorivo g6 = new Gorivo();
        g6.setVrsta("Eurodizel plavi");
        g6.setProdajnaCijena(new BigDecimal(4.59));
        g6.setKapacitetSpremnika(new BigDecimal(25000));

        go.save(g6);

        Obrada<Djelatnik> dj = new Obrada<>();

        Djelatnik d1 = new Djelatnik();
        d1.setIme("Marko");
        d1.setPrezime("Maric");
        d1.setOib("58779632591");
        d1.setIban("HR1200009866124589635");
        d1.setEmail("marko.maric@gmail.com");
        d1.setPlaca(new BigDecimal(4256.73));

        dj.save(d1);

        Djelatnik d2 = new Djelatnik();
        d2.setIme("Pero");
        d2.setPrezime("Peric");
        d2.setOib("21158896453");
        d2.setIban("HR7880000632955541963");
        d2.setEmail("pero.peric@gmail.com");
        d2.setPlaca(new BigDecimal(3256.26));

        dj.save(d2);

        Djelatnik d3 = new Djelatnik();
        d3.setIme("Davor");
        d3.setPrezime("Ivic");
        d3.setOib("57889651233");
        d3.setIban("HR7562000456000365656");
        d3.setEmail("d.ivic@gmail.com");
        d3.setPlaca(new BigDecimal(4189.12));

        dj.save(d3);

        Djelatnik d4 = new Djelatnik();
        d4.setIme("Mario");
        d4.setPrezime("Horvat");
        d4.setOib("45229875612");
        d4.setIban("HR4598756321954759639");
        d4.setEmail("m.horvat@gmail.com");
        d4.setPlaca(new BigDecimal(2890.56));

        dj.save(d4);

        Djelatnik d5 = new Djelatnik();
        d5.setIme("Darko");
        d5.setPrezime("Lucic");
        d5.setOib("58774596323");
        d5.setIban("HR4550000009632545698");
        d5.setEmail("darko.lucic@gmail.com");
        d5.setPlaca(new BigDecimal(3776.33));

        dj.save(d5);

        Djelatnik d6 = new Djelatnik();
        d6.setIme("Zoran");
        d6.setPrezime("Zoranic");
        d6.setOib("15448963521");
        d6.setIban("HR8541111000023633985");
        d6.setEmail("z.zoranic@gmail.com");
        d6.setPlaca(new BigDecimal(3995.01));

        dj.save(d6);

    }

}
