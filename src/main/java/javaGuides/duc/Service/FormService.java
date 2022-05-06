package javaGuides.duc.Service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import javaGuides.duc.DTO.FormDTO;
import javaGuides.duc.Entity.Form;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Repository.FormRepository;
import javaGuides.duc.Repository.studentRepository;

@Service
public class FormService {
	private FormRepository formRepository;
	private studentRepository studentRepository;

	public FormService(FormRepository formRepository, studentRepository studentRepository) {
		this.formRepository = formRepository;
		this.studentRepository = studentRepository;
	}

	public String createFormm(FormDTO data) {
		try {
			Student student = studentRepository.findByStudentCode(data.getCode());
			if (student == null)
				return "Not found student in system";
			Form form = new Form();
			form.setName(data.getName());
			form.setStatus(data.getStatus());
			form.setTime(data.getTime());
			form.setStudent(student);
			formRepository.save(form);
			return "Submit form successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}
	public List<Form> getAll() {
		return formRepository.findAll();
	}
	public Set<Form> findByStudent(String code) {
		try {
			Student student=studentRepository.findByStudentCode(code);
			if(student==null) return null;
			Set<Form> form=student.getForms();
			return form;
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

}
