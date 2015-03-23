package model;

import control.MoveProcessor;



public class King implements PieceInterface {

	private String type;
	private String color;
	private boolean is_first_move;

	public King(String color, String type, boolean is_first_move) {
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
		
		//right
		if(end == start+1)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;

			return true;
			
		}
		//left
		if(end == start-1)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//up
		if(end == start+8)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;

			return true;
		}
		//down
		if(end == start-8)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;

			return true;
		}
		//up and right
		if(end == start+9)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;
			
			return true;
		}
		//up and left
		if(end == start+7)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;

			return true;
		}
		//down and right
		if(end == start-7)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;

			return true;
		}
		//up and left
		if(end == start-9)
		{
			//capture
			if(board.getPieceAt(end) != null)
				Board.willCapture = true;
			else
				Board.willCapture = false;

			return true;
		}
		
		//castling
		if(!(MoveProcessor.checkForCheck(board)))
		{
			if(this.getIsFirstMove()==true)
			{
				if(board.getPieceAt(8) != null && board.getPieceAt(8).getIsFirstMove() == true)
				{
					if(start == 5 && end == 7)
					{
						if(board.getPieceAt(6) == null && board.getPieceAt(7) == null)
						{
							Board.willCastle = true;
							return true;
						}
						
					}
				}
				if(board.getPieceAt(1) != null && board.getPieceAt(1).getIsFirstMove() == true)
				{
					if(start == 5 && end == 3)
					{
						if(board.getPieceAt(2) == null && board.getPieceAt(3) == null && board.getPieceAt(4) == null)
						{
							Board.willCastle = true;
							return true;
						}
					}
				}
				if(board.getPieceAt(57) != null && board.getPieceAt(57).getIsFirstMove() == true)
				{
					if(start == 61 && end == 59)
					{
						if(board.getPieceAt(58) == null && board.getPieceAt(59) == null && board.getPieceAt(60) == null)
						{
							Board.willCastle = true;
							return true;
						}
					}
				}
				if(board.getPieceAt(64) != null && board.getPieceAt(64).getIsFirstMove() == true)
				{
					if(start == 61 && end == 63)
					{
						if(board.getPieceAt(62) == null && board.getPieceAt(63) == null)
						{
							Board.willCastle = true;
							return true;
						}
					}
				}
			}
		}
		

		return false;
	}
	

}
