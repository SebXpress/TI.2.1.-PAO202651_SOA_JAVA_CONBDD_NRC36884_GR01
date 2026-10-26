package ec.edu.monster.prueba;

import ec.edu.monster.dao.ClienteDAO;
import ec.edu.monster.dao.EurekaBankDAO;
import ec.edu.monster.db.ConexionBD;
import ec.edu.monster.modelo.Cliente;
import ec.edu.monster.modelo.Movimiento;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * Pruebas de conexión a EurekaBank (ejecutar con clic derecho > Run File).
 *
 * 1. Verifica que el Singleton devuelve siempre la misma instancia.
 * 2. Abre una conexión, muestra los datos del servidor y la CIERRA en finally.
 * 3. Recorre el CRUD de cliente: insertar, seleccionar, listar, actualizar, eliminar
 *    (usa un cliente de prueba 99999 que se borra al final; no ensucia la BD).
 * 4. Consulta saldo y movimientos de la cuenta 00100001.
 */
public class PruebasConexion {

    private static final String CODIGO_PRUEBA = "99999";

    public static void main(String[] args) {
        int ok = 0;
        int fallos = 0;

        // ---------- 1. Singleton ----------
        ConexionBD a = ConexionBD.getInstancia();
        ConexionBD b = ConexionBD.getInstancia();
        if (a == b) { ok++; System.out.println("[OK] Singleton: misma instancia -> " + a.getUrl()); }
        else { fallos++; System.out.println("[FALLO] Singleton devolvió instancias distintas"); }

        // ---------- 2. Conexión (siempre se cierra) ----------
        Connection cn = null;
        try {
            cn = a.getConexion();
            DatabaseMetaData md = cn.getMetaData();
            System.out.println("[OK] Conectado a " + md.getDatabaseProductName() + " " + md.getDatabaseProductVersion()
                    + " | BD: " + cn.getCatalog());
            ok++;
        } catch (Exception e) {
            fallos++;
            System.out.println("[FALLO] No se pudo conectar: " + e.getMessage());
        } finally {
            ConexionBD.cerrar(cn);
            try {
                System.out.println("[OK] Conexión cerrada: " + (cn == null || cn.isClosed()));
            } catch (Exception ignorada) { /* sin acción */ }
        }

        // ---------- 3. CRUD de cliente ----------
        ClienteDAO dao = new ClienteDAO();
        try {
            dao.eliminar(CODIGO_PRUEBA); // por si quedó de una ejecución anterior
            Cliente c = new Cliente(CODIGO_PRUEBA, "MONSTER", "PRUEBA", "CLIENTE", "12345678",
                    "SANGOLQUI", "ESPE", "099-999-9999", "monster@espe.edu.ec");

            if (dao.insertar(c)) { ok++; System.out.println("[OK] insertar  -> " + c); }
            else { fallos++; System.out.println("[FALLO] insertar"); }

            Cliente leido = dao.seleccionar(CODIGO_PRUEBA);
            if (leido != null) { ok++; System.out.println("[OK] seleccionar -> " + leido); }
            else { fallos++; System.out.println("[FALLO] seleccionar"); }

            List<Cliente> todos = dao.listar();
            ok++;
            System.out.println("[OK] listar    -> " + todos.size() + " clientes");

            c.setVch_clieciudad("QUITO");
            if (dao.actualizar(c) && "QUITO".equals(dao.seleccionar(CODIGO_PRUEBA).getVch_clieciudad())) {
                ok++; System.out.println("[OK] actualizar -> ciudad = QUITO");
            } else { fallos++; System.out.println("[FALLO] actualizar"); }

            if (dao.eliminar(CODIGO_PRUEBA) && dao.seleccionar(CODIGO_PRUEBA) == null) {
                ok++; System.out.println("[OK] eliminar  -> cliente " + CODIGO_PRUEBA + " borrado");
            } else { fallos++; System.out.println("[FALLO] eliminar"); }
        } catch (Exception e) {
            fallos++;
            System.out.println("[FALLO] CRUD cliente: " + e.getMessage());
        }

        // ---------- 4. Operaciones de cuenta ----------
        EurekaBankDAO banco = new EurekaBankDAO();
        try {
            System.out.println("[OK] Saldo 00100001 = " + banco.consultarSaldo("00100001"));
            List<Movimiento> movs = banco.listarMovimientos("00100001");
            System.out.println("[OK] Movimientos 00100001 = " + movs.size());
            ok += 2;
        } catch (Exception e) {
            fallos++;
            System.out.println("[FALLO] Cuenta: " + e.getMessage());
        }

        System.out.println("--------------------------------------------");
        System.out.println("Resultado: " + ok + " OK, " + fallos + " fallos");
    }
}
