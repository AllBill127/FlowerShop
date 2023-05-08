package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Category;
import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.repository.CategoryRepository;
import PSK.FlowerShop.repository.ProductRepository;
import PSK.FlowerShop.request.AddProductDTO;
import PSK.FlowerShop.request.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    public ProductDTO createProduct(AddProductDTO productDTO) throws Exception {
        /*"""Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());

        if(!category.isPresent())
            throw new Exception(String.format(
                    "Category with ID:{0} not found. Product not created!",
                    productDTO.getCategoryId()
            ));"""*/

        Product newProduct = modelMapper.map(productDTO, Product.class);
        newProduct.setReviews(Collections.emptyList());

        Product product = productRepository.save(newProduct);
        return modelMapper.map(product, ProductDTO.class);
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<Product> getProductsByCategory(UUID id) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent())
            throw new Exception(String.format(
                    "Category with ID:{0} not found!",
                    id
            ));

        List<Product> products = productRepository.findAllByCategory(category.get());
        return products;
    }

    public Optional<Product> getProductById(UUID id) throws Exception {
        Optional<Product> product = productRepository.findById(id);

        if(!product.isPresent())
            throw new Exception(String.format(
                    "Product with ID:{0} not found!",
                    id
            ));

        return product;
    }

    public ProductDTO updateProduct(UUID id, AddProductDTO productDTO) throws Exception {
        Optional<Product> currentProduct = productRepository.findById(id);

        if(!currentProduct.isPresent())
            throw new Exception(String.format(
                    "Product to update with ID:{0} not found!",
                    id
            ));

        Product product = currentProduct.get();

        product.setCategory(productDTO.getCategory());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        //product.setImage(productDTO.getImage());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    public void deleteProduct(UUID id) throws Exception {
        Optional<Product> product = productRepository.findById(id);

        if(!product.isPresent())
            throw new Exception(String.format(
                    "Product to delete with ID:{0} not found!",
                    id
            ));

        productRepository.deleteById(id);
    }
}
