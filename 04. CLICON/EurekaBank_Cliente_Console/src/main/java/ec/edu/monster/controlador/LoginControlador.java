package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Sesion;
import ec.edu.monster.vista.LoginVista;

public class LoginControlador {

    private final LoginVista vista;

    public LoginControlador() {
        this.vista = new LoginVista();
    }

    public boolean iniciarSesion() {
        System.out.println("\n=================================");
        System.out.println("   INICIO DE SESION EUREKABANK   ");
        System.out.println("=================================");
        
        String usuario = vista.solicitarUsuario();
        String clave = vista.solicitarClave();

        // Validacion de credenciales
        if ("monster".equals(usuario) && "monster69".equals(clave)) {
            Sesion.getInstancia().setUsuario(usuario);
            vista.mostrarMensaje("Acceso concedido. ¡Bienvenido " + usuario + "!");
            return true;
        } else {
            vista.mostrarMensaje("Credenciales incorrectas. Intente nuevamente.");
            return false;
        }
    }
}