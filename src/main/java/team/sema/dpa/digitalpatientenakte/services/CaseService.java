package team.sema.dpa.digitalpatientenakte.services;

import lombok.RequiredArgsConstructor;
import team.sema.dpa.digitalpatientenakte.dao.CaseRepo;
import team.sema.dpa.digitalpatientenakte.models.CaseEntity;
import team.sema.dpa.digitalpatientenakte.state.Component;

import java.util.List;
import java.util.UUID;

@Component
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

    public List<CaseEntity> getCasesByPatientId(UUID patientId) {
        return repository.findByPatientIdQuery(patientId);
    }

    public List<CaseEntity> getCasesByPatientIdAndQuery(UUID patientId, String query) {
        return repository.findByPatientIdQuery(patientId, query);
    }
}

