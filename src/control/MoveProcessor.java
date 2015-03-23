/**
 * 
 */
package control;

import java.util.StringTokenizer;

import model.Bishop;
import model.Board;
import model.King;
import model.Knight;
import model.Pawn;
import model.PieceInterface;
import model.Queen;
import model.Rook;

/**
 * @author Ben Green & Kate Sussman
 *
 */
public class MoveProcessor {
	
	//Board board = new Board();

	public static boolean processMove(Board board, String move) {
		
		/*if(parseMove(move) == null)
		{
			return false;
		}*/
		
		//parse move into numbers
		String[]parsed_moves = parseMove(move);
		Integer[] parsed_move = new Integer[3];
		
		if (parsed_moves == null)
			return false;
		
		for(int i=0; i<2; i++)
		{
			parsed_move[i] = Integer.parseInt(parsed_moves[i]);
		}

		Integer start = parsed_move[0];
		Integer end = parsed_move[1];
		String option = parsed_moves[2];
	
		
		//verify that a piece exists on the specified space that belongs to the player
		PieceInterface piece = board.getPieceAt(parsed_move[0]);
		
		//check that the piece exists
		if (piece == null) {
			return false;
		
		//check that the piece belongs to the current player
		} else if (!piece.getColor().equals(board.getWhoseTurn())) {
			return false;
		}
		
		//analyze move further with helper method
		if (!piece.isValidPieceMove(board, parsed_moves)) {
			return false;
		
		//make sure move doesn't violate check rules
		}else if (obeysCheck(board, parsed_move) == false) {
			return false;
		}
		
		//if(board.getPieceAt(end) != null)
		//	return false;
		
		
		//does actual moving of pieces
		
		Board.draw = false;
		
		if(Board.willCastle)
		{
			board.castle(board, parsed_move);
		} 
		else if(Board.promotion)
		{
			board.movePiece(start, end);
			board.replacePiece(board.getPieceAt(end).getColor(), option, end);
		}		
		/*else if (Board.enPassant)
		{
			board.enPassant(board, board.getWhoseTurn(), start, end);
		}*/
		else if (Board.willCapture)
		{
			//make sure not capturing your own piece
			if(board.getPieceAt(end).getColor() != board.getPieceAt(start).getColor())
			{
				board.removePiece(end);
				board.movePiece(start, end);
			}
			Board.willCapture = false;
		} 
		else
		{
			board.movePiece(start, end);
		}
			

		
		//check if player wants to draw
		if(option != null && option.equals("draw?"))
			Board.draw = true;	
	
		return true;
		
	}
	
	
	/**Checks that move doesn't bring the player's kind into check (or doesn't get the
	 * player's king out of check)
	 * @param board Board to use
	 * @param parsed_move Move parsed with ParseMove
	 * @return True if the move obeys rules of check
	 */
	private static boolean obeysCheck(Board board, Integer[] parsed_move) {
		Integer start = parsed_move[0];
		Integer end = parsed_move[1];
		
		/*Board board2 = new Board();
		board2 = board; 
		
		board2.movePiece(start, end);
		System.out.println("board 2:");
		BoardPrinter.printBoard(board2);
		
		if(checkForCheck(board2) == true)
		{
			board2.movePiece(end, start);
			return false;
		}
			
		board2.movePiece(end, start);*/
		
		board.movePiece(start, end);
		
		if(checkForCheck(board) == true)
		{
			board.movePiece(end, start);
			return false;
		} else 
			board.movePiece(end, start);
		
		return true;
	}
	
