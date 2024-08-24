package cafeshopmanagmentsystem;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardProductController implements Initializable {

    @FXML
    private Button prod_addBtn;

    @FXML
    private AnchorPane prod_form;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    private SpinnerValueFactory<Integer> spin;

    @SuppressWarnings("unused")
    private productData prodData;
    private Image image;

    private Connection connect;
    private java.sql.PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;
    private String prodID ;
    private String type ;
    private String prod_date ;
    private String prod_image ;

    public void setData(productData prodData) {
        this.prodData = prodData;
        prod_name.setText(prodData.getProdName());
        prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 190, 90, false, true);
        prod_imageView.setImage(image);
        pr = prodData.getPrice();
        prodID = prodData.getProdId();
        type = prodData.getType();
        prod_date =String.valueOf(prodData.getDate()) ;
        prod_image = prodData.getImage();
        System.out.println(prod_date);

    }

    private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    
    private double totalP;
    private double pr;

    public void addBtn() {

        mainFormController mForm = new mainFormController();
        mForm.customerID();
        qty = prod_spinner.getValue();
        System.out.println(qty);
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE prod_id = '"
                + prodID + "'";

        connect = database.connectionDB();
        try {
            int checkstck = 0;
                String checkstock = "SELECT stock FROM product WHERE prod_id = '"
                        + prodID + "'";
                prepare = connect.prepareStatement(checkstock);
                result = prepare.executeQuery();
                System.out.println(qty);
                if (result.next()) {
                    checkstck = result.getInt("stock");
                }
                if(checkstck == 0){
                    String updateStock ="UPDATE product SET "
                                + "prod_name = '" + prod_name.getText() +  
                                "', type = '"+ type +"' , price = '" + pr +
                                "' , status = 'Unvailable' , date = '" + prod_date + 
                                "' , image = '" + prod_image + 
                                "', prod_id = '" + prodID +
                                "' WHERE prod_id = '" + prodID+"'";
                    prepare = connect.prepareStatement(updateStock);
                    prepare.executeUpdate();
                }
            prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if (result.next()) {
                check = result.getString("status");
            }
            if (check.equals("Unavailable") || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Someting Wrong!!");
                alert.showAndWait();
                check = "";
                prod_spinner.setPromptText("0");
            } else {

                if (checkstck < qty) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Le stock de " + prod_name.getText() + " est insuffisante !!");
                    alert.showAndWait();
                } else  {
                    prod_image = prod_image.replace("\\","\\\\");

                    String insertData = "INSERT INTO customer(customer_id , prod_name , quantity , price , date , em_username , prod_id , type , image)"
                            + "VALUES(?,?,?,?,?,?,?,?,?) ";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, String.valueOf(data.cDI));
                    prepare.setString(2, prod_name.getText());
                    prepare.setString(3, String.valueOf(qty));

                    totalP = (qty * pr);
                    prepare.setString(4, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.setString(6, data.username);
                    prepare.setString(7, prodID);
                    prepare.setString(8, type);
                    prepare.setString(9 , prod_image);

                    prepare.executeUpdate();

                    int upStock = checkstck - qty ;

                    prod_image = prod_image.replace("\\","\\\\");
                    String updateStock ="UPDATE product SET "
                                + "prod_name = '" + prod_name.getText() +  "', type = '"
                                + type + "' , stock = '"
                                + upStock + "' , price = '" + pr
                                + "' , status = '" + check
                                + "' , date = '" + prod_date + 
                                "' , image = '" + prod_image + 
                                "', prod_id = '" + prodID +
                                "' WHERE prod_id = '" + prodID+"'";
                    prepare = connect.prepareStatement(updateStock);
                    prepare.executeUpdate();
                    prod_spinner.setPromptText("0");

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();

                    mForm.menuGetTotal();
                    //mForm.menuDisplayTotal();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setQuantity();
    }

}
