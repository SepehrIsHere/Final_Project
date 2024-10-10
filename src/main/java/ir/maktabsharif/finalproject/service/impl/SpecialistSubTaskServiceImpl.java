package ir.maktabsharif.finalproject.service.impl;

import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;
import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.entities.SubTask;
import ir.maktabsharif.finalproject.exception.SpecialistNotFoundException;
import ir.maktabsharif.finalproject.exception.SubTaskNotFoundException;
import ir.maktabsharif.finalproject.repository.SpecialistRepository;
import ir.maktabsharif.finalproject.repository.SubTaskRepository;
import ir.maktabsharif.finalproject.service.SpecialistSubTaskService;
import ir.maktabsharif.finalproject.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistSubTaskServiceImpl implements SpecialistSubTaskService {
    private MapperUtil mapperUtil;
    private final SpecialistRepository specialistRepository;
    private final SubTaskRepository subTaskRepository;

    @Override
    public List<SubTaskDto> specialistSubTasks(SpecialistDto specialistDto) {
        Specialist specialist = findSpecialist(specialistDto);
        if (specialist != null) {
            List<SubTask> specialistSubTasks = specialist.getSubTasks();
            if (specialistSubTasks != null) {
                List<SubTaskDto> subTaskDtos = new ArrayList<>();
                for (SubTask subTask : specialistSubTasks) {
                    subTaskDtos.add(mapperUtil.convertToDto(subTask));
                }
                return subTaskDtos;
            } else {
                throw new SpecialistNotFoundException("Specialist list is null or empty");
            }
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found ! ");
        }
    }

    @Override
    public void addSubTaskToSpecialist(SpecialistDto specialistDto, SubTaskDto subTaskDto) {
        Specialist specialist = findSpecialist(specialistDto);
        SubTask subTask = subTaskRepository.findByName(subTaskDto.subTaskName());
        if (specialist != null) {
            if (subTask != null) {
                List<SubTask> specialistSubTasks = specialist.getSubTasks();
                if (specialistSubTasks != null && !specialistSubTasks.contains(subTask)) {
                    specialistSubTasks.add(subTask);
                }
            } else {
                throw new SubTaskNotFoundException("SubTask Not Found ! ");
            }
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found ! ");
        }
    }

    @Override
    public void removeSubTaskFromSpecialist(SpecialistDto specialistDto, SubTaskDto subTaskDto) {
        Specialist specialist = findSpecialist(specialistDto);
        SubTask subTask = findSubTask(subTaskDto);
        if (specialist != null) {
            if (subTask != null) {
                List<SubTask> specialistSubTasks = specialist.getSubTasks();
                if (specialistSubTasks != null) {
                    specialistSubTasks.remove(subTask);
                }
            } else {
                throw new SubTaskNotFoundException("SubTask Not Found ! ");
            }
        } else {
            throw new SpecialistNotFoundException("Specialist Not Found ! ");
        }
    }

    private Specialist findSpecialist(SpecialistDto specialistDto) {
        return specialistRepository.findByFirstNameAndLastName(specialistDto.firstname(), specialistDto.lastname());
    }

    private SubTask findSubTask(SubTaskDto subTaskDto) {
        return subTaskRepository.findByName(subTaskDto.subTaskName());
    }
}
