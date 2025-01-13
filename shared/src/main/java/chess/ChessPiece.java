/**
 * TODO: Optimize PieceMoves()
 *          Move end_position outside if statements
 *          Combine redundant code (as found)
 */

package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> move_list = new ArrayList<>();
        if(type == PieceType.BISHOP || type == PieceType.QUEEN){
            int row, col;
            ChessPosition end_position;

            // Check all four diagonal directions:
            // Up-Right
            row = myPosition.getRow() + 1;
            col = myPosition.getColumn() + 1;
            while(row <= 8 && col <= 8){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                row++;
                col++;
            }

            // Up-Left
            row = myPosition.getRow() + 1;
            col = myPosition.getColumn() - 1;
            while(row <= 8 && col >= 1){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                row++;
                col--;
            }

            // Down-Left
            row = myPosition.getRow() - 1;
            col = myPosition.getColumn() - 1;
            while(row >= 1 && col >= 1){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                row--;
                col--;
            }

            // Down-Right
            row = myPosition.getRow() - 1;
            col = myPosition.getColumn() + 1;
            while(row >= 1 && col <= 8){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                row--;
                col++;
            }
        }
        if(type == PieceType.ROOK || type == PieceType.QUEEN){
            int row, col;
            ChessPosition end_position;

            // Check all four directions:
            // Right
            row = myPosition.getRow();
            col = myPosition.getColumn() + 1;
            while(col <= 8){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                col++;
            }

            // Up
            row = myPosition.getRow() + 1;
            col = myPosition.getColumn();
            while(row <= 8){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                row++;
            }

            // Left
            row = myPosition.getRow();
            col = myPosition.getColumn() - 1;
            while(col >= 1){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                col--;
            }

            // Down
            row = myPosition.getRow() - 1;
            col = myPosition.getColumn();
            while(row >= 1){
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    break;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
                row--;
            }
        }
        if(type == PieceType.KING){
            int row, col;
            ChessPosition end_position;
            for(int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j ++) {
                    row = myPosition.getRow() + i;
                    col = myPosition.getColumn() + j;
                    if(row > 8 || row < 1 || col > 8 || col < 1) continue;
                    end_position = new ChessPosition(row, col);
                    ChessPiece curr_occupant = board.getPiece(end_position);
                    if(curr_occupant != null) {
                        if(curr_occupant.pieceColor != this.pieceColor){
                            move_list.add(new ChessMove(myPosition, end_position, null));
                        }
                        continue;
                    }
                    move_list.add(new ChessMove(myPosition, end_position, null));
                }
            }
        }
        if(type == PieceType.KNIGHT){
            int row, col;
            ChessPosition end_position;
            int[][] direction_arr = {{1, 2},{2, 1},{2, -1},{1, -2},{-1, -2},{-2, -1},{-2, 1},{-1, 2}};
            for(int[] i : direction_arr) {
                row = myPosition.getRow() + i[0];
                col = myPosition.getColumn() + i[1];
                if(row > 8 || row < 1 || col > 8 || col < 1) continue;
                end_position = new ChessPosition(row, col);
                ChessPiece curr_occupant = board.getPiece(end_position);
                if(curr_occupant != null) {
                    if(curr_occupant.pieceColor != this.pieceColor){
                        move_list.add(new ChessMove(myPosition, end_position, null));
                    }
                    continue;
                }
                move_list.add(new ChessMove(myPosition, end_position, null));
            }
        }
        return move_list;
    }
}