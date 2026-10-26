# Cambios en la capa de base de datos — EurekaBank SOAP

Ajustes para cumplir lo que pidió el inge Monster para los proyectos **con BDD**.
Nada de la lógica de negocio de Moisés cambió (validaciones, transacciones, bloqueo `FOR UPDATE` siguen igual).

## 03. BDD
| Archivo | Cambio |
|---|---|
| `Crea_BD.sql` | Ahora es **DDL puro**: `DROP DATABASE` / `CREATE DATABASE` / `USE`, 17 `CREATE TABLE`, y las 16 llaves foráneas como `ALTER TABLE ... ADD CONSTRAINT` (CREATE + DROP + ALTER, como pidió el inge). Sin INSERTs. |
| `Carga_Datos.sql` | Solo agrega `USE eurekabank;` al inicio para poder ejecutarlo solo. |

Orden de ejecución: `Crea_BD.sql` → `Carga_Datos.sql`. Probado en MariaDB 10.11: 17 tablas, 16 FK, datos cargados sin errores.

## 04. SERVIDOR / EurekaBank_SOAP
| Archivo | Cambio |
|---|---|
| `ec/edu/monster/db/ConexionBD.java` | **Nuevo.** Conexión con patrón **Singleton** en el paquete `ec.edu.monster.db` (pedido del inge). Lee `db.properties`. |
| `src/main/resources/db.properties` | **Nuevo.** URL, usuario y clave. Se mantiene el **puerto 3307** y `root/rootroot`. Si cambia algo, solo se edita aquí. |
| `ec/edu/monster/dao/EurekaBankDAO.java` | Usa `ConexionBD.getInstancia().getConexion()`. **Nuevo método** `listarMovimientos(cuenta)`. |
| `ec/edu/monster/ws/EurekaBankWS.java` | **Nueva operación SOAP** `listarMovimientos(cuenta)`. |
| `ec/edu/monster/modelo/Cliente.java` | **Nuevo** modelo de la tabla cliente. |
| `ec/edu/monster/dao/ClienteDAO.java` | **Nuevo.** CRUD con nombres en español: `insertar`, `seleccionar`, `listar`, `actualizar`, `eliminar`. |
| `ec/edu/monster/prueba/PruebasConexion.java` | **Nuevo** (reemplaza `PruebaConexion`). Prueba Singleton, conexión (**siempre la cierra en `finally`**), CRUD completo con un cliente temporal 99999 que se borra al final, saldo y movimientos. |
| `ec/edu/monster/database/AccesoDB.java` | **Eliminado** (reemplazado por `ConexionBD`). |
| `ec/edu/monster/prueba/PruebaConexion.java` | **Eliminado** (reemplazado por `PruebasConexion`). |

### ¿Por qué el Singleton entrega una conexión por operación?
La instancia es única (Singleton), pero cada método abre su `Connection` y la cierra con
try-with-resources. Si todos compartieran **una sola** `Connection`, dos depósitos o
transferencias al mismo tiempo se mezclarían en la misma transacción (un `rollback` de uno
desharía el otro). Así se cumple el patrón y el servicio sigue siendo seguro con varios clientes.

## Cómo probar
1. Ejecutar `Crea_BD.sql` y luego `Carga_Datos.sql` en MariaDB (puerto 3307).
2. NetBeans → clic derecho en `PruebasConexion.java` → **Run File**. Debe terminar con `9 OK, 0 fallos`.
3. `VerificarValidaciones` sigue pasando igual que antes.

## Pendiente (no tocado)
- `eurekabank_soap/JakartaRestConfiguration` + `resources/JakartaEE11Resource` son restos de la plantilla REST de NetBeans; se pueden borrar si no se usan.
- `01. UML` todavía tiene diagramas de conversión de unidades; falta el MER en `02. MER` (PowerDesigner → Reverse Engineer desde `Crea_BD.sql`).
- La carpeta dice NRC **36884**; verificar si debe ser **36841** (NRC del grupo).
