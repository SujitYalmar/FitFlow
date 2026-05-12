package com.becoder.controller;

import com.becoder.entity.Nutrition;
import com.becoder.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    // Show the form
    @GetMapping("/nutrition_form")
    public String showNutritionForm(Model model) {
        model.addAttribute("nutrition", new Nutrition());
        return "nutrition_form";  // Renders the nutrition_form.html template
    }

    // Handle form submission
    @PostMapping
    public String submitNutritionForm(@ModelAttribute Nutrition nutrition) {
        nutritionService.saveNutrition(nutrition);
        return "redirect:/nutrition_form";
    }
    
    @GetMapping("/search")
    public String searchByFoodName(@RequestParam("foodName") String foodName, Model model) {
        Nutrition nutrition = nutritionService.findByFoodName(foodName);
        if (nutrition != null) {
            model.addAttribute("nutrition", nutrition);
        } else {
            model.addAttribute("nutrition", null);
        }

        return "nutrition_form"; // Return to the same page to display results
    }
    @GetMapping("/nutrition_result")
    public String showNutritionInfo(Model model) {
        Nutrition nutrition = new Nutrition();
      //  nutrition.setFoodName("Apple");  // Example food name, this should come from your logic.
        model.addAttribute("nutrition", nutrition);  // Add the nutrition object to the model.

        // Optionally, retrieve saved nutrition info from a database or session
        return "nutrition_result"; // The page to display nutrition info
    }

    @GetMapping("/search1")
    public String searchByFoodName1(@RequestParam("foodName") String foodName, Model model) {
        Nutrition nutrition = nutritionService.findByFoodName1(foodName);
        if (nutrition != null) {
            model.addAttribute("nutrition", nutrition);
        } else {
            model.addAttribute("nutrition", null);
        }

        return "nutrition_result"; // Return to the same page to display results
    }

    
}
