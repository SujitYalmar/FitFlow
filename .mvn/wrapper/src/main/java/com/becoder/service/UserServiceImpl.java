package com.becoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.becoder.entity.User;
import com.becoder.entity.Workout;
import com.becoder.repository.UserRepo;
import com.becoder.repository.WorkoutRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    String global1;
	@Autowired
	private UserRepo userRepo;
	@Autowired
    private WorkoutRepository workoutRepository;
 

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {

		String password=passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		User newuser = userRepo.save(user);

		return newuser;
	}

	@Override
	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");
	}
@Override
public User getUserByName(String name) {
    return userRepo.findByName(name);
}
@Override
public User saveUser1(User user) {
	User u=userRepo.findById(user.getId()).orElse(user);
	if(u!=null)
	{
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setFitness_goal(user.getFitness_goal());
		u.setMobileNo(user.getMobileNo());
	}
    // Implement save logic
    userRepo.save(u);
    return u;
}
public void deleteUser(int id)
{
   
        userRepo.deleteById(id);
    } 

public void createWorkout(Workout workout) {
    
	//String p=user.getEmail();
	//workout.setName(global1);
	//System.out.println(global1+"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
    workoutRepository.save(workout);
    
}


}
