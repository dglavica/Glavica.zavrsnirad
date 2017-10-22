/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Pomocno.HibernateUtil;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import controller.Obrada;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import model.BenzinskaCrpka;
import model.Gorivo;
import model.NarudzbaCisterne;
import model.NarudzbaGorivo;

/**
 *
 * @author Avalg
 */
public class FormaNarudzbaCisterne extends Forma<NarudzbaCisterne> {

    /**
     * Creates new form FormaTvrtka
     */
    private List<NarudzbaGorivo> narudzbaGorivo;
    private ArrayList<NarudzbaGorivo> narudzbaGorivoList = new ArrayList();

    private List<Gorivo> goriva;

    private DatePicker datumNarudzbe;
    private DatePicker datumIsporuke;
    private Object Gorivo;

    public FormaNarudzbaCisterne() {
        initComponents();
        obrada = new Obrada();
        definirajKalendare();
        ucitajBenzinskeCrpke();
        goriva = HibernateUtil.getSession().createQuery("from Gorivo a where a.obrisan=false").list();
        narudzbaGorivo = HibernateUtil.getSession().createQuery("from NarudzbaGorivo a where a.obrisan=false").list();
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setTitle("Narudžbe");
        this.setLocationRelativeTo(null);
        definirajDesniKlikNaPolaznicima();
        ucitaj();
        ucitajSvaGoriva();
    }

