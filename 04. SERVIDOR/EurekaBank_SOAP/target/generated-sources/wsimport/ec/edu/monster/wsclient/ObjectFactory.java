
package ec.edu.monster.wsclient;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ec.edu.monster.wsclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Exception_QNAME = new QName("http://ws.monster.edu.ec/", "Exception");
    private static final QName _ConsultarSaldo_QNAME = new QName("http://ws.monster.edu.ec/", "consultarSaldo");
    private static final QName _ConsultarSaldoResponse_QNAME = new QName("http://ws.monster.edu.ec/", "consultarSaldoResponse");
    private static final QName _RegistrarDeposito_QNAME = new QName("http://ws.monster.edu.ec/", "registrarDeposito");
    private static final QName _RegistrarDepositoResponse_QNAME = new QName("http://ws.monster.edu.ec/", "registrarDepositoResponse");
    private static final QName _RegistrarRetiro_QNAME = new QName("http://ws.monster.edu.ec/", "registrarRetiro");
    private static final QName _RegistrarRetiroResponse_QNAME = new QName("http://ws.monster.edu.ec/", "registrarRetiroResponse");
    private static final QName _RegistrarTransferencia_QNAME = new QName("http://ws.monster.edu.ec/", "registrarTransferencia");
    private static final QName _RegistrarTransferenciaResponse_QNAME = new QName("http://ws.monster.edu.ec/", "registrarTransferenciaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ec.edu.monster.wsclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     * @return
     *     the new instance of {@link Exception }
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link ConsultarSaldo }
     * 
     * @return
     *     the new instance of {@link ConsultarSaldo }
     */
    public ConsultarSaldo createConsultarSaldo() {
        return new ConsultarSaldo();
    }

    /**
     * Create an instance of {@link ConsultarSaldoResponse }
     * 
     * @return
     *     the new instance of {@link ConsultarSaldoResponse }
     */
    public ConsultarSaldoResponse createConsultarSaldoResponse() {
        return new ConsultarSaldoResponse();
    }

    /**
     * Create an instance of {@link RegistrarDeposito }
     * 
     * @return
     *     the new instance of {@link RegistrarDeposito }
     */
    public RegistrarDeposito createRegistrarDeposito() {
        return new RegistrarDeposito();
    }

    /**
     * Create an instance of {@link RegistrarDepositoResponse }
     * 
     * @return
     *     the new instance of {@link RegistrarDepositoResponse }
     */
    public RegistrarDepositoResponse createRegistrarDepositoResponse() {
        return new RegistrarDepositoResponse();
    }

    /**
     * Create an instance of {@link RegistrarRetiro }
     * 
     * @return
     *     the new instance of {@link RegistrarRetiro }
     */
    public RegistrarRetiro createRegistrarRetiro() {
        return new RegistrarRetiro();
    }

    /**
     * Create an instance of {@link RegistrarRetiroResponse }
     * 
     * @return
     *     the new instance of {@link RegistrarRetiroResponse }
     */
    public RegistrarRetiroResponse createRegistrarRetiroResponse() {
        return new RegistrarRetiroResponse();
    }

    /**
     * Create an instance of {@link RegistrarTransferencia }
     * 
     * @return
     *     the new instance of {@link RegistrarTransferencia }
     */
    public RegistrarTransferencia createRegistrarTransferencia() {
        return new RegistrarTransferencia();
    }

    /**
     * Create an instance of {@link RegistrarTransferenciaResponse }
     * 
     * @return
     *     the new instance of {@link RegistrarTransferenciaResponse }
     */
    public RegistrarTransferenciaResponse createRegistrarTransferenciaResponse() {
        return new RegistrarTransferenciaResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarSaldo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConsultarSaldo }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "consultarSaldo")
    public JAXBElement<ConsultarSaldo> createConsultarSaldo(ConsultarSaldo value) {
        return new JAXBElement<>(_ConsultarSaldo_QNAME, ConsultarSaldo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarSaldoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConsultarSaldoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "consultarSaldoResponse")
    public JAXBElement<ConsultarSaldoResponse> createConsultarSaldoResponse(ConsultarSaldoResponse value) {
        return new JAXBElement<>(_ConsultarSaldoResponse_QNAME, ConsultarSaldoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarDeposito }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistrarDeposito }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "registrarDeposito")
    public JAXBElement<RegistrarDeposito> createRegistrarDeposito(RegistrarDeposito value) {
        return new JAXBElement<>(_RegistrarDeposito_QNAME, RegistrarDeposito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarDepositoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistrarDepositoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "registrarDepositoResponse")
    public JAXBElement<RegistrarDepositoResponse> createRegistrarDepositoResponse(RegistrarDepositoResponse value) {
        return new JAXBElement<>(_RegistrarDepositoResponse_QNAME, RegistrarDepositoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarRetiro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistrarRetiro }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "registrarRetiro")
    public JAXBElement<RegistrarRetiro> createRegistrarRetiro(RegistrarRetiro value) {
        return new JAXBElement<>(_RegistrarRetiro_QNAME, RegistrarRetiro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarRetiroResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistrarRetiroResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "registrarRetiroResponse")
    public JAXBElement<RegistrarRetiroResponse> createRegistrarRetiroResponse(RegistrarRetiroResponse value) {
        return new JAXBElement<>(_RegistrarRetiroResponse_QNAME, RegistrarRetiroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarTransferencia }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistrarTransferencia }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "registrarTransferencia")
    public JAXBElement<RegistrarTransferencia> createRegistrarTransferencia(RegistrarTransferencia value) {
        return new JAXBElement<>(_RegistrarTransferencia_QNAME, RegistrarTransferencia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarTransferenciaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistrarTransferenciaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.monster.edu.ec/", name = "registrarTransferenciaResponse")
    public JAXBElement<RegistrarTransferenciaResponse> createRegistrarTransferenciaResponse(RegistrarTransferenciaResponse value) {
        return new JAXBElement<>(_RegistrarTransferenciaResponse_QNAME, RegistrarTransferenciaResponse.class, null, value);
    }

}
