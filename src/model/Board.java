/**
 * 
 */
package model;

import java.util.HashMap;

/**
 * @author Ben Green & Kate Sussman
 *
 */
public class Board implements BoardInterface {
	
	/**HashMap of the 64 squares in the chessboard, keys numbered 1-64
	 * 
	 */
	private HashMap<Integer, Piece> squares;
	private String status;
	private String whose_turn;
	public static boolean draw = false;
	
	/**Constructor for board, initializes with a fresh set of pieces
	 * 
	 */
	public Board() {
		
		//initialize squares
		squares = new HashMap<Integer, Piece>();
	
		
		//add white pawns
		for(Integer x = 9; x < 17; x++) {
			this.addPiece("w", "p", x);
		}
		
		//add black pawns
		for(Integer x = 49; x < 57; x++) {
			addPiece("b", "p", x);
		}
		
		//add other white pieces
		addPiece("w", "R", 1);
		addPiece("w", "R", 8);
		addPiece("w", "N", 2);
		addPiece("w", "N", 7);
		addPiece("w", "B", 3);
		addPiece("w", "B", 6);
		addPiece("w", "Q", 4);
		addPiece("w", "K", 5);

		//add other black pieces
//		addPiece("b", "R", 57);
//		addPiece("b", "R", 64);
//		addPiece("b", "N", 58);
//		addPiece("b", "N", 63);
		addPiece("b", "B", 59);
		addPiece("b", "B", 62);
		addPiece("b", "Q", 60);
		addPiece("b", "K", 61);

        addPiece("w", "p", 56);
        addPiece("w", "p", 55);
		
		
		//set status
		status = "progressing";
		
		//set white to go next
		whose_turn = "w";
	}
	
	@Override
	public void addPiece(String color, String type, Integer location) {
		switch (type) {
		
			//handle pawns
			case ("p"):
			{
				squares.put(location, new Pawn(color));
				break;
			}
			//handle rooks
			case ("R"):
			{
				squares.put(location, new Rook(color));
				break;
			}
			//handle knights
			case ("N"):
			{
				squares.put(location, new Knight(color));
				break;
			}
			//handle bishops
			case ("B"):
			{
				squares.put(location, new Bishop(color));
				break;
			}
			//handle queens
			case ("Q"):
			{
				squares.put(location, new Queen(color));
				break;
			}
			//handle king
			case ("K"):
			{
				squares.put(location, new King(color));
				break;
			}
		}
		
	}

	@Override
	public void removePiece(Integer location) {
		squares.remove(location);
			
		
	}

	@Override
	public void movePiece(Integer start_location, Integer end_location) {
		Piece piece = getPieceAt(start_location);
		squares.remove(start_location);
		addPiece(piece.getColor(), piece.getType(), end_location);
		getPieceAt(end_location).markMoved();
	}
	

	@Override
	public Piece getPieceAt(Integer location) {
		return squares.get(location);
	}
	
	/*for promotion*/
	@Override
	public void replacePiece(String color, String type, Integer location) {
		removePiece(location);
		
		switch (type) {
		
			//handle rooks
			case ("R"):
			{
				squares.put(location, new Rook(color));
				break;
			}
			//handle knights
			case ("N"):
			{
				squares.put(location, new Knight(color));
				break;
			}
			//handle bishops
			case ("B"):
			{
				squares.put(location, new Bishop(color));
				break;
			}
			//handle queens
			case ("Q"):
			{
				squares.put(location, new Queen(color));
				break;
			}	
			default:
			{
				squares.put(location, new Queen(color));
				break;
			}
		}
	}
	
	/*public void enPassant(Board board, String player, Integer start, Integer end)
	{
		board.removePiece(start);
		
		//player is white
		//black will capture
		if(player.equals("w"))
		{
			//if white pawn is to the left of black pawn
			if(board.getPieceAt(start - 1) != null && board.getPieceAt(start - 1).getType().equals("p") && board.getPieceAt(start - 1).getColor().equals("w"))
				board.movePiece(start, start - 9);
			//if white pawn is to the right of black pawn
			if(board.getPieceAt(start + 1) != null && board.getPieceAt(start + 1).getType().equals("p") && board.getPieceAt(start + 1).getColor().equals("w"))
				board.movePiece(start, start - 7);
		}
			
		
		
		//player is black
		//white will capture
		if(player.equals("b"))
		{
			//if black pawn is to the left of white pawn
			if(board.getPieceAt(start - 1) != null && board.getPieceAt(start - 1).getType().equals("p") && board.getPieceAt(start - 1).getColor().equals("w"))
				board.movePiece(start, start + 9);
			//if black pawn is to the right of white pawn
			if(board.getPieceAt(start + 1) != null && board.getPieceAt(start + 1).getType().equals("p") && board.getPieceAt(start + 1).getColor().equals("w"))
				board.movePiece(start, start + 7);
		}
			
		
	}*/
	
	@Override
	public void switchPlayer() {
		if (whose_turn.equals("w")) {
			whose_turn = "b";
		} else {
			whose_turn = "w";
		}
		
	}
	
	@Override
	public String getStatus() {
		return status;
	}
	
	@Override
	public String getWhoseTurn() {
		return whose_turn;
	}

	public boolean hasPieceAt(Integer location) {
		return squares.containsKey(location);
	}
	
	public void print() {
		for (int i = 1; i < 65; i++) {
			if (squares.containsKey(i)) {
				System.out.println("piece at " + i + ": " + squares.get(i).getClass());
			}
		}
	}
	
	public Board duplicate() {
		Board clone = new Board();
		
		clone.whose_turn = this.getWhoseTurn();
		clone.status = this.status;
		clone.squares = new HashMap<Integer, Piece>();
		
		for (int i = 1; i < 65; i++) {
			if (this.hasPieceAt(i)) {
				Piece piece = this.getPieceAt(i);
				clone.addPiece(piece.getColor(), piece.getType(), i);
			}
		}
		
		return clone;
	}

	public int findKing(String whoseTurn) {
		for (int loc = 1; loc < 65; loc++) {
			if (this.hasPieceAt(loc)) {
				Piece piece = this.getPieceAt(loc);
				if (piece.getType().equals("K") && piece.getColor().equals(whoseTurn)) {
					return loc;
				}
			}
		}
		return -1;
	}

	

}
