package team.sema.dpa.digitalpatientenakte.views.graphs;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleCell extends Cell {
    public RectangleCell(String cellId) {
        super(cellId);

        final var view = new Rectangle(50, 50);

        view.setStroke(Color.DODGERBLUE);
        view.setFill(Color.DODGERBLUE);

        setView(view);
    }
}
