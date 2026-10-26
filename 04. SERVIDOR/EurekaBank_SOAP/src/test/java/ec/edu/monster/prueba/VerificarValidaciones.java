package ec.edu.monster.prueba;

import ec.edu.monster.dao.EurekaBankDAO;
import java.sql.SQLException;

/** Prueba manual contra la base local: solo ejecuta operaciones que deben rechazarse. */
public class VerificarValidaciones {
    interface Intento { void ejecutar() throws SQLException; }
    static void rechazar(String caso, Intento intento) throws SQLException {
        try { intento.ejecutar(); }
        catch (SQLException e) { System.out.println("OK " + caso + ": " + e.getMessage()); return; }
        throw new AssertionError("Se aceptó una operación inválida: " + caso);
    }
    public static void main(String[] args) throws Exception {
        EurekaBankDAO dao = new EurekaBankDAO();
        double antes = dao.consultarSaldo("00100001");
        rechazar("importe negativo", () -> dao.registrarDeposito("00100001", -1, "0001"));
        rechazar("importe NaN", () -> dao.registrarDeposito("00100001", Double.NaN, "0001"));
        rechazar("fracción de centavo", () -> dao.registrarDeposito("00100001", 0.001, "0001"));
        rechazar("cuenta mal formada", () -> dao.consultarSaldo("1"));
        rechazar("empleado inexistente", () -> dao.registrarDeposito("00100001", 1, "0000"));
        rechazar("mismo origen y destino", () -> dao.registrarTransferencia("00100001", "00100001", 1, "0001"));
        rechazar("destino inexistente", () -> dao.registrarTransferencia("00100001", "00000000", 1, "0001"));
        rechazar("saldo insuficiente", () -> dao.registrarRetiro("00100001", antes + 1, "0001"));
        if (Double.compare(antes, dao.consultarSaldo("00100001")) != 0) throw new AssertionError("Cambió el saldo tras operaciones rechazadas.");
        System.out.println("OK saldo conservado después de todos los rechazos");
    }
}
