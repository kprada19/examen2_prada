package co.edu.poli.examen2_prada.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.edu.poli.examen2_prada.modelo.*;
import co.edu.poli.examen2_prada.servicios.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class ControlFormSeguro {

    @FXML private TextField txtNumeroConsulta;
    @FXML private TextArea txtAreaResultado; // Coincide con tu FXML
    @FXML private TextField txtNumero;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Asegurado> cmbAsegurado;
    @FXML private RadioButton radioVida;
    @FXML private RadioButton radioVehiculo;
    @FXML private TextField txtExtra; // Marca o Beneficiario

    private DAOSeguro daoSeguro;
    private DAOAsegurado daoAsegurado;

    @FXML
    public void initialize() {
        daoSeguro = new DAOSeguro();
        daoAsegurado = new DAOAsegurado();
        
        // 1. Cargar la fecha actual
        datePicker.setValue(LocalDate.now());

        // 2. Cargar asegurados desde la BD (Dinámico como el del profesor)
        try {
            List<Asegurado> lista = daoAsegurado.readall();
            if (lista != null) {
                cmbAsegurado.getItems().setAll(lista);
            }
        } catch (Exception e) {
            mostrarAlerta("Error de Carga", "No se pudieron cargar los asegurados: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void pressConsulta() {
        String numeroBusqueda = txtNumeroConsulta.getText().trim();
        
        if (numeroBusqueda.isEmpty()) {
            mostrarAlerta("Atención", "Por favor ingrese un número de seguro.", AlertType.WARNING);
            return;
        }

        try {
            Seguro s = daoSeguro.readone(numeroBusqueda);
            if (s != null) {
                txtAreaResultado.setText(s.toString());
            } else {
                txtAreaResultado.clear();
                mostrarAlerta("Consulta", "El seguro con número " + numeroBusqueda + " no existe.", AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error de Base de Datos", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void pressCrear() {
        try {
            // Validaciones de campos vacíos
            if (txtNumero.getText().trim().isEmpty() || 
                cmbAsegurado.getValue() == null || 
                txtExtra.getText().trim().isEmpty()) {
                
                mostrarAlerta("Validación", "Todos los campos son obligatorios para crear el seguro.", AlertType.WARNING);
                return;
            }

            Asegurado seleccionado = cmbAsegurado.getValue();
            
            // Formatear fecha para la BD (String)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaStr = datePicker.getValue().format(formatter);

            Seguro nuevoSeguro;

            if (radioVehiculo.isSelected()) {
                nuevoSeguro = new SeguroVehiculo(
                    txtNumero.getText().trim(), 
                    fechaStr, 
                    true, 
                    seleccionado, 
                    txtExtra.getText().trim()
                );
            } else if (radioVida.isSelected()) {
                nuevoSeguro = new SeguroVida(
                    txtNumero.getText().trim(), 
                    fechaStr, 
                    true, 
                    seleccionado, 
                    txtExtra.getText().trim()
                );
            } else {
                mostrarAlerta("Selección", "Debe seleccionar si es Seguro de Vida o Vehículo.", AlertType.WARNING);
                return;
            }

            // Guardar en BD
            String respuesta = daoSeguro.create(nuevoSeguro);
            
            // Si la respuesta tiene el check de éxito que pusimos en el DAO
            if (respuesta.contains("✔")) {
                mostrarAlerta("Éxito", respuesta, AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", respuesta, AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error Crítico", "Ocurrió un error inesperado: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtNumero.clear();
        txtExtra.clear();
        cmbAsegurado.setValue(null);
        datePicker.setValue(LocalDate.now());
        txtAreaResultado.clear();
        txtNumeroConsulta.clear();
    }
}