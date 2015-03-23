package model;

public class Queen implements PieceInterface{

	private String type;
	private String color;
	private boolean is_first_move;

	public Queen(String color, String type, boolean is_first_move) {
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
		if((end-start) % 8 == 0 && end > start)
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
		if((start-end) % 8 == 0 && start > end)
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
		if((start-end) < 8 && start % 8 > end % 8)
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
		if((end-start) < 8 && end % 8 > start % 8)
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
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
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
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
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
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		
		
		return false;
	}

}
