package team.sema.dpa.digitalpatientenakte.views;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.scene.input.MouseButton;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

public class ClickableTableRowCell {

    public static <T, U extends Comparable<? super U>> MFXTableColumn<T> of(String title, Function<T, U> extractor, Consumer<T> onClick) {
        final var tableColumn = new MFXTableColumn<T>(title, true, Comparator.comparing(extractor));
        tableColumn.setRowCellFactory(ClickableTableRowCell.of(extractor, onClick));
        return tableColumn;
    }

    public static <T, E> Function<T, MFXTableRowCell<T, ?>> of(Function<T, E> extractor, Consumer<T> onClick) {
        return t -> new MFXTableRowCell<>(extractor) {{
            this.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    onClick.accept(t);
                }
            });
        }};
    }

    public static <T> Function<T, MFXTableRow<T>> of(MFXTableView<T> tableView, Consumer<T> onClick) {
        return t -> new MFXTableRow<>(tableView, t) {{
            this.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    onClick.accept(t);
                }
            });
        }};
    }
}
