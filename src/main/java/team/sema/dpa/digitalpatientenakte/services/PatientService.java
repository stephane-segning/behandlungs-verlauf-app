package team.sema.dpa.digitalpatientenakte.services;

import lombok.NoArgsConstructor;
import lombok.Setter;
import team.sema.dpa.digitalpatientenakte.dao.PatientRepo;
import team.sema.dpa.digitalpatientenakte.models.PatientEntity;
import team.sema.dpa.digitalpatientenakte.state.Autowired;
import team.sema.dpa.digitalpatientenakte.state.Component;

import java.util.List;
import java.util.UUID;

@Component
@Setter
@NoArgsConstructor
public class PatientService {

    @Autowired
    private PatientRepo repository;

    public PatientEntity getPatient(UUID id) {
        return repository.findById(id);
    }

    public List<PatientEntity> getAllPatients() {
        return repository.findAll();
    }

    public PatientEntity savePatient(PatientEntity patient) {
        return repository.save(patient);
    }

    public void deletePatient(PatientEntity patient) {
        repository.delete(patient);
    }

    public List<PatientEntity> getPatientsByQuery(String input) {
        return repository.findByQuery(input);
    }
}

