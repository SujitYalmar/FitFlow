package com.becoder.service;

import com.becoder.entity.Nutrition;
import com.becoder.repository.NutritionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutritionService {

    @Autowired
    private NutritionRepository nutritionRepository;

    public Nutrition saveNutrition(Nutrition nutrition) {
        return nutritionRepository.save(nutrition);
    }

    public List<Nutrition> getAllNutritions() {
        return nutritionRepository.findAll();
    }

    public Optional<Nutrition> getNutritionById(Long id) {
        return nutritionRepository.findById(id);
    }
    public Nutrition findByFoodName(String foodName) {
        return nutritionRepository.findByFoodName(foodName);
    }
    public Nutrition findByFoodName1(String foodName) {
        return nutritionRepository.findByFoodName(foodName);
    }

}
