package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Category;
import PSK.FlowerShop.repository.CategoryRepository;
import PSK.FlowerShop.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getById(UUID id) {
        return categoryRepository.getById(id);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    public CategoryRequest updateCategory(UUID id, CategoryRequest categoryRequest) throws Exception {
        Optional<Category> currentCategory = categoryRepository.findById(id);
        if(!currentCategory.isPresent())
            throw new Exception(String.format(
               "Category with id:{0} now found!",
               id
            ));
        Category category = currentCategory.get();
        modelMapper.map(categoryRequest, category);

        Category updatedCategory = categoryRepository.save(category);
        return categoryRequest;
    }
}
