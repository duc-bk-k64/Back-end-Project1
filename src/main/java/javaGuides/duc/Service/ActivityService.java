package javaGuides.duc.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import javaGuides.duc.DTO.ActivityDTO;
import javaGuides.duc.Entity.Activity;
import javaGuides.duc.Entity.User;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Repository.ActivityRepository;
import javaGuides.duc.Repository.UserRepository;

@Service
public class ActivityService {
	private ActivityRepository activityRepository;
	private UserRepository userRepository;

	public ActivityService(ActivityRepository activityRepository,UserRepository userRepository) {
		this.activityRepository = activityRepository;
		this.userRepository=userRepository;
	}

	public String create(ActivityDTO activityDTO) {
		try {
			Activity activity = activityRepository.findByCode(activityDTO.getCode());
			if(activity!=null) return "Activity "+activityDTO.getCode()+" already exist in system";
			activity=new Activity();
			activity.setDetail(activityDTO.getDetail());
			activity.setName(activityDTO.getName());
			activity.setTime(activityDTO.getTime());
			activity.setTime_create(Instant.now());
			activity.setCode(activityDTO.getCode());
			activityRepository.save(activity);
			return "Create activity successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	public List<Activity> getAll() {
		return activityRepository.findAll();

	}
	public String update(ActivityDTO activityDTO) {
		try {
			Activity activity=activityRepository.findByCode(activityDTO.getCode());
			if(activity==null) return "Activity not found in system";
			activity.setDetail(activityDTO.getDetail());
			activity.setName(activityDTO.getName());
			activity.setTime(activityDTO.getTime());
			activity.setTime_update(Instant.now());
			activityRepository.save(activity);
			return "Update activity successfully";
			
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	public String addAccount(String code,String email) {
		try {
			Activity activity=activityRepository.findByCode(code);
			User user=userRepository.findByEmail(email);
			if(activity==null) return "Activity not found in system";
			if(user==null) return "User not found in system";
			Set<Activity> set=user.getActivities();
			set.add(activity);
			user.setActivities(set);
			userRepository.save(user);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
}
