package com.becoder.repository;

import com.becoder.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
    // You can add custom queries here if needed, e.g.
    // List<Nutrition> findByFoodName(String foodName);
    Nutrition findByFoodName(String foodName);
    

}
