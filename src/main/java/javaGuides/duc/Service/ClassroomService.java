package javaGuides.duc.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import javaGuides.duc.Entity.ClassRoom;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Entity.teacher;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Exception.ResourseNotFoundException;
import javaGuides.duc.Repository.ClassroomRepository;
import javaGuides.duc.Repository.studentRepository;
import javaGuides.duc.Repository.teacherRepository;

@Service
public class ClassroomService {
	private ClassroomRepository classroomRepository;
	private teacherRepository teacherRepository;
	private studentRepository studentRepository;

	public ClassroomService(ClassroomRepository classroomRepository, teacherRepository teacherRepository,
			studentRepository studentRepository) {
		this.classroomRepository = classroomRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
	}

	public String createClassroom(String name) {
		ClassRoom classRoom = new ClassRoom();
		classRoom.setName(name);
		classroomRepository.save(classRoom);
		return "Successfully";
	}

	public void save(ClassRoom classRoom) {
		classroomRepository.save(classRoom);
	}

	public String updateClassroom(Long id, String name) {
		try {
			ClassRoom classRoom = classroomRepository.getById(id);
			if (classRoom == null)
				return "Classroom not found in system";
			classRoom.setName(name);
			classroomRepository.save(classRoom);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public List<ClassRoom> getAll() {
		return classroomRepository.findAll();
	}

	public Set<Student> getStudent(Long id) {
		try {
			ClassRoom classRoom = classroomRepository.getById(id);
			if (classRoom == null)
				return null;
			return classRoom.getStudents();

		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public Set<teacher> getTeacher(Long id) {
		try {
			ClassRoom classRoom = classroomRepository.getById(id);
			if (classRoom == null)
				return null;
			return classRoom.getTeachers();
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public String addStudent(Long ClassroomID, String StudentCode) {
		try {
			ClassRoom classRoom = classroomRepository.getById(ClassroomID);
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

	public String addTeacher(Long ClassroomID, String teacherCode) {
		try {
			ClassRoom classRoom = classroomRepository.getById(ClassroomID);
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

	public List<String> getInformation(Long id) {
		try {
			List<String> list = new ArrayList<>();
			ClassRoom classRoom = classroomRepository.getById(id);
			if (classRoom == null) {
				list.add("Classroom not found in system");
				return list;
			}
			list.add("Student information:");
			classRoom.getStudents().forEach(student -> {
				list.add(student.getDetails());
			});
			list.add("Teacher information:");
			classRoom.getTeachers().forEach(teacher -> {
				list.add(teacher.getDetail());
			});
			return list;
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

}
