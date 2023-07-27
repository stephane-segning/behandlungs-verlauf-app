package team.sema.dpa.digitalpatientenakte.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;
import team.sema.dpa.digitalpatientenakte.services.CaseService;
import team.sema.dpa.digitalpatientenakte.services.PatientService;
import team.sema.dpa.digitalpatientenakte.views.factories.CaseServiceFactory;
import team.sema.dpa.digitalpatientenakte.views.factories.ControlledScreen;
import team.sema.dpa.digitalpatientenakte.views.factories.PatientServiceFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class ScreensController extends StackPane {
    private final PatientService patientService;
    private final CaseService caseService;
    private final ConcurrentMap<String, Node> screens = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> controllers = new ConcurrentHashMap<>();

    public void addScreen(String name, Node screen, Object controller) {
        screens.put(name, screen);
        controllers.put(name, controller);
    }

    public void loadScreen(String name, String resource) {
        try {
            final var loader = new FXMLLoader(getClass().getResource(resource));
            final var loadScreen = (Parent) loader.load();
            final var controller = loader.getController();

            if (controller instanceof PatientServiceFactory myScreenController) {
                myScreenController.setPatientService(patientService);
            }
            if (controller instanceof CaseServiceFactory myScreenController) {
                myScreenController.setCaseService(caseService);
            }
            if (controller instanceof ControlledScreen myScreenController) {
                myScreenController.setScreenParent(this);
            }

            addScreen(name, loadScreen, controller);
        } catch (Exception e) {
            System.out.println("Could not add screen " + name + " to screens!");
            e.printStackTrace();
        }
    }

    public void setScreen(final String name, final Object... params) {
        final var element = screens.get(name);
        if (element == null) {
            System.out.println("Screen hasn't been loaded!");
            return;
        }

        if (!getChildren().isEmpty()) {
            getChildren().remove(0);
            getChildren().add(0, element);
        } else {
            getChildren().add(element);
        }
    }

    public Object getController(String name) {
        // Assuming you have a map from screen names to controller instances
        return controllers.get(name);
    }
}

