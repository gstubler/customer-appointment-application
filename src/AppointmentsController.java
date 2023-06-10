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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for Appointments.fxml
 */
public class AppointmentsController implements Initializable {
    @FXML private Button newButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private RadioButton weekRadio;
    @FXML private RadioButton monthRadio;
    @FXML private RadioButton allRadio;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> id;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, String> location;
    @FXML private TableColumn<Appointment, String> contactName;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, Timestamp> start;
    @FXML private TableColumn<Appointment, Timestamp> end;
    @FXML private TableColumn<Appointment, Integer> customerId;

    ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
    ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();


    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * Populates appointmentTable with all Appointments from database.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            weekRadio.setText("Semaine");
            monthRadio.setText("Mois");
            allRadio.setText("Tout");
            title.setText("Titre");
            description.setText("La description");
            location.setText("Emplacement");
            contactName.setText("Nom du contact");
            type.setText("Taper");
            start.setText("Début");
            end.setText("Fin");
            newButton.setText("Nouveau");
            updateButton.setText("Mettre à jour");
            deleteButton.setText("Effacer");
            backButton.setText("Revenir");
        }

        id.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
        title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        location.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        start.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        end.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        customerId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));

        allAppointments.forEach((r) -> r.setStart(Timestamp.valueOf(r.getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));
        allAppointments.forEach((r) -> r.setEnd(Timestamp.valueOf(r.getEnd().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));

        appointmentTable.setItems(allAppointments);
    }

    /**
     * Listener for radio toggle group. Filters appointmentTable based on selected radio.
     * @param actionEvent
     */
    public void toggleListener(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

        if (allRadio.isSelected()) {
            appointmentTable.setItems(allAppointments);
        }
        else if (weekRadio.isSelected()) {
            for (int i = 0; i < allAppointments.size(); i++) {
                if (allAppointments.get(i).getStart().before(Timestamp.valueOf(LocalDateTime.now().plusDays(7))) && allAppointments.get(i).getStart().after(Timestamp.valueOf(LocalDateTime.now()))) {
                    weekAppointments.add(allAppointments.get(i));
                }
            }
            weekAppointments.forEach((r) -> r.setStart(Timestamp.valueOf(r.getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));
            weekAppointments.forEach((r) -> r.setEnd(Timestamp.valueOf(r.getEnd().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));

            appointmentTable.setItems(weekAppointments);
        }
        else if (monthRadio.isSelected()) {
            for (int i = 0; i < allAppointments.size(); i++) {
                if (allAppointments.get(i).getStart().toLocalDateTime().getMonth() == LocalDateTime.now().getMonth()) {
                    monthAppointments.add(allAppointments.get(i));
                }
            }
            monthAppointments.forEach((r) -> r.setStart(Timestamp.valueOf(r.getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));
            monthAppointments.forEach((r) -> r.setEnd(Timestamp.valueOf(r.getEnd().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));
            appointmentTable.setItems(monthAppointments);
        }
    }

    /**
     * Listener for newButton. Loads AddAppointment.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void newButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage  = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listener for updateButton. Displays error message if no selection is made. Otherwise, gets selected Appointment
     * and loads UpdateAppointment.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void updateButtonListener(ActionEvent actionEvent) throws Exception {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            if (System.getProperty("user.language").contains("fr")) {
                MessageWindowController.messageText = "Veuillez faire une sélection.";
            } else {
                MessageWindowController.messageText = "Please make a selection.";
            }
            Parent parent = FXMLLoader.load(getClass().getResource("MessageWindow.fxml"));
            Stage popUp = new Stage();
            Scene messageWindow = new Scene(parent);
            popUp.setTitle("Program");
            popUp.setScene(messageWindow);
            popUp.setResizable(false);
            popUp.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UpdateAppointment.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            UpdateAppointmentController controller = loader.getController();
            controller.updateAppointmentData(appointmentTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Listener for deleteButton. Displays error message if no selection is made. Otherwise, displays confirmation
     * pop-up and deletes the Appointment.
     * @param actionEvent
     * @throws Exception
     */
    public void deleteButtonListener(ActionEvent actionEvent) throws Exception {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            if (System.getProperty("user.language").contains("fr")) {
                MessageWindowController.messageText = "Veuillez faire une sélection.";
            }
            else {
                MessageWindowController.messageText = "Please make a selection.";
            }
            Parent parent = FXMLLoader.load(getClass().getResource("MessageWindow.fxml"));
            Stage popUp = new Stage();
            Scene messageWindow = new Scene(parent);
            popUp.setTitle("Program");
            popUp.setScene(messageWindow);
            popUp.setResizable(false);
            popUp.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Program");
            if (System.getProperty("user.language").contains("fr")) {
                alert.setContentText("Êtes-vous sûr de vouloir supprimer le rendez-vous sélectionné?");
            }
            else {
                alert.setContentText("Are you sure you want to delete the selected appointment?");
            }

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                int deleteId = appointmentTable.getSelectionModel().getSelectedItem().getId();
                String deleteType = appointmentTable.getSelectionModel().getSelectedItem().getType();
                DBAppointments.deleteAppointment(appointmentTable.getSelectionModel().getSelectedItem());
                appointmentTable.setItems(DBAppointments.getAllAppointments());
                if (System.getProperty("user.language").contains("fr")) {
                    MessageWindowController.messageText = "Rendez-vous supprimé.";
                }
                else {
                    MessageWindowController.messageText = "Appointment deleted - ID: " + deleteId + " Type: " + deleteType;
                }
                Parent parent = FXMLLoader.load(getClass().getResource("MessageWindow.fxml"));
                Stage popUp = new Stage();
                Scene messageWindow = new Scene(parent);
                popUp.setTitle("Program");
                popUp.setScene(messageWindow);
                popUp.setResizable(false);
                popUp.show();
                allRadio.setSelected(true);
            }
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

