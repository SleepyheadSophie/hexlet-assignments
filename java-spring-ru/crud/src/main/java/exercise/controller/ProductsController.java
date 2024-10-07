package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> index() {
        var productEntity = productRepository.findAll();
        return productEntity.stream().map(p -> productMapper.map(p)).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO show(@PathVariable Long id) {
        var productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        return productMapper.map(productEntity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductCreateDTO dto) {
        categoryRepository.findById(dto.getCategoryId());
        var entity = productMapper.map(dto);
        productRepository.save(entity);
        return productMapper.map(entity);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO dto) {
        var productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        productMapper.update(dto, productEntity);
        productRepository.save(productEntity);
        return productMapper.map(productEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        var productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        productRepository.delete(productEntity);
    }
    // END
}
