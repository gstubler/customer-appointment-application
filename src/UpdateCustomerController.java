import access.DBCountries;
import access.DBCustomers;
import access.DBDivisions;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Controller class for UpdateCustomer.fxml
 */
public class UpdateCustomerController implements Initializable {
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postalCodeField;
    @FXML private TextField phoneNumberField;
    @FXML private ComboBox<String> countryField;
    @FXML private ComboBox<String> divisionIdField;
    @FXML private Button updateButton;
    @FXML private Button backButton;
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label postalCodeLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label countryLabel;
    @FXML private Label regionLabel;

    private int divisionId;

    private Customer selectedCustomer;
    private Division division;

    ObservableList<Country> countryList = DBCountries.getAllCountries();
    ObservableList<Division> divisionList = DBDivisions.getAllDivisions();

    ObservableList<String> countryNames = FXCollections.observableArrayList();
    ObservableList<String> divisionCountry1Names = FXCollections.observableArrayList();
    ObservableList<String> divisionCountry2Names = FXCollections.observableArrayList();
    ObservableList<String> divisionCountry3Names = FXCollections.observableArrayList();

    /**
     * Retrieves data from selected Customer and populates fields with corresponding data.
     * @param customer
     */
    public void updateCustomerData(Customer customer) {
        selectedCustomer = customer;

        for (int i = 0; i < divisionList.size(); i++) {
            if (divisionList.get(i).getDivisionId() == selectedCustomer.getDivisionId()) {
                division = divisionList.get(i);
            }
        }

        for (int i = 0; i < divisionList.size(); i++) {
            String name = divisionList.get(i).getDivisionName();
            if (divisionList.get(i).getCountryId() == 1) {
                divisionCountry1Names.add(name);
            }
            else if (divisionList.get(i).getCountryId() == 2) {
                divisionCountry2Names.add(name);
            }
            else if (divisionList.get(i).getCountryId() == 3) {
                divisionCountry3Names.add(name);
            }
        }

        idField.setText(String.valueOf(selectedCustomer.getId()));
        nameField.setText(selectedCustomer.getName());
        addressField.setText(selectedCustomer.getAddress());
        postalCodeField.setText(selectedCustomer.getPostalCode());
        phoneNumberField.setText(selectedCustomer.getPhoneNumber());
        countryField.getSelectionModel().select(division.getCountryId() - 1);

        if (countryField.getSelectionModel().getSelectedIndex() == 0) {
            divisionIdField.setDisable(false);
            divisionIdField.setItems(divisionCountry1Names);
        }
        else if (countryField.getSelectionModel().getSelectedIndex() == 1) {
            divisionIdField.setDisable(false);
            divisionIdField.setItems(divisionCountry2Names);
        }
        else if (countryField.getSelectionModel().getSelectedIndex() == 2) {
            divisionIdField.setDisable(false);
            divisionIdField.setItems(divisionCountry3Names);
        }
        else {
            divisionIdField.setDisable(true);
        }

        for (int i = 0; i < divisionIdField.getItems().size(); i++) {
            if (division.getDivisionName().contains(divisionIdField.getItems().get(i))) {
                divisionIdField.getSelectionModel().select(i);
            }
        }
    }

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * Populates countryComboBox.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            nameLabel.setText("Nom:");
            addressLabel.setText("Adresse:");
            postalCodeLabel.setText("Code postal:");
            phoneNumberLabel.setText("Numéro de téléphone:");
            countryLabel.setText("Pays:");
            regionLabel.setText("Région:");
            updateButton.setText("Mettre à jour");
            backButton.setText("Revenir");
        }
        countryList.forEach((n) -> countryNames.add(n.getName()));
        countryField.setItems(countryNames);
    }

    /**
     * Listener for countryComboBox. Enables and populates divisionComboBox based on corresponding Country selection.
     * @param actionEvent
     */
    public void countryIdFieldListener(ActionEvent actionEvent) {
        if (countryField.getSelectionModel().getSelectedIndex() == 0) {
            divisionIdField.setDisable(false);
            divisionIdField.setItems(divisionCountry1Names);
        }
        else if (countryField.getSelectionModel().getSelectedIndex() == 1) {
            divisionIdField.setDisable(false);
            divisionIdField.setItems(divisionCountry2Names);
        }
        else if (countryField.getSelectionModel().getSelectedIndex() == 2) {
            divisionIdField.setDisable(false);
            divisionIdField.setItems(divisionCountry3Names);
        }
        else {
            divisionIdField.setDisable(true);
        }
    }

    /**
     * Listener for updateButton. Converts timestamp from local to UTC. Displays error message if form is not filled correctly.
     * Otherwise, updates selected Customer to database table and loads Customers.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void updateButtonListener(ActionEvent actionEvent) throws Exception {
        String divisionName = divisionIdField.getSelectionModel().getSelectedItem();
        for (int i = 0; i < divisionList.size(); i++) {
            if (divisionName == divisionList.get(i).getDivisionName()) {
                divisionId = divisionList.get(i).getDivisionId();
                break;
            }
        }

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

        if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || postalCodeField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || countryField.getSelectionModel().isEmpty() || divisionIdField.getSelectionModel().isEmpty()) {
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
            DBCustomers.updateCustomer(selectedCustomer.getId(),
                    nameField.getText(),
                    addressField.getText(),
                    postalCodeField.getText(),
                    phoneNumberField.getText(),
                    divisionId,
                    timestamp,
                    LoginController.currentUser);

            Parent parent = FXMLLoader.load(getClass().getResource("Customers.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            System.out.println("Customer updated.");
        }
    }

    /**
     * Listener for backButton. Loads Customers.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void backButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage  = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
