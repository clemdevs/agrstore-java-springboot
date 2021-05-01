package devs.clem.agrstore.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<ByteArrayResource> writeAgreementToFileFromIdv2(@PathVariable("Id") long agreementId) throws IOException
    {
        ByteArrayResource fileToReturn = storageService.getAgreementFilePathFromId2(agreementId);
        String fileName = fileToReturn.getFilename();
        long fileLength = fileToReturn.contentLength();
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName.toString() + "\"");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentLength(fileLength);
        
        return ResponseEntity.ok()
        .headers(headers)
        .contentLength(fileLength)
        .body(fileToReturn);
    }


}
