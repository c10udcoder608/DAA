/**
 * Authors: Scott Howard, Joe Falcone, Brian Rosenblum
 * DAA Fall 2014
 * Dr. Baliga
 * Traveling Salesman Problem - Nearest Neighbor
 */

package neighbor;

/**
 * The Cities class holds the information for a particular
 * city, including its ID number and its X coordinate and
 * Y coordinate for its location as well as the
 * necessary mutator and accessor methods for all variables
 */

public class Cities {
	int id, x, y;
	
	/**
	 *  Constructor
	 */
	public Cities(int id, int x, int y)
	{
		this.id = id;
		this.x = x;
		this.y = y;
	} // end Constructor
	
	/**
	 *  Mutator method for x
	 */
	public void setX(int newX) {
		x = newX;
	} // end setX
	
	/**
	 *  Accessor method for x
	 *  @return x
	 */
	public int getX()
	{
		return x;
	} // end getX
	
	/**
	 *  Mutator method for y
	 */
	public void setY(int newY) 
	{
		y = newY;
	} // end setY
	
	/**
	 *  Accessor method for y
	 *  @return y
	 */
	public int getY()
	{
		return y;
	} // end getY
	
	/**
	 *  Mutator method for id
	 */
	public void setID(int newID) 
	{
		id = newID;
	} // end setID
	
	/**
	 *  Accessor method for id
	 *  @return id
	 */
	public int getID()
	{
		return id;
	} // end getID
	
	/**
	 *  Override the toString to print the city cleanly
	 *  @return str
	 */
	public String toString() {
		String str = "";
		
		str = "(" + id + ", " + x + ", " + y + ")";
		
		return str;
	} // end toString
	
} // end class Cities
