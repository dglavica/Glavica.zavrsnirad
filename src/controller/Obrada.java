/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Pomocno.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.Entitet;
import model.NarudzbaGorivo;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Avalg
 * @param <T>
 */
public class Obrada<T extends Entitet> {

    private final Session session;

    public Obrada() {
        session = HibernateUtil.getSession();
    }

    public T save(T entitet) {
        Date d = new Date();
        if (entitet.getSifra() == null) {
            entitet.setDatumKreiranja(d);
        }
        entitet.setDatumPromjene(d);
        session.beginTransaction();
        //System.out.println(entitet.hashCode());
        session.saveOrUpdate(entitet);
        session.getTransaction().commit();
        return entitet;
    }

    @Deprecated
    public List<T> createQuery(String hql) {
        Query q = session.createQuery(hql);
        return q.list();
    }

    public void delete(T entitet) {
        entitet.setObrisan(true);
        save(entitet);
    }

    public List<T> save(List<T> lista) {
        Date d = new Date();
        session.beginTransaction();
        lista.stream().forEach((entitet) -> {
            if (entitet.getSifra() == null) {
                entitet.setDatumKreiranja(d);
            }
            entitet.setDatumPromjene(d);
            session.saveOrUpdate(entitet);
        });
        session.getTransaction().commit();
        return lista;
    }

    public NarudzbaGorivo save(NarudzbaGorivo narudzbaGorivo) {
        Date d = new Date();
        if (narudzbaGorivo.getSifra() == null) {
            narudzbaGorivo.setDatumKreiranja(d);
        }
        narudzbaGorivo.setDatumPromjene(d);
        session.beginTransaction();
        //System.out.println(entitet.hashCode());
        session.saveOrUpdate(narudzbaGorivo);
        session.getTransaction().commit();
        return narudzbaGorivo;
    }

}
