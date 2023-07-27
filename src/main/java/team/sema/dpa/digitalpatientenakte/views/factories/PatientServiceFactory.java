package team.sema.dpa.digitalpatientenakte.views.factories;

import team.sema.dpa.digitalpatientenakte.services.PatientService;

public interface PatientServiceFactory {
    void setPatientService(PatientService patientService);
}
