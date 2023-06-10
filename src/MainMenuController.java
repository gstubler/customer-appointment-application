import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for MainMenu.fxml
 */
public class MainMenuController implements Initializable  {
    @FXML private Button customersButton;
    @FXML private Button appointmentsButton;
    @FXML private Button monthButton;
    @FXML private Button scheduleButton;
    @FXML private Button hoursButton;
    @FXML private Button logoutButton;
    @FXML private Label mainMenuLabel;

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            mainMenuLabel.setText("Menu principal");
            customersButton.setText("Les clients");
            appointmentsButton.setText("Rendez-vous");
            monthButton.setText("Rapport mensuel");
            scheduleButton.setText("Calendrier des contacts");
            hoursButton.setText("Heures de rendez-vous");
            logoutButton.setText("Se déconnecter");
        }
    }

    /**
     * Listener for customersButton. Loads Customers.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void customersButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listener for appointmentsButton. Loads Appointments.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void appointmentsButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listener for monthButton. Loads MonthReport.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void monthButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("MonthReport.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listener for scheduleButton. Loads ContactSchedule.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void scheduleButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("ContactSchedule.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listener for hoursButton. Loads HoursReport.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void hoursButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("HoursReport.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listener for logoutButton. Loads Logout.fxml window.
     * @param actionEvent
     * @throws Exception
     */
    public void logoutButtonListener(ActionEvent actionEvent) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
