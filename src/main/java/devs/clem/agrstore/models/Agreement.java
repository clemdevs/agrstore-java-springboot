package devs.clem.agrstore.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;



@Entity
@Table(name = "AGREEMENT")
public class Agreement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "agreementID")
    private long Id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Signed_By")
    private String signedBy;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinTable(name = "AGREEMENT_PRODUCTS",
                joinColumns = {
                    @JoinColumn(name = "agreementID")
                }, 
                inverseJoinColumns = {
                    @JoinColumn(name = "productID")
                })
    @JsonIgnoreProperties(value="agreements")
    private Set<Product> products = new HashSet<Product>();

    public Agreement() {}

    public Agreement(String signedBy) {
        this.setSignedBy(signedBy);
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");

        Date date = new Date();  

        String currentDate = formatter.format(date);  
        
        this.name = "Agreement" + " " + currentDate;
    }
    
    public long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String toString(){

        String involves = "List of products included into this agreement:";
        String detailsOfAgreement = String.format("Id: %d%nSigned By: %s%n%n%s%n%n", Id, signedBy, involves);

        String includedProducts = "";
        for(Product p: products){
            String productName = p.getName();
            double productPrice = p.getPrice();
            includedProducts += String.format("Name: %s%nPrice: %.2f%n", productName, productPrice) ;
        }
        return detailsOfAgreement + includedProducts;

    }
    
}
