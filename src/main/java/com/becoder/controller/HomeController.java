
package com.becoder.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.becoder.entity.Nutrition;
import com.becoder.entity.User;
import com.becoder.entity.Workout;
import com.becoder.repository.UserRepo;
//import com.becoder.repository.NutritionRepo;
//import com.becoder.service.NutritionService;
import com.becoder.service.PasswordResetService;
import com.becoder.service.UserService;
import com.becoder.service.WorkoutService;

import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
    private static final String UPLOADED_FOLDER = "src/main/resources/static/uploads/";


	@Autowired
	private UserService userService;
	
	@Autowired
    private PasswordResetService passwordResetService;

	@Autowired
	private UserRepo userRepo;
    @Autowired
    private WorkoutService workoutService;

//	@Autowired
	//private NutritionRepo nutritionRepo;

	//@Autowired
	//private NutritionService nutiService;

	
	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String index() {
		return "index";
	}
	@GetMapping("/index")
	public String index1() {
		return "index";
	}


	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user,@RequestParam("email") String email, HttpSession session, Model m) {

		// System.out.println(user);
		try {

		User u = userService.saveUser(user);

		if (u != null) {
			// System.out.println("save sucess");
			session.setAttribute("msg", "Register successfully");

		} else {
			// System.out.println("error in server");
			session.setAttribute("msg", "Something wrong server");
		}
		}
		catch(Exception e)
		{
			session.setAttribute("msg", "emailid or username exists");

		}
		return "redirect:/register";
	}


	 @GetMapping("/forgot-password")
	    public String showForgotPasswordForm() {
	        return "forgot-password";
	    }

	    @PostMapping("/password/forgot")
	    public String handleForgotPassword(@RequestParam String email) {
	        passwordResetService.initiatePasswordReset(email);
	        return "redirect:/forgot-password?success";
	    }

	    @GetMapping("/reset-password")
	    public ModelAndView showResetPasswordForm(@RequestParam String token) {
	        ModelAndView mav = new ModelAndView("reset-password");
	        mav.addObject("token", token);
	        return mav;
	    }

	    @PostMapping("/password/reset")
	    public String handleResetPassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword){
	    //	System.out.println("new token"+token);
	    	//System.out.println("my token isss"+newPassword);
	        passwordResetService.resetPassword(email, newPassword);
	        return "redirect:/reset-password?resetSuccess";
	    }

	    
		 @GetMapping("/membership")
		    public String showmembership() {
		        return "membership";
		    }

		 @GetMapping("/bmi_calculator")
		    public String showbmi() {
		        return "bmi_calculator";
		    }


		    @PostMapping("/upload")
		    public String uploadFile(@RequestParam("profilePic") MultipartFile file,
                    RedirectAttributes redirectAttributes,
                    Authentication authentication,@ModelAttribute User user, HttpSession session, Model m) {
		        if (file.isEmpty()) {
		            return "redirect:/";
		        }

		        try {
		            // Create the upload directory if it doesn't exist
		            File directory = new File(UPLOADED_FOLDER);
		            if (!directory.exists()) {
		                directory.mkdirs();
		            }
		           
		            int username = user.getId();

		            // Get the file name and save it to the local file system
		            String fileName = username+".jpg";
		            Path path = Paths.get(UPLOADED_FOLDER + fileName);
		            Files.write(path, file.getBytes());

					session.setAttribute("msg", "uploaded");

		        } catch (IOException e) {
		            e.printStackTrace();
					session.setAttribute("msg", "failed to upload");
		        }

		        return "redirect:/";
		    }
		    @GetMapping("/uploads/{filename}")
		    public ResponseEntity<?> getFile(@PathVariable String filename) {
		        try {
		            Path file = Paths.get(UPLOADED_FOLDER).resolve(filename);
		            if (Files.exists(file)) {
		                Resource resource = new UrlResource(file.toUri());
		                return ResponseEntity.ok()
		                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file))
		                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
		                        .header(HttpHeaders.PRAGMA, "no-cache")
		                        .header(HttpHeaders.EXPIRES, "0")
		                        .body(resource);
		            } else {
		                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
		        }
		    }

	
		    
		    @GetMapping("/profile")
		    public String showProfile() {
				return "profile";

		    }
		    

		    @PostMapping("/update")
		    public String updateUser(@ModelAttribute User user) 
		    {
		    	 userService.saveUser1(user);
			        return "redirect:/";
		    	
		    }
		    
		    @PostMapping("/deleteUser")
		    public String deleteUser(@RequestParam int id) {
		        userService.deleteUser(id);
		        return "redirect:/";
 
		    }
			@GetMapping("/delete")
			public String delete() {
				return "deleteUser";
			}

         /*  @PostMapping("/nutrition")
           public void addnutrition(@ModelAttribute Nutrition nutri)
           {
        	   nutritionRepo.save(nutri);
        	 
           }
           
           @GetMapping("/nutrition")
           public String showNutritionForm(Model model) {
               model.addAttribute("nutrition", new Nutrition());
               return "nutrition";
           }*/
			
			@GetMapping("/workouts")
		    public String getAllWorkouts(Model model) {
		        // Retrieve the current logged-in user's email
		        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        String currentUserEmail = authentication.getName(); // The user's email is typically the username

		        // Fetch workouts of the current user by email
		        List<Workout> userWorkouts = workoutService.getWorkoutsByUserEmail(currentUserEmail);
		        
		        // Add workouts to the model
		        model.addAttribute("workouts", userWorkouts);
		        
		        return "workoutList"; // Thymeleaf template
		    }

			 @GetMapping("/welcome")
			    public String showi() {
			        return "welcome";
			    }
			 @GetMapping("/home1")
			    public String home1() {
			        return "home1";
			    }

			 @GetMapping("/calorie")
			    public String clories() {
			        return "calorie";
			    }
			 @GetMapping("/bmr")
			    public String bmr() {
			        return "bmr";
			    }
			 @GetMapping("/recipe")
			    public String recipe() {
			        return "recipe";
			    }

			 @GetMapping("/Explore")
			    public String Explore() {
			        return "Explore";
			    }
			 @GetMapping("/index2")
			    public String index2() {
			        return "index2";
			    }
			 @GetMapping("/index3")
			    public String index3() {
			        return "index3";
			    }
			 @GetMapping("/pushup")
			    public String pushup() {
			        return "pushup";
			    }
			 @GetMapping("/kneepushup")
			    public String kneepushup() {
			        return "kneepushup";
			    }

			 @GetMapping("/inclinepush")
			    public String inclinepush() {
			        return "inclinepush";
			    }
			 @GetMapping("/burpees")
			    public String burpees() {
			        return "burpees";
			    }
			 @GetMapping("/legraises")
			    public String legraises() {
			        return "legraises";
			    }

			 @GetMapping("/russiantwist")
			    public String russiantwist() {
			        return "russiantwist";
			    }
			 @GetMapping("/plank")
			    public String plank() {
			        return "plank";
			    }
			 @GetMapping("/bicyclecrunches")
			    public String bicyclecrunches() {
			        return "bicyclecrunches";
			    }
			 @GetMapping("/squats")
			    public String squats() {
			        return "squats";
			    }
			 @GetMapping("/jumpingjack1")
			    public String jumpingjack1() {
			        return "jumpingjack1";
			    }
			 @GetMapping("/pushup1")
			    public String pushup1() {
			        return "pushup1";
			    }
			 @GetMapping("/burpees1")
			    public String burpees1() {
			        return "burpees1";
			    }

			 @GetMapping("/widearm")
			    public String widearm() {
			        return "widearm";
			    }
			 @GetMapping("/diamondpush")
			    public String diamondpush() {
			        return "diamondpush";
			    }
			 @GetMapping("/inclinepush1")
			    public String inclinepush1() {
			        return "inclinepush1";
			    }

			 @GetMapping("/jumpingjack2")
			    public String jumpingjack2() {
			        return "jumpingjack2";
			    }
			 @GetMapping("/jumpingjack3")
			    public String jumpingjack3() {
			        return "jumpingjack3";
			    }
			 @GetMapping("/russiantwist2")
			    public String russiantwist2() {
			        return "russiantwist2";
			    }
			 @GetMapping("/abdominal2")
			    public String abdominal2() {
			        return "abdominal2";
			    }
			 @GetMapping("/legraises2")
			    public String legraises2() {
			        return "legraises2";
			    }

			 @GetMapping("/mountain2")
			    public String mountain2() {
			        return "mountain2";
			    }
			 @GetMapping("/plank2")
			    public String plank2() {
			        return "plank2";
			    }
			 @GetMapping("/bicyclecrunches2")
			    public String bicyclecrunches2() {
			        return "bicyclecrunches2";
			    }
			 @GetMapping("/vup2")
			    public String vup2() {
			        return "vup2";
			    }
			 @GetMapping("/pushrotate2")
			    public String pushrotate2() {
			        return "pushrotate2";
			    }
			 @GetMapping("/heeltouch2")
			    public String heeltouch2() {
			        return "heeltouch2";
			    }
			 @GetMapping("/inclinepush3")
			    public String inclinepush3() {
			        return "inclinepush3";
			    }
			 @GetMapping("/inchworm3")
			    public String inchworm3() {
			        return "inchworm3";
			    }
			 @GetMapping("/reversepush3")
			    public String reversepush3() {
			        return "reversepush3";
			    }
			 @GetMapping("/hyper3")
			    public String hyper3() {
			        return "hyper3";
			    }
			 @GetMapping("/pushrotate4")
			    public String pushrotate4() {
			        return "pushrotate4";
			    }
			 @GetMapping("/pushrotate5")
			    public String pushrotate5() {
			        return "pushrotate5";
			    }

			 @GetMapping("/skipping4")
			    public String skipping4() {
			        return "skipping4";
			    }
			 @GetMapping("/pushup4")
			    public String pushup4() {
			        return "pushup4";
			    }
			 @GetMapping("/burpees4")
			    public String burpees4() {
			        return "burpees4";
			    }
			 @GetMapping("/inclinepush4")
			    public String inclinepush4() {
			        return "inclinepush4";
			    }
			 @GetMapping("/jumpingjack5")
			    public String jumpingjack5() {
			        return "jumpingjack5";
			    }
			 @GetMapping("/squats5")
			    public String squats5() {
			        return "squats5";
			    }
			 @GetMapping("/squats6")
			    public String squats6() {
			        return "squats6";
			    }
			 @GetMapping("/burpees5")
			    public String burpees5() {
			        return "burpees5";
			    }
			 @GetMapping("/curtsey5")
			    public String curtsey5() {
			        return "curtsey5";
			    }
			 @GetMapping("/jumpingsquat5")
			    public String jumpingsquat5() {
			        return "jumpingsquat5";
			    }
			 @GetMapping("/tadasana")
			    public String tadasana() {
			        return "tadasana";
			    }
			 @GetMapping("/vraksasana")
			    public String vraksasana() {
			        return "vraksasana";
			    }


			    @GetMapping("/workouth")
			    public String getWorkoutReport(Model model) {
			        // Fetch workout data for a specific user (use your actual logic)
			        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			        String currentUserEmail = authentication.getName(); // The user's email is typically the username

			        // Fetch workouts of the current user by email
			        List<Workout> userWorkouts = workoutService.getWorkoutsByUserEmail(currentUserEmail);
			        
			        // Add workouts to the model
			      
			        
			        // Extract data such as duration and calories burned from workouts
			        List<Integer> durations = userWorkouts.stream()
			                                       .map(Workout::getDuration)
			                                       .collect(Collectors.toList());
			        List<Integer> calories = userWorkouts.stream()
			                                      .map(Workout::getCaloriesBurned)
			                                      .collect(Collectors.toList());
			        List<String> workoutNames = userWorkouts.stream()
                            .map(Workout::getDescription)
                            .collect(Collectors.toList());

			        model.addAttribute("workouts", userWorkouts);

			        // Add these lists to the model
			        model.addAttribute("currentUserEmail",currentUserEmail);
			        model.addAttribute("durations", durations);
			        model.addAttribute("calories", calories);
			        model.addAttribute("workoutNames", workoutNames);
			        
			        return "welcome";  // This is the Thymeleaf template name
			    }
			    @PostMapping("/workouts")
			    public String addWorkout(@RequestParam("description") String description,
			                              @RequestParam("date") LocalDate date,
			                              @RequestParam("duration") int duration,
			                              @RequestParam("runSteps") int runSteps,
			                              @RequestParam("caloriesBurned") int caloriesBurned,
			                              @RequestParam("running") String running,
			                              @RequestParam("gymExercises") String gymExercises,
			                              @RequestParam("userEmail") String userEmail) {
			        // Process the form data here
			        Workout workout = new Workout(userEmail, description, date, duration, caloriesBurned, runSteps, gymExercises);
			        userService.createWorkout(workout); // Assuming a service to save the workout data

			        return "redirect:/workouts"; // Redirect to a workout listing page, for example
			    }
				 @GetMapping("/yoga")
				    public String yoga() {
				        return "yoga";
				    }


}
