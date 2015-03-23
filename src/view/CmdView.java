/**
 * 
 */
package view;

import java.util.Scanner;

import control.BoardPrinter;
import control.MoveProcessor;
import model.Board;
/**
 * @author Ben Green & Kate Sussman
 *
 */
public class CmdView {

	public static void main(String[] args) {
		
		//initialize scanner & move holder
		Scanner input = new Scanner(System.in);
		String move = "";
		
		//initialize board
		Board board = new Board();
		
		while (board.getStatus().equals("progressing")) {
			
			//print board
			BoardPrinter.printBoard(board);
			
			//query move
			if(board.getWhoseTurn() == "w")
				System.out.print("White's move: ");
			
			else if(board.getWhoseTurn() == "b")
				System.out.print("Black's move: ");
			
			move = input.nextLine();
			
			if(move.equals("resign"))
			{
				if(board.getWhoseTurn() == "b")
					System.out.println("White wins");
				if(board.getWhoseTurn() == "w")
					System.out.println("Black wins");
				
				System.exit(0);
			}
			
			if(Board.draw)
				if(move.equals("draw"))
				{
					System.out.println("draw");
					System.exit(0);
				}
						
			
			
			//check move validity
			while (!MoveProcessor.processMove(board, move)) {
				System.out.print("\nIllegal Move, try again: ");
				move = input.nextLine();
			}
			
			board.switchPlayer();
			
		
		}
		
		input.close();
		
	}

}
