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
    ChessPosition enPassantablePawnPosition;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        whitePieceSquares = new ArrayList<>(16);
        blackPieceSquares = new ArrayList<>(16);
        setBoard(new ChessBoard());
        resetGame();
        enPassantablePawnPosition = null;
    }

    /**
     * @return List of squares containing active white pieces
     */
    public ArrayList<ChessPosition> getWhitePieceSquares() {
        return whitePieceSquares;
    }

    /**
     * @return List of squares containing active black pieces
     */
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
        ChessPiece currPiece = boardState.getPiece(startPosition);
        if(currPiece == null) return null;
        if(currPiece.getPieceType() == ChessPiece.PieceType.PAWN){}
        Collection<ChessMove> moves  = new ArrayList<>(
                boardState.getPiece(startPosition).pieceMoves(boardState, startPosition
                ));
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessBoard origBoardState = new ChessBoard(boardState);
        for(ChessMove cm : moves){
            try {
                makeMove(cm);
                validMoves.add(cm);
                changeTeamTurn(); // Corrects for makeMove() auto changing team turn if it succeeds
            }catch(InvalidMoveException e){
                if(e.getMessage().equals("It's not this team's turn")){
                    changeTeamTurn();
                    try{
                        makeMove(cm);
                        validMoves.add(cm);
                        changeTeamTurn(); // Corrects for makeMove() auto changing team turn if it succeeds
                    } catch (InvalidMoveException ex) {
                        //System.out.print(ex.getMessage());
                    }
                }
            }
            setBoard(origBoardState);
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece.PieceType promotionPiece = move.getPromotionPiece();
        ChessBoard previousBoardState = boardState;
        ChessPiece pieceToMove = boardState.getPiece(move.getStartPosition());

        if(pieceToMove == null) throw new InvalidMoveException("There's no piece on this square to move");
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
                throw new InvalidMoveException("The King is in check after this move is completed!");
            }else{
                adjustTeamListAfterMove(move);
                if(pieceToMove.getPieceType() == ChessPiece.PieceType.PAWN &&
                        Math.abs(move.getStartPosition().getRow() - move.getEndPosition().getRow()) == 2
                ){
                    enPassantablePawnPosition = move.getEndPosition();
                }
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
        ArrayList<ChessPosition> otherTeamPositions = teamColor == TeamColor.WHITE ?
                blackPieceSquares : whitePieceSquares;
        ChessPiece currPiece;
        boolean isInCheck = false;
        final ChessPosition kingPosition = getPiecePosition(teamColor, ChessPiece.PieceType.KING);

        for(ChessPosition p : otherTeamPositions){
            currPiece = boardState.getPiece(p);
            if(currPiece == null || currPiece.getTeamColor() == teamColor) continue;
            Collection<ChessMove> currPieceMoves = currPiece.pieceMoves(boardState, p);
            for(ChessMove c : currPieceMoves){
                if(c.getEndPosition().equals(kingPosition)) return true;
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
        return noValidTeamMoves(teamColor) && isInCheck(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves and not being in check
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return noValidTeamMoves(teamColor) && !isInCheck(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board using the copy constructor in ChessBoard
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        boardState = new ChessBoard(board);
        resetTeamPieceArrays();
        populateTeamPieceArrays();
    }

    /**
     * Resets the game back to the beginning so it's ready to be played as a new game
     */
    public void resetGame(){
        boardState.resetBoard();
        resetTeamPieceArrays();
        populateTeamPieceArrays();
        setTeamTurn(TeamColor.WHITE);
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return boardState;
    }

    /**
     * Finds and returns the position of a given piece on the current boardState
     *  Presently this is only used to find the King, so it doesn't need to find more than a single instance. If I end
     *  up changing that in the future I'll refactor this function.
     * @param color The color of the piece to be found
     * @param piece The type of piece to be found
     * @return The ChessPosition of the specified piece.
     *
     */
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

    /**
     * Handles the capture of a piece. This involves removing the captured piece both from the board and from its
     *  respective teamPieceSquares list and moving the capturing piece to its new board position.
     *
     * @param startPosition The square that the capturing piece started on.
     * @param capturePosition The square containing the piece that is going to be captured.
     * @param capturer The ChessPiece that is doing the capturing.
     *
     * TODO: Remove @param capturer both here and in all calling functions
     */
    public void capturePiece(ChessPosition startPosition, ChessPosition capturePosition, ChessPiece capturer){
        boardState.removePiece(startPosition);
        ChessPosition posToRemove = null;
        ArrayList<ChessPosition> removeList = capturer.getTeamColor() == TeamColor.WHITE ?
                blackPieceSquares : whitePieceSquares;
        for (ChessPosition cp : removeList){
            if(cp.equals(capturePosition)) posToRemove = cp;
        }
        removeList.remove(posToRemove);
        boardState.addPiece(capturePosition, capturer);
    }

    /**
     * Updates the appropriate teamPieceSquares array after a ChessMove is made.
     *
     * @param move The move that will be used to update the teamPieceSquares array.
     */
    public void adjustTeamListAfterMove(ChessMove move){
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece mover = boardState.getPiece(endPosition);
        ArrayList<ChessPosition> moveList = mover.getTeamColor() == TeamColor.WHITE ?
                whitePieceSquares : blackPieceSquares;

        moveList.remove(startPosition);
        moveList.add(endPosition);
    }

    /**
     * Changes which team's turn it is
     */
    public void changeTeamTurn(){
        if(teamTurn == TeamColor.WHITE) teamTurn = TeamColor.BLACK;
        else teamTurn = TeamColor.WHITE;
    }

    /**
     * Determines if the given team has no valid moves.
     * This is done by iterating through the teamPieceSquares ArrayList and running validMoves() on each piece.
     *
     * @param team which team to check for valid moves. The team tracking list is assumed to only contain positions
     *             which have pieces on them (not null)
     * @return True if the specified team has no valid moves, otherwise false
     */
    public boolean noValidTeamMoves(TeamColor team){
        final ArrayList<ChessPosition> teamPiecePositions = team == TeamColor.WHITE ?
                whitePieceSquares : blackPieceSquares;
        ArrayList<ChessPosition> tPPCopy = new ArrayList<>(teamPiecePositions);
        for (ChessPosition p : tPPCopy) {
            if (!validMoves(p).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reads the current board state and updates the team piece tracking arrays
     */
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

    /**
     * Empties the tracking arrays for currently active white and black pieces
     */
    public void resetTeamPieceArrays(){
        whitePieceSquares.clear();
        blackPieceSquares.clear();
    }
}
