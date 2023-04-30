package PSK.FlowerShop.controller;

import PSK.FlowerShop.entities.Category;
import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.repository.CategoryRepository;
import PSK.FlowerShop.request.CategoryRequest;
import PSK.FlowerShop.service.CategoryService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryService.createCategory(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<CategoryRequest> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable UUID id) {
        try {
            CategoryRequest request = categoryService.updateCategory(id, categoryRequest);
            return ResponseEntity.ok(request);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
