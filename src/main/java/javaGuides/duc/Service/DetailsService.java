package javaGuides.duc.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import javaGuides.duc.DTO.DetailDTO;
import javaGuides.duc.Entity.Detail;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Exception.ResourseNotFoundException;
import javaGuides.duc.Repository.DetailRepository;
import javaGuides.duc.Repository.studentRepository;

@Service
public class DetailsService {
	private DetailRepository detailRepository;
	private studentRepository studentRepository;

	public DetailsService(DetailRepository detailRepository, studentRepository studentRepository) {
		this.detailRepository = detailRepository;
		this.studentRepository = studentRepository;
	}

	public String createDetail(DetailDTO detailDTO) {
		try {
			Student student = studentRepository.findByStudentCode(detailDTO.getCode());
			if (student == null)
				return "Student not found in system";
			Detail detail = student.getDetail();
			if (detail == null)
				detail = new Detail();
			detail.setPhoto(detailDTO.getPhoto());
			detail.setTimeIn(Instant.now());
			detail.setCode(detailDTO.getDetailCode());
			detail.setTimeOut(null);
			detail.setComment(null);
			student.setDetail(detail);
			detailRepository.save(detail);
			studentRepository.save(student);
			return "Create detail successfully";
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}

	}

	public String endDetail(DetailDTO detailDTO) {
		try {
			Student student = studentRepository.findByStudentCode(detailDTO.getCode());
			if (student == null)
				return "Student not found in system";
			Detail detail = student.getDetail();
			if (detail == null)
				return "Not found information";
			detail.setComment(detailDTO.getComment());
			detail.setTimeOut(Instant.now());
			detailRepository.save(detail);
			return "End detail successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Detail save(Detail detail) {
		return detailRepository.save(detail);
	}

	public List<Detail> getAll() {
		return detailRepository.findAll();
	}
}
