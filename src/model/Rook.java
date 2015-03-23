package model;

public class Rook implements PieceInterface{

	private String type;
	private String color;
	private boolean is_first_move;

	public Rook(String color, String type, boolean is_first_move) {
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
		
		//forwards
		if((end-start) % 8 == 0)
		{
			for(int i=start+8; i<end; i+=8)
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
		//backwards
		if((start-end) % 8 == 0)
		{
			for(int i=start-8; i>end; i-=8)
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
		//left
		if((start-end) < 8 && (start-end) >= -7)
		{
			for(int i=start-1; i>end; i--)
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
		//right
		if((end-start) < 8 && (end-start) >= -7)
		{
			for(int i=start+1; i<end; i++)
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

		return false;
	}

}
