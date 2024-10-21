package com.example.inventoryandorderservice.CategoryPackage.service;

import com.example.inventoryandorderservice.CategoryPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.model.Category;

public interface CategoryService {
    Category validateCategoryAndGet(Long categoryId) throws CategoryNotFoundException;
}
