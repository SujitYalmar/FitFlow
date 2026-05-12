package com.becoder.controller;



import com.becoder.entity.User;

import com.becoder.entity.Workout;
import com.becoder.service.UserService;
import com.becoder.service.WorkoutService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;
    @Autowired
	private UserService userService;
	

    // Get all workouts
        // Add new workout form
   /** @GetMapping("/add")
    public String addWorkoutForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "addWorkout"; // Thymeleaf template
    }**/
  

    @GetMapping("/add")
    public String addWorkoutForm(Model model, Authentication authentication) {
        // Retrieve the logged-in user's email from Spring Security
        String userEmail = authentication.getName(); // or from a custom user object, if available
        model.addAttribute("userEmail", userEmail); // Pass email to Thymeleaf
        return "addWorkout"; // Thymeleaf template name
    }

    // Create workout
   /** @PostMapping
    public String createWorkout(@ModelAttribute Workout workout,@ModelAttribute User user) {
    	userService.createWorkout(workout,user);
        return "redirect:/workouts"; // Redirect to list page
    }
**/
    // Edit workout form
    @GetMapping("/edit/{id}")
    public String editWorkoutForm(@PathVariable Long id, Model model) {
        Workout workout = workoutService.getWorkoutById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id " + id));
        model.addAttribute("workout", workout);
        return "editWorkout"; // Thymeleaf template
    }

    // Update workout
    @PostMapping("/edit/{id}")
    public String updateWorkout(@PathVariable Long id, @ModelAttribute Workout workoutDetails) {
        workoutService.updateWorkout(id, workoutDetails);
        return "redirect:/workouts"; // Redirect to list page
    }

    // Delete workout
    @GetMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/workouts"; // Redirect to list page
    }
    
    
}

