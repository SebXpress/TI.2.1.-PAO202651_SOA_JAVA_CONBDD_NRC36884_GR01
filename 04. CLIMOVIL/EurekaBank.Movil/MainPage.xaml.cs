using EurekaBank.Shared.Adaptadores;

using System.Security.Cryptography;

namespace EurekaBank.Movil;

public partial class MainPage : ContentPage
{
    private readonly IAdaptadorCuenta _adaptadorCuenta;

    private async void OnLoginSizeChanged(object? sender, EventArgs e)
    {
        if (LoginPanel.IsVisible && (TxtUsuario.IsFocused || TxtClave.IsFocused))
            await LoginPanel.ScrollToAsync(BtnIngresar, ScrollToPosition.End, false);
    }

    private void OnLoginClicked(object sender, EventArgs e)
    {
        if (TxtUsuario.Text == "monster" && TxtClave.Text == "monster69")
        {
            LoginPanel.IsVisible = false;
            OperationsPanel.IsVisible = true;
            MostrarMensaje($"Sesión iniciada. Token: {Convert.ToHexString(RandomNumberGenerator.GetBytes(24))}", Colors.Green);
        }
        else MostrarMensaje("Usuario o contraseña incorrectos.", Colors.Red);
    }

    public MainPage(IAdaptadorCuenta adaptadorCuenta)
    {
        InitializeComponent();
        _adaptadorCuenta = adaptadorCuenta;
    }

    private void OnLogoutClicked(object sender, EventArgs e)
    {
        TxtClave.Text = string.Empty;
        OperationsPanel.IsVisible = false;
        LoginPanel.IsVisible = true;
        FrameResultado.IsVisible = false;
    }

    private async void OnConsultarSaldoClicked(object sender, EventArgs e)
    {
        if (!ValidarDatos(PckCuentaSaldo.Text))
        {
            return;
        }

        string cuenta = PckCuentaSaldo.Text.Trim();
        try
        {
            double saldo = await _adaptadorCuenta.ConsultarSaldoAsync(cuenta);
            MostrarMensaje($"Saldo de la cuenta {cuenta}: ${saldo:F2}", Colors.Green);
        }
        catch (Exception ex)
        {
            MostrarMensaje($"Error: {ex.Message}", Colors.Red);
        }
    }

    private async void OnDepositoClicked(object sender, EventArgs e)
    {
        if (!ValidarDatos(PckCuentaDeposito.Text, TxtEmpleadoDeposito.Text ?? "") || !LeerMonto(TxtMontoDeposito.Text, out double monto))
        {
            return;
        }

        string cuenta = PckCuentaDeposito.Text.Trim();
        try
        {
            string resp = await _adaptadorCuenta.RegistrarDepositoAsync(cuenta, monto, TxtEmpleadoDeposito.Text.Trim());
            MostrarMensaje(resp, Colors.Green);
            TxtMontoDeposito.Text = string.Empty;
        }
        catch (Exception ex)
        {
            MostrarMensaje($"Error: {ex.Message}", Colors.Red);
        }
    }

    private async void OnRetiroClicked(object sender, EventArgs e)
    {
        if (!ValidarDatos(PckCuentaRetiro.Text, TxtEmpleadoRetiro.Text ?? "") || !LeerMonto(TxtMontoRetiro.Text, out double monto))
        {
            return;
        }

        string cuenta = PckCuentaRetiro.Text.Trim();
        try
        {
            string resp = await _adaptadorCuenta.RegistrarRetiroAsync(cuenta, monto, TxtEmpleadoRetiro.Text.Trim());
            MostrarMensaje(resp, Colors.DarkGoldenrod);
            TxtMontoRetiro.Text = string.Empty;
        }
        catch (Exception ex)
        {
            MostrarMensaje($"Error: {ex.Message}", Colors.Red);
        }
    }

    private void MostrarMensaje(string mensaje, Color colorFondo)
    {
        if (mensaje.StartsWith("Error", StringComparison.OrdinalIgnoreCase)) colorFondo = Colors.Red;
        LblResultado.Text = mensaje;
        FrameResultado.BackgroundColor = colorFondo;
        FrameResultado.IsVisible = true;
    }

    private async void OnTransferenciaClicked(object sender, EventArgs e)
    {
        if (!ValidarDatos(PckCuentaOrigen.Text, TxtEmpleadoTransferencia.Text ?? "") || !ValidarDatos(PckCuentaDestino.Text) || !LeerMonto(TxtMontoTransferencia.Text, out double monto)) return;
        if (PckCuentaOrigen.Text.Trim() == PckCuentaDestino.Text.Trim()) { MostrarMensaje("Las cuentas deben ser diferentes.", Colors.OrangeRed); return; }
        try { var resp = await _adaptadorCuenta.RegistrarTransferenciaAsync(PckCuentaOrigen.Text.Trim(), PckCuentaDestino.Text.Trim(), monto, TxtEmpleadoTransferencia.Text.Trim()); MostrarMensaje(resp, Colors.Teal); TxtMontoTransferencia.Text = string.Empty; }
        catch (Exception ex) { MostrarMensaje($"Error: {ex.Message}", Colors.Red); }
    }

    private bool ValidarDatos(string? cuenta, string? empleado = null)
    {
        if (!OperationsPanel.IsVisible) return false;
        if (!System.Text.RegularExpressions.Regex.IsMatch(cuenta?.Trim() ?? "", "^[0-9]{8}$"))
        { MostrarMensaje("Ingrese una cuenta de ocho dígitos, conservando los ceros iniciales.", Colors.OrangeRed); return false; }
        if (empleado != null && !System.Text.RegularExpressions.Regex.IsMatch(empleado.Trim(), "^[0-9]{4}$"))
        { MostrarMensaje("Ingrese un código de empleado de cuatro dígitos, por ejemplo 0001.", Colors.OrangeRed); return false; }
        return true;
    }

    private bool LeerMonto(string? texto, out double monto)
    {
        if (double.TryParse(texto?.Trim().Replace(',', '.'), System.Globalization.NumberStyles.AllowDecimalPoint, System.Globalization.CultureInfo.InvariantCulture, out monto)
            && double.IsFinite(monto) && monto > 0 && Math.Round(monto, 2) == monto) return true;
        MostrarMensaje("Ingrese un monto positivo con máximo dos decimales.", Colors.OrangeRed);
        return false;
    }
}
