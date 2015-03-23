/**
 * 
 */
package model;

import java.util.ArrayList;

/**Interface for chess pieces
 * @author Ben Green & Kate Sussman
 *
 */
public abstract class Piece {
	
	private String type;
	private String color;
	private boolean hasMoved;
	
	/**Getter for the piece's type (rook, knight, etc.)
	 * @return Piece's type
	 */
	public String getType() {
		return type;
	}
	
	/**Getter for piece's color (black, white)
	 * @return Piece's color
	 */
	public String getColor() {
		return color;
	}
	
	/**Checks piece color using a boolean
	 * @return True if white, False if black
	 */
	public boolean isWhite() {
		return color.equals("w");
	}
	
	public boolean getHasMoved() {
		return hasMoved;
	}

	/**Get array of valid destination moves
	 * @return
	 */
	public abstract ArrayList<Integer> getMoveRange();
	
	/**Get array of valid destination attacks (for pawns)
	 * @return
	 */
	public abstract ArrayList<Integer> getAttackRange();


}
