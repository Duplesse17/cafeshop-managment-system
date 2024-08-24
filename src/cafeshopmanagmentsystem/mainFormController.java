package cafeshopmanagmentsystem;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class mainFormController implements Initializable {

    @FXML
    private Label dashboard_totalI;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_NSP;

    @FXML
    private Label dashboard_TI;

    @FXML
    private AreaChart<String, Float> dashboard_customerChart;

    @FXML
    private AreaChart<String, Float> dashboard_incomeChart;

    @FXML
    private TableColumn<customersData, String> customer_col_cashier;

    @FXML
    private TableColumn<customersData, String> customer_col_customerID;

    @FXML
    private TableColumn<customersData, String> customer_col_date;

    @FXML
    private TableColumn<customersData, String> customer_col_total;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private TableView<customersData> customer_tableView;

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<productData, String> inventory_col_date;

    @FXML
    private TableColumn<productData, String> inventory_col_idProduct;

    @FXML
    private TableColumn<productData, String> inventory_col_price;

    @FXML
    private TableColumn<productData, String> inventory_col_productName;

    @FXML
    private TableColumn<productData, String> inventory_col_status;

    @FXML
    private TableColumn<productData, String> inventory_col_stock;

    @FXML
    private TableColumn<productData, String> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TableView<productData> inventory_tabeView;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private Label username;

    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productName;

    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private TextField inventory_stock;

    @FXML
    private ComboBox<?> inventory_type;

    @FXML
    private TextField inventory_idProduct;

    private Alert alert;

    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<productData, String> menu_col_price;

    @FXML
    private TableColumn<productData, String> menu_col_productName;

    @FXML
    private TableColumn<productData, String> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPanel;

    @FXML
    private Button menu_pay;

    @FXML
    private Button menu_receipt;

    @FXML
    private Label menu_total;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AnchorPane menu_remove;

    @FXML
    private ScrollPane menu_scrollPanel;

    @FXML
    private TableView<productData> menu_tableView;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private java.sql.Statement statement;

    private Image image;
    List<String> typesL = new ArrayList<>();

    private ObservableList<productData> cardListData = FXCollections.observableArrayList();

    public ObservableList<productData> menuGetData() {

        String sql = "SELECT * FROM product";

        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"),
                        result.getString("type"));

                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPanel.getChildren().clear();
        menu_gridPanel.getRowConstraints().clear();
        menu_gridPanel.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPanel.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(17));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public ObservableList<productData> menuGetOrder() {
        customerID();
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;
        connect = database.connectionDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;
            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"),
                        result.getString("type"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<productData> menuOrderListData;

    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }

    private Double totalP = 0.0;

    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;

        connect = database.connectionDB();

        try {
            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            } // else{System.err.println("salut la compagnie");}

            // menu_total.setText("$" + totalP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("$" + totalP);
    }

    private double amount;
    private double change;

    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank Amount");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            change = 0;
            if (amount < totalP) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The Amount is ussufisant");
                alert.showAndWait();
                menu_amount.setText("");
            } else {
                change = (amount - totalP);
                menu_change.setText("$" + change);

            }
        }
    }

    public void menuReceiptBtn() {
        if (totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Error items not selected");
            alert.showAndWait();
        } else {
            
             HashMap map = new HashMap();
             map.put("getReceipt ", String.valueOf(cID));
             try {
             //JasperDesign jDesign = JRXmlLoader.load("/home/duplesse_nangmo/report.jrxml");
             JasperReport jReport = JasperCompileManager.compileReport("home/duplesse_nangmo/report.jasper");
             JasperPrint jPrint = JasperFillManager.fillReport(jReport , map , connect);
             
             JasperViewer.viewReport(jPrint , false);
             

            menuRestart();
                        
        } catch (Exception e) {
            e.printStackTrace();
            }
            

        }
    }

    public void menuRemoveBtn() {
        if (getid == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Error items not selected");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;

            connect = database.connectionDB();

            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setHeaderText(null);
                alert.setContentText("You are sure ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                    getid = 0;
                    menuShowOrderData();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getid = 0;

    public void menuSelectOrder() {
        if (getid == 0) {
            productData prod = menu_tableView.getSelectionModel().getSelectedItem();
            int num = menu_tableView.getSelectionModel().getFocusedIndex();
            

            if (num - 1 < -1) {
                return;
            }

            getid = prod.getId();
        }

    }

    public void menuRestart() {
        totalP = 0.0;
        change = 0;
        amount = 0;
        menu_total.setText("$0.0");
        menu_amount.setText("");
        menu_change.setText("$0.0");
    }

    public void menupayBtn() {
        if (totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Order firs");
            alert.showAndWait();
        } else {
            String insertpay = "INSERT INTO receipt(customer_id , total , date , em_username)"
                    + "VALUES(?,?,?,?)";
            connect = database.connectionDB();

            try {
                if (amount < 0 || amount < totalP ) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("invalid amount");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure ?");

                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertpay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.username);
                        prepare.executeUpdate();

                        menuShowOrderData();
                        menuRestart();
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int cID;

    public void customerID() {
        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }
            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }
            data.cDI = cID;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            customer_form.setVisible(false);
            menu_form.setVisible(false);

            dashboartDisplayNC();
            dashboarDisplayTI();
            dashboarDisplayNSP();
            dashboarDisplayTotalI();
            dashboardIncomeChart();
            dashboardCustomerChart();

        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            customer_form.setVisible(false);
            menu_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            customer_form.setVisible(false);
            menu_form.setVisible(true);

            menuDisplayCard();
            menuShowOrderData();
            menuDisplayTotal();
        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(true);

            customersShowData();
        }
    }

    public void inventoryAddBtn() {
        if (inventory_idProduct.getText().isEmpty() || inventory_productName.getText().isEmpty() ||
                inventory_type.getSelectionModel().getSelectedItem() == null ||
                inventory_stock.getText().isEmpty() || inventory_price.getText().isEmpty() ||
                inventory_status.getSelectionModel().getSelectedItem() == null ||
                data.path.isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String checkProID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + inventory_idProduct.getText() + "'";

            connect = database.connectionDB();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProID);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("'" + inventory_idProduct.getText() + "', is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(prod_id , prod_name ,type ,  stock , price , status , image , date ) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_idProduct.getText());
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());
                    prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());
                    prepare.setString(7, data.path.replace("\\", "\\\\"));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    inventoryShowData();
                    inventoryClearBtn();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Registered Product");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryUpdateBtn() {
        if (inventory_idProduct.getText().isEmpty() || inventory_productName.getText().isEmpty() ||
                inventory_type.getSelectionModel().getSelectedItem() == null ||
                inventory_stock.getText().isEmpty() || inventory_price.getText().isEmpty() ||
                inventory_status.getSelectionModel().getSelectedItem() == null ||
                data.path.isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            connect = database.connectionDB();

            try {
                String checkProID = "SELECT prod_id FROM product WHERE prod_id = '"
                        + inventory_idProduct.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkProID);
                if (!result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product id : '" + inventory_idProduct.getText() + "', is not ixist");
                    alert.showAndWait();
                } else {
                    String checkProdName = "SELECT prod_name FROM product WHERE prod_name = '"
                            + inventory_productName.getText() + "'";

                    statement = connect.createStatement();
                    result = statement.executeQuery(checkProdName);

                    if (result.next()) {
                        String checkProIDProd = "SELECT prod_id FROM product WHERE prod_id = '"
                                + inventory_idProduct.getText() + "' AND prod_name <> '"
                                + inventory_productName.getText()
                                + "'";
                        statement = connect.createStatement();
                        result = statement.executeQuery(checkProIDProd);
                        if (result.next()) {
                            alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Product name : '" + inventory_productName.getText() + "', is already taken");
                            alert.showAndWait();
                        } else {
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Message");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Are you sure , you want to Update Product ID: " + inventory_idProduct.getText());
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get().equals(ButtonType.OK)) {

                                String path = data.path.replace("\\", "\\\\");
                                String updateData = "UPDATE product SET "
                                        + "prod_id = '" + inventory_idProduct.getText() + "', prod_name = '"
                                        + inventory_productName.getText() + "', type = '"
                                        + inventory_type.getSelectionModel().getSelectedItem() + "' , stock = '"
                                        + inventory_stock.getText() + "' , price = '" + inventory_price.getText()
                                        + "' , status = '" + inventory_status.getSelectionModel().getSelectedItem()
                                        + "' , date = '" + data.date + "' , image = '" + path + "' WHERE id = "
                                        + data.id;

                                // TO UPDATE YOUR TABLE
                                inventoryShowData();
                                // TO CLEAR YOUR FIELDS
                                inventoryClearBtn();

                                prepare = connect.prepareStatement(updateData);
                                prepare.executeUpdate();

                                alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Successfully Update of " + inventory_idProduct.getText());
                                alert.showAndWait();

                            } else {
                                alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Cancelled");
                                alert.showAndWait();
                            }
                        }

                    } else {

                        alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Message");
                        alert.setHeaderText(null);
                        alert.setContentText(
                                "Are you sure , you want to Update Product ID: " + inventory_idProduct.getText());
                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.get().equals(ButtonType.OK)) {

                            String path = data.path.replace("\\", "\\\\");
                            String updateData = "UPDATE product SET "
                                    + "prod_id = '" + inventory_idProduct.getText() + "', prod_name = '"
                                    + inventory_productName.getText() + "', type = '"
                                    + inventory_type.getSelectionModel().getSelectedItem() + "' , stock = '"
                                    + inventory_stock.getText() + "' , price = '" + inventory_price.getText()
                                    + "' , status = '" + inventory_status.getSelectionModel().getSelectedItem()
                                    + "' , date = '" + data.date + "' , image = '" + path + "' WHERE id = " + data.id;

                            // TO UPDATE YOUR TABLE
                            inventoryShowData();
                            // TO CLEAR YOUR FIELDS
                            inventoryClearBtn();

                            prepare = connect.prepareStatement(updateData);
                            prepare.executeUpdate();

                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Update of " + inventory_idProduct.getText());
                            alert.showAndWait();

                        } else {
                            alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Cancelled");
                            alert.showAndWait();
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Firs select the row to delete");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM product WHERE id = " + data.id;

            connect = database.connectionDB();
            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure , you want to Delete Product ID: " + inventory_idProduct.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    // String deathID = inventory_idProduct.getText();
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    // TO UPDATE YOUR TABLE
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

                    /*
                     * alertalert = new Alert(AlertType.INFORMATION);
                     * alert.setTitle("Information Message");
                     * alert.setHeaderText(null);
                     * alert.setContentText("Successfully Delete of " + deathID);
                     * alert.showAndWait();
                     */
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void inventoryClearBtn() {

        inventory_stock.setText("");
        inventory_idProduct.setText("");
        inventory_price.setText("");
        inventory_productName.setText("");
        inventory_status.getSelectionModel().clearSelection();
        inventory_type.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);
    }

    // LETS MAKE A BEHAVIOR FOR IMPORT BTN FIRST
    public void inventoryImportBtn() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg", "*jpeg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 145, 130, false, true);

            inventory_imageView.setImage(image);
        }
    }

    // MERGE ALL DATA
    public ObservableList<productData> inventoryDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";

        connect = database.connectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prodData;

            while (result.next()) {
                prodData = new productData(result.getInt("id"), result.getString("prod_id"),
                        result.getString("prod_name"), result.getInt("stock"), result.getDouble("price"),
                        result.getString("status"), result.getString("image"), result.getDate("date"),
                        result.getString("type"));
                listData.add(prodData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    // SHOW DATA
    private ObservableList<productData> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_col_idProduct.setCellValueFactory(new PropertyValueFactory<>("prodId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tabeView.setItems(inventoryListData);

    }

    public void inventorySelectData() {

        productData prodData = inventory_tabeView.getSelectionModel().getSelectedItem();
        int num = inventory_tabeView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1)
            System.err.println("Erorr");
        ;

        inventory_idProduct.setText(prodData.getProdId());
        inventory_productName.setText(prodData.getProdName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));
        data.path = prodData.getImage();
        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 145, 130, false, true);
        inventory_imageView.setImage(image);

    }

    private String[] typeList = { "Meals", "Drinks" };

    @SuppressWarnings("unchecked")
    public void inventoryTypeList() {

        List<String> typesL = new ArrayList<>();

        for (String data : typeList) {
            typesL.add(data);
        }

        @SuppressWarnings("rawtypes")
        ObservableList listData = FXCollections.observableArrayList(typesL);
        inventory_type.setItems(listData);

    }

    private String[] statusList = { "Available", "Unavalaible" };

    @SuppressWarnings("unchecked")
    public void inventoryStatusList() {
        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        @SuppressWarnings("rawtypes")
        ObservableList listd = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listd);
    }

    public void logout() {
        try {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure , you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            // inventoryTypeList();
            if (option.get().equals(ButtonType.OK)) {

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                // TO HIDE MAIN FORM
                logout_btn.getScene().getWindow().hide();

                // LINK YOU LOGIN FORM EN SHOW IT
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Cafe Shop Mangement System");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {

        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);

    }

    public ObservableList<customersData> customersDataList() {
        ObservableList<customersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        connect = database.connectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            customersData cData;

            while (result.next()) {
                cData = new customersData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));
                listData.add(cData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<customersData> customersListData;

    public void customersShowData() {

        customersListData = customersDataList();

        customer_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customer_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customer_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customer_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));

        customer_tableView.setItems(customersListData);
    }

    public void dashboartDisplayNC() {
        String sql = "SELECT COUNT(id) FROM receipt";
        connect = database.connectionDB();

        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboarDisplayTI() {
        Date date = new Date();
        java.sql.Date sqldate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total) FROM receipt WHERE date = '" + sqldate + "'";
        connect = database.connectionDB();

        try {
            Double ti = 0.0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            dashboard_TI.setText("$" + String.valueOf(ti));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboarDisplayTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";
        connect = database.connectionDB();

        try {
            float totalI = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                totalI = result.getFloat("SUM(total)");
            }
            dashboard_totalI.setText("$" + String.valueOf(totalI));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardIncomeChart() {
        dashboard_incomeChart.getData().clear();
        String sql = "SELECT date , SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connectionDB();

        XYChart.Series chart = new XYChart.Series<String, Float>();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }

            dashboard_incomeChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardCustomerChart() {
        dashboard_customerChart.getData().clear();
        String sql = "SELECT date , COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connectionDB();
        XYChart.Series chart = new XYChart.Series();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            dashboard_customerChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboarDisplayNSP() {

        String sql = "SELECT COUNT(quantity) FROM customer";
        connect = database.connectionDB();

        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                nc = result.getInt("COUNT(quantity)");
            }
            dashboard_NSP.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        displayUsername();
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        menuDisplayCard();

        menuDisplayTotal();
        menuShowOrderData();
        customersShowData();

        dashboartDisplayNC();
        dashboarDisplayTI();
        dashboarDisplayNSP();
        dashboarDisplayTotalI();
        dashboardIncomeChart();
        dashboardCustomerChart();

    }

}
