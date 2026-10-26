# EurekaBank — Java SOAP con base de datos

Proyecto académico de servicios bancarios SOAP con servidor Java, persistencia en MariaDB y cuatro clientes: consola Java, web ASP.NET Core, escritorio WPF y móvil .NET MAUI.

## Funcionalidades

- Consulta de saldo de cuentas activas.
- Registro de depósitos y retiros con código de empleado.
- Transferencias entre cuentas, dentro de una única transacción de base de datos.
- Validación de cuentas, empleados existentes, montos positivos y saldo disponible.
- Pantallas de inicio y cierre de sesión en los clientes gráficos.

Las cuentas se ingresan como texto de **8 dígitos** y los empleados como **4 dígitos**, conservando sus ceros iniciales. Los importes admiten hasta dos decimales. Ejemplos de formato: cuenta `00100001`, empleado `0001`; deben existir en la base cargada.

## Estructura

| Carpeta | Contenido |
| --- | --- |
| `01. UML` | Requisitos y diagramas UML |
| `02. MER` | Material del modelo entidad-relación |
| `03. BDD` | Scripts SQL y modelo de base de datos |
| `04. SERVIDOR/EurekaBank_SOAP` | Servicio SOAP Java y acceso JDBC |
| `04. CLICON/EurekaBank_Cliente_Console` | Cliente consola Java |
| `04. CLIWEB/EurekaBank.Web` | Cliente web ASP.NET Core |
| `04. CLIESC/EurekaBank.Escritorio` | Cliente de escritorio WPF |
| `04. CLIMOVIL/EurekaBank.Movil` | Cliente móvil .NET MAUI |
| `05. DOCUMENTACION` | Documentación adicional |

La imagen de presentación está en la raíz y tiene copias dentro de los clientes gráficos.

## Requisitos

- JDK 21 y Maven; configurar `JAVA_HOME` y disponer de `java` y `mvn` en `PATH`.
- Payara Server compatible con el proyecto Jakarta EE. El entorno utilizado tiene Payara 7.
- MariaDB, configurado actualmente en `localhost:3307`.
- .NET SDK 10 para los clientes .NET.
- Windows para WPF; Visual Studio con las herramientas de .NET MAUI y Android SDK para el móvil.

### Dependencia compartida de los clientes .NET

Actualmente web, escritorio y móvil referencian una biblioteca **externa a este repositorio**, mediante un `ProjectReference` hacia:

```text
../TI.1.2. PAO202651_SOA_DOTNET_SINBDD_NRC36884_GR01/
  04. CLIESC/EurekaBank/EurekaBank/EurekaBank.csproj
```

Debe estar disponible en esa ubicación, junto a la carpeta de este repositorio, o se deben ajustar los `ProjectReference` de los tres archivos `.csproj`. Clonar únicamente este repositorio no proporciona esa biblioteca.

## Preparar la base de datos

1. Iniciar MariaDB.
2. Revisar y ejecutar `03. BDD/Crea_BD.sql` para crear el esquema y cargar los datos incluidos. **El script contiene instrucciones de eliminación de tablas; usar una base de pruebas o respaldar antes.**
3. `Carga_Datos.sql` también contiene datos iniciales: no ejecutarlo de nuevo sobre una base ya cargada sin revisar su contenido.
4. Ajustar servidor, puerto y credenciales en:

```text
04. SERVIDOR/EurekaBank_SOAP/src/main/java/ec/edu/monster/database/AccesoDB.java
```

La URL configurada es `jdbc:mariadb://localhost:3307/eurekabank`. Las credenciales de desarrollo están en ese archivo; deben adaptarse al entorno.

## Ejecutar el servidor

Desde la raíz, en PowerShell:

```powershell
Set-Location '04. SERVIDOR/EurekaBank_SOAP'
mvn package
```

Desplegar `target/EurekaBank_SOAP-1.0-SNAPSHOT.war` en Payara usando el contexto `/EurekaBank_SOAP`.

