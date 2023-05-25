package PSK.FlowerShop.Seeders;

import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.repository.ProductRepository;
import PSK.FlowerShop.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/seeder")
public class ProductSeeder {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> saveProducts() {
        List<Product> products = productRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode productsNode = objectMapper.valueToTree(products);
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.set("products", productsNode);

        try {
            objectMapper.writeValue(new File("products.json"), rootNode);
            return ResponseEntity.ok("Products seeded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to seed products.");
        }
    }

    @GetMapping()
    public ResponseEntity<?> seedProducts() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("products.json");

            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode productsNode = rootNode.get("products");

            List<Product> products = new ArrayList<>();
            if (productsNode.isArray()) {
                Iterator<JsonNode> iterator = productsNode.elements();
                while (iterator.hasNext()) {
                    JsonNode productNode = iterator.next();
                    String name = productNode.get("name").asText();
                    String category = productNode.get("category").asText();
                    String description = productNode.get("description").asText();
                    String image = productNode.get("image").asText();
                    int quantity = productNode.get("quantity").asInt();
                    double price = productNode.get("price").asDouble();

                    Product product = new Product(name, category, image , price, quantity, description);
                    products.add(product);
                }
            }

            productRepository.saveAll(products);
            return ResponseEntity.ok("Products seeded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to seed products.");
        }
    }
}
