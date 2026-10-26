using System.Net.Http;
using System.Windows;
using System.Windows.Media;
using System.Security.Cryptography;
using EurekaBank.Shared.Adaptadores;

namespace EurekaBank.Escritorio;

public partial class MainWindow : Window
{
    private readonly IAdaptadorCuenta _adaptadorCuenta;
    private bool _autenticado;

    public MainWindow()
    {
        InitializeComponent();

        // Instancia directa del adaptador usando HttpClient
        var httpClient = new HttpClient();
        _adaptadorCuenta = new AdaptadorCuentaJavaSoap(httpClient);
    }

    private void BtnLogin_Click(object sender, RoutedEventArgs e)
    {
        if (TxtUsuario.Text == "monster" && TxtClave.Password == "monster69")
        {
            _autenticado = true; LoginPanel.Visibility = Visibility.Collapsed; OperationsPanel.Visibility = Visibility.Visible; BtnLogout.Visibility = Visibility.Visible;
            MostrarNotificacion($"Sesión iniciada. Token: {Convert.ToHexString(RandomNumberGenerator.GetBytes(24))}", Colors.Green);
        }
        else MostrarNotificacion("Usuario o contraseña incorrectos.", Colors.Red);
    }

    private void BtnLogout_Click(object sender, RoutedEventArgs e)
    {
        _autenticado = false;
        TxtClave.Clear();
        OperationsPanel.Visibility = Visibility.Collapsed;
        BtnLogout.Visibility = Visibility.Collapsed;
        BorderMensaje.Visibility = Visibility.Collapsed;
        LoginPanel.Visibility = Visibility.Visible;
    }

    private async void BtnConsultarSaldo_Click(object sender, RoutedEventArgs e)
    {
        if (!ValidarDatos(CmbCuentaSaldo.Text)) return;
        string cuenta = CmbCuentaSaldo.Text;
        try
        {
            double saldo = await _adaptadorCuenta.ConsultarSaldoAsync(cuenta);
            MostrarNotificacion($"Saldo de la cuenta {cuenta}: ${saldo:F2}", Colors.Green);
        }
        catch (Exception ex)
        {
            MostrarNotificacion($"Error: {ex.Message}", Colors.Red);
        }
    }

    private async void BtnDeposito_Click(object sender, RoutedEventArgs e)
    {
        if (!ValidarDatos(CmbCuentaDeposito.Text, TxtEmpleadoDeposito.Text)) return;
        if (!double.TryParse(TxtMontoDeposito.Text, out double monto) || !double.IsFinite(monto) || monto <= 0)
        {
            MostrarNotificacion("Ingrese un monto válido.", Colors.OrangeRed);
            return;
        }

        string cuenta = CmbCuentaDeposito.Text;
        try
        {
            string resp = await _adaptadorCuenta.RegistrarDepositoAsync(cuenta, monto, TxtEmpleadoDeposito.Text.Trim());
            MostrarNotificacion(resp, Colors.Green);
            TxtMontoDeposito.Clear();
        }
        catch (Exception ex)
        {
            MostrarNotificacion($"Error: {ex.Message}", Colors.Red);
        }
    }

    private async void BtnRetiro_Click(object sender, RoutedEventArgs e)
    {
        if (!ValidarDatos(CmbCuentaRetiro.Text, TxtEmpleadoRetiro.Text)) return;
        if (!double.TryParse(TxtMontoRetiro.Text, out double monto) || !double.IsFinite(monto) || monto <= 0)
        {
            MostrarNotificacion("Ingrese un monto válido.", Colors.OrangeRed);
            return;
        }

        string cuenta = CmbCuentaRetiro.Text;
        try
        {
            string resp = await _adaptadorCuenta.RegistrarRetiroAsync(cuenta, monto, TxtEmpleadoRetiro.Text.Trim());
            MostrarNotificacion(resp, Colors.DarkGoldenrod);
            TxtMontoRetiro.Clear();
        }
        catch (Exception ex)
        {
            MostrarNotificacion($"Error: {ex.Message}", Colors.Red);
        }
    }

    private async void BtnTransferencia_Click(object sender, RoutedEventArgs e)
    {
        if (!ValidarDatos(CmbCuentaOrigen.Text, TxtEmpleadoTransferencia.Text) || !ValidarDatos(CmbCuentaDestino.Text)) return;
        if (CmbCuentaOrigen.Text.Trim() == CmbCuentaDestino.Text.Trim()) { MostrarNotificacion("Las cuentas deben ser diferentes.", Colors.OrangeRed); return; }
        if (!double.TryParse(TxtMontoTransferencia.Text, out double monto) || !double.IsFinite(monto) || monto <= 0) { MostrarNotificacion("Ingrese un monto válido.", Colors.OrangeRed); return; }
        try { var resp = await _adaptadorCuenta.RegistrarTransferenciaAsync(CmbCuentaOrigen.Text.Trim(), CmbCuentaDestino.Text.Trim(), monto, TxtEmpleadoTransferencia.Text.Trim()); MostrarNotificacion(resp, Colors.Teal); TxtMontoTransferencia.Clear(); }
        catch (Exception ex) { MostrarNotificacion($"Error: {ex.Message}", Colors.Red); }
    }

    private bool ValidarDatos(string cuenta, string? empleado = null)
    {
        if (!_autenticado) return false;
        if (!System.Text.RegularExpressions.Regex.IsMatch(cuenta.Trim(), "^[0-9]{8}$"))
        { MostrarNotificacion("Ingrese la cuenta de ocho dígitos, incluidos sus ceros iniciales.", Colors.OrangeRed); return false; }
        if (empleado != null && !System.Text.RegularExpressions.Regex.IsMatch(empleado.Trim(), "^[0-9]{4}$"))
        { MostrarNotificacion("Ingrese un código de empleado de cuatro dígitos, por ejemplo 0001.", Colors.OrangeRed); return false; }
        return true;
    }

    private void MostrarNotificacion(string mensaje, Color colorFuente)
    {
        if (mensaje.StartsWith("Error", StringComparison.OrdinalIgnoreCase)) colorFuente = Colors.Red;
        TxtMensaje.Text = mensaje;
        TxtMensaje.Foreground = new SolidColorBrush(colorFuente);
        BorderMensaje.Visibility = Visibility.Visible;
    }
}
