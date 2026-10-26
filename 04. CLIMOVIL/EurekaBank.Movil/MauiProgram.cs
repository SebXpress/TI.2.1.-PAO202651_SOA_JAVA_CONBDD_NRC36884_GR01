using EurekaBank.Shared.Adaptadores;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace EurekaBank.Movil;

public static class MauiProgram
{
    public static MauiApp CreateMauiApp()
    {
        var builder = MauiApp.CreateBuilder();
        builder
            .UseMauiApp<App>()
            .ConfigureFonts(fonts =>
            {
                fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
            });

        // Registrar HttpClient
        builder.Services.AddHttpClient();

        // Registrar Adaptadores y Fábrica
        builder.Services.AddSingleton<IAdaptadorCuenta, MobileSoapClient>();

        // Registrar Páginas
        builder.Services.AddTransient<MainPage>();

#if DEBUG
        builder.Logging.AddDebug();
#endif

        return builder.Build();
    }
}
