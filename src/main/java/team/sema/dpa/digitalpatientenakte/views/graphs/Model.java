package team.sema.dpa.digitalpatientenakte.views.graphs;

import lombok.Getter;

import java.util.*;

@Getter
public class Model {
    private final Cell graphParent;

    private final List<Edge> allEdges;
    private final List<Edge> edges;

    private final Map<String, Cell> cellMap;

    public Model() {
        graphParent = new Cell("_ROOT_");
        allEdges = new ArrayList<>();
        edges = new ArrayList<>();
        cellMap = new HashMap<>();
    }

    public void clear() {
        allEdges.clear();
        edges.clear();
        cellMap.clear();
    }

    public void addCell(String id, CellType type) {
        switch (type) {
            case RECTANGLE -> {
                RectangleCell rectangleCell = new RectangleCell(id);
                addCell(rectangleCell);
            }
            case TRIANGLE -> {
                TriangleCell circleCell = new TriangleCell(id);
                addCell(circleCell);
            }
            default -> throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    private void addCell(Cell cell) {
        cellMap.put(cell.getCellId(), cell);
    }

    public void addEdge(String sourceId, String targetId) {
        final var sourceCell = cellMap.get(sourceId);
        final var targetCell = cellMap.get(targetId);
        final var edge = new Edge(sourceCell, targetCell);
        edges.add(edge);
    }

    /**
     * Attach all cells which don't have a parent to graphParent
     *
     * @param cells list of cells
     */
    public void attachOrphansToGraphParent(Collection<Cell> cells) {
        for (var cell : cells) {
            if (cell.getCellParents().size() == 0) {
                graphParent.addCellChild(cell);
            }
        }
    }

    public void clearParent() {
        graphParent.getCellChildren().clear();
    }

    public void merge() {
        // edges
        allEdges.addAll(edges);
        edges.clear();
    }

    public Collection<Cell> getCells() {
        return cellMap.values();
    }
}
