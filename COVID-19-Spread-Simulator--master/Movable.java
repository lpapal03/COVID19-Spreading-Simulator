

/**
 * this interface is a guide of methods all humans should have.
 * 
 * @author Taoufik Sousak, Loukas Papalazarou
 *
 */
public interface Movable {

	public int getXpos();
	
	public int getYpos();
	
	public void moveTo(int x, int y);
	
	public boolean takesMeasures();
	
}
