package com.becoder.service;



import com.becoder.entity.User;
import com.becoder.entity.Workout;
import com.becoder.repository.UserRepo;
import com.becoder.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;
 //  @Autowired
    //private User user;
    @Autowired
    private UserRepo userRepository;


    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }


    public Workout updateWorkout(Long id, Workout workoutDetails) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id " + id));
        workout.setName(workoutDetails.getName());
        workout.setDescription(workoutDetails.getDescription());
        workout.setDate(workoutDetails.getDate());
        workout.setDuration(workoutDetails.getDuration());
        workout.setCaloriesBurned(workoutDetails.getCaloriesBurned());
        workout.setRunSteps(workoutDetails.getRunSteps());
        workout.setGymExercises(workoutDetails.getGymExercises());
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    public List<Workout> getWorkoutsByUserEmail(String email) {
        return workoutRepository.findByName(email); // assuming you have this method in your repository
    }

	public User getWorkoutsForUser(User user) {
		// TODO Auto-generated method stub
		return user;
	}
}
