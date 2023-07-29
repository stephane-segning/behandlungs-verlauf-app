package team.sema.dpa.digitalpatientenakte.views;

import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import team.sema.dpa.digitalpatientenakte.models.CaseEntity;
import team.sema.dpa.digitalpatientenakte.models.PatientEntity;
import team.sema.dpa.digitalpatientenakte.services.CaseService;
import team.sema.dpa.digitalpatientenakte.state.Autowired;
import team.sema.dpa.digitalpatientenakte.state.Component;
import team.sema.dpa.digitalpatientenakte.state.ViewController;
import team.sema.dpa.digitalpatientenakte.views.utils.ClickableTableRowCell;
import team.sema.dpa.digitalpatientenakte.views.utils.ScreenUtils;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Getter
@Component
@ViewController(name = ScreenUtils.PATIENT_INFO_SCREEN, view = "views/patient-info.fxml")
@RequiredArgsConstructor
public class PatientInfoController implements Initializable {
    @Autowired
    private ScreensController screenParent;

    @Autowired
    private CaseService caseService;

    private PatientEntity patient;
    private CaseEntity caseE;

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

    private void updateCaseTable() {
        updateCaseTable("");
    }

    private void updateCaseTable(String query) {
        if (caseService == null || patient == null) {
            return;
        }

        final var items = table.getItems();
        items.clear();
        final var casesByPatientId = query.isEmpty()
                ? caseService.getCasesByPatientId(patient.getId())
                : caseService.getCasesByPatientIdAndQuery(patient.getId(), query);
        System.out.println("Got " + casesByPatientId.size() + " cases for patient " + casesByPatientId);
        items.addAll(casesByPatientId);
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
        setupTable();
        input.textProperty().addListener((observable, oldValue, newValue) -> updatePatientData(newValue));
    }

    private void updatePatientData(String query) {
        updateCaseTable(query);
    }

    public void handleBack(ActionEvent actionEvent) {
        screenParent.setScreen(ScreenUtils.MAIN_SCREEN);
    }

    public void showGraph(ActionEvent actionEvent) {
        final var controller = screenParent.getController(ScreenUtils.CASE_GRAPH);
        // Make sure the controller is indeed a PatientInfoController
        if (controller instanceof CaseGraphController caseGraphController) {
            caseGraphController.setPatient(patient);
            caseGraphController.setACase(caseE);
        }
        screenParent.setScreen(ScreenUtils.CASE_GRAPH);
    }

    private void setupTable() {
        final var nameColumn = ClickableTableRowCell.of("ID Number", CaseEntity::getId, this::handleRowClick);
        final var firstnameColumn = ClickableTableRowCell.of("Date", CaseEntity::getDate, this::handleRowClick);
        final var lastnameColumn = ClickableTableRowCell.of("Arrived by", CaseEntity::getArrivedBy, this::handleRowClick);

        table.getTableColumns().addAll(nameColumn, firstnameColumn, lastnameColumn);
        table.getFilters().addAll(
                new StringFilter<>("ID Number", (CaseEntity c) -> c.getId().toString()),
                new StringFilter<>("Gender", (CaseEntity c) -> c.getDate().toString()),
                new StringFilter<>("Arrived by", CaseEntity::getArrivedBy)
        );

        table.setTableRowFactory(ClickableTableRowCell.of(table, this::handleRowClick));
    }

    private void handleRowClick(CaseEntity aCase) {
        this.caseE = aCase;
    }
}
