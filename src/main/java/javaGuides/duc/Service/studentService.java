package javaGuides.duc.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import javaGuides.duc.DTO.informationDTO;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Entity.User;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Exception.ResourseNotFoundException;
import javaGuides.duc.Repository.studentRepository;

@Service
public class studentService {
	private studentRepository studentRepository;

	public studentService(studentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public Student getStudentById(Long id) {
		try {
			return studentRepository.getById(id);
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public List<Student> getAll() {
		return studentRepository.findAll();
	}

	public Student findStudentByStudentCode(String code) {
		try {
			return studentRepository.findByStudentCode(code);
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public Student createStudent(Student data) {
		try {
			return studentRepository.save(data);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public String updateStudent(informationDTO data) {
		try {
			Student student = studentRepository.findByStudentCode(data.getCode());
			if (student == null)
				return "Student not found in systems";
			student.setAdress(data.getAdress());
			student.setDateOfBirth(data.getDateofBirth());
			student.setImage(data.getImage());
			student.setPhoneNumber(data.getPhoneNumber());
			student.setName(data.getName());
			student.setTime_update(Instant.now());
			studentRepository.save(student);
			return "Update student information successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	public String deleteStudent(String code) {
		Student student = studentRepository.findByStudentCode(code);
		if (student == null)
			return "Student not found in systems";
		studentRepository.delete(student);
		return "Delete succcessfully";
	}
	public String removeRelation(String code) {
		Student student = studentRepository.findByStudentCode(code);
		if (student == null)
			return "Student not found in systems";
		student.setUser(null);
		studentRepository.save(student);
		return "Successfully";
	}
}
