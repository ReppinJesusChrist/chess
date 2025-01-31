package chess;

import java.util.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] squares;
    public ChessBoard() {
        this.squares = new ChessPiece[8][8];
    }

    // Copy constructor
    public ChessBoard(ChessBoard original){
        this.squares = new ChessPiece[8][8];
        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                if(original.squares[i][j] == null) continue;
                this.squares[i][j] = new ChessPiece(original.squares[i][j]);
            }
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    public ChessPiece removePiece(ChessPosition position) {
        ChessPiece pieceToRemove = getPiece(position);
        squares[position.getRow()-1][position.getColumn()-1] = null;
        return pieceToRemove;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     * TODO: Make this function more efficient (Maybe use a parser like in TestUtilities.LoadBoard())
     */
    public void resetBoard() {
        // Pawns
        for(int i = 1; i <= 8; ++i){
            addPiece(new ChessPosition(2, i), new ChessPiece(
                    ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7, i), new ChessPiece(
                    ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
        // Knights
        addPiece(new ChessPosition(1, 2), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1, 7), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 2), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8, 7), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        // Bishops
        addPiece(new ChessPosition(1, 3), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1, 6), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 3), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8, 6), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        // Rooks
        addPiece(new ChessPosition(1, 1), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1, 8), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8, 1), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8, 8), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        // Queens
        addPiece(new ChessPosition(1, 4), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8, 4), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        // Kings
        addPiece(new ChessPosition(1, 5), new ChessPiece(
                ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8, 5), new ChessPiece(
                ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));

        return;
    }

    /**
     * I Found this map in TestUtilities.java and I liked it a lot, so I took it to use in my own code. As proof that I
     * actually do know how it works, I'm going to make a reverse map to use in my ChessPiece.toString() function.
     *
     * Likewise with the loadBoard() function. I implemented the reverse of the function below (in toString())
     * to demonstrate that I understand how the function works and that I'm not just ripping it off to save myself work.
     */
    private static final Map<ChessPiece.PieceType, Character> TYPE_TO_CHAR_MAP = Map.of(
            ChessPiece.PieceType.PAWN,'p',
            ChessPiece.PieceType.KNIGHT, 'n',
            ChessPiece.PieceType.BISHOP, 'b',
            ChessPiece.PieceType.ROOK, 'r',
            ChessPiece.PieceType.QUEEN, 'q',
            ChessPiece.PieceType.KING, 'k');

    @Override
    public String toString() {
        var board = this;
        StringBuilder bob = new StringBuilder();
        for (int i = 8; i >= 1; --i) {
            for (int j = 1; j <= 8; ++j) {
                bob.append('|');
                ChessPiece piece = getPiece(new ChessPosition(i, j));
                if(piece == null) bob.append(' ');
                else{
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        bob.append(Character.toUpperCase(TYPE_TO_CHAR_MAP.get(piece.getPieceType())));
                    }else{
                        bob.append(TYPE_TO_CHAR_MAP.get(piece.getPieceType()));
                    }
                }
            }
            bob.append("|\n");
        }
        return bob.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
