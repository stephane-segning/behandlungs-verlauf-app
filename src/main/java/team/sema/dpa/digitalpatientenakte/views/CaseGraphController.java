package team.sema.dpa.digitalpatientenakte.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import team.sema.dpa.digitalpatientenakte.models.CaseEntity;
import team.sema.dpa.digitalpatientenakte.models.PatientEntity;
import team.sema.dpa.digitalpatientenakte.state.Component;
import team.sema.dpa.digitalpatientenakte.state.ViewController;
import team.sema.dpa.digitalpatientenakte.views.graphs.CellType;
import team.sema.dpa.digitalpatientenakte.views.graphs.Graph;
import team.sema.dpa.digitalpatientenakte.views.utils.ScreenUtils;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Getter
@RequiredArgsConstructor
@Component
@ViewController(name = ScreenUtils.CASE_GRAPH, view = "views/case-graph.fxml")
public class CaseGraphController implements Initializable {
    private final ScreensController screenParent;

    private PatientEntity patient;
    private CaseEntity aCase;

    @FXML
    private Graph graph;

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
        updateGraph();
    }

    public void setACase(CaseEntity aCase) {
        this.aCase = aCase;
        updateGraph();
    }

    private void updateGraph() {
        if (patient == null || aCase == null) {
            return;
        }

        final var model = graph.getModel();
        model.clear();

        graph.beginUpdate();

        model.addCell("Cell A", CellType.RECTANGLE);
        model.addCell("Cell B", CellType.RECTANGLE);
        model.addCell("Cell C", CellType.RECTANGLE);
        model.addCell("Cell D", CellType.TRIANGLE);
        model.addCell("Cell E", CellType.TRIANGLE);
        model.addCell("Cell F", CellType.RECTANGLE);
        model.addCell("Cell G", CellType.RECTANGLE);

        model.addEdge("Cell A", "Cell B");
        model.addEdge("Cell A", "Cell C");
        model.addEdge("Cell B", "Cell C");
        model.addEdge("Cell C", "Cell D");
        model.addEdge("Cell B", "Cell E");
        model.addEdge("Cell D", "Cell F");
        model.addEdge("Cell D", "Cell G");

        graph.endUpdate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleBack(ActionEvent actionEvent) {
        screenParent.setScreen(ScreenUtils.PATIENT_INFO_SCREEN);
    }
}
