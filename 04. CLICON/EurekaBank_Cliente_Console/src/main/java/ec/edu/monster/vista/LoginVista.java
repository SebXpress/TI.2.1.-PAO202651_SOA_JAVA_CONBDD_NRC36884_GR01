package ec.edu.monster.vista;

import java.util.Scanner;

public class LoginVista {

    public String solicitarUsuario() {
        return EntradaConsola.leerLinea("Ingrese Usuario").trim();
    }

    public String solicitarClave() {
        return EntradaConsola.leerLinea("Ingrese Contrasena");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(">> " + mensaje);
    }
}
