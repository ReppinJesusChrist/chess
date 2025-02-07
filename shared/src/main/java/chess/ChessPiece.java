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
    private boolean canEnPasRight, canEnPasLeft;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        canEnPasRight = false;
        canEnPasLeft = false;
    }

    public ChessPiece(ChessPiece original){
        this.pieceColor = original.pieceColor;
        this.type = original.type;
        this.canEnPasRight = original.canEnPasRight;
        this.canEnPasLeft = original.canEnPasLeft;
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
        if(type == PieceType.PAWN){
            // TODO: Condense this code. Right now it seems way too long and wordy
            boolean on_home_square = (myPosition.getRow() == (this.pieceColor == ChessGame.TeamColor.WHITE ? 2 : 7));
            boolean ready_to_promote = (myPosition.getRow() == (this.pieceColor == ChessGame.TeamColor.WHITE ? 7 : 2));
            boolean en_passant_row = (myPosition.getRow() == (this.pieceColor == ChessGame.TeamColor.WHITE ? 5 : 4));

            // Identifies squares to check for capture potential and adds them if the capture is valid
            int adv_inc = (this.pieceColor == ChessGame.TeamColor.WHITE ? 1 : -1); // Indicates advancement direction

            if(myPosition.getColumn() < 8){
                ChessPosition right_capture_square = new ChessPosition(
                        myPosition.getRow() + adv_inc, myPosition.getColumn() + 1);
                ChessPiece right_capture_piece = board.getPiece(right_capture_square);
                if(right_capture_piece != null && right_capture_piece.pieceColor != this.pieceColor) {
                    if(ready_to_promote) addPromotionMoves(move_list, myPosition, right_capture_square);
                    else move_list.add(new ChessMove(myPosition, right_capture_square, null));
                }
                if(canEnPasRight) move_list.add(new ChessMove(myPosition, right_capture_square, null));
            }

            if(myPosition.getColumn() > 1){
                ChessPosition left_capture_square = new ChessPosition(
                        myPosition.getRow() + adv_inc, myPosition.getColumn() - 1);
                ChessPiece left_capture_piece = board.getPiece(left_capture_square);
                if(left_capture_piece != null && left_capture_piece.pieceColor != this.pieceColor){
                    if(ready_to_promote) addPromotionMoves(move_list, myPosition, left_capture_square);
                    else move_list.add(new ChessMove(myPosition, left_capture_square, null));
                }
                if(canEnPasLeft) move_list.add(new ChessMove(myPosition, left_capture_square, null));
            }

            // Add single advance square if valid
            ChessPosition single_adv_square = new ChessPosition(myPosition.getRow() + adv_inc,
                    myPosition.getColumn());
            if(board.getPiece(single_adv_square) == null){
                if(ready_to_promote) addPromotionMoves(move_list, myPosition, single_adv_square);
                else move_list.add(new ChessMove(myPosition, single_adv_square, null));
                // Add double move if on home square
                if(on_home_square){
                    ChessPosition double_adv_square = new ChessPosition(myPosition.getRow() + 2 * adv_inc,
                            myPosition.getColumn());
                    if(board.getPiece(double_adv_square) == null){
                        move_list.add(new ChessMove(myPosition, double_adv_square, null));
                    }
                }
            }
        }
        return move_list;
    }

    public static void addPromotionMoves(ArrayList<ChessMove> move_list, ChessPosition myPosition,
                                         ChessPosition captureSquare){
        move_list.add(new ChessMove(myPosition, captureSquare, PieceType.KNIGHT));
        move_list.add(new ChessMove(myPosition, captureSquare, PieceType.BISHOP));
        move_list.add(new ChessMove(myPosition, captureSquare, PieceType.ROOK));
        move_list.add(new ChessMove(myPosition, captureSquare, PieceType.QUEEN));
    }

    /**
     * @param b Whether the pawn can capture en passant to the right
     */
    public void setEnPasRight(boolean b){
        canEnPasRight = b;
    }

    /**
     * @param b Whether the pawn can capture en passant to the left
     */
    public void setEnPasLeft(boolean b){
        canEnPasLeft = b;
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


    @Override
    public String toString() {
        return switch (type) {
            case KING -> (pieceColor == ChessGame.TeamColor.WHITE ? "K" : "k");
            case QUEEN -> (pieceColor == ChessGame.TeamColor.WHITE ? "Q" : "q");
            case ROOK -> (pieceColor == ChessGame.TeamColor.WHITE ? "R" : "r");
            case BISHOP -> (pieceColor == ChessGame.TeamColor.WHITE ? "B" : "b");
            case KNIGHT -> (pieceColor == ChessGame.TeamColor.WHITE ? "N" : "n");
            case PAWN -> (pieceColor == ChessGame.TeamColor.WHITE ? "P" : "p");
        };
    }
}