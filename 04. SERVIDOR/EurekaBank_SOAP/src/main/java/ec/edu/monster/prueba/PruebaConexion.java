package ec.edu.monster.prueba;

import ec.edu.monster.dao.EurekaBankDAO;

public class PruebaConexion {
    public static void main(String[] args) {
        try {
            EurekaBankDAO dao = new EurekaBankDAO();
            
            // Probamos consultar el saldo de una cuenta existente de Carga_Datos.sql
            String cuentaPrueba = "00100001";
            double saldo = dao.consultarSaldo(cuentaPrueba);
            System.out.println(">>> ÉXITO EN LA CONEXIÓN <<<");
            System.out.println("Saldo de la cuenta " + cuentaPrueba + ": $" + saldo);
            
        } catch (Exception e) {
            System.err.println(">>> ERROR DE CONEXIÓN O DAO <<<");
            e.printStackTrace();
        }
    }
}