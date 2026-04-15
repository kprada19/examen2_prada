# Examen Parcial 2 - Sistema de Gestión de Seguros 🛡️

Proyecto desarrollado para la asignatura de Programación, que consiste en un sistema de gestión de seguros (Vida y Vehículo) con persistencia en base de datos MySQL, desarrollado en **JavaFX** y gestionado con **Maven**.

## 🚀 Características
* **Modelo de Datos**: Implementación de herencia con clases `Seguro` (Abstracta), `SeguroVida` y `SeguroVehiculo`.
* **Persistencia**: Uso de patrón DAO para operaciones CRUD en base de datos relacional.
* **Interfaz Gráfica**: Desarrollada en JavaFX con arquitectura MVC.
* **Pruebas**: Inclusión de pruebas unitarias e integración con JUnit 5.

## 🛠️ Tecnologías Utilizadas
* **Java 11** (o superior)
* **Maven** (Gestor de dependencias)
* **MySQL** (Base de datos)
* **JavaFX 13** (Interfaz de usuario)
* **JUnit 5** (Pruebas automatizadas)

## 📋 Requisitos e Instalación

1. **Base de Datos**:
   * Ejecutar el script incluido en la raíz: `script_bd.sql`.
   * Asegurarse de tener configurada la conexión en `ConexionBD.java` con el usuario `root` y clave `1234`.

2. **Configuración en Eclipse**:
   * Importar como un **Existing Maven Project**.
   * Hacer clic derecho sobre el proyecto > **Maven** > **Update Project** para descargar las dependencias.

3. **Ejecución**:
   * Correr la clase `App.java` ubicada en `co.edu.poli.examen2_prada.vista`.

## 🧪 Pruebas (Tests)
Para ejecutar las pruebas y verificar la barra verde en JUnit:
* Clic derecho en la carpeta `src/test/java`.
* Seleccionar **Run As** > **JUnit Test**.
* Se validará la lógica del `toString` y la conexión real a la base de datos.

---
**Autor:** Juan Prada
