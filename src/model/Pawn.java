package model;

public class Pawn extends Piece {
	
	private int direction;

	public Pawn(String color) {
		this.color = color;
		this.type = "p";
		this.hasMoved = false;
		this.setDirection();
		
	}

	public void setDirection() {
		if (color.equals("b")) {
			direction = -1;
		} else {
			direction = 1;
		}
	}
	
	public int getDirection() {
		return direction;
	}
	
	@Override
	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {
		
		//normal one-square move
		if (end == start + direction * 8 && !board.hasPieceAt(end)) {
			return new MoveResponse(true, "move");
		}
		
		//starting two-square move
		if (end == start + direction * 16 && !board.hasPieceAt(end) && !this.hasMoved) {
			return new MoveResponse(true, "move");
		}
		
		//attack diagonally
		if (board.hasPieceAt(end) && Math.abs(end - (start + direction * 8)) == 1) {
			return new MoveResponse(true, "move");
		}
		
		//TODO en passant and promotion
		
		
		return new MoveResponse(false, null);
	}

}
