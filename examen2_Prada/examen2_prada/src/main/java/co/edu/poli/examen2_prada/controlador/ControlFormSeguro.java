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
    @FXML private TextArea txtResultado;
    @FXML private TextField txtNumero;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Asegurado> cmbAsegurado;
    @FXML private RadioButton radioVida;
    @FXML private RadioButton radioVehiculo;
    @FXML private TextField txtExtra; // Marca o Beneficiario

    private DAOSeguro dao;

    @FXML
    public void initialize() {
        dao = new DAOSeguro();
        
        // Cargar datos iniciales (Idealmente desde un DAOAsegurado)
        cmbAsegurado.getItems().addAll(
            new Asegurado("A001", "Juan Prada"),
            new Asegurado("A002", "Maria Lopez")
        );

        datePicker.setValue(LocalDate.now());
    }

    @FXML
    public void pressConsulta() {
        if (txtNumeroConsulta.getText().isEmpty()) {
            mostrarAlerta("Error", "Ingrese un número de seguro para buscar.", AlertType.WARNING);
            return;
        }

        try {
            Seguro s = dao.readone(txtNumeroConsulta.getText());
            if (s != null) {
                txtResultado.setText(s.toString());
            } else {
                mostrarAlerta("No encontrado", "El seguro no existe en la base de datos.", AlertType.INFORMATION);
                txtResultado.clear();
            }
        } catch (Exception e) {
            mostrarAlerta("Error BD", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void pressCrear() {
        try {
            if (txtNumero.getText().isEmpty() || cmbAsegurado.getValue() == null) {
                mostrarAlerta("Error", "Campos obligatorios faltantes", AlertType.WARNING);
                return;
            }

            Seguro s;
            Asegurado a = cmbAsegurado.getValue();
            String fecha = datePicker.getValue().toString();

            if (radioVehiculo.isSelected()) {
                s = new SeguroVehiculo(txtNumero.getText(), fecha, true, a, txtExtra.getText());
            } else {
                s = new SeguroVida(txtNumero.getText(), fecha, true, a, txtExtra.getText());
            }

            String mensaje = dao.create(s);
            
            // Ventana de éxito
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sistema de Seguros");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();

            limpiarCampos();

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear: " + e.getMessage(), AlertType.ERROR);
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
        txtResultado.clear();
    }
}