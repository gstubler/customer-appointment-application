import access.DBAppointments;
import access.DBCustomers;
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
import model.Customer;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for Customers.fxml
 */
public class CustomersController implements Initializable {
    @FXML private Button newButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> id;
    @FXML private TableColumn<Customer, String> name;
    @FXML private TableColumn<Customer, String> address;
    @FXML private TableColumn<Customer, String> postalCode;
    @FXML private TableColumn<Customer, String> phoneNumber;
    @FXML private TableColumn<Customer, Integer> divisionId;
    @FXML private TableColumn<Customer, Timestamp> dateCreated;
    @FXML private TableColumn<Customer, Timestamp> dateModified;
    @FXML private TableColumn<Customer, String> createdBy;
    @FXML private TableColumn<Customer, String> modifiedBy;

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * Populates customerTable with all Customers from database.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            name.setText("Nom");
            address.setText("Adresse");
            postalCode.setText("Code postal");
            phoneNumber.setText("Numéro de téléphone");
            dateCreated.setText("Date créée");
            dateModified.setText("Date modifiée");
            createdBy.setText("créé par");
            modifiedBy.setText("modifié par");
            newButton.setText("Nouveau");
            updateButton.setText("Mettre à jour");
            deleteButton.setText("Effacer");
            backButton.setText("Revenir");
        }

        id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        address.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        divisionId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));
        dateCreated.setCellValueFactory(new PropertyValueFactory<Customer, Timestamp>("dateCreated"));
        dateModified.setCellValueFactory(new PropertyValueFactory<Customer, Timestamp>("dateModified"));
        createdBy.setCellValueFactory(new PropertyValueFactory<Customer, String>("createdBy"));
        modifiedBy.setCellValueFactory(new PropertyValueFactory<Customer, String>("modifiedBy"));

        customerTable.setItems(DBCustomers.getAllCustomers());
    }

    /**
     * Listener for newButton. Loads AddCustomer.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void newButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage  = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listener for updateButton. Displays error message if no selection is made. Otherwise, gets selected Customer and
     * loads UpdateCustomer.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void updateButtonListener(ActionEvent actionEvent) throws Exception {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UpdateCustomer.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            UpdateCustomerController controller = loader.getController();
            controller.updateCustomerData(customerTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Listener for deleteButton. Displays error message if no selection is made. Displays error message is selected
     * Customer has any associated Appointments. Otherwise, displays confirmation pop-up and deletes the Customer.
     * @param actionEvent
     * @throws Exception
     */
    public void deleteButtonListener(ActionEvent actionEvent) throws Exception {
        ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
        boolean relatedAppointment = false;

        if (!(customerTable.getSelectionModel().getSelectedItem() == null)) {
            for (int i = 0; i < appointments.size(); i++) {
                if (appointments.get(i).getCustomerId() == customerTable.getSelectionModel().getSelectedItem().getId()) {
                    relatedAppointment = true;
                }
            }
        }

        if (relatedAppointment) {
            if (System.getProperty("user.language").contains("fr")) {
                MessageWindowController.messageText = "Supprimez d'abord le rendez-vous associé.";
            }
            else {
                MessageWindowController.messageText = "Delete associated appointment first.";
            }
            Parent parent = FXMLLoader.load(getClass().getResource("MessageWindow.fxml"));
            Stage popUp = new Stage();
            Scene messageWindow = new Scene(parent);
            popUp.setTitle("Program");
            popUp.setScene(messageWindow);
            popUp.setResizable(false);
            popUp.show();
            System.out.println("Delete associated appointment first.");
        }
        else {
            if (customerTable.getSelectionModel().getSelectedItem() == null) {
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
                    alert.setContentText("Êtes-vous sûr de vouloir supprimer le client sélectionné?");
                }
                else {
                    alert.setContentText("Are you sure you want to delete the selected customer?");
                }

                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes) {
                    DBCustomers.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());
                    customerTable.setItems(DBCustomers.getAllCustomers());
                    if (System.getProperty("user.language").contains("fr")) {
                        MessageWindowController.messageText = "Client supprimé.";
                    } else {
                        MessageWindowController.messageText = "Customer deleted.";
                    }
                    Parent parent = FXMLLoader.load(getClass().getResource("MessageWindow.fxml"));
                    Stage popUp = new Stage();
                    Scene messageWindow = new Scene(parent);
                    popUp.setTitle("Program");
                    popUp.setScene(messageWindow);
                    popUp.setResizable(false);
                    popUp.show();
                }
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
