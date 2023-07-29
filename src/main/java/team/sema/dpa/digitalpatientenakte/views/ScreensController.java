package team.sema.dpa.digitalpatientenakte.views;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import team.sema.dpa.digitalpatientenakte.state.Component;
import team.sema.dpa.digitalpatientenakte.state.PostConstruct;
import team.sema.dpa.digitalpatientenakte.views.utils.ScreenUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScreensController extends StackPane {
    private final Map<String, Node> screens;
    private final Map<String, Object> controllers;

    public ScreensController() {
        screens = new ConcurrentHashMap<>();
        controllers = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void init() {
        setScreen(ScreenUtils.MAIN_SCREEN);
    }

    public void addScreen(String name, Node screen, Object controller) {
        screens.put(name, screen);
        controllers.put(name, controller);
    }

    public void setScreen(final String name) {
        final var element = screens.get(name);
        if (element == null) {
            System.out.println("There is no screen with name " + name);
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

