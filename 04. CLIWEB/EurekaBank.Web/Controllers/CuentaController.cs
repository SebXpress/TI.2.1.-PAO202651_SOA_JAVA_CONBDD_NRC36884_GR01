using EurekaBank.Shared.Adaptadores;
using Microsoft.AspNetCore.Mvc;

namespace EurekaBank.Web.Controllers;

public class CuentaController : Controller
{
    public override void OnActionExecuting(Microsoft.AspNetCore.Mvc.Filters.ActionExecutingContext context)
    {
        if (string.IsNullOrEmpty(HttpContext.Session.GetString("Usuario")) || string.IsNullOrEmpty(HttpContext.Session.GetString("Token")))
            context.Result = RedirectToAction("Login", "Acceso");
        base.OnActionExecuting(context);
    }
    private readonly IAdaptadorCuenta _adaptadorCuenta;

    public override void OnActionExecuted(Microsoft.AspNetCore.Mvc.Filters.ActionExecutedContext context)
    {
        if (ViewBag.Resultado is string mensaje && mensaje.StartsWith("Error", StringComparison.OrdinalIgnoreCase))
            ViewBag.TipoResultado = "danger";
        base.OnActionExecuted(context);
    }

    public CuentaController(FabricaEstrategiaBackend fabrica)
    {
        _adaptadorCuenta = fabrica.ObtenerAdaptadorCuenta(TipoBackend.JavaSoap);
    }

    public IActionResult Index()
    {
        var usuario = HttpContext.Session.GetString("Usuario");
        if (string.IsNullOrEmpty(usuario))
        {
            return RedirectToAction("Login", "Acceso");
        }

        ViewBag.Usuario = usuario;
        ViewBag.Token = HttpContext.Session.GetString("Token");
        return View();
    }

    [HttpPost]
    public async Task<IActionResult> ConsultarSaldo(string cuenta)
    {
        try
        {
            double saldo = await _adaptadorCuenta.ConsultarSaldoAsync(cuenta);
            ViewBag.Resultado = $"El saldo actual de la cuenta {cuenta} es: ${saldo:F2}";
            ViewBag.TipoResultado = "success";
        }
        catch (Exception ex)
        {
            ViewBag.Resultado = $"Error: {ex.Message}";
            ViewBag.TipoResultado = "danger";
        }

        ViewBag.Usuario = HttpContext.Session.GetString("Usuario");
        ViewBag.Token = HttpContext.Session.GetString("Token");
        ViewBag.Operacion = "saldo";
        return View("Index");
    }

    [HttpPost]
    public async Task<IActionResult> RegistrarDeposito(string cuenta, double monto, string empleado)
    {
        try
        {
            string resp = await _adaptadorCuenta.RegistrarDepositoAsync(cuenta, monto, empleado);
            ViewBag.Resultado = resp;
            ViewBag.TipoResultado = "success";
        }
        catch (Exception ex)
        {
            ViewBag.Resultado = $"Error: {ex.Message}";
            ViewBag.TipoResultado = "danger";
        }

        ViewBag.Usuario = HttpContext.Session.GetString("Usuario");
        ViewBag.Token = HttpContext.Session.GetString("Token");
        ViewBag.Operacion = "deposito";
        return View("Index");
    }

    [HttpPost]
    public async Task<IActionResult> RegistrarRetiro(string cuenta, double monto, string empleado)
    {
        try
        {
            string resp = await _adaptadorCuenta.RegistrarRetiroAsync(cuenta, monto, empleado);
            ViewBag.Resultado = resp;
            ViewBag.TipoResultado = "warning";
        }
        catch (Exception ex)
        {
            ViewBag.Resultado = $"Error: {ex.Message}";
            ViewBag.TipoResultado = "danger";
        }

        ViewBag.Usuario = HttpContext.Session.GetString("Usuario");
        ViewBag.Token = HttpContext.Session.GetString("Token");
        ViewBag.Operacion = "retiro";
        return View("Index");
    }

    [HttpPost]
    public async Task<IActionResult> RegistrarTransferencia(string cuentaOrigen, string cuentaDestino, double monto, string empleado)
    {
        try
        {
            string resp = await _adaptadorCuenta.RegistrarTransferenciaAsync(cuentaOrigen, cuentaDestino, monto, empleado);
            ViewBag.Resultado = resp;
            ViewBag.TipoResultado = "info";
        }
        catch (Exception ex)
        {
            ViewBag.Resultado = $"Error: {ex.Message}";
            ViewBag.TipoResultado = "danger";
        }

        ViewBag.Usuario = HttpContext.Session.GetString("Usuario");
        ViewBag.Token = HttpContext.Session.GetString("Token");
        ViewBag.Operacion = "transferencia";
        return View("Index");
    }
}
