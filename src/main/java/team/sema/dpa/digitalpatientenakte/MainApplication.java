package team.sema.dpa.digitalpatientenakte;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import team.sema.dpa.digitalpatientenakte.state.Injector;
import team.sema.dpa.digitalpatientenakte.views.ScreensController;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Injector.startApplication(MainApplication.class);
        final var mainContainer = Injector.getService(ScreensController.class);

        final var scene = new Scene(mainContainer);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);

        primaryStage.setTitle("Digital Patient Act");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}