	//checks all possible piece moves to see if player's king is in check
	//returns true if king is in check
	//returns false if king is not in check
	public static boolean checkForCheck(Board board)
	{
		String players_color = board.getWhoseTurn();
		
		//check every square for piece of opposite color.
		//once found piece, check all moves 
		for(int i=1; i<=64; i++)
		{
			//only check pieces of opposite color 
			if(board.getPieceAt(i) != null && board.getPieceAt(i).getColor() != board.getWhoseTurn())
			{
				
				PieceInterface piece = board.getPieceAt(i);
				
				//if white piece if found, check if it can get to black king
				if(board.getPieceAt(i).equals("w"))
				{
					switch (piece.getType())
					{
						case("p"):
						{
							if(checkPawns(i, board, players_color, "b"))
								return true;
							
							break;
						}
						case("R"):
						{
							if(checkRooks(i, board, players_color, "b"))
								return true;
	
							break;
						
						}
						case("N"):
						{
							if(checkKnights(i, board, players_color, "b"))
								return true;
	
							break;
						}
						case("B"):
						{
							if(checkBishops(i, board, players_color, "b"))
								return true;
			
							break;
						}
						case("Q"):
						{
							if(checkQueens(i, board, players_color, "b"))
								return true;
				
							break;
						}
						case("K"):
						{
							if(checkKings(i, board, players_color, "b"))
								return true;
					
							break;
						}
					}
				}
				
				//if black piece is found/ check if it can get to white king
				if(board.getPieceAt(i).equals("w"))
				{
					switch (piece.getType())
					{
						case("p"):
						{
							if(checkPawns(i, board, players_color, "w"))
								return true;
							
							break;
						}
						case("R"):
						{
							if(checkRooks(i, board, players_color, "w"))
								return true;
	
							break;
						
						}
						case("N"):
						{
							if(checkKnights(i, board, players_color, "w"))
								return true;
	
							break;
						}
						case("B"):
						{
							if(checkBishops(i, board, players_color, "w"))
								return true;
			
							break;
						}
						case("Q"):
						{
							if(checkQueens(i, board, players_color, "w"))
								return true;
				
							break;
						}
						case("K"):
						{
							if(checkKings(i, board, players_color, "w"))
								return true;
					
							break;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public static boolean checkPawns(int i, Board board, String players_color, String king_color)
	{
		//if player is black
		//check to see if white pawns can capture black king
		if(players_color.equals("b"))
		{
			if(i+7 < 65)
				if(board.getPieceAt(i+7) != null && board.getPieceAt(i+7).getType().equals("K") && board.getPieceAt(i+7).getColor().equals("b"))
					return true;
			if(i+9 < 65)
				if(board.getPieceAt(i+9) != null && board.getPieceAt(i+9).getType().equals("K") && board.getPieceAt(i+9).getColor().equals("b"))
					return true;
			
		}

		//if player is white
		//check to see if black pawns can capture white king
		if(players_color.equals("w"))
		{
			if(i-7 > 0)
					if(board.getPieceAt(i-7) != null && board.getPieceAt(i-7).getType().equals("K") && board.getPieceAt(i-7).getColor().equals("b"))
						return true;
				if(i-9 > 0)
					if(board.getPieceAt(i-9) != null && board.getPieceAt(i-9).getType().equals("K") && board.getPieceAt(i-9).getColor().equals("b"))
						return true;
		}		
		
		return false;
	}
	
	public static boolean checkRooks(int i, Board board, String players_color, String king_color)
	{
		//if player is black
		//check to see if white rook can capture black king
		if(players_color.equals("b")) 
		{
			//check all pieces going up until hit a piece that's not the king
			for(int k = i+8; k < 65; k += 8)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
			//check all pieces going right until hit a piece that's not king
			for(int k = i+1; k % 8 != 0; k++)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
		}
		//if player is white
		//check to see if black rook can capture white king
		if(players_color.equals("w"))
		{
			//check all pieces going down until hit a piece that's not the king
			for(int k = i-8; k > 0; k -= 8)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
			
			//check all pieces going left until hit a piece that's not king
			for(int k = i-1; k % 8 != 1; k--)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
		}
		
		return false;
	}
	
	public static boolean checkKnights(int i, Board board, String players_color, String king_color)
	{
		//if player is black
		//check to see if white knight can capture black king
		if(players_color.equals("b")) 
		{
			//up 2 right 1
			if(i+17 < 65)
				if(board.getPieceAt(i+17) != null && board.getPieceAt(i+17).getType().equals("K") && board.getPieceAt(i+17).getColor().equals("b"))
					return true;
			//up 2 left 1
			if(i+15 < 65)
				if(board.getPieceAt(i+15) != null && board.getPieceAt(i+15).getType().equals("K") && board.getPieceAt(i+15).getColor().equals("b"))
					return true;
			//up 1 right 2
			if(i+10 < 65)
				if(board.getPieceAt(i+10) != null && board.getPieceAt(i+10).getType().equals("K") && board.getPieceAt(i+10).getColor().equals("b"))
					return true;
			//up 1 left 2
			if(i+6 < 65)
				if(board.getPieceAt(i+6) != null && board.getPieceAt(i+6).getType().equals("K") && board.getPieceAt(i+6).getColor().equals("b"))
					return true;
		}
		
		//if player is white
		//check to see if black knight can capture white king
		if(players_color.equals("w")) 
		{
			//down 2 right 1
			if(i-15 > 0)
				if(board.getPieceAt(i-15) != null && board.getPieceAt(i-15).getType().equals("K") && board.getPieceAt(i-15).getColor().equals("b"))
					return true;
			//down 2 left 1
			if(i-17 > 0)
				if(board.getPieceAt(i-17) != null && board.getPieceAt(i-17).getType().equals("K") && board.getPieceAt(i-17).getColor().equals("b"))
					return true;
			//down 1 right 2
			if(i-6 > 0)
				if(board.getPieceAt(i-6) != null && board.getPieceAt(i-6).getType().equals("K") && board.getPieceAt(i-6).getColor().equals("b"))
					return true;
			//down 1 left 2
			if(i-10 > 0)
				if(board.getPieceAt(i-10) != null && board.getPieceAt(i-10).getType().equals("K") && board.getPieceAt(i-10).getColor().equals("b"))
					return true;
		}
		
		return false;
	}
	
	public static boolean checkBishops(int i, Board board, String players_color, String king_color)
	{
		//if player is black
		//check to see if white bishop can capture black king
		if(players_color.equals("b"))
		{
			//check all pieces going up and left until hit a piece that's not king
			for(int k = i+9; k < 65 ; k+=9)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
			//check all pieces going up and right until hit a piece that's not king
			for(int k = i+7; k < 65 ; k+=7)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
		}
		
		//if player is white
		//check to see if black bishop can capture white king
		if(players_color.equals("w"))
		{
			//check all pieces going down and left until hit a piece that's not king
			for(int k = i-7; k > 0; k-=7)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
			//check all pieces going down and right until hit a piece that's not king
			for(int k = i-9; k > 0; k-=9)
			{
				if(board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
		}
		
		return false;
	}
	
	public static boolean checkQueens(int i, Board board, String players_color, String king_color)
	{
		//if player is black
		//check to see if white queen can capture black king
		if(players_color.equals("b"))
		{
			//check all pieces going up and left until hit a piece that's not king
			for(int k = i+9; k < 65 ; k+=9)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
			//check all pieces going up and right until hit a piece that's not king
			for(int k = i+7; k < 65 ; k+=7)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
			//check all pieces going up until hit a piece that's not the king
			for(int k = i+8; k < 65; k += 8)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
			//check all pieces going right until hit a piece that's not king
			for(int k = i+1; k % 8 != 0; k++)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
		}
		
		//if player is white
		//check to see if black queen can capture white king
		if(players_color.equals("w"))
		{
			//check all pieces going down and left until hit a piece that's not king
			for(int k = i-7; k > 0; k-=7)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
			//check all pieces going down and right until hit a piece that's not king
			for(int k = i-9; k > 0; k-=9)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;	
			}
			
			//check all pieces going down until hit a piece that's not the king
			for(int k = i-8; k > 0; k -= 8)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor() != players_color)
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
			
			//check all pieces going left until hit a piece that's not king
			for(int k = i-1; k % 8 != 1; k--)
			{
				if(board.getPieceAt(k) != null && board.getPieceAt(k).getType().equals("K") && board.getPieceAt(k).getColor().equals("b"))
					return true;
				else if (board.getPieceAt(k) != null)
					break;						
			}
		}
		
		return false;
	}
	
	public static boolean checkKings(int i, Board board, String players_color, String king_color)
	{
		//if player is black
		//check to see if white king can capture black king
		if(players_color.equals("b"))
		{
			if(i+1 < 65)
				if(board.getPieceAt(i+1) != null && board.getPieceAt(i+1).getType().equals("K") && board.getPieceAt(i+1).getColor().equals("b"))
					return true;
			if(i+8 < 65)
				if(board.getPieceAt(i+8) != null && board.getPieceAt(i+8).getType().equals("K") && board.getPieceAt(i+8).getColor().equals("b"))
					return true;
			if(i+7 < 65)
				if(board.getPieceAt(i+7) != null && board.getPieceAt(i+7).getType().equals("K") && board.getPieceAt(i+7).getColor().equals("b"))
					return true;
			if(i+9 < 65)
				if(board.getPieceAt(i+9) != null && board.getPieceAt(i+9).getType().equals("K") && board.getPieceAt(i+9).getColor().equals("b"))
					return true;
		}
		
		//if player is white
		//check to see if black king can capture white king
		if(players_color.equals("w"))
		{
			if(i-1 > 0)
				if(board.getPieceAt(i-1) != null && board.getPieceAt(i-1).getType().equals("K") && board.getPieceAt(i-1).getColor().equals("b"))
					return true;
			if(i-8 > 0)
				if(board.getPieceAt(i-8) != null && board.getPieceAt(i-8).getType().equals("K") && board.getPieceAt(i-8).getColor().equals("b"))
					return true;
			if(i-7 > 0)
				if(board.getPieceAt(i-7) != null && board.getPieceAt(i-7).getType().equals("K") && board.getPieceAt(i-7).getColor().equals("b"))
					return true;
			if(i-9 > 0)
				if(board.getPieceAt(i-9) != null && board.getPieceAt(i-9).getType().equals("K") && board.getPieceAt(i-9).getColor().equals("b"))
					return true;
		}
		
		return false;
	}

	/**Parses 'move' parameter into numbers representing current and destination squares
	 * @param move Move formatted like 'e2 e4'
	 * @return Integer array of size 2 with Integer representations of the move. For
	 * example, 'e2 e4' would become [13, 29]. Null is returned if the one or both of the
	 * squares given are nonsensical, or if something else is wrong with the input.
	 */
	public static String[] parseMove(String move) {
		
		//initialize return array
		String[] parsed_moves = new String[3];
		
		//parse move into tokens
		StringTokenizer tokz = new StringTokenizer(move);
		
		//check that only two arguments exist - current location and destination
		if (tokz.countTokens() < 2 || tokz.countTokens() > 3) {
			return null;
		}
		
		//iterate through tokens and parse each one into return array
		for (int x = 0; x < 2; x++) {
			String token = tokz.nextToken().toLowerCase();
			
			//check each location for length
			if (token.length() != 2) {
				System.err.println("Invalid number of moves");
				return null;
			
			//check that first char is a letter and second char is a number
			} else if (!Character.isLetter(token.charAt(0)) || (!Character.isDigit(token.charAt(1)))) {
				System.err.println("Invalid char types in moves");
				return null;
			
			//check that letter is between a-h and number is between 1-8
			} else if ((Character.getNumericValue(token.charAt(0))) > 17 || ((Character.getNumericValue(token.charAt(1)) > 8))) {

				System.err.println("Invalid move range");
				return null;
			}
			
			//put parsed location into return array
			parsed_moves[x] = Integer.toString((Character.getNumericValue(token.charAt(0))-9) + (Character.getNumericValue(token.charAt(1)-1) * 8));
			System.out.println(parsed_moves[x]);
		}
		
		if(tokz.hasMoreTokens())
			parsed_moves[2] = tokz.nextToken().toString();
		
		return parsed_moves;
	}

}
