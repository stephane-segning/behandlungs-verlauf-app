package team.sema.dpa.digitalpatientenakte.dao;

import team.sema.dpa.digitalpatientenakte.models.PatientEntity;

import java.util.List;
import java.util.UUID;

public interface PatientRepo {
    PatientEntity findById(UUID id);

    List<PatientEntity> findAll();

    PatientEntity save(PatientEntity patient);

    void delete(PatientEntity patient);

    List<PatientEntity> findByQuery(String input);
}

