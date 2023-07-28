package team.sema.dpa.digitalpatientenakte.views.graphs;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Cell extends Pane {
    @Getter
    final String cellId;

    final private List<Node> children = new ArrayList<>();
    final private List<Node> parents = new ArrayList<>();

    @Getter
    private Node view;

    public void addCellChild(Node cell) {
        children.add(cell);
    }

    public List<Node> getCellChildren() {
        return children;
    }

    public void addCellParent(Node cell) {
        parents.add(cell);
    }

    public List<Node> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void setView(Node view) {
        this.view = view;
        getChildren().add(view);
    }
}
