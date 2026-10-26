package ec.edu.monster.ws;

import ec.edu.monster.dao.EurekaBankDAO;
import ec.edu.monster.modelo.Movimiento;
import java.util.List;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.UUID;

@WebService(serviceName = "EurekaBankWS")
public class EurekaBankWS {

    private final EurekaBankDAO dao = new EurekaBankDAO();

    @WebMethod(operationName = "autenticar")
    public String autenticar(@WebParam(name = "usuario") String usuario, @WebParam(name = "clave") String clave) {
        try {
            return dao.autenticar(usuario, clave) ? UUID.randomUUID().toString() : "";
        } catch (Exception e) { return ""; }
    }

    @WebMethod(operationName = "consultarSaldo")
    public double consultarSaldo(@WebParam(name = "cuenta") String cuenta) throws Exception {
        return dao.consultarSaldo(cuenta);
    }

    @WebMethod(operationName = "listarMovimientos")
    public List<Movimiento> listarMovimientos(@WebParam(name = "cuenta") String cuenta) throws Exception {
        return dao.listarMovimientos(cuenta);
    }

    @WebMethod(operationName = "registrarDeposito")
    public String registrarDeposito(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "emplcodigo") String emplcodigo) {
        try {
            dao.registrarDeposito(cuenta, importe, emplcodigo);
            return "Depósito realizado exitosamente en la cuenta " + cuenta;
        } catch (Exception e) {
            return "Error al procesar el depósito: " + e.getMessage();
        }
    }

    @WebMethod(operationName = "registrarRetiro")
    public String registrarRetiro(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "emplcodigo") String emplcodigo) {
        try {
            dao.registrarRetiro(cuenta, importe, emplcodigo);
            return "Retiro realizado exitosamente de la cuenta " + cuenta;
        } catch (Exception e) {
            return "Error al procesar el retiro: " + e.getMessage();
        }
    }

    @WebMethod(operationName = "registrarTransferencia")
    public String registrarTransferencia(
            @WebParam(name = "cuentaOrigen") String cuentaOrigen,
            @WebParam(name = "cuentaDestino") String cuentaDestino,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "emplcodigo") String emplcodigo) {
        try {
            dao.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, emplcodigo);
            return "Transferencia realizada exitosamente de " + cuentaOrigen + " a " + cuentaDestino;
        } catch (Exception e) {
            return "Error al procesar la transferencia: " + e.getMessage();
        }
    }
}
