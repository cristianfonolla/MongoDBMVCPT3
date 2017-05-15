/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controlador.Controlador;
import Model.Model;
import Vista.Vista;
import Vista.VistaDB;

/**
 *
 * @author cristian
 */
public class Main {

    private static Model model = new Model();
    private static Vista vista = new Vista();
    private static VistaDB vistaDB = new VistaDB();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Controlador(model, vista, vistaDB);
        vista.setVisible(false);
        vistaDB.setVisible(true);
    }

}
