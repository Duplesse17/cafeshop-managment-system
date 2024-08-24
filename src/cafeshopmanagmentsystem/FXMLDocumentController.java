package cafeshopmanagmentsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassword;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgot;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHaveAccount;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField fp_username;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

   

    public void loginBtn(){
        if(si_username.getText().isEmpty() || si_password.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(("Error Message"));
            alert.setHeaderText(null);
            alert.setContentText("Please Inser UserName/Password");
            alert.showAndWait();
        }else{
            String selcData = "SELECT username , password FROM employer WHERE username = ? AND password = ?";

            connect = database.connectionDB();
            try {
                prepare = connect.prepareStatement(selcData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2 , si_password.getText());

                result = prepare.executeQuery();
                //IF SUCCESSFULY LOGIN ; THEN PROCEED TO ANOTHER FORM WHICH IS OUP MAIN FORM
                if(result.next()){

                    //TO GET THE USERNAME THAT USER USED
                    data.username = si_username.getText(); 

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(("Information Message"));
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!!");
                    alert.showAndWait();

                    // Link YOUR MAIN FORM 
                    Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
                
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Cafe Shop Managment System");
                    stage.setMinHeight(600);
                    stage.setMinWidth(1100);
                    stage.setScene(scene);
                    stage.show();

                    si_loginBtn.getScene().getWindow().hide();

                }else{// IF NOT ; THEN THE ERRO MESSAGE WILL APPEAR
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(("Error Message"));
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    

    public void regBtn() {

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_answer.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employer (username , password , question , answer , date)"
                    + "VALUES(?,?,?,?,?)";
            connect = database.connectionDB();
            try {
                //CHECK IF USERNAME IS ALREADY RECORDED
                String checkUserName = "SELECT username FROM employer WHERE username = '"
                        + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUserName);
                result = prepare.executeQuery();
                
                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait(); 
                    su_username.setText("");
                }else if(su_password.getText().length() <= 8){
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password , atleast 8 characters is needed");
                    alert.showAndWait();
                }else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());
    
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
    
                    prepare.executeUpdate();
    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Registered Account");
                    alert.showAndWait();
    
                    su_username.setText("");
                    su_password.setText("");
                    su_answer.setText("");
                    su_question.getSelectionModel().clearSelection();

    
                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.8));
    
                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHaveAccount.setVisible(false);
                        side_CreateBtn.setVisible(true);
                    });
    
                    slider.play();
                }
               
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

    private String[] questionList = { "What is your favorite color ?", "What is your favorite food ?",
            "What is your birth date ?" };

    @SuppressWarnings("unchecked")
    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        @SuppressWarnings("rawtypes")
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }

    public void switchforgotPass(){  
        si_loginForm.setVisible(false);      
        fp_questionForm.setVisible(true);
        fp_question.getSelectionModel().clearSelection();
        fp_answer.setText("");
        fp_username.setText("");
        forgotPassQuestionList();
    }

    public void proceedBtn(){
        if(fp_question.getSelectionModel().getSelectedItem() == null 
                || fp_answer.getText().isEmpty() || fp_username.getText().isEmpty()){

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message") ;
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{

            String selectData = "SELECT username , question , answer From employer WHERE username = ? AND question = ? AND answer = ?";
            connect = database.connectionDB();

            try {
                
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();

                if(result.next()){
                    
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                    np_confirmPassword.setText("");
                    np_newPassword.setText("");

                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message") ;
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information ");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void changePassBtn(){

        if(np_confirmPassword.getText().isEmpty() || np_newPassword.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message") ;
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{

            if(np_confirmPassword.getText().equals(np_newPassword.getText())){

                String getDate = "SELECT date FROM employer WHERE username = '"
                    +fp_username.getText() +"'";

                connect = database.connectionDB();
                try {
                    
                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date = "";
                    if(result.next()){
                        date = result.getString("date");
                    }

                    String updatePass = "UPDATE employer SET password = '"
                    +np_newPassword.getText() + "' , question = '"
                    +fp_question.getSelectionModel().getSelectedItem() + "' , answer = '"
                    +fp_answer.getText() + "', date = '"
                    + date +"' WHERE username = '"
                    +fp_username.getText() + "'";

                prepare = connect.prepareStatement(updatePass);
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message") ;
                alert.setHeaderText(null);
                alert.setContentText("Successfuly changed password");
                alert.showAndWait();

                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                //TO CLEAR FIELDS
                np_confirmPassword.setText("");
                np_newPassword.setText("");
                fp_question.getSelectionModel().clearSelection();
                fp_answer.setText("");
                fp_username.setText("");

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message") ;
                alert.setHeaderText(null);
                alert.setContentText("Not macht : ");
                alert.showAndWait();
            }

            
        }
    }

    @SuppressWarnings("unchecked")
    public void forgotPassQuestionList(){
        List<String> listQ = new ArrayList<>();

        for(String data : questionList){
            listQ.add(data);
        }

        @SuppressWarnings("rawtypes")
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
    }

    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }

    public void backToQuestionForm(){
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.8));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHaveAccount.setVisible(true);
                side_CreateBtn.setVisible(false);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                regLquestionList();
            });

            slider.play();
        } else if (event.getSource() == side_alreadyHaveAccount) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.8));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHaveAccount.setVisible(false);
                side_CreateBtn.setVisible(true);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });

            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}