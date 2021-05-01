package devs.clem.agrstore.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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

    public String getAgreementContentsById(long Id){
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

    //logic for creating and writing to file.
    public ByteArrayResource getAgreementFilePathFromId2(Long Id) throws IOException{

        String baseDir = getAgreementFilePathFromId(Id).toString();
        String contents = getAgreementContentsById(Id).toString();
        File resourceFile = new File(baseDir);

        try(FileOutputStream fos = new FileOutputStream(resourceFile)){
            OutputStreamWriter outputFile = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            outputFile.write(contents);
            outputFile.flush();
        } catch (IOException e){
            e.getMessage();
        }
        Path filePath = Paths.get(resourceFile.getAbsolutePath());
        return new ByteArrayResource(Files.readAllBytes(filePath)){
            @Override
            public String getFilename() {
                // TODO Auto-generated method stub
                return resourceFile.getName();
            }
        };
    }
}
