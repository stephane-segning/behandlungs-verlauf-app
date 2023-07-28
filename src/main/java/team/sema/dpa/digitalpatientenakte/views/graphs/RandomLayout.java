package team.sema.dpa.digitalpatientenakte.views.graphs;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class RandomLayout implements Layout {
    final Random rnd = new Random();
    private final Graph graph;

    @Override
    public void execute() {
        final var cells = graph.getModel().getCells();

        for (final var cell : cells) {
            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;
            cell.relocate(x, y);
        }
    }
}
