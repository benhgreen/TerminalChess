/**
 * 
 */
package model;

/**Interface for chess pieces
 * @author Ben Green & Kate Sussman
 *
 */
public abstract class Piece {
	
	protected String type;
	protected String color;
	protected boolean hasMoved;
	protected int direction;
	
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
	
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	public void markMoved() {
		hasMoved = true;
	}

	/**Get array of valid destination moves
	 * @return
	 */
	public abstract MoveResponse isMoveValid(Board board, Integer start, Integer end);
	
	public static int getRow(int location) {
		if (location % 8 == 0) {
			return location / 8;
		} else {
			return ((location - (location % 8)) / 8) + 1;
		}
	}
	
	public static int getColumn(int location) {
		if (location % 8 == 0) {
			return 8;
		} else {
			return location % 8;
		}
	}


}
