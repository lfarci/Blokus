package blokus.model;

import blokus.exception.ModelException;

/**
 * Represents the board of <i>Blokus</i>.
 *
 * @author Logan Farci (47923)
 */
class Board {

    private final static int BOARD_SIZE = 20;
    private final Piece[][] squares;

    /**
     * Initializes this board with a 20 by 20 empty grid of squares.
     */
    Board() {
        this.squares = new Piece[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * Gets the squares of this board.
     *
     * @return the squares of this board.
     */
    Piece[][] getSquares() {
        return squares;
    }

    /**
     * Tells if the given square is free. A free square does not contains a
     * piece.
     *
     * @param row is the row of the square to test.
     * @param column is the column of the square to test.
     * @return true if the given position is an empty square.
     */
    boolean isEmptyAt(int row, int column) {
        return squares[row][column] == null;
    }

    /**
     * Tells if the given square is in this board bounds.
     *
     * @param row is the row of the square to test.
     * @param column is the column of the square to test.
     * @return true if the given square is in the board bounds.
     */
    boolean contains(int row, int column) {
        return 0 <= row && row < BOARD_SIZE && 0 <= column && column < BOARD_SIZE;
    }

    /**
     * Tells if the given square is valid. A valid square is in this board
     * bounds and free.
     *
     * @param row is the row of the square to test.
     * @param column is the column of the square to test.
     * @return true if the given square is in the board bounds and free.
     */
    boolean isValid(int row, int column) {
        return contains(row, column) && isEmptyAt(row, column);
    }

    /**
     * Makes sure the given square is valid. A valid square is free and is
     * inside the board.
     *
     * @param square is the square to check.
     * @return a valid square.
     */
    void requireValidSquare(int row, int column) throws ModelException {
        if (!contains(row, column)) {
            throw new ModelException("Square at position row "
                    + row + ", " + column + " is out of the board bounds.");
        }
        if (!isEmptyAt(row, column)) {
            throw new ModelException("not a free position at row " + row + ", "
                    + "column " + column + " is occupied by "
                    + squares[row][column].getColor() + " player.");
        }
    }

    /**
     * Tells if the given piece can be placed at the given position.
     *
     * @param piece is the piece to check the placement for.
     * @param row is the row where the piece is to be placed.
     * @param column is the column where the piece is to be placed.
     * @return true if the given piece can placed at the given position.
     */
    boolean hasSpaceFor(Piece piece, int row, int column) {
        return piece.getShape().getSquares().stream()
                .map(s -> s.move(row, column))
                .allMatch(s -> isValid(s.getRow(), s.getColumn()));
    }

    void addSquare(Piece piece, int row, int column) {
        squares[row][column] = piece;
    }

    /**
     * Adds the given piece at the given row and column.
     *
     * @param piece is the piece to addPiece to this board.
     * @param row is the row of the square of destination in this board.
     * @param column is the column of the square of destination in this board.
     */
    void addPiece(Piece piece, int row, int column) throws ModelException {
        if (!hasSpaceFor(piece, row, column)) {
            throw new ModelException("the given piece cannot be place at row "
                    + row + ", column " + column + ".");
        }
        for (Square pieceSquare : piece.getShape().getSquares()) {
            Square boardSquare = pieceSquare.move(row, column);
            addSquare(piece, boardSquare.getRow(), boardSquare.getColumn());
        }
    }

}
