package Extra;

import ClasesAuxiliares.InfoProducto;
import ClasesAuxiliares.Producto;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ImageTextCell extends ListCell<Producto> {

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    InfoProducto info;

    @Override
    protected void updateItem(Producto item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // doesn't show anything
        } else {
            // create the cell
            HBox hbox = new HBox(8.0); // Gap between controls
            hbox.setAlignment(Pos.CENTER_RIGHT);

            // set cover image
            /*ImageView coverImageView = new ImageView(new Image(item.getCover()));
            coverImageView.setPreserveRatio(false);
            coverImageView.setFitHeight(32.0);
            coverImageView.setFitWidth(32.0);*/

            // set text
            info=item.getInfoProducto();
            Label title = new Label(info.getNombre());
            title.setFont(Font.font("System", FontWeight.BOLD, 12));
            title.setTextAlignment(TextAlignment.LEFT);

            Label platform = new Label(info.getCategoria());
            platform.setFont(Font.font("System", 11));
            platform.setTextAlignment(TextAlignment.LEFT);

            Label price = new Label();
            String currencyPrice = currencyFormatter.format(info.getPrecioUnitario());
            price.setText(currencyPrice);
            price.setFont(Font.font("System", FontWeight.BOLD, 12));

            VBox vb = new VBox(2.0);

            Pane pane = new Pane();

            vb.getChildren().addAll(title, platform);

            hbox.getChildren().addAll(vb, pane, price);
            HBox.setHgrow(pane, Priority.ALWAYS);
            setGraphic(hbox);
            setPrefWidth(USE_PREF_SIZE);
        }
    }
}
