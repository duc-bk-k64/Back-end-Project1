package javaGuides.duc.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import javaGuides.duc.Entity.ClassRoom;
import javaGuides.duc.Entity.Menu;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Entity.teacher;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Exception.ResourseNotFoundException;
import javaGuides.duc.Repository.ClassroomRepository;
import javaGuides.duc.Repository.MenuRepository;
import javaGuides.duc.Repository.studentRepository;
import javaGuides.duc.Repository.teacherRepository;

@Service
public class ClassroomService {
	private ClassroomRepository classroomRepository;
	private teacherRepository teacherRepository;
	private studentRepository studentRepository;
	private MenuRepository menuRepository;

	public ClassroomService(ClassroomRepository classroomRepository, teacherRepository teacherRepository,
			studentRepository studentRepository, MenuRepository menuRepository) {
		this.classroomRepository = classroomRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
		this.menuRepository = menuRepository;
	}

	public String createClassroom(String name) {
		ClassRoom classRoom = classroomRepository.findByName(name);
		if(classRoom!=null) return "Classroom "+name+" already exist";
		classRoom=new ClassRoom();
		classRoom.setName(name);
		classRoom.setTime_create(Instant.now());
		classroomRepository.save(classRoom);
		return "Successfully";
	}

	public void save(ClassRoom classRoom) {
		classroomRepository.save(classRoom);
	}

	public String updateClassroom(String oldName, String name) {
		try {
			ClassRoom classRoom = classroomRepository.findByName(oldName);
			if (classRoom == null)
				return "Classroom not found in system";
			classRoom.setName(name);
			classRoom.setTime_update(Instant.now());
			classroomRepository.save(classRoom);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public List<ClassRoom> getAll() {
		return classroomRepository.findAll();
	}

	public Set<Student> getStudent(String name) {
		try {
			ClassRoom classRoom = classroomRepository.findByName(name);
			if (classRoom == null)
				return null;
			return classRoom.getStudents();

		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public Set<teacher> getTeacher(String name) {
		try {
			ClassRoom classRoom = classroomRepository.findByName(name);
			if (classRoom == null)
				return null;
			return classRoom.getTeachers();
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public String addStudent(String name, String StudentCode) {
		try {
			ClassRoom classRoom = classroomRepository.findByName(name);
			Student student = studentRepository.findByStudentCode(StudentCode);
			if (classRoom == null)
				return "Classroom not found in system";
			if (student == null)
				return "Student not found in system";
			student.setClassRoom(classRoom);
			studentRepository.save(student);
			return "Successfully";
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}

	}

	public String addTeacher(String name, String teacherCode) {
		try {
			ClassRoom classRoom = classroomRepository.findByName(name);
			teacher teacher = teacherRepository.findByTeacherCode(teacherCode);
			if (classRoom == null)
				return "Classroom not found in system";
			if (teacher == null)
				return "Teacher not found in system";
			teacher.setClassRoom(classRoom);
			teacherRepository.save(teacher);
			return "Successfully";
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}

	}

	public List<Map<String, String>> getInformation(String name) {
		try {
			List<Map<String, String>> list = new ArrayList<>();
			ClassRoom classRoom = classroomRepository.findByName(name);
			if (classRoom == null) {
				Map<String, String> map = new HashMap<>();
				map.put("Message", "Classroom not found in system");
				list.add(map);
				return list;
			}
			// list.add("Student information:");
			classRoom.getStudents().forEach(student -> {
				Map<String, String> map = new HashMap<>();
				map.put("Student_code", student.getStudentCode());
				map.put("Adress", student.getAdress());
				map.put("Name", student.getName());
				map.put("ImageURL", student.getImage());
				map.put("PhoneNumber", student.getPhoneNumber());
				list.add(map);
			});
			// list.add("Teacher information:");
			classRoom.getTeachers().forEach(teacher -> {
				Map<String, String> map = new HashMap<>();
				map.put("Teacher_code", teacher.getTeacherCode());
				map.put("Adress", teacher.getAdress());
				map.put("Name", teacher.getName());
				map.put("ImageURL", teacher.getImage());
				map.put("PhoneNumber", teacher.getPhoneNumber());
				list.add(map);
			});
			return list;
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public List<Map<String, String>> getall() {
		List<Map<String, String>> list = new ArrayList<>();
		classroomRepository.findAll().forEach(classroom -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(classroom.getId()));
			map.put("Name", classroom.getName());
			list.add(map);
		});
		return list;
	}

	public String addMenu(Long menuID, Long classRoomID) {
		try {
			Menu menu = menuRepository.getById(menuID);
			ClassRoom classRoom = classroomRepository.getById(classRoomID);
			if (menu == null)
				return "Menu not found in system";
			if (classRoom == null)
				return "Classroom not found in system";
			Set<Menu> set = classRoom.getMenus();
			set.add(menu);
			classRoom.setMenus(set);
			classroomRepository.save(classRoom);
			return "Successfully";
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}

	}

	public String removeMenu(Long menuID, Long classRoomID) {
		try {
			Menu menu = menuRepository.getById(menuID);
			ClassRoom classRoom = classroomRepository.getById(classRoomID);
			if (menu == null)
				return "Menu not found in system";
			if (classRoom == null)
				return "Classroom not found in system";
			Set<Menu> set = classRoom.getMenus();
			if (set.contains(menu))
				set.remove(menu);
			classRoom.setMenus(set);
			classroomRepository.save(classRoom);
			return "Successfully";
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}

	}

	public Set<Menu> getMenu(Long id) {
		ClassRoom classRoom = classroomRepository.getById(id);
		if (classRoom == null)
			return null;
		return classRoom.getMenus();
	}

}
