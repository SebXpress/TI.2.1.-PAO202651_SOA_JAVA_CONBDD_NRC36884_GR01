package ec.edu.monster.vista;

public class VerificarEntrada {
    public static void main(String[] args) {
        LoginVista login = new LoginVista();
        if (!"monster".equals(login.solicitarUsuario())) throw new AssertionError("Usuario truncado");
        if (!"monster69".equals(login.solicitarClave())) throw new AssertionError("Clave truncada");
        MenuVista menu = new MenuVista();
        if (!"00100001".equals(menu.leerTexto("Cuenta"))) throw new AssertionError("Cuenta truncada");
        if (menu.leerMonto("Monto") != 12.34) throw new AssertionError("Monto inválido");
        System.out.println("OK: usuario, clave, cuenta y monto completos.");
    }
}
