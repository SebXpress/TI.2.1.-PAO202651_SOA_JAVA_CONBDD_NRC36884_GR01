using EurekaBank.Shared.Adaptadores;

var builder = WebApplication.CreateBuilder(args);

// Agregar servicios al contenedor.
builder.Services.AddControllersWithViews();

// 1. Registrar HttpClient para la comunicación SOAP
builder.Services.AddHttpClient();

// 2. Registrar los Adaptadores
builder.Services.AddScoped<AdaptadorCuentaJavaSoap>();
builder.Services.AddScoped<IAdaptadorCuenta, AdaptadorCuentaJavaSoap>();

// 3. Registrar la Fábrica de Estrategia
builder.Services.AddScoped<FabricaEstrategiaBackend>();

// Habilitar manejo de sesiones
builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(30);
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();
app.UseSession();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Acceso}/{action=Login}/{id?}");

app.Run();