package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamTurn;
    ChessBoard boardState;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        boardState = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     * TODO: FIRST
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean isInCheck = false;
        ChessPiece currPiece;
        final ChessPosition kingPosition = findPiecePosition(teamColor, ChessPiece.PieceType.KING);
        for(int row = 1; row <=8; ++row) {
            for (int col = 1; col <= 8; ++col) {
                currPiece = boardState.getPiece(new ChessPosition(row, col));
                if(currPiece == null || currPiece.getTeamColor() == teamColor) continue;
                Collection<ChessMove> currPieceMoves = currPiece.pieceMoves(boardState, new ChessPosition(row, col));
                for(ChessMove c : currPieceMoves){
                    if(c.getEndPosition().equals(kingPosition)) return true;
                }
            }
        }
        return isInCheck;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        boardState = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return boardState;
    }

    public ChessPosition findPiecePosition(TeamColor color, ChessPiece.PieceType piece){
        ChessPiece currPiece;
        ChessPosition currPosition;
        for(int row = 1; row <=8; ++row) {
            for (int col = 1; col <= 8; ++col) {
                currPosition = new ChessPosition(row, col);
                currPiece = boardState.getPiece(currPosition);
                if(currPiece == null) continue;
                if(currPiece.getTeamColor() == color && currPiece.getPieceType() == piece){
                    return currPosition;
                }
            }
        }
        return null;
    }
}
