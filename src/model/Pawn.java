package model;

public class Pawn implements PieceInterface {
	
	private String type;
	private String color;
	private boolean is_first_move;

	public Pawn(String color, String type, boolean is_first_move) {
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

		
		
		//white 
		if(color.equals("w"))
		{
			//capture piece diagonally
			if((board.getPieceAt(start+7) != null) || (board.getPieceAt(start+9) != null))
			{
				if(end == start+7 || end == start+9)
				{
					Board.willCapture = true;
					Board.promotion = false;
					Board.enPassant = false;
					
					//reached end of board
					if(end <= 64 && end >= 57)
						Board.promotion = true;
					
					return true;
				}			
			}
		
			//moving one square up
			if(end == start+8)
			{
				if(board.getPieceAt(end) == null)
				{
					Board.willCapture = false;
					Board.promotion = false;
					Board.enPassant = false;
					
					//reached end of board
					if(end <= 64 && end >= 57)
						Board.promotion = true;
					
					return true;
				}	
			}
			
			//moving 2 squares up
			if(this.getIsFirstMove())
			{
				if(board.getPieceAt(end) == null && board.getPieceAt(start+8) == null)
				{
					Board.willCapture = false;
					Board.promotion = false;
					Board.enPassant = false;
					
					//if pawn lands next to another pawn of opposite color
					if(end + 1 <= 64 && end + 1 % 8 > end % 8)
					{
						if(board.getPieceAt(end+1) != null && board.getPieceAt(end+1).getType().equals("p") && board.getPieceAt(end+1).getColor().equals("b"))
							Board.enPassant = true;
					}
					if(end -1 > 0 && end % 8 > end - 1 % 8)
					{
						if(board.getPieceAt(end-1) != null && board.getPieceAt(end-1).getType().equals("p") && board.getPieceAt(end-1).getColor().equals("b"))
							Board.enPassant = true;
					}

					return true;
				}
			}
		}
		
		//black 
		if(color.equals("b"))
		{
			//capture piece diagonally
			if((board.getPieceAt(start-7) != null) || (board.getPieceAt(start-9) != null))
			{
				if(end == start-7 || end == start-9)
				{
					Board.willCapture = true;
					Board.promotion = false;
					Board.enPassant = false;
					
					//reached end of board
					if(end <= 8 && end >= 1)
						Board.promotion = true;
					
					return true;
				}			
			}
		
			//moving one square down
			if(end == start-8)
			{
				if(board.getPieceAt(end) == null)
				{
					Board.enPassant = false;
					Board.willCapture = false;
					Board.promotion = false;
					
					//reached end of board
					if(end <= 8 && end >= 1)
						Board.promotion = true;
					
					return true;
				}	
			}
			
			//moving 2 squares down
			if(this.getIsFirstMove())
			{
				if(board.getPieceAt(end) == null && board.getPieceAt(start-8) == null)
				{
					Board.enPassant = false;
					Board.willCapture = false;
					Board.promotion = false;
					
					//if pawn lands next to another pawn of opposite color
					if(end + 1 <= 64 && end + 1 % 8 > end % 8)
					{
						if(board.getPieceAt(end+1) != null && board.getPieceAt(end+1).getType().equals("p") && board.getPieceAt(end+1).getColor().equals("b"))
							Board.enPassant = true;
					}
					if(end -1 > 0 && end % 8 > end - 1 % 8)
					{
						if(board.getPieceAt(end-1) != null && board.getPieceAt(end-1).getType().equals("p") && board.getPieceAt(end-1).getColor().equals("b"))
							Board.enPassant = true;
					}
					
					return true;
				}
			}
		}

		return false;
	}
}
