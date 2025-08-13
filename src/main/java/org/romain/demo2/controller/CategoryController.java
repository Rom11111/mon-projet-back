package org.romain.demo2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.romain.demo2.dao.CategoryDao;
import org.romain.demo2.model.Category;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;
import org.romain.demo2.view.ProductViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    // Récupérer toutes les catégories (accessible aux clients et techniciens)
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }
}

