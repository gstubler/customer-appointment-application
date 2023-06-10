import access.DBAppointments;
import access.DBUsers;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Controller class for Login.fxml
 */
public class LoginController implements Initializable {
    @FXML private TextField idField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private Button exitButton;
    @FXML private Label userSignInLabel;
    @FXML private Label locationLabel;
    @FXML private Label userIdLabel;
    @FXML private Label passwordLabel;

    public static String currentUser;
    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

    /**
     * Initializable method. Checks system language and translates labels and buttons to French when necessary.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        locationLabel.setText("Location: " + ZoneId.systemDefault());

        if (System.getProperty("user.language").contains("fr")) {
            userSignInLabel.setText("Connexion de l'utilisateur");
            locationLabel.setText("Emplacement: " + ZoneId.systemDefault());
            userIdLabel.setText("ID d'utilisateur:");
            passwordLabel.setText("Mot de passe:");
            loginButton.setText("Connecter");
            exitButton.setText("Sortir");
        }
    }

    /**
     * Listener for loginButton. Checks username/password input against database. Shows pop-up message for bad login
     * attempt. Loads Customers.fxml window on good attempt, and loads notification display to show whether there are
     * upcoming appointments. All login attempts are logged to the login_activity.txt file.
     * @param actionEvent
     * @throws Exception
     */
    public void loginButtonListener(ActionEvent actionEvent) throws Exception {
        ObservableList<User> users = DBUsers.getAllUsers();
        boolean goodLogin = false;
        File loginActivity = new File("login_activity.txt");

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().contentEquals(idField.getText())) {
                if (users.get(i).getPassword().contentEquals(passwordField.getText())) {
                    goodLogin = true;
                    currentUser = idField.getText();

                    fileWriter.append("Login SUCCESSFUL - Username: " + idField.getText() + ", Password: " + passwordField.getText() + ", Time: " + Timestamp.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()) + "." + "\r\n");
                    fileWriter.close();

                    Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage  = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                    boolean appointmentSoon = false;

                    for (int j = 0; j < appointments.size(); j++) {
                        if (appointments.get(j).getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().isAfter(LocalDateTime.now())) {
                            if (appointments.get(j).getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().minusMinutes(15).isBefore(LocalDateTime.now())) {
                                appointmentSoon = true;
                                if (System.getProperty("user.language").contains("fr")) {
                                    MessageWindowController.messageText = "Rendez-vous dans les 15 minutes: " + appointments.get(j).getId() + ", " + appointments.get(j).getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                                }
                                else {
                                    MessageWindowController.messageText = "Appointment within 15 minutes: " + appointments.get(j).getId() + ", " + appointments.get(j).getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                                }
                                try {
                                    Parent parent2 = FXMLLoader.load(getClass().getResource("MessageWindow.fxml"));
                                    Stage popUp = new Stage();
                                    Scene messageWindow = new Scene(parent2);
                                    popUp.setTitle("Program");
                                    popUp.setScene(messageWindow);
                                    popUp.setResizable(false);
                                    popUp.show();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (!appointmentSoon) {
                        if (System.getProperty("user.language").contains("fr")) {
                            MessageWindowController.messageText = "Aucun rendez-vous à venir.";
                        }
                        else {
                            MessageWindowController.messageText = "No upcoming appointments.";
                        }
                        try {
                            Parent parent2 = FXMLLoader.load(getClass().getResource("MessageWindow.fxml"));
                            Stage popUp = new Stage();
                            Scene messageWindow = new Scene(parent2);
                            popUp.setTitle("Program");
                            popUp.setScene(messageWindow);
                            popUp.setResizable(false);
                            popUp.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if (!goodLogin) {
            fileWriter.append("Login FAILED - Username: " + idField.getText() + ", Password: " + passwordField.getText() + ", Time: " + Timestamp.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()) + "." + "\r\n");
            fileWriter.close();

            if (System.getProperty("user.language").contains("fr")) {
                MessageWindowController.messageText = "Nom d'utilisateur / Mot de passe incorrect.";
            }
            else {
                MessageWindowController.messageText = "Incorrect username/password.";
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

    /**
     * Listener for exitButton. Closes the program.
     * @param actionEvent
     */
    public void exitButtonListener(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
