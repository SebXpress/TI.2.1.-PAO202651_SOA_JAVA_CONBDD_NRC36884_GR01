using Microsoft.AspNetCore.Mvc;
using System.Security.Cryptography;

namespace EurekaBank.Web.Controllers;

public class AccesoController : Controller
{
    [HttpGet] public IActionResult Login() => View();
    [HttpPost]
    public IActionResult Login(string usuario, string clave)
    {
        if (usuario == "monster" && clave == "monster69")
        {
            HttpContext.Session.SetString("Usuario", usuario);
            HttpContext.Session.SetString("Token", Convert.ToHexString(RandomNumberGenerator.GetBytes(24)));
            return RedirectToAction("Index", "Cuenta");
        }
        ViewBag.Error = "Credenciales incorrectas.";
        return View();
    }
    public IActionResult Logout() { HttpContext.Session.Clear(); return RedirectToAction("Login"); }
}
