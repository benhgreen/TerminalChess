/**
 * 
 */
package chess;

import java.util.Scanner;

import control.BoardPrinter;
import control.MoveProcessor;
import model.Board;
import model.MoveResponse;
/**
 * @author Ben Green & Kate Sussman
 *
 */
public class Chess {

	public static void main(String[] args) {
		
		//initialize scanner & move holder
		Scanner input = new Scanner(System.in);
		String move = "";
		
		//initialize board
		Board board = new Board();
		
		while (board.getStatus().equals("progressing")) {
			
			//print board
			BoardPrinter.printBoard(board);
			
			//check for check/checkmate/stalemate
			//response.valid is true if a valid move exists, otherwise special
			//will contain reason for failure
			MoveResponse response = MoveProcessor.checkMate(board);
			if (!response.valid) {
				if (response.special.equals("stalemate")) {
					System.out.println("Stalemate!");
					System.exit(0);
				} else {
					if (board.getWhoseTurn().equals("w")) {
						System.out.println("Black wins");
						System.exit(0);
					} else {
						System.out.println("White wins");
						System.exit(0);
					}
				}
			} else if (response.special.equals("check")) {
				System.out.println("You are in check.");
			}
			
			//query move
			if (board.getWhoseTurn() == "w")
				System.out.print("White's move: ");
			else if (board.getWhoseTurn() == "b")
				System.out.print("Black's move: ");
			
			//get input
			move = input.nextLine();
			
			//print pre-board padding
			System.out.println();
			
			//check for resignation
			if (move.equals("resign")) {
				if (board.getWhoseTurn() == "b") {
					System.out.println("White wins");
					System.exit(0);
				} else if (board.getWhoseTurn() == "w") {
					System.out.println("Black wins");
					System.exit(0);
				}
			}
			
			//check for accepted draw
			if (Board.draw) {
				if (move.equals("draw")) {
					System.out.println("draw");
					System.exit(0);
				}
			}
			
			//check move validity & make move
			while (!MoveProcessor.processMove(board, move)) {
				System.out.print("\nIllegal Move, try again: ");
				move = input.nextLine();
			}
			
			//switch move
			board.switchPlayer();
		
		}
		
		input.close();
		
	}

}
