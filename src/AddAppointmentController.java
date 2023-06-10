import access.DBAppointments;
import access.DBContacts;
import access.DBCustomers;
import access.DBUsers;
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
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Controller class for AddAppointment.fxml
 */
public class AddAppointmentController implements Initializable {
    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private TextField locationField;
    @FXML private TextField typeField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> startTimeComboBox;
    @FXML private ComboBox<String> endTimeComboBox;
    @FXML private ComboBox<String> customerComboBox;
    @FXML private ComboBox<String> userComboBox;
    @FXML private ComboBox<String> contactComboBox;
    @FXML private Label idLabel;
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label typeLabel;
    @FXML private Label dateLabel;
    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label customerIdLabel;
    @FXML private Label userIdLabel;
    @FXML private Label contactIdLabel;
    @FXML private Button addButton;
    @FXML private Button backButton;

    ObservableList<Customer> customers = DBCustomers.getAllCustomers();
    ObservableList<User> users = DBUsers.getAllUsers();
    ObservableList<Contact> contacts = DBContacts.getAllContacts();
    ObservableList<String> customersString = FXCollections.observableArrayList();
    ObservableList<String> usersString = FXCollections.observableArrayList();
    ObservableList<String> contactsString = FXCollections.observableArrayList();

    /**
     * Converts EST business hours to local system timezone. Case 1 is used for start hours and case 2 is used for end
     * hours.
     * @param choice
     * @return
     */
    public ObservableList<String> getHours(int choice) {
        ObservableList<String> localHours = FXCollections.observableArrayList();
        ZonedDateTime utcHour = ZonedDateTime.of(datePicker.getValue(), LocalTime.of(12, 00), ZoneId.of("UTC"));

        try {
            switch (choice) {
                case 1:
                    if (datePicker.getValue().isBefore(LocalDate.now()) || datePicker.getValue().getDayOfWeek().toString().contains("SUNDAY") || datePicker.getValue().getDayOfWeek().toString().contains("SATURDAY")) {
                        startTimeComboBox.setDisable(true);
                        endTimeComboBox.setDisable(true);
                    } else {
                        startTimeComboBox.setDisable(false);
                        for (int i = 0; i <= 13; i++) {
                            localHours.add(utcHour.withZoneSameInstant(ZoneId.systemDefault()).plusHours(i).toLocalDateTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                        }
                        for (int i = localHours.size() - 1; i >= 0; i--) {
                            if (LocalDateTime.of(datePicker.getValue(), LocalTime.parse(localHours.get(i), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))).isBefore(LocalDateTime.now())) {
                                localHours.remove(i);
                            }
                        }
                    }
                    break;

                case 2:
                    endTimeComboBox.setDisable(false);
                    for (int i = 1; i <= 14; i++) {
                        localHours.add(utcHour.withZoneSameInstant(ZoneId.systemDefault()).plusHours(i).toLocalDateTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                    }
                    for (int i = 0; i < localHours.size(); i++) {
                        if (startTimeComboBox.getSelectionModel().getSelectedItem().contains(localHours.get(i))) {
                            for (int j = i; j >= 0; j--) {
                                localHours.remove(j);
                            }
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return localHours;
    }

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * Populates comboBoxes for customerId, userId, and contactId.
     *
     * Lambda expressions used to populate fields without multiple for-loops.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            titleLabel.setText("Titre:");
            descriptionLabel.setText("La description:");
            locationLabel.setText("Emplacement:");
            typeLabel.setText("Taper:");
            startLabel.setText("Début:");
            endLabel.setText("Fin:");
            customerIdLabel.setText("ID de client:");
            userIdLabel.setText("ID d'utilisateur:");
            contactIdLabel.setText("ID de contact:");
            addButton.setText("Ajouter");
            backButton.setText("Revenir");
        }
        customers.forEach((n) -> customersString.add(String.valueOf(n.getId())));
        users.forEach((n) -> usersString.add(String.valueOf(n.getId())));
        contacts.forEach((n) -> contactsString.add(String.valueOf(n.getId())));

        customerComboBox.setItems(customersString);
        userComboBox.setItems(usersString);
        contactComboBox.setItems(contactsString);
    }

    /**
     * Listener for datePicker. Enables and populates startTimeComboBox.
     * @param actionEvent
     */
    public void datePickerListener(ActionEvent actionEvent) {
        startTimeComboBox.getItems().clear();
        startTimeComboBox.setItems(getHours(1));
        startTimeComboBox.setDisable(false);
        endTimeComboBox.getItems().clear();
        endTimeComboBox.setDisable(true);
    }

    /**
     * Listener for startTimeComboBox. Enables and populates endTimeComboBox.
     * @param actionEvent
     */
    public void startTimeComboBoxListener(ActionEvent actionEvent) {
        endTimeComboBox.getItems().clear();
        endTimeComboBox.setItems(getHours(2));
        endTimeComboBox.setDisable(false);
    }

    /**
     * Listener for addButton. Converts timestamps from local to UTC. Displays error message if form is not filled
     * correctly. Displays error message if there is overlap with an existing Appointment. Otherwise, adds new
     * Appointment to database table and loads Appointments.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void addButtonListener(ActionEvent actionEvent) throws Exception {
        Timestamp dateCreated = Timestamp.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
        ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
        boolean overlap = false;

        if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() || locationField.getText().isEmpty() || typeField.getText().isEmpty() || datePicker.getValue() == null || startTimeComboBox.getSelectionModel().isEmpty() || endTimeComboBox.getSelectionModel().isEmpty() || customerComboBox.getSelectionModel().isEmpty() || userComboBox.getSelectionModel().isEmpty() || contactComboBox.getSelectionModel().isEmpty()) {
            if (System.getProperty("user.language").contains("fr")) {
                MessageWindowController.messageText = "Veuillez remplir le formulaire en entier.";
            }
            else {
                MessageWindowController.messageText = "Please fill out entire form.";
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
            Timestamp start = Timestamp.valueOf(LocalDateTime.of(datePicker.getValue(), LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem(), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
            Timestamp end = Timestamp.valueOf(LocalDateTime.of(datePicker.getValue(), LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem(), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
            for (int i = 0; i < allAppointments.size(); i++) {
                if (((start.before(allAppointments.get(i).getEnd())) && (start.after(allAppointments.get(i).getStart()))) || ((end.before(allAppointments.get(i).getEnd())) && (end.after(allAppointments.get(i).getStart()))) || ((allAppointments.get(i).getStart().after(start)) && (allAppointments.get(i).getStart().before(end))) || ((allAppointments.get(i).getEnd().after(start)) && (allAppointments.get(i).getEnd().before(end))) || start.equals(allAppointments.get(i).getStart()) || end.equals(allAppointments.get(i).getEnd())) {
                    overlap = true;
                    break;
                }
            }
            if (overlap) {
                if (System.getProperty("user.language").contains("fr")) {
                    MessageWindowController.messageText = "Chevauchement de rendez-vous. Veuillez sélectionner une autre heure.";
                }
                else {
                    MessageWindowController.messageText = "Appointment overlap. Please select a different time.";
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
                DBAppointments.addAppointment(  titleField.getText(),
                                                descriptionField.getText(),
                                                locationField.getText(),
                                                typeField.getText(),
                                                start,
                                                end,
                                                dateCreated,
                                                dateCreated,
                                                LoginController.currentUser,
                                                LoginController.currentUser,
                                                Integer.parseInt(customerComboBox.getSelectionModel().getSelectedItem()),
                                                Integer.parseInt(userComboBox.getSelectionModel().getSelectedItem()),
                                                Integer.parseInt(contactComboBox.getSelectionModel().getSelectedItem()));

                Parent parent = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /**
     * Listener for backButton. Loads Appointments.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void backButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage  = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
