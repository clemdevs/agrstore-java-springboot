package devs.clem.agrstore.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devs.clem.agrstore.models.Agreement;
import devs.clem.agrstore.models.Product;
import devs.clem.agrstore.repo.AgreementRepository;
import devs.clem.agrstore.repo.ProductRepository;

@Service
public class StorageService {

    private final ProductRepository productRepo;

    private final AgreementRepository agreementRepo;

    @Autowired
    public StorageService(ProductRepository productRepo, AgreementRepository agreementRepo) {
        this.productRepo = productRepo;
        this.agreementRepo = agreementRepo;
    }

    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    public List<Agreement> getAgreements(){
        return agreementRepo.findAll();
    }


    private static String SERVER_LOCATION = "src/main/resources/uploads";

    public Agreement getAgreementById(long Id){
        return agreementRepo.findById(Id).get();
    }

    public String getAgreementFileNameById(long Id){
        return getAgreementById(Id).toString();
    }

    public String getAgreementFilePathFromId(long Id){

        Agreement agreement = agreementRepo.findById(Id).get();
        String fileExt = ".txt";
        String fileName = agreement.getName().toString() + fileExt;
        String separator = File.separator;
        String thePath = SERVER_LOCATION + separator + fileName;
        //use Jackson's object mapper 
        return thePath;
        
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

}