- Servicio: `http://localhost:8080/EurekaBank_SOAP/EurekaBankWS`
- WSDL: `http://localhost:8080/EurekaBank_SOAP/EurekaBankWS?wsdl`

Operaciones publicadas: `autenticar`, `consultarSaldo`, `registrarDeposito`, `registrarRetiro` y `registrarTransferencia`.

## Ejecutar los clientes

Iniciar primero MariaDB y desplegar el servidor SOAP.

### Consola

Abrir `04. CLICON/EurekaBank_Cliente_Console/iniciar-consola.cmd`. El lanzador compila, prepara dependencias y ejecuta la clase `ec.edu.monster.app.AppConsola` en la consola de Windows. Busca Maven en `C:\netbeans\java\maven\bin\mvn.cmd` y, si no lo encuentra, usa `mvn.cmd` del `PATH`.

También puede ejecutarse desde NetBeans. La generación de clases SOAP utiliza el WSDL; mantener el servidor accesible al preparar el cliente.

### Web

Desde la raíz:

```powershell
dotnet run --project '04. CLIWEB/EurekaBank.Web' --launch-profile https
```

Abrir `https://localhost:7045` y aceptar/configurar el certificado de desarrollo cuando corresponda.

### Escritorio

```powershell
dotnet run --project '04. CLIESC/EurekaBank.Escritorio'
```

Tras iniciar sesión, ingresar la cuenta y, para transacciones, el código de empleado.

### Móvil Android

Abrir `04. CLIMOVIL/EurekaBank.Movil/EurekaBank.Movil.csproj` en Visual Studio, seleccionar el teléfono o emulador y ejecutar el destino Android.

Para compilar desde PowerShell:

```powershell
dotnet build '04. CLIMOVIL/EurekaBank.Movil' -f net10.0-android
```

La URL SOAP móvil se configura en `MobileSoapClient.cs`, actualmente con el host `192.168.0.165:8080`. Cambiarlo por la IP del equipo que ejecuta Payara. En un teléfono, `localhost` apunta al propio teléfono. Para usar la IP local, ambos dispositivos deben poder comunicarse por la red y el puerto 8080 debe estar accesible. Android está configurado para permitir HTTP en este entorno de desarrollo.

## Acceso y estado de los tokens

Los clientes usan actualmente las credenciales de demostración:

```text
Usuario: monster
Contraseña: monster69
```

**La protección mediante token todavía está incompleta.** Los clientes gráficos validan estas credenciales localmente y generan un token de sesión. La operación SOAP `autenticar` consulta la tabla `usuario` y devuelve un UUID, pero las operaciones bancarias no reciben ni validan ese token. Mostrar un token o bloquear la pantalla de operaciones no equivale a proteger el servicio SOAP. Este estado debe considerarse al evaluar o ampliar la autenticación del proyecto.

## Comprobaciones y problemas frecuentes

- **MariaDB rechaza la conexión:** comprobar que el servicio esté iniciado y escuche en el puerto configurado, actualmente `3307`.
- **Cuenta o empleado inexistente:** verificar los códigos reales en las tablas `cuenta` y `empleado`. Conservar los ceros iniciales.
- **No compilan los clientes .NET:** revisar la biblioteca compartida externa indicada arriba.
- **El móvil no conecta:** revisar la IP en `MobileSoapClient.cs`, la red y el acceso al puerto de Payara. Reinstalar la aplicación después de cambiar la URL.
- **Error XML con carácter `0x00` en `GetPrimaryCpuAbi`:** una interrupción puede dañar `obj/Debug/net10.0-android/devices.cache`. Con la ejecución detenida, renombrar esa caché y volver a ejecutar para que se regenere.
- **Cursor extraño en NetBeans:** usar `iniciar-consola.cmd` para ejecutar en una consola normal de Windows.

El servidor incluye una comprobación manual en `src/test/java/ec/edu/monster/prueba/VerificarValidaciones.java`. Requiere la base local y el controlador JDBC; comprueba operaciones rechazadas y que el saldo no cambie. No es una prueba automática de todos los clientes ni debe ejecutarse mientras otras personas modifican la cuenta de prueba.
