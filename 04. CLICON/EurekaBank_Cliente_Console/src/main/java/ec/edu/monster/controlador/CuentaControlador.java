package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Sesion;
import ec.edu.monster.vista.MenuVista;
import ec.edu.monster.wsclient.EurekaBankWS;
import ec.edu.monster.wsclient.EurekaBankWS_Service;

public class CuentaControlador {

    private final MenuVista vista;
    private final EurekaBankWS port;

    public CuentaControlador() {
        this.vista = new MenuVista();
        EurekaBankWS_Service service = new EurekaBankWS_Service();
        this.port = service.getEurekaBankWSPort();
    }

    public void ejecutar() {
        int opcion;
        do {
            opcion = vista.mostrarMenuPrincipal(Sesion.getInstancia().getUsuario());
            try {
            switch (opcion) {
                case 1 -> consultarSaldo();
                case 2 -> registrarDeposito();
                case 3 -> registrarRetiro();
                case 4 -> registrarTransferencia();
                case 0 -> {
                    Sesion.getInstancia().cerrarSesion();
                    System.out.println("Sesion cerrada. ¡Hasta luego!");
                }
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }
            } catch (Exception e) {
                vista.mostrarResultado("ERROR", "No se completó la operación: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void consultarSaldo() {
        String cuenta = vista.leerTexto("Ingrese numero de cuenta (ej: 00100001)");
        try {
            double saldo = port.consultarSaldo(cuenta);
            vista.mostrarResultado("CONSULTA DE SALDO", "El saldo actual de la cuenta " + cuenta + " es: $" + saldo);
        } catch (Exception e) {
            vista.mostrarResultado("ERROR", e.getMessage());
        }
    }

    private void registrarDeposito() {
        String cuenta = vista.leerTexto("Ingrese numero de cuenta destino");
        double monto = vista.leerMonto("Ingrese el monto a depositar");
        String empleado = vista.leerTexto("Ingrese codigo de empleado (ej: 0001)");

        String resp = port.registrarDeposito(cuenta, monto, empleado);
        vista.mostrarResultado("OPERACION DE DEPOSITO", resp);
    }

    private void registrarRetiro() {
        String cuenta = vista.leerTexto("Ingrese numero de cuenta origen");
        double monto = vista.leerMonto("Ingrese el monto a retirar");
        String empleado = vista.leerTexto("Ingrese codigo de empleado (ej: 0001)");

        String resp = port.registrarRetiro(cuenta, monto, empleado);
        vista.mostrarResultado("OPERACION DE RETIRO", resp);
    }

    private void registrarTransferencia() {
        String origen = vista.leerTexto("Ingrese cuenta origen");
        String destino = vista.leerTexto("Ingrese cuenta destino");
        double monto = vista.leerMonto("Ingrese monto a transferir");
        String empleado = vista.leerTexto("Ingrese codigo de empleado (ej: 0001)");

        String resp = port.registrarTransferencia(origen, destino, monto, empleado);
        vista.mostrarResultado("OPERACION DE TRANSFERENCIA", resp);
    }
}
