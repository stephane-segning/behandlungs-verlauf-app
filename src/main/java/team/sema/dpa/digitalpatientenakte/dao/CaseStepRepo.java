package team.sema.dpa.digitalpatientenakte.dao;

import team.sema.dpa.digitalpatientenakte.models.CaseStepEntity;

import java.util.List;
import java.util.UUID;

public interface CaseStepRepo {
    CaseStepEntity findById(UUID id);

    List<CaseStepEntity> findAll();

    CaseStepEntity save(CaseStepEntity model);

    void delete(CaseStepEntity model);

    List<CaseStepEntity> findByCaseId(UUID caseId);
}

