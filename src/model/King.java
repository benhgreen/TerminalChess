package model;

public class King extends Piece {
	
	public King(String color) {
		this.color = color;
		this.type = "K";
		this.hasMoved = false;
	}

	@Override
	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {
		
		if (Math.abs(getRow(start)-getRow(end)) < 2 && Math.abs(getColumn(start)-getColumn(end)) < 2) {
			return new MoveResponse(true, "move");
		}
		
		//TODO castling
		return new MoveResponse(false, null);
	}

}
