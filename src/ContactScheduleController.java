import access.DBAppointments;
import access.DBContacts;
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
import model.Contact;

import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Controller class for ContactSchedule.fxml
 */
public class ContactScheduleController implements Initializable {
    @FXML private Label contactLabel;
    @FXML private Button backButton;
    @FXML private ComboBox<String> contactComboBox;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> id;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, String> location;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, Timestamp> start;
    @FXML private TableColumn<Appointment, Timestamp> end;
    @FXML private TableColumn<Appointment, Integer> customerId;

    ObservableList<Contact> contacts = DBContacts.getAllContacts();
    ObservableList<String> contactNames = FXCollections.observableArrayList();
    ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
    ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary. Sets
     * values for appointmentTable columns and populates contactComboBox with contactNames.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            title.setText("Titre");
            description.setText("La description");
            location.setText("Emplacement");
            type.setText("Taper");
            start.setText("Début");
            end.setText("Fin");
            backButton.setText("Revenir");
        }
        id.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
        title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        location.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        type.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        start.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        end.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        customerId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));

        allAppointments.forEach((r) -> r.setStart(Timestamp.valueOf(r.getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));
        allAppointments.forEach((r) -> r.setEnd(Timestamp.valueOf(r.getEnd().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())));

        contacts.forEach((n) -> contactNames.add(n.getName()));
        contactComboBox.setItems(contactNames);
    }

    /**
     * Listener for contactComboBox. Populates appointmentTable based on selected Contact with corresponding
     * Appointments.
     * @param actionEvent
     */
    public void contactComboBoxListener(ActionEvent actionEvent) {
        filteredAppointments.clear();
        int contactId = 0;
        for (int i = 0; i < contacts.size(); i++) {
            if (contactComboBox.getSelectionModel().getSelectedItem().contains(contacts.get(i).getName())) {
                contactId = contacts.get(i).getId();
                for (int j = 0; j < allAppointments.size(); j++) {
                    if (contactId == allAppointments.get(j).getContactId()) {
                        filteredAppointments.add(allAppointments.get(j));
                    }
                }
            }
        }

        System.out.println(contactId);
        appointmentTable.setItems(filteredAppointments);
    }

    /**
     * Listener for backButton. Loads MainMenu.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void backButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

