package chess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
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
     */
    public void resetBoard() {
        return;
    }

    /**
     * I Found this map in TestUtilities.java and I liked it a lot, so I took it to use in my own code. As proof that I
     * actually do know how it works, I'm going to make a reverse map to use in my ChessPiece.toString() function.
     *
     * Likewise with the loadBoard() function. I implemented the reverse of the function below (in toString())
     * to demonstrate that I understand how the function works and that I'm not just ripping it off to save myself work.
     */
    private static final Map<Character, ChessPiece.PieceType> CHAR_TO_TYPE_MAP = Map.of(
            'p', ChessPiece.PieceType.PAWN,
            'n', ChessPiece.PieceType.KNIGHT,
            'r', ChessPiece.PieceType.ROOK,
            'q', ChessPiece.PieceType.QUEEN,
            'k', ChessPiece.PieceType.KING,
            'b', ChessPiece.PieceType.BISHOP);

    public static ChessBoard loadBoard(String boardText) {
        var board = new ChessBoard();
        int row = 8;
        int column = 1;
        for (var c : boardText.toCharArray()) {
            switch (c) {
                case '\n' -> {
                    column = 1;
                    row--;
                }
                case ' ' -> column++;
                case '|' -> {
                }
                default -> {
                    ChessGame.TeamColor color = Character.isLowerCase(c) ? ChessGame.TeamColor.BLACK
                            : ChessGame.TeamColor.WHITE;
                    var type = CHAR_TO_TYPE_MAP.get(Character.toLowerCase(c));
                    var position = new ChessPosition(row, column);
                    var piece = new ChessPiece(color, type);
                    board.addPiece(position, piece);
                    column++;
                }
            }
        }
        return board;
    }

    public static ChessBoard defaultBoard() {
        return loadBoard("""
                |r|n|b|q|k|b|n|r|
                |p|p|p|p|p|p|p|p|
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                |P|P|P|P|P|P|P|P|
                |R|N|B|Q|K|B|N|R|
                """);
    }


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
}
