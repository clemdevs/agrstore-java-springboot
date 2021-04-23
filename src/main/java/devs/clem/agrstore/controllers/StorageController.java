package devs.clem.agrstore.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devs.clem.agrstore.models.Agreement;
import devs.clem.agrstore.models.Product;
import devs.clem.agrstore.services.StorageService;

@RestController
@RequestMapping(path = "/api")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String helloWorld(){
        return "Hello from Home Page";
    }
    
    @GetMapping("/products")
    public List<Product> getProducts(){
        return storageService.getProducts();
    }

    @GetMapping("/agreements")
    public List<Agreement> getAgreements(){
        return storageService.getAgreements();
    }


    @GetMapping("/downloads/{Id}")
    public ResponseEntity<FileSystemResource> writeAgreementToFileFromId(@PathVariable("Id") long agreementId) throws IOException{

        String fullPath = storageService.getAgreementFilePathFromId(agreementId);

        Agreement agreementContents = storageService.getAgreementById(agreementId);

        File file = new File(fullPath);
        String fileName = file.getName();
        long fileLength = file.length(); 

       //use Jackson's object mapper to write
        ObjectMapper mapper = new ObjectMapper();
        String stringifiedContent = mapper.writer().writeValueAsString(agreementContents.toString());

        //use the writeValue method to write the object to the file
        mapper.writeValue(file, stringifiedContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentLength(fileLength);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<FileSystemResource>(
            new FileSystemResource(file), headers, HttpStatus.OK
        );
    }
}
