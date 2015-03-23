package model;

public class Bishop implements PieceInterface {

	private String type;
	private String color;
	private boolean is_first_move;

	public Bishop(String color, String type, boolean is_first_move) {
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
	
	public boolean getIsFirstMove()
	{
		return is_first_move;
	}

	@Override
	public boolean isWhite() {
		return color.equals("white");
	}

	@Override
	public boolean isValidPieceMove(Board board, String[] location) {
		Integer start = Integer.parseInt(location[0]);
		Integer end = Integer.parseInt(location[1]);
		String option = location[2];
		
		//up and right 
		if((end-start) % 9 == 0)
		{	
			for(int i=start+9; i<end; i+= 9)
			{
				if(board.getPieceAt(i) != null)
					return false;
			}
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//up and left 
		if((end-start) % 7 == 0)
		{
			for(int i=start+7; i<end; i+= 7)
			{
				if(board.getPieceAt(i) != null)
					return false;
			}
			//capture
			if(board.getPieceAt(end) != null)
				//board.removePiece(end);
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			//board.movePiece(start, end);
			return true;
		}
		//down and left
		if((start-end) % 9 == 0)
		{
			for(int i=start-9; i>end; i-= 9)
			{
				if(board.getPieceAt(i) != null)
					return false;
			}
			//capture
			if(board.getPieceAt(end) != null)
				//board.removePiece(end);
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			//board.movePiece(start, end);
			return true;
		}
		//down and right
		if((start-end) % 7 == 0)
		{
			for(int i=start-7; i>end; i-= 7)
			{
				if(board.getPieceAt(i) != null)
					return false;
			}
			//capture
			if(board.getPieceAt(end) != null)
				//board.removePiece(end);
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			//board.movePiece(start, end);
			return true;
		}
	
		return false;
	}

}
