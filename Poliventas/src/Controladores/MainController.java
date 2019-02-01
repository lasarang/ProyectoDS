/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ClasesAuxiliares.Producto;
import Extra.ImageTextCell;
import FamiliaPersona.Estudiante;
import PatronDAO.Producto.ProductoDAOImpl;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class MainController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Hyperlink cartButton;
    @FXML
    private VBox gamesVBox;
    @FXML
    private ListView<Producto> gamesListView;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView coverImageView;
    @FXML
    private Hyperlink videoHyperlink;
    @FXML
    private Button addToCartButton;
    @FXML
    private ImageView firstImageView;
    @FXML
    private ImageView secondImageView;
    @FXML
    private ImageView thirdImageView;
    @FXML
    private ImageView fourthImageView;

    private Alert alert;
    private Stage helpStage;
    private ObservableList<Producto> products = FXCollections.observableArrayList();
    private ObservableList<Producto> cartItems = FXCollections.observableArrayList();
    private List<Producto> results;
    private Estudiante usuario;
    private final ProductoDAOImpl productoDao = new ProductoDAOImpl();
    @FXML
    private VBox imagesVBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            results = productoDao.readMasBuscados();
            System.out.println("Mas buscasdos: " + results);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        products.addAll(results);

        gamesListView.setItems(products);

        gamesListView.getSelectionModel().select(0);
        gamesListView.setCellFactory((listview) -> new ImageTextCell());

    }

    @FXML
    private void showHelp(ActionEvent event) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(helpStage);
        alert.getDialogPane().setContentText("Una empresa desea crear una plataforma que permita reunir en un solo lugar a varios estudiantes emprendedores que deseen vender sus productos y que puedan receptar el pago mediante dicha plataforma. Para el desarrollo de este sistema se ha decidido contratar a personas con sólidos conocimientos en Diseño de Software para que diseñen una solución robusta y escalable.");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait().filter(response -> response == ButtonType.CLOSE);
    }

    @FXML
    private void proccessLogout(ActionEvent event) {
    }

    @FXML
    private void gotoStore(ActionEvent event) {
    }

    @FXML
    private void gotoLibrary(ActionEvent event) {
    }

    @FXML
    private void gotoAccount(ActionEvent event) {
    }

    @FXML
    private void showCart(ActionEvent event) {
    }

    @FXML
    private void addToCartButtonClicked(ActionEvent event) {
    }

}
