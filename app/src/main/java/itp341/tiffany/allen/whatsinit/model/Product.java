package itp341.tiffany.allen.whatsinit.model;

import android.media.Image;

/**
 * Created by tiffanyniicole on 5/6/16.
 */

//Store info about individual product; name, Image and URL
public class Product {
    //Instance variables
    private String name;
    private Image productImage;
    private String url;
    private String brand;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product() {
        super();
    }

    public Product(String name, Image productImage) {
        this.name = name;
        this.productImage = productImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    public String getBrand() {return brand;}

    public void setBrand(String brand) {this.brand = brand;}
}
