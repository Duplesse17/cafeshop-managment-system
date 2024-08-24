package cafeshopmanagmentsystem;

import java.sql.Date;

public class productData {
    
    private Integer id ;
    private String prodId ;
    private String prodName ;
    private String type ;
    private Integer stock ;
    private Double price ;
    private String status ;
    private String image;
    private  Date date ;
    private Integer quantity ;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public productData(Integer id, String prodId, String prodName,Integer quantity , Double price, String image , Date date , String type) {
        this.id = id;
        this.prodId = prodId;
        this.prodName = prodName;
        this.price = price;
        this.image = image;
        this.date = date ;
        this.type = type ;
        this.quantity = quantity ;
    }

    public productData(Integer id,String prodId, String prodName, Integer stock, Double price, String status,
            String image, Date date , String type ) {
        this.id = id;
        this.prodId = prodId;
        this.prodName = prodName;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
        this.type = type ;
    }

    public Integer getId() {
        return id;
    }

    public String getProdId() {
        return prodId;
    }

    public String getType() {
        return type;
    }
    
    public String getProdName() {
        return prodName;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

   
}
