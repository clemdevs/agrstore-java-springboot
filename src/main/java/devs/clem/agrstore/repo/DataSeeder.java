package devs.clem.agrstore.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import devs.clem.agrstore.models.Agreement;
import devs.clem.agrstore.models.Product;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepo;

    private final AgreementRepository agreementRepo;

    @Autowired
    public DataSeeder(ProductRepository productRepo, AgreementRepository agreementRepo) {
        this.productRepo = productRepo;
        this.agreementRepo = agreementRepo;
    }
    
    @Override
    public void run(String... args) throws Exception {

        List<Product> products = new ArrayList<>();
        products.add(new Product("HP Laptop Testing One", 1234.99));
        products.add(new Product("HP Laptop Beast Gaming", 3234.99));
        products.add(new Product("Razer Mouse Chroma One", 199.99));
        products.add(new Product("NoAgr Asus PC", 2234.99)); //product without agreement
        products.add(new Product("NoAgr Gaming Mouse", 135.99)); //product without agreement
        products.add(new Product("AlienWare Gaming PC", 5614.99));

        List<Agreement> agreements = new ArrayList<>();
        agreements.add(new Agreement("Jon Doe"));
        agreements.add(new Agreement("Anette Nae"));
        agreements.add(new Agreement("Samuel Smith"));

        agreements.get(0).addProduct(products.get(0));
        agreements.get(1).addProduct(products.get(1));
        agreements.get(1).addProduct(products.get(5));
        agreements.get(2).addProduct(products.get(2));

        agreementRepo.saveAll(agreements);
        productRepo.saveAll(products);

    }
    
}
