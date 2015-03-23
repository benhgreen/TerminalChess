package model;

public class Knight implements PieceInterface{

	private String type;
	private String color;
	private boolean is_first_move;

	public Knight(String color, String type, boolean is_first_move) {
			this.color = color;
			this.type = type;
			this.is_first_move = is_first_move;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public boolean isWhite() {
		return color.equals("white");
	}
	
	public boolean getIsFirstMove()
	{
		return is_first_move;
	}

	@Override
	public boolean isValidPieceMove(Board board, String[] location) {
		Integer start = Integer.parseInt(location[0]);
		Integer end = Integer.parseInt(location[1]);
		String option = location[2];
		
		//up 2 right 1
		if(end-start == 17)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//up 2 left 1
		if(end-start == 15)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//up 1 left 2
		if(end-start == 6)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//up 1 right 2
		if(end-start == 10)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//down 2 right 1
		if(start-end == 15)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//down 2 left 1
		if(start-end == 17)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//down 1 left 2
		if(start-end == 10)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//down 1 right 2
		if(start-end == 6)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		
		return false;
	}

}
