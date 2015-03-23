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
	
	@Override
	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {

        String special = "move";

        //promotion
        if ((direction == 1 && getRow(end) == 8) || (direction == -1 && getRow(end) == 1)) {
            special = "promotion";
        }
		
		//normal one-square move
		if (end == start + direction * 8 && !board.hasPieceAt(end)) {
			return new MoveResponse(true, special);
		}
		
		//starting two-square move
		if (end == start + direction * 16 && !board.hasPieceAt(end) && !this.hasMoved) {
			return new MoveResponse(true, special);
		}
		
		//attack diagonally
		if (board.hasPieceAt(end) && Math.abs(end - (start + direction * 8)) == 1) {
			return new MoveResponse(true, special);
		}
		
		//TODO en passant
		
		
		return new MoveResponse(false, null);
	}

}
