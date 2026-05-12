package com.becoder.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private LocalDate date;
    private Integer duration; // Duration in minutes
    private Integer caloriesBurned;
    private Integer runSteps; // Number of steps run
    private String gymExercises; // Gym exercises performed

    // Constructors, Getters, Setters
    public Workout() {}

    public Workout(String name, String description, LocalDate date, Integer duration, Integer caloriesBurned, Integer runSteps, String gymExercises) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.runSteps = runSteps;
        this.gymExercises = gymExercises;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Integer getRunSteps() {
        return runSteps;
    }

    public void setRunSteps(Integer runSteps) {
        this.runSteps = runSteps;
    }

    public String getGymExercises() {
        return gymExercises;
    }

    public void setGymExercises(String gymExercises) {
        this.gymExercises = gymExercises;
    }
}
