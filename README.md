# Rest Api using the Spring Boot Framework

##We have two classes - Agreement and Product

Agreement class has fields:
*name - string that has the agreement name that it generates by rule "Agreement " + current date in format day_month_year.
*signed by - string with name of person who signed an agreement.
*products - collection of products that are included into this agreement. 
This collection has only products that are directly under agreement.
        
Product class has fields:
+parent object - reference to agreement or parent product.
+products - collection of children products.
+name - string with the product name.
+price - number with product's price. Can be non integer.

What the API does 
  1) receives Agreement object and stores it into a file with agreement's name.
  2) receives file path to agreement saved in previous point (in src/main/resources/uploads and creates Agreement object with all nested products. 
  3) By navigating to /api/downloads/Id (the agreement's Id) the file can be downloaded
