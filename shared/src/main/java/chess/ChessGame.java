package chess;

import java.util.ArrayList;
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
    ArrayList<ChessPosition> whitePieceSquares, blackPieceSquares;


    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        whitePieceSquares = new ArrayList<>(16);
        blackPieceSquares = new ArrayList<>(16);
        setBoard(new ChessBoard());
    }

    public ArrayList<ChessPosition> getWhitePieceSquares() {
        return whitePieceSquares;
    }
    public ArrayList<ChessPosition> getBlackPieceSquares() {
        return blackPieceSquares;
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
        if(boardState.getPiece(startPosition) == null) return null;
        Collection<ChessMove> moves;
        throw new RuntimeException("Not finished");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece.PieceType promotionPiece = move.getPromotionPiece();
        ChessBoard previousBoardState = boardState;
        ChessPiece pieceToMove = boardState.getPiece(move.getStartPosition());
        if (pieceToMove == null) throw new InvalidMoveException("There's no piece on this square to move");
        if(pieceToMove.getTeamColor() != teamTurn) throw new InvalidMoveException("It's not this team's turn");
        if(pieceToMove.pieceMoves(boardState, move.getStartPosition()).contains(move)){
            if(boardState.getPiece(move.getEndPosition()) == null){
                boardState.removePiece(move.getStartPosition());
                if(promotionPiece != null) boardState.addPiece(move.getEndPosition(), new ChessPiece(
                        pieceToMove.getTeamColor(), promotionPiece
                ));
                else boardState.addPiece(move.getEndPosition(), pieceToMove);
            }
            else{
                ChessPiece capturer;
                if(promotionPiece == null) capturer = new ChessPiece(
                        pieceToMove.getTeamColor(), pieceToMove.getPieceType());
                else capturer = new ChessPiece(pieceToMove.getTeamColor(), promotionPiece);
                capturePiece(move.getStartPosition(), move.getEndPosition(), capturer);
            }
            changeTeamTurn();

            if(isInCheck(pieceToMove.getTeamColor())){
                boardState = previousBoardState;
                changeTeamTurn();
                throw new InvalidMoveException("This move puts the King in check!");
            }
        } else throw new InvalidMoveException("This move isn't part of the piece's moveset");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean isInCheck = false;
        ChessPiece currPiece;
        final ChessPosition kingPosition = getPiecePosition(teamColor, ChessPiece.PieceType.KING);
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
        ChessPosition kingPosition = getPiecePosition(teamColor, ChessPiece.PieceType.KING);
        if(isInCheck(teamColor)){

        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return noValidMoves(teamColor) && isInCheck(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        boardState = board;
        populateTeamPieceArrays();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return boardState;
    }

    public ChessPosition getPiecePosition(TeamColor color, ChessPiece.PieceType piece){
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

    // TODO: Use the result of removePiece to track captured pieces from both sides
    public void capturePiece(ChessPosition startPosition, ChessPosition capturePosition, ChessPiece capturer){
        boardState.removePiece(startPosition);
        boardState.addPiece(capturePosition, capturer);
    }

    public void changeTeamTurn(){
        if(teamTurn == TeamColor.WHITE) teamTurn = TeamColor.BLACK;
        else teamTurn = TeamColor.WHITE;
    }

    public boolean noValidMoves(TeamColor team){
        throw new RuntimeException("Not Implemented");
    }

    public void populateTeamPieceArrays(){
        ChessPiece currPiece;
        for(int i = 1; i <=8; ++i){
            for(int j = 1; j <=8; ++j){
                ChessPosition posToCheck = new ChessPosition(i,j);
                currPiece = boardState.getPiece(posToCheck);
                if(currPiece == null) continue;
                if(currPiece.getTeamColor() == TeamColor.WHITE) whitePieceSquares.add(posToCheck);
                else blackPieceSquares.add(posToCheck);
            }
        }
    }
    /*
    public boolean isValidMove(ChessMove move){
        return false;
    }
    */
}
