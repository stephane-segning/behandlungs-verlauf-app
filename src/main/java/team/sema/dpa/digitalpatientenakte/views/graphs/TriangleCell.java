package team.sema.dpa.digitalpatientenakte.views.graphs;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleCell extends Cell {
    public TriangleCell(String cellId) {
        super(cellId);

        double width = 50;
        double height = 50;

        final var view = new Polygon(width / 2, 0, width, height, 0, height);

        view.setStroke(Color.RED);
        view.setFill(Color.RED);

        setView(view);
    }
}
