package team.sema.dpa.digitalpatientenakte.views;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;
import team.sema.dpa.digitalpatientenakte.state.Component;
import team.sema.dpa.digitalpatientenakte.state.PostConstruct;
import team.sema.dpa.digitalpatientenakte.views.utils.ScreenUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
public class ScreensController extends StackPane {
    private final ConcurrentMap<String, Node> screens = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> controllers = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        setScreen(ScreenUtils.MAIN_SCREEN);
    }

    public void addScreen(String name, Node screen, Object controller) {
        screens.put(name, screen);
        controllers.put(name, controller);
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
        System.out.println("Screen set: " + name);
    }

    public Object getController(String name) {
        // Assuming you have a map from screen names to controller instances
        return controllers.get(name);
    }
}

