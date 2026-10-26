package ec.edu.monster.vista;

import java.util.Scanner;

public class MenuVista {

    public int mostrarMenuPrincipal(String usuario) {
        System.out.println(" __  __   ___   _   _   _____   _____   _____   _____");
        System.out.println("|  \\/  | / _ \\ | \\ | | / ____| |_   _| |  ___| |  __ \\");
        System.out.println("| \\  / || | | ||  \\| || (___     | |   | |__   | |__) |");
        System.out.println("| |\\/| || | | || . ` | \\___ \\    | |   |  __|  |  _  /");
        System.out.println("| |  | || |_| || |\\  | ____) |   | |   | |___  | | \\ \\");
        System.out.println("|_|  |_| \\___/ |_| \\_||_____/    |_|   |_____| |_|  \\_\\");
        System.out.println("========================================================");
        System.out.println("                 >>> SISTEMA SOAP <<<                   ");
        System.out.println("========================================================");
        System.out.println("    Usuario en sesion: " + usuario);
        System.out.println("=================================");
        System.out.println("1. Consultar Saldo");
        System.out.println("2. Registrar Deposito");
        System.out.println("3. Registrar Retiro");
        System.out.println("4. Registrar Transferencia");
        System.out.println("0. Cerrar Sesion / Salir");
        
        try {
            return Integer.parseInt(EntradaConsola.leerLinea("Seleccione una opcion").trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public String leerTexto(String etiqueta) {
        return EntradaConsola.leerLinea(etiqueta).trim();
    }

    public double leerMonto(String etiqueta) {
        while (true) {
            try {
                double monto = Double.parseDouble(EntradaConsola.leerLinea(etiqueta).trim().replace(',', '.'));
                if (Double.isFinite(monto) && monto > 0 && java.math.BigDecimal.valueOf(monto).stripTrailingZeros().scale() <= 2) return monto;
            } catch (NumberFormatException e) { }
            System.out.println("Ingrese un monto positivo con máximo dos decimales.");
        }
    }

    public void mostrarResultado(String titulo, String detalle) {
        System.out.println("\n--- " + titulo + " ---");
        System.out.println(detalle);
    }
}
