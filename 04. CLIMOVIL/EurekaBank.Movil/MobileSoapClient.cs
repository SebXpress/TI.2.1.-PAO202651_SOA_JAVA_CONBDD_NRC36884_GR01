using System.Globalization;
using System.Text;
using System.Xml.Linq;
using EurekaBank.Shared.Adaptadores;

namespace EurekaBank.Movil;

public sealed class MobileSoapClient : IAdaptadorCuenta
{
    private readonly HttpClient _http = new() { Timeout = TimeSpan.FromSeconds(20) };
    private const string Url = "http://192.168.0.165:8080/EurekaBank_SOAP/EurekaBankWS";

    public async Task<double> ConsultarSaldoAsync(string cuenta)
    {
        var result = Extraer(await Enviar("consultarSaldo", $"<cuenta>{cuenta}</cuenta>"));
        return double.TryParse(result, NumberStyles.Any, CultureInfo.InvariantCulture, out var saldo) ? saldo : throw new InvalidOperationException("Saldo inválido.");
    }
    public async Task<string> RegistrarDepositoAsync(string cuenta, double monto, string empleado) => Extraer(await Enviar("registrarDeposito", $"<cuenta>{cuenta}</cuenta><importe>{Inv(monto)}</importe><emplcodigo>{empleado}</emplcodigo>"));
    public async Task<string> RegistrarRetiroAsync(string cuenta, double monto, string empleado) => Extraer(await Enviar("registrarRetiro", $"<cuenta>{cuenta}</cuenta><importe>{Inv(monto)}</importe><emplcodigo>{empleado}</emplcodigo>"));
    public async Task<string> RegistrarTransferenciaAsync(string origen, string destino, double monto, string empleado) => Extraer(await Enviar("registrarTransferencia", $"<cuentaOrigen>{origen}</cuentaOrigen><cuentaDestino>{destino}</cuentaDestino><importe>{Inv(monto)}</importe><emplcodigo>{empleado}</emplcodigo>"));

    private async Task<string> Enviar(string op, string args)
    {
        var envelope = $"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.monster.edu.ec/\"><soapenv:Header/><soapenv:Body><ws:{op}>{args}</ws:{op}></soapenv:Body></soapenv:Envelope>";
        using var response = await _http.PostAsync(Url, new StringContent(envelope, Encoding.UTF8, "text/xml"));
        var body = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode) throw new HttpRequestException($"Servidor SOAP no disponible ({(int)response.StatusCode}).");
        return body;
    }
    private static string Extraer(string xml)
    {
        var doc = XDocument.Parse(xml);
        var fault = doc.Descendants().FirstOrDefault(x => x.Name.LocalName == "faultstring")?.Value;
        if (!string.IsNullOrWhiteSpace(fault)) throw new InvalidOperationException(fault);
        return doc.Descendants().FirstOrDefault(x => x.Name.LocalName == "return")?.Value ?? throw new InvalidOperationException("Respuesta SOAP inválida.");
    }
    private static string Inv(double value) => value.ToString(CultureInfo.InvariantCulture);
}
