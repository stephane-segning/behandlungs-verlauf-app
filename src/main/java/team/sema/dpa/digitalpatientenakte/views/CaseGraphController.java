package team.sema.dpa.digitalpatientenakte.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.SetUtils;
import team.sema.dpa.digitalpatientenakte.models.CaseEntity;
import team.sema.dpa.digitalpatientenakte.models.CaseStepEntity;
import team.sema.dpa.digitalpatientenakte.models.PatientEntity;
import team.sema.dpa.digitalpatientenakte.services.CaseStepService;
import team.sema.dpa.digitalpatientenakte.state.Autowired;
import team.sema.dpa.digitalpatientenakte.state.Component;
import team.sema.dpa.digitalpatientenakte.state.ViewController;
import team.sema.dpa.digitalpatientenakte.views.graphs.CellType;
import team.sema.dpa.digitalpatientenakte.views.graphs.Graph;
import team.sema.dpa.digitalpatientenakte.views.utils.ScreenUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Component
@ViewController(name = ScreenUtils.CASE_GRAPH, view = "views/case-graph.fxml")
public class CaseGraphController implements Initializable {
    @Autowired
    private ScreensController screenParent;

    @Autowired
    private CaseStepService stepService;

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
        System.out.println("Update graph");
        if (patient == null || aCase == null) {
            System.out.println("Patient or case is null");
            return;
        }

        final var model = graph.getModel();
        model.clear();

        graph.beginUpdate();

        List<CaseStepEntity> stepsByCaseId = stepService.getStepsByCaseId(aCase.getId());
        final var flowCharts = stepsByCaseId.stream().map(CaseStepEntity::getFlowChart).collect(Collectors.toSet());
        final var flowEdges = flowCharts.stream().flatMap(x -> SetUtils.union(x.getStartingEdges(), x.getEndingEdges()).stream()).collect(Collectors.toSet());

        flowCharts.forEach(x -> model.addCell(x.getName(), CellType.RECTANGLE));
        flowEdges.forEach(x -> model.addEdge(x.getStartNode().getName(), x.getEndNode().getName()));

        graph.endUpdate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleBack(ActionEvent actionEvent) {
        screenParent.setScreen(ScreenUtils.PATIENT_INFO_SCREEN);
    }
}
