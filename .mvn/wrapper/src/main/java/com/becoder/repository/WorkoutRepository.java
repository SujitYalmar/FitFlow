package com.becoder.repository;



import com.becoder.entity.Workout;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByName(String email); // assuming Workout has a userEmail field
}

