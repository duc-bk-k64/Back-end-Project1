package javaGuides.duc.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javaGuides.duc.DTO.informationDTO;
import javaGuides.duc.Entity.teacher;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Exception.ResourseNotFoundException;
import javaGuides.duc.Repository.teacherRepository;

@Service
public class teacherService {
	private teacherRepository teacherRepository;

	public teacherService(teacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	public List<teacher> getAll() {
		return teacherRepository.findAll();
	}

	public teacher findById(Long id) {
		try {
			return teacherRepository.getById(id);
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public teacher findByTeacherCode(String code) {
		try {
			return teacherRepository.findByTeacherCode(code);
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public teacher createTeacher(teacher teacher) {
		try {
			return teacherRepository.save(teacher);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public String updateTeacher(informationDTO data) {
		teacher oldTeacher = findByTeacherCode(data.getCode());
		if(oldTeacher==null) return "Not found information";
		try {
			oldTeacher.setAdress(data.getAdress());
			oldTeacher.setDateOfBirth(data.getDateofBirth());
			oldTeacher.setImage(data.getImage());
			oldTeacher.setName(data.getName());
			oldTeacher.setPhoneNumber(data.getPhoneNumber());
			oldTeacher.setTime_update(Instant.now());
			teacherRepository.save(oldTeacher);
			return "Update teacher information successfully";

		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public String deleteTeacher(String code) {
		teacher data = findByTeacherCode(code);
		try {
			teacherRepository.delete(data);
			return "delete teacher successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

}