    private void definirajKalendare() {
        datumNarudzbe = new DatePicker();
        datumNarudzbe.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dce) {
                LocalDate d = datumNarudzbe.getDate();
                d = d.plusDays(5);
                datumIsporuke.setDate(d);
            }

        });

        datumNarudzbe.setSize(pnlDatumNarudzbe.getSize());
        datumNarudzbe.setLocale(new Locale("hr"));
        pnlDatumNarudzbe.add(datumNarudzbe);

        datumIsporuke = new DatePicker();
        datumIsporuke.setSize(pnlDatumIsporuke.getSize());
        datumIsporuke.setLocale(new Locale("hr"));
        pnlDatumIsporuke.add(datumIsporuke);

    }

    private void definirajDesniKlikNaPolaznicima() {
        lstGoriva.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {

                    if (lstGoriva.getSelectedValue() == null) {
                        return;
                    }

                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Obriši");
                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Gorivo g = lstGoriva.getSelectedValue();
                            NarudzbaGorivo ngTemp = null;
                            for (NarudzbaGorivo ng : narudzbaGorivo) {
                                if (ng.getNarudzbaCisterne() != null && entitet.getSifra() != null && Objects.equals(ng.getNarudzbaCisterne().getSifra(), entitet.getSifra())) {
                                    if (ng.getGorivo() != null && Objects.equals(ng.getGorivo().getSifra(), g.getSifra())) {
                                        ng.setGorivo(null);
                                        ng.setNarudzbaCisterne(null);
                                        ng.setObrisan(true);
                                        ngTemp = ng;
                                        break;

                                        //obriši ng
                                    }
                                }
                            }
                            if (ngTemp != null) {
                                //spremi - s obrisan = true
                                test(ngTemp);
                                ucitajSvaGorivaZaNarudzbu();
                                ucitajSvaGoriva();
                            }
                            //spremi();
                        }
                    });
                    menu.add(item);
                    menu.show(lstGoriva, 25, lstGoriva.getCellBounds(
                            lstGoriva.getSelectedIndex() + 1,
                            lstGoriva.getSelectedIndex() + 1).y);
                }
            }
        });
    }

    public void test(NarudzbaGorivo ngTemp) {
        super.spremi(ngTemp);
    }

    private void ucitajBenzinskeCrpke() {
        DefaultComboBoxModel<BenzinskaCrpka> bc = new DefaultComboBoxModel<>();
        cmbBenzinskaCrpka.setModel(bc);
        List<BenzinskaCrpka> crpke = HibernateUtil.getSession().
                createQuery("from BenzinskaCrpka a where "
                        + "a.obrisan=false  ").list();

        for (BenzinskaCrpka b : crpke) {

            bc.addElement(b);
            cmbBenzinskaCrpka.setSelectedItem(b);

        }
    }

    private void ucitajSvaGoriva() {
        
        DefaultComboBoxModel<Gorivo> model = new DefaultComboBoxModel<Gorivo>();
        cmbGorivo.setModel(model);
        List<Gorivo> list = HibernateUtil.getSession().
                createQuery("from Gorivo a where a.obrisan=false").list();
        for (Gorivo g : list) {
            model.addElement(g);
            cmbGorivo.setSelectedItem(g);
        }
    }

    private void ucitajSvaGorivaZaNarudzbu() {
        DefaultComboBoxModel<Gorivo> model = new DefaultComboBoxModel<Gorivo>();
        cmbGorivo.setModel(model);
        String inQuery = "";
        ArrayList<Long> list1 = new ArrayList();
        for (NarudzbaGorivo ng : narudzbaGorivo) {
            if (ng.getGorivo() != null) {
                list1.add(ng.getGorivo().getSifra());
                inQuery += ng.getGorivo().getSifra() + ", ";
            }
        }
        if (inQuery.endsWith(", ")) {
            inQuery = inQuery.substring(0, inQuery.length() - 2);
        }
        List<Gorivo> list = HibernateUtil.getSession().
                createQuery("from Gorivo a where sifra in (" + inQuery + ") and  a.obrisan=false").list();

        DefaultListModel<Gorivo> nc = new DefaultListModel<>();
        lstGoriva.setModel(nc);
        if (list != null && list.size() > 0) {
            for (Gorivo g : list) {
                nc.addElement(g);
            }
        }
    }

    private void ucitajGorivaZaNarudzbu(ArrayList<NarudzbaGorivo> narudzbaGorivo) {
        int index = lstGoriva.getSelectedIndex();
        DefaultComboBoxModel<Gorivo> model = new DefaultComboBoxModel<Gorivo>();
        cmbGorivo.setModel(model);
        String inQuery = "";
        ArrayList<Long> list1 = new ArrayList();
        for (NarudzbaGorivo ng : narudzbaGorivo) {
            if (ng.getGorivo() != null) {
                list1.add(ng.getGorivo().getSifra());
                inQuery += ng.getGorivo().getSifra() + ", ";
            }
        }
        if (inQuery.endsWith(", ")) {
            inQuery = inQuery.substring(0, inQuery.length() - 2);
        }
        List<Gorivo> list = HibernateUtil.getSession().
                createQuery("from Gorivo a where sifra in (" + inQuery + ") and  a.obrisan=false").list();

        DefaultListModel<Gorivo> nc = new DefaultListModel<>();
        lstGoriva.setModel(nc);
        if (list != null && list.size() > 0) {
            for (Gorivo g : list) {
                nc.addElement(g);
            }
        }
        lstGoriva.setSelectedIndex(index);
    }

    @Override
    protected void ucitaj() {

        DefaultListModel<NarudzbaCisterne> nc = new DefaultListModel<>();
        lstNarudzba.setModel(nc);
        List<NarudzbaCisterne> l = HibernateUtil.getSession().createQuery("from NarudzbaCisterne a where a.obrisan=false").list();
        l.forEach((s) -> {
            nc.addElement(s);
        });

        System.out.println(entitet);
        if (entitet != null) {
            for (int i = 0; i < lstNarudzba.getModel().getSize(); i++) {
                //System.out.println(lstEntiteti.getModel());
                if (lstNarudzba.getModel().getElementAt(i).getSifra().equals(entitet.getSifra())) {
                    lstNarudzba.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstNarudzba = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        txtVrijemeNarudzbe = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtVrijemeIsporuke = new javax.swing.JTextField();
        btnDodaj = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        txtTrosak = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbBenzinskaCrpka = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbGorivo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNabavnaCijena = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstGoriva = new javax.swing.JList<>();
        btnDodajGorivo = new javax.swing.JButton();
        pnlDatumNarudzbe = new javax.swing.JPanel();
        pnlDatumIsporuke = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lstNarudzba.setBackground(new java.awt.Color(204, 255, 255));
        lstNarudzba.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstNarudzbaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstNarudzba);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Popis narudzbi");

        txtVrijemeNarudzbe.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setText("Datum narudžbe");

        jLabel3.setText("Datum Isporuke");

        txtVrijemeIsporuke.setBackground(new java.awt.Color(204, 255, 255));

        btnDodaj.setText("Dodaj");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
            }
        });

        btnPromjeni.setText("Promjeni");
        btnPromjeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjeniActionPerformed(evt);
            }
        });

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiActionPerformed(evt);
            }
        });

        txtTrosak.setBackground(new java.awt.Color(204, 255, 255));

        jLabel6.setText("Trošak");

        cmbBenzinskaCrpka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBenzinskaCrpkaActionPerformed(evt);
            }
        });

        jLabel4.setText("Benzinska crpka");

        jLabel5.setText("Gorivo");

        jLabel7.setText("Količina");

        txtKolicina.setBackground(new java.awt.Color(204, 255, 255));

        jLabel8.setText("Nabavna cijena");

        txtNabavnaCijena.setBackground(new java.awt.Color(204, 255, 255));

        lstGoriva.setBackground(new java.awt.Color(204, 255, 255));
        lstGoriva.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(lstGoriva);

        btnDodajGorivo.setText("Dodaj Gorivo");
        btnDodajGorivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajGorivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDatumNarudzbeLayout = new javax.swing.GroupLayout(pnlDatumNarudzbe);
        pnlDatumNarudzbe.setLayout(pnlDatumNarudzbeLayout);
        pnlDatumNarudzbeLayout.setHorizontalGroup(
            pnlDatumNarudzbeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
        );
        pnlDatumNarudzbeLayout.setVerticalGroup(
            pnlDatumNarudzbeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlDatumIsporukeLayout = new javax.swing.GroupLayout(pnlDatumIsporuke);
        pnlDatumIsporuke.setLayout(pnlDatumIsporukeLayout);
        pnlDatumIsporukeLayout.setHorizontalGroup(
            pnlDatumIsporukeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
        pnlDatumIsporukeLayout.setVerticalGroup(
            pnlDatumIsporukeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDodaj)
                                .addGap(39, 39, 39)
                                .addComponent(btnPromjeni)
                                .addGap(37, 37, 37)
                                .addComponent(btnObrisi))
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVrijemeNarudzbe)
                            .addComponent(txtTrosak)
                            .addComponent(txtKolicina)
                            .addComponent(txtNabavnaCijena)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtVrijemeIsporuke)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlDatumNarudzbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlDatumIsporuke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 8, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbGorivo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDodajGorivo))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(169, 169, 169)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(cmbBenzinskaCrpka, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(52, 52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVrijemeNarudzbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDatumNarudzbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVrijemeIsporuke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDatumIsporuke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(txtTrosak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbGorivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBenzinskaCrpka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDodajGorivo))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtNabavnaCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDodaj)
                    .addComponent(btnPromjeni)
                    .addComponent(btnObrisi))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstNarudzbaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstNarudzbaValueChanged
        if (evt.getValueIsAdjusting()) {
            return;
        }
        if (lstNarudzba.getSelectedValue() == null) {
            return;
        }

        try {
            this.entitet = lstNarudzba.getSelectedValue();

            txtTrosak.setText(entitet.getTrosak().toString());
            txtVrijemeNarudzbe.setText(entitet.getVrijemeNarudzbe().toString());
            txtVrijemeIsporuke.setText(entitet.getVrijemeIsporuke().toString());
            cmbBenzinskaCrpka.setSelectedItem(entitet.getBenzinskaCrpka());
            narudzbaGorivoList = new ArrayList();
            for (NarudzbaGorivo ng : narudzbaGorivo) {
                if (ng.getNarudzbaCisterne() != null && entitet.getSifra() != null && Objects.equals(ng.getNarudzbaCisterne().getSifra(), entitet.getSifra())) {
                    narudzbaGorivoList.add(ng);
                    break;
                }
            }
            if (narudzbaGorivoList.size() > 0) {
                txtKolicina.setText(narudzbaGorivoList.get(0).getKolicina().toString());
                txtNabavnaCijena.setText(narudzbaGorivoList.get(0).getNabavnaCijena().toString());
                ucitajSvaGorivaZaNarudzbu();
                ucitajSvaGoriva();
            } else {
                txtKolicina.setText("");
                txtNabavnaCijena.setText("");
                if (lstGoriva.getModel() != null) {
                    if (lstGoriva.getModel() instanceof DefaultListModel) {
                        DefaultListModel listModel = (DefaultListModel) lstGoriva.getModel();
                        listModel.removeAllElements();
                    }
                }
                ucitajSvaGoriva();

            }
            //cmbGorivo.setSelectedItem(entitet.getNarudzbaGoriva().contains(Gorivo));
//            cmbBenzinskaCrpka.setSelectedItem(entitet.getBenzinskaCrpka());
//            DefaultListModel<Gorivo> go = new DefaultListModel<>();
//            lstGoriva.setModel(go);
//            goriva.forEach((s) -> {
//                go.addElement(s);
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lstNarudzbaValueChanged

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        entitet = new NarudzbaCisterne();

        spremi();
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed
        if (lstNarudzba.getSelectedValue() == null) {
            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
            return;
        }
        spremi();
    }//GEN-LAST:event_btnPromjeniActionPerformed
    @Override
    protected void spremi() {
        if (datumNarudzbe.getDate() != null) {
            entitet.setVrijemeNarudzbe(Date.from(datumNarudzbe.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        if (datumIsporuke.getDate() != null) {
            entitet.setVrijemeIsporuke(Date.from(datumIsporuke.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        entitet.setTrosak(new BigDecimal(txtTrosak.getText()));
        entitet.setBenzinskaCrpka(cmbBenzinskaCrpka.getItemAt(cmbBenzinskaCrpka.getSelectedIndex()));
        NarudzbaGorivo narudzba = new NarudzbaGorivo();
        narudzba.setKolicina(new BigDecimal(txtKolicina.getText()));
        narudzba.setNabavnaCijena(new BigDecimal(txtNabavnaCijena.getText()));
        if (cmbGorivo == null || cmbGorivo.getModel() == null || cmbGorivo.getModel().getSize() < 1 || cmbGorivo.getSelectedIndex() < 0) {
            narudzba.setGorivo(null);
        } else {
            narudzba.setGorivo(cmbGorivo.getItemAt(cmbGorivo.getSelectedIndex()));
        }

        super.spremi(narudzba);
    }

    private void dodajGorivo(NarudzbaGorivo narudzba) {
        if (datumNarudzbe.getDate() != null) {
            entitet.setVrijemeNarudzbe(Date.from(datumNarudzbe.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        if (datumIsporuke.getDate() != null) {
            entitet.setVrijemeIsporuke(Date.from(datumIsporuke.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        entitet.setTrosak(new BigDecimal(txtTrosak.getText()));
        entitet.setBenzinskaCrpka(cmbBenzinskaCrpka.getItemAt(cmbBenzinskaCrpka.getSelectedIndex()));
        super.spremi(narudzba);
        narudzbaGorivo = HibernateUtil.getSession().createQuery("from NarudzbaGorivo a where a.obrisan=false").list();
        narudzbaGorivoList = new ArrayList();
        for (NarudzbaGorivo ng : narudzbaGorivo) {
            if (ng.getNarudzbaCisterne() != null && entitet.getSifra() != null && Objects.equals(ng.getNarudzbaCisterne().getSifra(), entitet.getSifra())) {
                narudzbaGorivoList.add(ng);
                break;
            }
        }
        ucitajGorivaZaNarudzbu(narudzbaGorivoList);
        ucitajSvaGoriva();
    }

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        if (lstNarudzba.getSelectedValue() == null) {
            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
            return;
        }
        obrisi();
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void cmbBenzinskaCrpkaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBenzinskaCrpkaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBenzinskaCrpkaActionPerformed

    private void btnDodajGorivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajGorivoActionPerformed
        if (cmbGorivo.getSelectedItem() == null) {
            return;
        }
        Gorivo g = (Gorivo) cmbGorivo.getSelectedItem();
        NarudzbaGorivo ng = new NarudzbaGorivo();
        ng.setGorivo(g);
        ng.setNarudzbaCisterne(entitet);
        ng.setKolicina(new BigDecimal(txtKolicina.getText()));
        ng.setNabavnaCijena(new BigDecimal(txtNabavnaCijena.getText()));
        dodajGorivo(ng);
    }//GEN-LAST:event_btnDodajGorivoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnDodajGorivo;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JComboBox<BenzinskaCrpka> cmbBenzinskaCrpka;
    private javax.swing.JComboBox<Gorivo> cmbGorivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<Gorivo> lstGoriva;
    private javax.swing.JList<NarudzbaCisterne> lstNarudzba;
    private javax.swing.JPanel pnlDatumIsporuke;
    private javax.swing.JPanel pnlDatumNarudzbe;
    private javax.swing.JTextField txtKolicina;
    private javax.swing.JTextField txtNabavnaCijena;
    private javax.swing.JTextField txtTrosak;
    private javax.swing.JTextField txtVrijemeIsporuke;
    private javax.swing.JTextField txtVrijemeNarudzbe;
    // End of variables declaration//GEN-END:variables

}
