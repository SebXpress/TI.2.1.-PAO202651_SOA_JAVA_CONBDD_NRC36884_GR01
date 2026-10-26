package ec.edu.monster.modelo;

public class Sesion {
    private static Sesion instancia;
    private String usuario;

    private Sesion() {}

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public void cerrarSesion() {
        this.usuario = null;
    }
}