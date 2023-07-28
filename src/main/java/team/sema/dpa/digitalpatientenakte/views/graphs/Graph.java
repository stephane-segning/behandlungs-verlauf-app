package team.sema.dpa.digitalpatientenakte.views.graphs;

import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Graph extends BorderPane {
    /**
     * the pane wrapper is necessary or else the scrollpane would always align
     * the top-most and left-most child to the top and left eg when you drag the
     * top child down, the entire scrollpane would move down
     */
    private CellLayer cellLayer;
    private Model model;
    private Group canvas;
    private ZoomableScrollPane scrollPane;
    private MouseGestures mouseGestures;
    private Layout layout;

    public Graph() {
        super();
        model = new Model();

        canvas = new Group();
        cellLayer = new CellLayer();

        canvas.getChildren().add(cellLayer);

        mouseGestures = new MouseGestures(this);
        scrollPane = new ZoomableScrollPane(canvas);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        layout = new RandomLayout(this);

        setCenter(getScrollPane());
    }

    public void beginUpdate() {
        final var children = getCellLayer().getChildren();
        children.clear();

        getModel().clearParent();
    }

    public void endUpdate() {
        final var children = getCellLayer().getChildren();
        // add components to graph pane
        children.addAll(model.getEdges());
        children.addAll(model.getCells());

        // enable dragging of cells
        final var cells = model.getCells();
        cells.forEach(cell -> mouseGestures.makeDraggable(cell));

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(cells);

        // merge added & removed cells with all cells
        getModel().merge();

        layout.execute();
    }

    public double getScale() {
        return this.scrollPane.getScaleValue();
    }
}
