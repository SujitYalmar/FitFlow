package com.becoder.service;

import com.becoder.entity.User;
import com.becoder.entity.Workout;

public interface UserService {

	public User saveUser(User user);

	public void removeSessionMessage();
    public User getUserByName(String name);
    public User saveUser1(User user);
    public void deleteUser(int id);

    public void createWorkout(Workout workout);

	
    
    
}
