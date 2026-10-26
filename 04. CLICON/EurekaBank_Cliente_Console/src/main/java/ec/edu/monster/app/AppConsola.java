package ec.edu.monster.app;

import ec.edu.monster.controlador.LoginControlador;
import ec.edu.monster.controlador.CuentaControlador;

public class AppConsola {

    public static void main(String[] args) {
        LoginControlador loginCtrl = new LoginControlador();
        
        // Pide usuario y clave al arrancar
        if (loginCtrl.iniciarSesion()) {
            CuentaControlador cuentaCtrl = new CuentaControlador();
            cuentaCtrl.ejecutar();
        } else {
            System.out.println("Aplicacion finalizada por autenticacion fallida.");
        }
    }
}