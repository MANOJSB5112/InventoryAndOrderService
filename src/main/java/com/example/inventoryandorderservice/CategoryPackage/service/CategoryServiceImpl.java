package com.example.inventoryandorderservice.CategoryPackage.service;

import com.example.inventoryandorderservice.CategoryPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.model.Category;
import com.example.inventoryandorderservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository)
    {
        this.categoryRepository=categoryRepository;
    }
    @Override
    public Category validateCategoryAndGet(Long categoryId) throws CategoryNotFoundException {
        Optional<Category> categoryOptional=categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty())
        {
            throw new CategoryNotFoundException("Category Not found ");
        }
        return categoryOptional.get();
    }
}
