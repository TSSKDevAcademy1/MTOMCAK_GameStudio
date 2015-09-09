package games.mine;

/**
 * Mine tile.
 */
public class Mine extends Tile {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (getState() == State.OPEN) {
			return "X";
		} else {
			return super.toString();
		}
	}
}
