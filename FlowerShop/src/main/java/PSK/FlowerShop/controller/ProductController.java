package PSK.FlowerShop.controller;

import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.request.AddProductDTO;
import PSK.FlowerShop.request.ProductDTO;
import PSK.FlowerShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


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
            @RequestParam(name = "categoryId", required = false) UUID id,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "inStock", required = false) Boolean inStock) {
        try {
            List<Product> productDTOs = new ArrayList<>();

            // Apply filtering based on category
            if (id != null) {
                productDTOs = productDTOs.stream()
                        .filter(product -> product.getCategory().equals(id))
                        .collect(Collectors.toList());
            } else {
                productDTOs = productService.getAllProducts();
            }

            // Apply filtering based on minimum price
            if (minPrice != null) {
                productDTOs = productDTOs.stream()
                        .filter(product -> product.getPrice().doubleValue() >= minPrice)
                        .collect(Collectors.toList());
            }

            // Apply filtering based on maximum price
            if (maxPrice != null) {
                productDTOs = productDTOs.stream()
                        .filter(product -> product.getPrice().doubleValue() <= maxPrice)
                        .collect(Collectors.toList());
            }

            // Apply filtering based on it being in stock
            if (inStock != null) {
                if(inStock == true)
                    productDTOs = productDTOs.stream()
                            .filter(product -> product.getQuantity() > 0)
                            .collect(Collectors.toList());
                else
                    productDTOs = productDTOs.stream()
                            .filter(product -> product.getQuantity() <= 0)
                            .collect(Collectors.toList());
            }

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
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity handleOptimisticLockingFailure(OptimisticLockingFailureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Resource has been modified by another transaction.");
    }
}