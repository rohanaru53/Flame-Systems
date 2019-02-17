package com.example.vaibhav.flamex;

public class ProductList {
    public String name;
    public String weight;
    public String units;
    public String quantity;
    public String price;
    public String category;
    public int quantityinsert;
    public String criticalquantity;
    public String discount;

    public ProductList(String name, String cname, String weight, String units,
                       String quantity, String price,String criticalquantity) {
        super();
        this.category = cname;
        this.name = name;
        this.weight = weight;
        this.units = units;
        this.quantity = quantity;
        this.price = price;
        this.criticalquantity=criticalquantity;
        this.quantityinsert=1;
        this.discount="0.00";
    }

}

