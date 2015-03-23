/**
 * 
 */
package model;

/**Interface for chess pieces
 * @author Ben Green & Kate Sussman
 *
 */
public interface PieceInterface {
	
	/**Getter for the piece's type (rook, knight, etc.)
	 * @return Piece's type
	 */
	public abstract String getType();
	
	/**Getter for piece's color (black, white)
	 * @return Piece's color
	 */
	public abstract String getColor();
	
	/**Checks piece color using a boolean
	 * @return True if white, False if black
	 */
	public boolean isWhite();
	
	public boolean getIsFirstMove();
	
	//public boolean isValidPieceMove(Board board, Integer[] location);

	public boolean isValidPieceMove(Board board, String[] location);


}
