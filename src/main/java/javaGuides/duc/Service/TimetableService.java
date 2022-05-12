package javaGuides.duc.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import javaGuides.duc.DTO.TimetableDTO;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Entity.TimeTable;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Repository.TimetableRepository;
import javaGuides.duc.Repository.studentRepository;

@Service
public class TimetableService {
	private TimetableRepository timetableRepository;
	private studentRepository studentRepository;

	public TimetableService(TimetableRepository timetableRepository, studentRepository studentRepository) {
		this.timetableRepository = timetableRepository;
		this.studentRepository = studentRepository;
	}

	public String createTimetable(TimetableDTO timetableDTO) {
		try {
			TimeTable table = timetableRepository.findByCode(timetableDTO.getCode());
			if(table!=null) return "Timetable "+timetableDTO.getCode()+" already exist";
			table=new TimeTable();
			table.setDetail(timetableDTO.getDetail());
			table.setTime(timetableDTO.getTime());
			table.setTime_create(Instant.now());
			table.setCode(timetableDTO.getCode());
			timetableRepository.save(table);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public void save(TimeTable table) {
		timetableRepository.save(table);
	}

	public String updateTimetable( TimetableDTO timetableDTO) {
		try {
			TimeTable table = timetableRepository.findByCode(timetableDTO.getCode());
			if (table == null)
				return "Timetable not found in system";
			table.setDetail(timetableDTO.getDetail());
			table.setTime(timetableDTO.getTime());
			table.setTime_update(Instant.now());
			timetableRepository.save(table);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public List<String> getAll() {
		try {
			List<String> list = new ArrayList<>();
			List<TimeTable> tables = timetableRepository.findAll();
			tables.forEach(timetable -> {
				String string = "ID:" + timetable.getId() + " Detail:" + timetable.getDetail() + " Time:"
						+ timetable.getTime();
				list.add(string);
			});
			return list;
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public String addStudent(Set<String> code, String codeTimetable) {
		try {
			TimeTable table = timetableRepository.findByCode(codeTimetable);
			ArrayList<String> error = new ArrayList<>();
			if (table == null) {
				return "Timetable not found in system";
			}
			code.forEach(studentCode -> {
				Student student = studentRepository.findByStudentCode(studentCode);
				if (student == null)
					error.add(studentCode);
				else {
					Set<TimeTable> tables=student.getTables();
					tables.add(table);
					student.setTables(tables);
					studentRepository.save(student);
				}
					
				
			});
			if (error.size() == 0) {
				return "Successfully";
			} else  {
				error.add(" Not found in system");
				return error.toString();
			}

		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

}
