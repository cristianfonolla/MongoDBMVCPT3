/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Model.Model;
import Vista.Vista;
import Vista.VistaDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import org.bson.Document;

/**
 *
 * @author cristian
 */
public class Controlador {

    int filasel;
    Vista vista;
    VistaDB vistaDB;
    Model model;

    public Controlador(Model model, Vista vista, VistaDB vistaDB) {
        this.vista = vista;
        this.model = model;
        this.vistaDB = vistaDB;
        control();
        //carregarLlistaDocuments();
        carregarDatabases();

    }

    public void carregarDatabases() {

        DefaultListModel m = new DefaultListModel();

        for (Object database : model.getAllDatabases()) {

            m.addElement(database);

        }

        vistaDB.getjList1().setModel(m);

    }

    public void carregarColections() {

        DefaultListModel m = new DefaultListModel();

        for (Object col : model.getCollections(vistaDB.getjList1().getSelectedValue())) {

            m.addElement(col);

        }

        vista.getjList1().setModel(m);

    }

    public void carregarDocuments() {

        DefaultListModel m = new DefaultListModel();

        for (Document doc : model.getDocuments(vista.getjList1().getSelectedValue(), vistaDB.getjList1().getSelectedValue())) {

            m.addElement(doc);

        }

        vista.getjList2().setModel(m);

    }

//    private void carregarLlistaDocuments() {
//
//        DefaultListModel m = new DefaultListModel();
//
//        for (Object doc : model.getAllDocuments("users")) {
//            m.addElement(doc);
//        }
//
//        vista.getjList1().setModel(m);
//
//    }
    private void control() {
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                //SORTIR
                if (actionEvent.getSource().equals(vista.getjButton6())) {
                    System.out.println("Sortint... ADEU!");
                    System.exit(0);
                }
                //SORTIR DB
                if (actionEvent.getSource().equals(vistaDB.getjButton2())) {
                    System.out.println("Sortint... ADEU!");
                    System.exit(0);
                }
                //Carrega Coleccions DB
                if (actionEvent.getSource().equals(vistaDB.getjButton1())) {
                    carregarColections();
                    vistaDB.setVisible(false);
                    vista.setVisible(true);
                }

                if (actionEvent.getSource().equals(vista.getjButton1())) {

                }
                //Canviar de database
                if (actionEvent.getSource().equals(vista.getjButton7())) {

                    vista.setVisible(false);
                    vistaDB.setVisible(true);

                }

            }

        };

        vista.getjButton6().addActionListener(actionListener);
        vistaDB.getjButton2().addActionListener(actionListener);
        vistaDB.getjButton1().addActionListener(actionListener);
        vista.getjButton1().addActionListener(actionListener);
        vista.getjButton7().addActionListener(actionListener);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getSource().equals(vista.getjList2())) {
                    try {
                        filasel = vista.getjList2().getSelectedIndex();
                        if (filasel != -1) {

                            Document myDoc = vista.getjList2().getSelectedValue();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String json = gson.toJson(myDoc);

                            vista.getjTextArea1().setText(json);
                            
                            
                        }
                    } catch (NumberFormatException ex) {
                    }
                }

                if (e.getSource().equals(vista.getjList1())) {

                    try {
                        filasel = vista.getjList1().getSelectedIndex();
                        if (filasel != -1) {
                            carregarDocuments();
                            //carregaCombo((ArrayList) model.getDocuments(vista.getjList1().getSelectedValue(), vistaDB.getjList1().getSelectedValue()), vista.getjComboBox1());
                        }
                    } catch (NumberFormatException ex) {
                    }
                }

            }

        };
        vista.getjList2().addMouseListener(mouseAdapter);
        vista.getjList1().addMouseListener(mouseAdapter);

    }

    public void carregaCombo(ArrayList resultSet, JComboBox combo) {
        combo.setModel(new DefaultComboBoxModel((resultSet != null ? resultSet.toArray() : new Object[]{})));
    }

}
