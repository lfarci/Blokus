package blokus.view.fx;

import blokus.controller.MissTurn;
import blokus.controller.Rotate;
import blokus.controller.SelectBoardSquare;
import blokus.controller.SelectCurrentPiece;
import blokus.controller.Withdraw;
import blokus.model.Game;
import blokus.model.Piece;
import blokus.model.Square;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents main box of <i>Blokus</i>.
 *
 * @author Logan Farci (47923)
 */
public class FxView extends VBox implements Observer {

    /**
     * Maximal width of the view.
     */
    public static final int MAX_WIDTH = 1_000;

    /**
     * Maximal height of the view.
     */
    public static final int MAX_HEIGHT = 750;

    /**
     * Title of the view.
     */
    public static final String TITLE = "Blokus";

    private final Stage stage;
    private final MenuBar menu;
    private final Game game;
    private final GameBox gameBox;
    private final ControlPane control;

    /**
     * Constructs the main view of the <i>Blokus</i> application.
     *
     * @param stage javaFX stage build by the Application class.
     * @param game is the game to display.
     */
    public FxView(Stage stage, Game game) {
        this.stage = Objects.requireNonNull(stage, "No given stage in arguments");
        this.game = game;
        this.gameBox = new GameBox(game);
        this.menu = new MenuBar(new Menu("File"), new Menu("Options"), new Menu("Help"));
        this.control = new ControlPane();
        setContent();
        setStyle();
    }

    /**
     * Initialize the stage and show it to the screen.
     */
    public void initialize() {
        initializeStage();
        stage.show();
    }

    private void initializeStage() {
        Scene scene = new Scene(this);
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        prefWidthProperty().bind(scene.widthProperty());
        prefHeightProperty().bind(scene.heightProperty());

    }

    public void setSelectCurrentPieceAction() {
        gameBox.getStockPanes().forEach((stockPane) -> {
            stockPane.getPiecePanes().forEach((piecePane) -> {
                piecePane.setOnMousePressed(new SelectCurrentPiece(game, piecePane));
            });
        });
    }

    public void setSelectBoardSquareAction() {
        gameBox.getBoardSquares().forEach((node) -> {
            Square current = new Square(GridPane.getRowIndex(node),
                    GridPane.getColumnIndex(node));
            node.setOnMousePressed(new SelectBoardSquare(current, game));
        });
    }

    public void setRotateAction() {
        control.getRotateButton().setOnMousePressed(new Rotate(this, game));
    }

    public void setMissTurnAction() {
        control.getMissTurnButton().setOnMousePressed(new MissTurn(game, this));
    }

    public void setWithdrawAction() {
        control.getWithdrawButton().setOnMousePressed(new Withdraw(this, game));
    }

    public void setPiecePreviewAction() {
        gameBox.getBoardSquares().forEach((node) -> {
            Square current = new Square(GridPane.getRowIndex(node),
                    GridPane.getColumnIndex(node));
            setOnMouseEntered(event -> {
                Piece currentPiece = game.getCurrentPlayer().getCurrentPiece();
                gameBox.getBoard().previewPiece(currentPiece, current);
            });
        });
    }

    /**
     * Sets this box content.
     */
    final void setContent() {
        this.getChildren().addAll(menu, this.gameBox, control);
    }

    /**
     * Sets this box style.
     */
    final void setStyle() {
        this.setSpacing(20);
    }

    /**
     * Updates the content of game box.
     *
     * @param o the game.
     * @param o1 nothing.
     */
    @Override
    public void update(Observable o, Object o1) {
        gameBox.updateContent();
    }

    public static void displayAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Piece placement warning");
        alert.setHeaderText("Be aware...");
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
