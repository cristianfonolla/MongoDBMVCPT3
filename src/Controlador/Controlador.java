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
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.UpdateResult;
import javax.swing.JOptionPane;
import static com.mongodb.client.model.Updates.unset;

/**
 *
 * @author cristian
 */
public class Controlador {

    int filasel;
    Vista vista;
    VistaDB vistaDB;
    Model model;
    String value;

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

    public void carregarKeysCombo() {

//        DefaultComboBoxModel m = new DefaultComboBoxModel();
//
        Document d = vista.getjList2().getSelectedValue();
//
//        Object[] llista = d.keySet().toArray();
//
//        System.out.println(d.keySet());
//
//        for (Object key : llista) {
//            m.addElement(key);
//        }
//
//        vista.getjComboBox2().setModel(m);

        ComboBoxModel m = vista.getjComboBox2().getModel();

        for (Object key : d.keySet().toArray()) {
            vista.getjComboBox2().addItem(key.toString());
        }

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
                //Actualitzar document
                if (actionEvent.getSource().equals(vista.getjButton2())) {

                    Document d = Document.parse(vista.getjTextArea1().getText());
                    System.out.println(d);

                    try {
                        MongoCollection col = model.getDatabase().getCollection(vista.getjList1().getSelectedValue());
                        //UpdateResult resultat = col.updateOne(eq("i", 10), set("i", 110));

                    } catch (Exception e) {
                    }

                }
                //Inserir documents sencers
                if (actionEvent.getSource().equals(vista.getjButton1())) {

                    MongoCollection col = model.getMongoClient().getDatabase(
                            vistaDB.getjList1().getSelectedValue()
                    ).getCollection(
                            vista.getjList1().getSelectedValue()
                    );

                    Document d = Document.parse(vista.getjTextArea1().getText());

                    col.insertOne(d);
                    carregarDocuments();

                }
                //Borrar documents
                if (actionEvent.getSource().equals(vista.getjButton3())) {

                    MongoCollection col = model.getMongoClient().getDatabase(
                            vistaDB.getjList1().getSelectedValue()
                    ).getCollection(
                            vista.getjList1().getSelectedValue()
                    );

                    Document doc = vista.getjList2().getSelectedValue();

                    System.out.println(doc.get("_id.timestamp"));

                    ObjectId id = (ObjectId) doc.get("_id");
                    col.deleteOne(eq("_id", new ObjectId(id.toString())));
                    carregarDocuments();
                }
                //Canviar de database
                if (actionEvent.getSource().equals(vista.getjButton7())) {

                    vista.setVisible(false);
                    vistaDB.setVisible(true);
                    carregarKeysCombo();

                }

                if (actionEvent.getSource().equals(vista.getjButton5())) {

                    try {
                        MongoCollection col = model.getMongoClient().getDatabase(
                                vistaDB.getjList1().getSelectedValue()
                        ).getCollection(
                                vista.getjList1().getSelectedValue()
                        );

                        Document d = vista.getjList2().getSelectedValue();
                        String key = vista.getjComboBox2().getSelectedItem().toString();
                        String value = d.get(key).toString();
                        String newKey = vista.getjTextField1().getText();

                        UpdateResult resultat = col.updateOne(
                                eq(key, value),
                                unset(newKey)
                        );

                        carregarDocuments();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Selecciona un document que modificar");
                    }

                }

                if (actionEvent.getSource().equals(vista.getjButton4())) {
                    try {
                        MongoCollection col = model.getMongoClient().getDatabase(
                                vistaDB.getjList1().getSelectedValue()
                        ).getCollection(
                                vista.getjList1().getSelectedValue()
                        );

                        Document d = vista.getjList2().getSelectedValue();
                        String key = vista.getjComboBox2().getSelectedItem().toString();
                        String value = d.get(key).toString();
                        String newKey = vista.getjTextField1().getText();
                        String newValue = vista.getjTextArea2().getText();

                        if (vista.getjTextField1().getText().equals("")) {
                            UpdateResult resultat = col.updateOne(
                                    eq(key, value),
                                    set(key, newValue)
                            );
                        } else if (!vista.getjTextField1().getText().equals("")) {
                            UpdateResult resultat = col.updateOne(
                                    eq(key, value),
                                    set(newKey, newValue)
                            );
                        }

                        carregarDocuments();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Selecciona un document que modificar");
                    }

                }

            }

        };

        vista.getjButton6().addActionListener(actionListener);
        vistaDB.getjButton2().addActionListener(actionListener);
        vistaDB.getjButton1().addActionListener(actionListener);
        vista.getjButton1().addActionListener(actionListener);
        vista.getjButton7().addActionListener(actionListener);
        vista.getjButton2().addActionListener(actionListener);
        vista.getjButton3().addActionListener(actionListener);
        vista.getjButton5().addActionListener(actionListener);
        vista.getjButton4().addActionListener(actionListener);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getSource().equals(vista.getjList2())) {
                    try {
                        filasel = vista.getjList2().getSelectedIndex();
                        if (filasel != -1) {

                            carregarDocumentsGson();
                            carregarKeysCombo();

                        }
                    } catch (NumberFormatException ex) {
                    }
                }

                if (e.getSource().equals(vista.getjList1())) {

                    try {
                        filasel = vista.getjList1().getSelectedIndex();
                        if (filasel != -1) {
                            carregarDocuments();

                        }
                    } catch (NumberFormatException ex) {
                    }
                }

            }

        };

        vista.getjList2()
                .addMouseListener(mouseAdapter);
        vista.getjList1()
                .addMouseListener(mouseAdapter);

    }

    public void carregarDocumentsGson() {
        Document myDoc = vista.getjList2().getSelectedValue();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(myDoc);

        vista.getjTextArea1().setText(json);
    }

    public void carregaCombo(ArrayList resultSet, JComboBox combo) {
        combo.setModel(new DefaultComboBoxModel((resultSet != null ? resultSet.toArray() : new Object[]{})));
    }

}
