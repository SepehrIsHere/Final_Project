package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.dto.SpecialistDto;
import ir.maktabsharif.finalproject.dto.SubTaskDto;

import java.util.List;

public interface SpecialistSubTaskService {
    List<SubTaskDto> specialistSubTasks(SpecialistDto specialistDto);

    void addSubTaskToSpecialist(SpecialistDto specialistDto, SubTaskDto subTaskDto);

    void removeSubTaskFromSpecialist(SpecialistDto specialistDto, SubTaskDto subTaskDto);
}
