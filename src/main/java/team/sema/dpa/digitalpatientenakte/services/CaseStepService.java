package team.sema.dpa.digitalpatientenakte.services;

import lombok.NoArgsConstructor;
import lombok.Setter;
import team.sema.dpa.digitalpatientenakte.dao.CaseStepRepo;
import team.sema.dpa.digitalpatientenakte.models.CaseStepEntity;
import team.sema.dpa.digitalpatientenakte.state.Autowired;
import team.sema.dpa.digitalpatientenakte.state.Component;

import java.util.List;
import java.util.UUID;

@Component
@Setter
@NoArgsConstructor
public class CaseStepService {

    @Autowired
    private CaseStepRepo repository;

    public CaseStepEntity getStep(UUID id) {
        return repository.findById(id);
    }

    public List<CaseStepEntity> getAllSteps() {
        return repository.findAll();
    }

    public CaseStepEntity saveStep(CaseStepEntity model) {
        return repository.save(model);
    }

    public void deleteStep(CaseStepEntity model) {
        repository.delete(model);
    }

    public List<CaseStepEntity> getStepsByCaseId(UUID patientId) {
        return repository.findByCaseId(patientId);
    }
}

