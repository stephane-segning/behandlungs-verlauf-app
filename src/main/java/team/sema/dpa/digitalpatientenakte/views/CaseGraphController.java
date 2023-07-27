package team.sema.dpa.digitalpatientenakte.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.sema.dpa.digitalpatientenakte.models.CaseEntity;
import team.sema.dpa.digitalpatientenakte.models.PatientEntity;
import team.sema.dpa.digitalpatientenakte.views.factories.ControlledScreen;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Getter
@NoArgsConstructor
public class CaseGraphController implements Initializable, ControlledScreen {
    private PatientEntity patient;
    private CaseEntity caseE;
    private ScreensController screenParent;

    @FXML
    private Label label;

    @FXML
    private Text patientID;

    @FXML
    private Text birthDate;

    @FXML
    private Text telNumber;

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
        updateGraph();
    }

    public void setCaseE(CaseEntity caseE) {
        this.caseE = caseE;
        updateGraph();
    }

    private void updateGraph() {
        if (patient != null) {
            patientID.setText(patient.getIdNumber());
            if (patient.getBirthDate() != null) birthDate.setText(patient.getBirthDate().toString());
            if (patient.getTelNumber() != null) birthDate.setText(patient.getTelNumber());
            label.setText(patient.getFirstName() + " " + patient.getLastName());
        }

        if (patient == null || caseE == null) {
            return;
        }

        // TODO fetch data from the view and show it in the graph
        return;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void updatePatientData(String query) {
        System.out.println("Search button clicked");
    }

    public void handleBack(ActionEvent actionEvent) {
        screenParent.setScreen(ScreenUtils.PATIENT_INFO_SCREEN);
    }
}
