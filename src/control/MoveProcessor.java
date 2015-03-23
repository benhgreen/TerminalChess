/**
 * 
 */
package control;

import java.util.StringTokenizer;

import model.Board;
import model.MoveResponse;
import model.Piece;

/**
 * @author Ben Green & Kate Sussman
 *
 */
public class MoveProcessor {

	public static boolean processMove(Board board, String move) {
		
		String[] parsed_moves = parseMove(move);
		Integer[] parsed_move = new Integer[3];
		
		if (parsed_moves == null)
			return false;
		
		parsed_move[0] = Integer.parseInt(parsed_moves[0]);
		parsed_move[1] = Integer.parseInt(parsed_moves[1]);

		Integer start = parsed_move[0];
		Integer end = parsed_move[1];
		String option = parsed_moves[2];
	
		
		//verify that a piece exists on the specified space that belongs to the player
		Piece piece = board.getPieceAt(parsed_move[0]);
		
		//check that the piece exists
		if (piece == null) {
			return false;
		
		//check that the piece belongs to the current player
		} else if (!piece.getColor().equals(board.getWhoseTurn())) {
			return false;
		}
		
		//make sure piece isn't trying to 'capture' a friend
		if (board.hasPieceAt(end)) {
			if (board.getPieceAt(end).getColor().equals(board.getWhoseTurn())) {
				return false;
			}
		}
		
		MoveResponse response = piece.isMoveValid(board, start, end);
		
		//analyze move further with helper method
		if (!response.valid) {
			return false;
		
		//make sure move doesn't violate check rules TODO this
		}else if (!obeysCheck(board, start, end)) {
			return false;
		}
		
		
		//handle move
		
		Board.draw = false;
		
		switch(response.special) {
		
		case("castle"):
			board.castle(board, parsed_move);
			break;
		
		case("promotion"):
			board.movePiece(start, end);
			board.replacePiece(board.getPieceAt(end).getColor(), option, end);
			break;
		
		default:
			board.movePiece(start, end);
			break;
		}
		
		//check if player wants to draw
		if(option != null && option.equals("draw?"))
			Board.draw = true;
		return true;
		
	}
	
	
	private static boolean obeysCheck(Board board, Integer start, Integer end) {
		// TODO Auto-generated method stub
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
