import access.DBAppointments;
import javafx.collections.ObservableList;
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
import model.Appointment;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller class for HoursReport.fxml
 */
public class HoursReportController implements Initializable {
    @FXML private Label hoursValueLabel;
    @FXML private Label hoursLabel;
    @FXML private Button backButton;

    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
    int totalHours = 0;

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * Displays total appointments hours for current month.
     *
     * Lambda expression used to easily add total hours from current-month Appointments.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (System.getProperty("user.language").contains("fr")) {
            hoursLabel.setText("Heures de rendez-vous cette semaine:");
            backButton.setText("Revenir");
        }

        appointments.forEach((n) -> {
            if (n.getStart().toLocalDateTime().getMonth() == LocalDateTime.now().getMonth()) {
                totalHours = totalHours + (n.getEnd().toLocalDateTime().getHour() - n.getStart().toLocalDateTime().getHour());
            }
        });
        hoursValueLabel.setText(String.valueOf(totalHours));
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
