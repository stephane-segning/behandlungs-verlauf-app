package team.sema.dpa.digitalpatientenakte.dao;

import team.sema.dpa.digitalpatientenakte.models.CaseEntity;

import java.util.List;
import java.util.UUID;

public interface CaseRepo {
    CaseEntity findById(UUID id);

    List<CaseEntity> findAll();

    CaseEntity save(CaseEntity model);

    void delete(CaseEntity model);

    List<CaseEntity> findByPatientIdQuery(UUID patientId);

    List<CaseEntity> findByPatientIdQuery(UUID patientId, String query);
}

