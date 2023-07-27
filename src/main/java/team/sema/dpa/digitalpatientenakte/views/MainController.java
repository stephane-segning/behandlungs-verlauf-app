package team.sema.dpa.digitalpatientenakte.views;

import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.sema.dpa.digitalpatientenakte.models.PatientEntity;
import team.sema.dpa.digitalpatientenakte.services.PatientService;
import team.sema.dpa.digitalpatientenakte.views.factories.ControlledScreen;
import team.sema.dpa.digitalpatientenakte.views.factories.PatientServiceFactory;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@NoArgsConstructor
public class MainController implements Initializable, PatientServiceFactory, ControlledScreen {
    private PatientService patientService;
    private ScreensController screenParent;

    @FXML
    private MFXTextField input;

    @FXML
    private MFXTableView<PatientEntity> table;

    @Override
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
        if (patientService == null) {
            return;
        }
        updatePatients();
    }

    private void updatePatients() {
        updatePatients("");
    }

    private void updatePatients(String newValue) {
        final var newPatients = newValue.length() == 0 ? patientService.getAllPatients() : patientService.getPatientsByQuery(newValue);

        final var items = table.getItems();
        items.clear();
        items.addAll(newPatients);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        input.textProperty().addListener((observable, oldValue, newValue) -> updatePatients(newValue));
    }

    private void setupTable() {
        final var nameColumn = ClickableTableRowCell.of("ID Number", PatientEntity::getIdNumber, this::handleRowClick);
        final var firstnameColumn = ClickableTableRowCell.of("Firstname", PatientEntity::getFirstName, this::handleRowClick);
        final var lastnameColumn = ClickableTableRowCell.of("Lastname", PatientEntity::getLastName, this::handleRowClick);
        final var genderColumn = ClickableTableRowCell.of("Gender", PatientEntity::getGender, this::handleRowClick);
        final var telNumberColumn = ClickableTableRowCell.of("Tel Number", PatientEntity::getTelNumber, this::handleRowClick);
        final var birthDateColumn = ClickableTableRowCell.of("Birth Date", PatientEntity::getBirthDate, this::handleRowClick);

        table.getTableColumns().addAll(nameColumn, firstnameColumn, lastnameColumn, genderColumn, telNumberColumn, birthDateColumn);
        table.getFilters().addAll(new StringFilter<>("ID Number", PatientEntity::getIdNumber), new StringFilter<>("Gender", PatientEntity::getGender));

        table.setTableRowFactory(ClickableTableRowCell.of(table, this::handleRowClick));
    }

    private void handleRowClick(PatientEntity patient) {
        final var controller = screenParent.getController(ScreenUtils.PATIENT_INFO_SCREEN);
        // Make sure the controller is indeed a PatientInfoController
        if (controller instanceof PatientInfoController patientInfoController) {
            patientInfoController.setPatient(patient);
        }
        screenParent.setScreen(ScreenUtils.PATIENT_INFO_SCREEN);
    }

}