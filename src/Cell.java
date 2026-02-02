/**
 * Represents a single cell within the Game of Life grid.
 * <p>
 * A cell encapsulates its state (alive or dead) and handles its own 
 * string representation (visual rendering) using Unicode characters.
 * </p>
 *
 * @author Gemini
 * @version 1.0
 */
public class Cell {

    /** The current state of the cell. True if alive, false if dead. */
    private boolean isAlive;

    /** Unicode character representing a Live cell (Black Square). */
    private static final String LIVE_CHAR = "\u25A0";

    /** Unicode character representing a Dead cell (White Square). */
    private static final String DEAD_CHAR = "\u25A1";

    /**
     * Constructs a new Cell with a specific initial state.
     *
     * @param isAlive The initial state of the cell (true for alive, false for dead).
     */
    public Cell(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Checks if the cell is currently alive.
     *
     * @return true if the cell is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the state of the cell for the next generation.
     *
     * @param alive true to make the cell alive, false to make it dead.
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * Returns the visual representation of the cell.
     *
     * @return A string containing a Black Square if alive, or White Square if dead.
     */
    @Override
    public String toString() {
        return isAlive ? LIVE_CHAR : DEAD_CHAR;
    }
}