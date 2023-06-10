import access.DBAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for MonthReport.fxml
 */
public class MonthReportController implements Initializable {
    @FXML private Label monthLabel;
    @FXML private Label typeLabel;
    @FXML private Label appointmentsLabel;
    @FXML private ComboBox<String> monthComboBox;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private Button searchButton;
    @FXML private Button backButton;

    ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<String> allAppointmentTypes = FXCollections.observableArrayList();
    ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    int appointments = 0;

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * Populates month and type comboBoxes.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            monthLabel.setText("Mois:");
            typeLabel.setText("Taper:");
            appointmentsLabel.setText("Nombre de rendez-vous:");
            searchButton.setText("Chercher");
            backButton.setText("Revenir");
        }

        for (int i = 1; i <= 12; i++) {
            months.add(String.valueOf(i));
        }

        for (int i = 0; i < allAppointments.size(); i++) {
            if (!appointmentTypes.contains(allAppointments.get(i).getType().toLowerCase())) {
                appointmentTypes.add(allAppointments.get(i).getType().toLowerCase());
            }
        }

        monthComboBox.setItems(months);
        typeComboBox.setItems(appointmentTypes);
    }

    /**
     * Listener for searchButton. Displays number of appointments by month and type.
     * @param actionEvent
     */
    public void searchButtonListener(ActionEvent actionEvent) {
        int selectedMonth = Integer.parseInt(monthComboBox.getSelectionModel().getSelectedItem());
        String selectedType = typeComboBox.getSelectionModel().getSelectedItem();
        appointments = 0;

        for (int i = 0; i < allAppointments.size(); i++) {
            if (allAppointments.get(i).getType().toLowerCase().contentEquals(selectedType) && allAppointments.get(i).getStart().toLocalDateTime().getMonth().getValue() == selectedMonth) {
                appointments++;
            }
        }

        if (System.getProperty("user.language").contains("fr")) {
            appointmentsLabel.setText("Nombre de rendez-vous: " + appointments);
        }
        else {
            appointmentsLabel.setText("Number of appointments: " + appointments);
        }
    }

    /**
     * Listener for backButton. Loads MainMenu.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void backButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage  = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
