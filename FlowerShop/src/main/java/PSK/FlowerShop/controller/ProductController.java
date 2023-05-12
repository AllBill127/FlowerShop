package PSK.FlowerShop.controller;

import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.request.AddProductDTO;
import PSK.FlowerShop.request.ProductDTO;
import PSK.FlowerShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody AddProductDTO request) {
        try {
            ProductDTO product = productService.createProduct(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(product);
        } catch (Exception e) {
            ProductDTO errorProductDTO = new ProductDTO();
            errorProductDTO.setErrorMessage(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorProductDTO);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(name = "categoryId", required = false) UUID id) {
        try {
            List<Product> productDTOs;

            if(id == null)
                productDTOs = productService.getAllProducts();
            else
                productDTOs = productService.getProductsByCategory(id);

            return ResponseEntity.ok(productDTOs);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable UUID id) {
        try {
            Optional<Product> product =  productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateProductById(@RequestBody AddProductDTO request,
                                            @PathVariable UUID id) {

        if (!id.equals(request.getId())){
            return ResponseEntity.badRequest().build();
        }
        try {
            ProductDTO productDTO =  productService.updateProduct(id, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}