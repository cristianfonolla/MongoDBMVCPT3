/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Model.Model;
import Vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author cristian
 */
public class Controlador {

    Vista vista;
    Model model;

    public Controlador(Model model, Vista vista) {
        this.vista = vista;
        this.model = model;
        control();
    }

    private void control() {
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                //SORTIR
                if (actionEvent.getSource().equals(vista.getjButton6())) {
                    System.out.println("Sortint... ADEU!");
                    System.exit(0);
                }

                if (actionEvent.getSource().equals(vista.getjButton6())) {

                }

            }

        };

        vista.getjButton6().addActionListener(actionListener);
    }
}
