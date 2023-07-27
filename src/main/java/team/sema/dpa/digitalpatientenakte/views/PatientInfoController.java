package team.sema.dpa.digitalpatientenakte.views;

import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import team.sema.dpa.digitalpatientenakte.services.CaseService;
import team.sema.dpa.digitalpatientenakte.views.factories.CaseServiceFactory;
import team.sema.dpa.digitalpatientenakte.views.factories.ControlledScreen;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Getter
@NoArgsConstructor
public class PatientInfoController implements Initializable, ControlledScreen, CaseServiceFactory {
    private PatientEntity patient;
    private CaseEntity caseE;
    private ScreensController screenParent;
    private CaseService caseService;
    @FXML
    private Label label;
    @FXML
    private Text patientID;
    @FXML
    private Text birthDate;
    @FXML
    private Text telNumber;
    @FXML
    private MFXTextField input;
    @FXML
    private MFXTableView<CaseEntity> table;

    public void setCaseService(CaseService caseService) {
        this.caseService = caseService;
        updateCaseTable();
    }

    private void updateCaseTable() {
        if (caseService == null || patient == null) {
            return;
        }

        final var items = table.getItems();
        items.clear();
        items.addAll(caseService.getCaseByPatientId(patient.getId()));
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;

        if (patient == null && label != null) {
            label.setText("No patient selected");
        } else if (patient != null && label != null) {
            updatePatientInfo();
            updateCaseTable();
        } else {
            System.out.println("PatientInfoController: Patient is null");
        }
    }

    private void updatePatientInfo() {
        if (patient == null) {
            return;
        }

        patientID.setText(patient.getIdNumber());
        if (patient.getBirthDate() != null) birthDate.setText(patient.getBirthDate().toString());
        if (patient.getTelNumber() != null) birthDate.setText(patient.getTelNumber());
        label.setText(patient.getFirstName() + " " + patient.getLastName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.textProperty().addListener((observable, oldValue, newValue) -> updatePatientData(newValue));
    }

    private void updatePatientData(String query) {
        System.out.println("Search button clicked");
    }

    public void handleBack(ActionEvent actionEvent) {
        screenParent.setScreen(ScreenUtils.MAIN_SCREEN);
    }

    public void showGraph(ActionEvent actionEvent) {
        final var controller = screenParent.getController(ScreenUtils.CASE_GRAPH);
        // Make sure the controller is indeed a PatientInfoController
        if (controller instanceof CaseGraphController caseGraphController) {
            caseGraphController.setPatient(patient);
            caseGraphController.setCaseE(caseE);
        }
        screenParent.setScreen(ScreenUtils.CASE_GRAPH);
    }
}
