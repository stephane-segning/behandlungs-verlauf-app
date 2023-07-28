package team.sema.dpa.digitalpatientenakte;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.cfg.Configuration;
import team.sema.dpa.digitalpatientenakte.dao.impl.CaseRepoImpl;
import team.sema.dpa.digitalpatientenakte.dao.impl.PatientRepositoryImpl;
import team.sema.dpa.digitalpatientenakte.services.CaseService;
import team.sema.dpa.digitalpatientenakte.services.PatientService;
import team.sema.dpa.digitalpatientenakte.views.ScreenUtils;
import team.sema.dpa.digitalpatientenakte.views.ScreensController;

import java.io.IOException;

public class MainApplication extends Application {
    private final PatientService patientService;
    private final CaseService caseService;

    public MainApplication() {
        final var sessionFactory = new Configuration().configure().buildSessionFactory();
        final var patientRepository = new PatientRepositoryImpl(sessionFactory);
        final var caseRepository = new CaseRepoImpl(sessionFactory);

        patientService = new PatientService(patientRepository);
        caseService = new CaseService(caseRepository);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        final var mainContainer = new ScreensController(patientService, caseService);
        mainContainer.loadScreen(ScreenUtils.MAIN_SCREEN, "main.fxml");
        mainContainer.loadScreen(ScreenUtils.PATIENT_INFO_SCREEN, "patient-info.fxml");
        mainContainer.loadScreen(ScreenUtils.CASE_GRAPH, "case-graph.fxml");

        mainContainer.setScreen(ScreenUtils.MAIN_SCREEN);

        final var scene = new Scene(mainContainer);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);

        primaryStage.setTitle("Digital Patient Act");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}