package javaGuides.duc.Controller;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import javaGuides.duc.Config.JWT.JwtProvider;
import javaGuides.duc.DTO.SignIn;
import javaGuides.duc.DTO.SignUp;
import javaGuides.duc.DTO.forgotPWdto;
import javaGuides.duc.DTO.resetPWdto;
import javaGuides.duc.DTO.responSignin;
import javaGuides.duc.Entity.Role;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Entity.User;
import javaGuides.duc.Entity.teacher;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Exception.ResourseNotFoundException;
import javaGuides.duc.Repository.RoleRepository;
import javaGuides.duc.Service.AccountService;
import javaGuides.duc.Service.studentService;
import javaGuides.duc.Service.teacherService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private PasswordEncoder passwordEncoder;
	private AccountService accountService;
	private JwtProvider jwtProvider;
	private RoleRepository roleRepository;
	private teacherService teacherService;
	private studentService studentService;

	public AuthController(PasswordEncoder passwordEncoder, AccountService accountService, JwtProvider jwtProvider,
			RoleRepository roleRepository,teacherService teacherService,studentService studentService) {
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.accountService = accountService;
		this.roleRepository = roleRepository;
		this.teacherService=teacherService;
		this.studentService=studentService;
	}

	@PostMapping("/signin")
	@ApiOperation("Signin system")
	public ResponseEntity<responSignin> authenticateUser(@RequestBody SignIn signIn) {
		User account = accountService.findByUsername(signIn.getUsername());
		if (account == null)
			throw new ResourseNotFoundException(signIn.getUsername() + " not found in system");
		else if (!passwordEncoder.matches(signIn.getPassword(), account.getPassword()))
			throw new BadRequestException("password incorrect");
		else {
			Set<Role> useRoles = account.getRoles();
			List<String> roleList = new ArrayList<>();
			for (Role role : useRoles) {

				roleList.add(role.getName());
			}
			String token = jwtProvider.generateToken(signIn.getUsername());
			return ResponseEntity.ok().body(new responSignin(token, roleList));
		}
	}

	@PostMapping("/signup")
	@ApiOperation("Signup account")
	public ResponseEntity<String> signup(@RequestBody SignUp signUp) {
		if (accountService.existByUsername(signUp.getUsername())) {
			return ResponseEntity.badRequest().body("Error: Username is already taken");
		} else if (accountService.existByEmail(signUp.getEmail())) {
			return ResponseEntity.badRequest().body("Error:Email is already in use");
		} else {
			User user = new User();
			user.setEmail(signUp.getEmail());
			user.setUsername(signUp.getUsername());
			user.setPassword(passwordEncoder.encode(signUp.getPassword()));
			Set<Role> roles = new HashSet<>();
			Set<String> strRole = signUp.getRole();
			if (strRole == null) {
				Role role = roleRepository.findByName("ROLE_STUDENT");
				roles.add(role);

			} else {
				strRole.forEach(role -> {
					switch (role) {
					case "admin":
						Role AdminRole = roleRepository.findByName("ROLE_ADMIN");
						roles.add(AdminRole);
						break;
					case "teacher":
						Role teacheRole=roleRepository.findByName("ROLE_TEACHER");
						roles.add(teacheRole);
						break;

					default:
						Role useRole = roleRepository.findByName("ROLE_STUDENT");
						roles.add(useRole);
						break;
					}
				});
			}
			StringBuilder rolelist = new StringBuilder();
			rolelist.append("register succesfuly. Roles: ");

			user.setRoles(roles);
			accountService.saveUser(user);
			
			for (Role role : roles) {

				rolelist.append(role.getName() + " ");
			}
			Student student= studentService.findStudentByStudentCode(signUp.getCode());
			if(student!=null)  {
				//user.setStudent(student);
				student.setUser(user);
				studentService.createStudent(student);
			}
			else {
				teacher teacherdata=teacherService.findByTeacherCode(signUp.getCode());
				if(teacherdata!=null) {
				//user.setTeacher(teacherdata);
				teacherdata.setUser(user);
				teacherService.createTeacher(teacherdata);//save information
				}
			}

			
			return ResponseEntity.ok().body(rolelist.toString());

		}

	}

	@PostMapping("/forgotpw")
	public ResponseEntity<String> forgotPW(@RequestBody forgotPWdto forgot) throws Exception  {
	try {
		String respon = accountService.forgotPassword(forgot.getEmail());
		return ResponseEntity.ok().body(respon);
	} catch (Exception e) {
		throw new Exception(e.getMessage());
	}
		
	}

	@PostMapping("/resetpw")
	public ResponseEntity<String> resetPW(@RequestBody resetPWdto reset) {
		String respon = accountService.resetPassword(reset.getToken(), reset.getPassword());
		return ResponseEntity.ok().body(respon);
	}
	

}
