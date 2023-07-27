package team.sema.dpa.digitalpatientenakte.services;

import lombok.RequiredArgsConstructor;
import team.sema.dpa.digitalpatientenakte.dao.CaseRepo;
import team.sema.dpa.digitalpatientenakte.models.CaseEntity;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CaseService {

    private final CaseRepo repository;

    public CaseEntity getCase(UUID id) {
        return repository.findById(id);
    }

    public List<CaseEntity> getAllCases() {
        return repository.findAll();
    }

    public CaseEntity saveCase(CaseEntity model) {
        return repository.save(model);
    }

    public void deleteCase(CaseEntity model) {
        repository.delete(model);
    }

    public List<CaseEntity> getCaseByPatientId(UUID patientId) {
        return repository.findByPatientIdQuery(patientId);
    }
}

