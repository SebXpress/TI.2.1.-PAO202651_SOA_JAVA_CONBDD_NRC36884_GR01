package ec.edu.monster.dao;

import ec.edu.monster.db.ConexionBD;
import ec.edu.monster.modelo.Movimiento;
import java.sql.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

public class EurekaBankDAO {
    public boolean autenticar(String usuario, String clave) throws SQLException {
        String hash;
        try { hash = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-1").digest(clave.getBytes(StandardCharsets.UTF_8))); }
        catch (java.security.NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
        try (Connection cn = ConexionBD.getInstancia().getConexion(); PreparedStatement ps = cn.prepareStatement("SELECT 1 FROM usuario WHERE vch_emplusuario=? AND vch_emplclave=? AND vch_emplestado='ACTIVO'")) {
            ps.setString(1, usuario); ps.setString(2, hash);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public double consultarSaldo(String cuenta) throws SQLException {
        cuenta = cuenta(cuenta);
        try (Connection cn = ConexionBD.getInstancia().getConexion(); PreparedStatement ps = cn.prepareStatement("SELECT dec_cuensaldo FROM cuenta WHERE chr_cuencodigo=? AND vch_cuenestado='ACTIVO'")) {
            ps.setString(1, cuenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException("La cuenta no existe o no está activa.");
                return rs.getBigDecimal(1).doubleValue();
            }
        }
    }

    /** Lista los movimientos de una cuenta (del más reciente al más antiguo). */
    public List<Movimiento> listarMovimientos(String cuenta) throws SQLException {
        cuenta = cuenta(cuenta);
        String sql = "SELECT chr_cuencodigo, int_movinumero, DATE_FORMAT(dtt_movifecha,'%Y-%m-%d %H:%i:%s') AS fecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia "
                + "FROM movimiento WHERE chr_cuencodigo=? ORDER BY int_movinumero DESC";
        List<Movimiento> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.getInstancia().getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cuenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movimiento m = new Movimiento();
                    m.setChr_cuencodigo(rs.getString(1));
                    m.setInt_movinumero(rs.getInt(2));
                    m.setDtt_movifecha(rs.getString(3));
                    m.setChr_emplcodigo(rs.getString(4));
                    m.setChr_tipocodigo(rs.getString(5));
                    m.setDec_moviimporte(rs.getBigDecimal(6).doubleValue());
                    m.setChr_cuenreferencia(rs.getString(7));
                    lista.add(m);
                }
            }
        }
        return lista;
    }

    public void registrarDeposito(String cuenta, double importe, String empleado) throws SQLException { operar(cuenta, null, importe, empleado, false); }
    public void registrarRetiro(String cuenta, double importe, String empleado) throws SQLException { operar(cuenta, null, importe, empleado, true); }
    public void registrarTransferencia(String origen, String destino, double importe, String empleado) throws SQLException {
        if (destino == null) throw new SQLException("Ingrese la cuenta destino.");
        operar(origen, cuenta(destino), importe, empleado, true);
    }

    private void operar(String origen, String destino, double importe, String empleado, boolean salida) throws SQLException {
        origen = cuenta(origen);
        if (destino != null && origen.equals(destino)) throw new SQLException("Las cuentas origen y destino deben ser diferentes.");
        if (!Double.isFinite(importe) || importe <= 0) throw new SQLException("Ingrese un importe positivo y finito.");
        BigDecimal monto = BigDecimal.valueOf(importe);
        if (monto.stripTrailingZeros().scale() > 2) throw new SQLException("El importe admite máximo dos decimales.");
        if (empleado == null || !empleado.trim().matches("[0-9]{1,4}")) throw new SQLException("Ingrese un código de empleado de cuatro dígitos, por ejemplo 0001.");
        empleado = String.format("%4s", empleado.trim()).replace(' ', '0');
        try (Connection cn = ConexionBD.getInstancia().getConexion()) {
            cn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = cn.prepareStatement("SELECT 1 FROM empleado WHERE chr_emplcodigo=?")) {
                    ps.setString(1, empleado);
                    try (ResultSet rs = ps.executeQuery()) { if (!rs.next()) throw new SQLException("El empleado " + empleado + " no existe."); }
                }
                // Lock both accounts in a stable order to avoid opposite-transfer deadlocks.
                String primera = destino != null && destino.compareTo(origen) < 0 ? destino : origen;
                String segunda = destino == null ? null : primera.equals(origen) ? destino : origen;
                bloquear(cn, primera);
                if (segunda != null) bloquear(cn, segunda);
                movimiento(cn, origen, salida ? monto.negate() : monto, empleado, destino == null ? (salida ? "004" : "003") : "009", destino);
                if (destino != null) movimiento(cn, destino, monto, empleado, "008", origen);
                cn.commit();
            } catch (SQLException e) {
                try { cn.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        }
    }

    private void bloquear(Connection cn, String cuenta) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement("SELECT chr_cuencodigo FROM cuenta WHERE chr_cuencodigo=? AND vch_cuenestado='ACTIVO' FOR UPDATE")) {
            ps.setString(1, cuenta);
            try (ResultSet rs = ps.executeQuery()) { if (!rs.next()) throw new SQLException("La cuenta " + cuenta + " no existe o no está activa."); }
        }
    }

    private void movimiento(Connection cn, String cuenta, BigDecimal cambio, String empleado, String tipo, String referencia) throws SQLException {
        BigDecimal saldo; int numero;
        try (PreparedStatement ps = cn.prepareStatement("SELECT dec_cuensaldo, int_cuencontmov FROM cuenta WHERE chr_cuencodigo=?")) {
            ps.setString(1, cuenta);
            try (ResultSet rs = ps.executeQuery()) { rs.next(); saldo = rs.getBigDecimal(1).add(cambio); numero = rs.getInt(2) + 1; }
        }
        if (saldo.signum() < 0) throw new SQLException("Saldo insuficiente.");
        try (PreparedStatement ps = cn.prepareStatement("UPDATE cuenta SET dec_cuensaldo=?, int_cuencontmov=? WHERE chr_cuencodigo=?")) {
            ps.setBigDecimal(1, saldo); ps.setInt(2, numero); ps.setString(3, cuenta); ps.executeUpdate();
        }
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO movimiento(chr_cuencodigo,int_movinumero,dtt_movifecha,chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia) VALUES(?,?,CURRENT_TIMESTAMP,?,?,?,?)")) {
            ps.setString(1, cuenta); ps.setInt(2, numero); ps.setString(3, empleado); ps.setString(4, tipo); ps.setBigDecimal(5, cambio.abs()); ps.setString(6, referencia); ps.executeUpdate();
        }
    }

    private String cuenta(String valor) throws SQLException {
        if (valor == null || !valor.trim().matches("[0-9]{8}")) throw new SQLException("Ingrese una cuenta de ocho dígitos, conservando los ceros iniciales.");
        return valor.trim();
    }
}
