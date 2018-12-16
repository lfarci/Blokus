package blokus.model;

import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the integrity of <i>Blokus</i>.
 *
 * @author g47923
 */
public class BlokusTest {

    /**
     * Game after construction should have an empty board and four players of
     * different colors.
     */
    @Test
    public void construction() {
        Blokus g = new Blokus();
        g.getPlayers().forEach((player) -> {
            assertEquals(1, Collections.frequency(g.getPlayers(), player));
        });
        assertTrue(g.getBoard().isEmpty() && g.getState() == BlokusState.FIRST_ROUND);
    }

    /**
     * Game with AI should have an empty board and four players of
     * different colors and the correct number of AI players.
     */
    @Test
    public void construction_case_2() {
        Blokus g = new Blokus(2);
        int cptAI = 0;
        g.getPlayers().forEach((player) -> {
            assertEquals(1, Collections.frequency(g.getPlayers(), player));
        });
        for (int i = 0; i < 2; i++) {
            assertTrue(g.getPlayers().get(i).isBot());
        }
        assertTrue(g.getBoard().isEmpty() && g.getState() == BlokusState.FIRST_ROUND);
    }

    /**
     * Game with AI should have an empty board and four players of
     * different colors and the correct number of AI players.
     */
    @Test
    public void construction_case_3() {
        Blokus g = new Blokus(1);
        int cptAI = 0;
        g.getPlayers().forEach((player) -> {
            assertEquals(1, Collections.frequency(g.getPlayers(), player));
        });
        for (int i = 0; i < 3; i++) {
            assertTrue(g.getPlayers().get(i).isBot());
        }
        assertTrue(g.getBoard().isEmpty() && g.getState() == BlokusState.FIRST_ROUND);
    }

    /**
     * Game after initialization should have an empty board and four players of
     * different colors.
     */
    @Test
    public void initialization() {
        Blokus g = new Blokus();
        g.selectCurrentPlayerPiece(Shape.SHAPE_01);
        g.placePiece(0, 0);
        g.getCurrentPlayer().withdraw();
        g.initialize();
        g.getPlayers().forEach((player) -> {
            assertEquals(1, Collections.frequency(g.getPlayers(), player));
        });
        assertTrue(g.getBoard().isEmpty() && g.getState() == BlokusState.FIRST_ROUND);
    }



    /**
     * Blokus is over when all the players have an empty stock.
     */
    @Test
    public void isOver_case_1() {
        Blokus g = new Blokus();
        g.endFirstRound();
        for (Player player : g.getPlayers()) {
            player.clearStock();
        }
        assertTrue(g.isOver());
    }

    /**
     * Blokus should not be over when one of the players has not placed all her/
     * his pieces.
     */
    @Test
    public void isOver_case_2() {
        Blokus g = new Blokus();
        g.endFirstRound();
        for (int i = 0; i < 3; i++) {
            g.getPlayers().get(i).clearStock();
        }
        assertFalse(g.isOver());
    }

    /**
     * Blokus is over when all the players are withdrawn.
     */
    @Test
    public void isOver_case_3() {
        Blokus g = new Blokus();
        g.endFirstRound();
        for (Player player : g.getPlayers()) {
            player.withdraw();
        }
        assertTrue(g.isOver());
    }

    /**
     * Blokus should not be over when one of the players is not withdrawn.
     */
    @Test
    public void isOver_case_4() {
        Blokus g = new Blokus();
        g.endFirstRound();
        for (int i = 0; i < 3; i++) {
            g.getPlayers().get(i).withdraw();
        }
        assertFalse(g.isOver());
    }

}
