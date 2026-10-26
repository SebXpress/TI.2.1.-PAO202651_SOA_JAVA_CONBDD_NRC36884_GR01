package ec.edu.monster.dao;

import ec.edu.monster.db.ConexionBD;
import ec.edu.monster.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de la tabla cliente con los nombres pedidos por el inge:
 * insertar (INSERT), seleccionar (SELECT por código), listar (SELECT *),
 * actualizar (UPDATE) y eliminar (DELETE).
 * Todas las conexiones se piden al Singleton y se cierran con try-with-resources.
 */
public class ClienteDAO {

    private static final String COLUMNAS = "chr_cliecodigo, vch_cliepaterno, vch_cliematerno, vch_clienombre, "
            + "chr_cliedni, vch_clieciudad, vch_cliedireccion, vch_clietelefono, vch_clieemail";

    private final ConexionBD bd = ConexionBD.getInstancia();

    /** INSERT INTO cliente. Devuelve true si se insertó una fila. */
    public boolean insertar(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (" + COLUMNAS + ") VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection cn = bd.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getChr_cliecodigo());
            llenar(ps, c, 2);
            return ps.executeUpdate() == 1;
        }
    }

    /** SELECT de un cliente por su código. Devuelve null si no existe. */
    public Cliente seleccionar(String codigo) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM cliente WHERE chr_cliecodigo = ?";
        try (Connection cn = bd.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /** SELECT de todos los clientes ordenados por código. */
    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM cliente ORDER BY chr_cliecodigo";
        List<Cliente> lista = new ArrayList<>();
        try (Connection cn = bd.getConexion(); PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** UPDATE cliente por código. Devuelve true si se actualizó una fila. */
    public boolean actualizar(Cliente c) throws SQLException {
        String sql = "UPDATE cliente SET vch_cliepaterno=?, vch_cliematerno=?, vch_clienombre=?, chr_cliedni=?, "
                + "vch_clieciudad=?, vch_cliedireccion=?, vch_clietelefono=?, vch_clieemail=? WHERE chr_cliecodigo=?";
        try (Connection cn = bd.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            llenar(ps, c, 1);
            ps.setString(9, c.getChr_cliecodigo());
            return ps.executeUpdate() == 1;
        }
    }

    /** DELETE cliente por código. Falla si el cliente tiene cuentas (FK). */
    public boolean eliminar(String codigo) throws SQLException {
        try (Connection cn = bd.getConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM cliente WHERE chr_cliecodigo = ?")) {
            ps.setString(1, codigo);
            return ps.executeUpdate() == 1;
        }
    }

    private void llenar(PreparedStatement ps, Cliente c, int i) throws SQLException {
        ps.setString(i, c.getVch_cliepaterno());
        ps.setString(i + 1, c.getVch_cliematerno());
        ps.setString(i + 2, c.getVch_clienombre());
        ps.setString(i + 3, c.getChr_cliedni());
        ps.setString(i + 4, c.getVch_clieciudad());
        ps.setString(i + 5, c.getVch_cliedireccion());
        ps.setString(i + 6, c.getVch_clietelefono());
        ps.setString(i + 7, c.getVch_clieemail());
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getString("chr_cliecodigo"), rs.getString("vch_cliepaterno"), rs.getString("vch_cliematerno"),
                rs.getString("vch_clienombre"), rs.getString("chr_cliedni"), rs.getString("vch_clieciudad"),
                rs.getString("vch_cliedireccion"), rs.getString("vch_clietelefono"), rs.getString("vch_clieemail"));
    }
}
