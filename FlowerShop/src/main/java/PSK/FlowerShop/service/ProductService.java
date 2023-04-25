package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Category;
import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findAllByCategory(category);
    }

    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public void updateProduct(Product product) throws Exception {
        if(productRepository.existsById(product.getId()))
            productRepository.save(product);
        else
            throw new Exception(String.format(
                    "Product to update with ID:{0} does not exist",
                    product.getId()
            ));
    }

    public void deleteProduct(UUID id) throws Exception {
        if(productRepository.existsById(id))
            productRepository.deleteById(id);
        else
            throw new Exception(String.format(
                    "Product to delete with ID:{0} does not exist",
                    id
            ));
    }
}
