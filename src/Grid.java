import java.util.Random;

/**
 * Represents the two-dimensional universe of the Game of Life.
 * <p>
 * This class manages the collection of Cell objects. It handles the initialization
 * of the board (random or manual) and executes the logic to advance the 
 * game to the next generation based on the standard rules.
 * </p>
 *
 * @author Gemini
 * @version 1.0
 */
public class Grid {

    /** The number of rows in the grid. */
    private final int rows;

    /** The number of columns in the grid. */
    private final int cols;

    /** The 2D array containing the Cell objects. */
    private Cell[][] cells;

    /**
     * Constructs a new Grid with the specified dimensions.
     * All cells are initialized to 'dead' by default.
     *
     * @param rows The number of rows.
     * @param cols The number of columns.
     */
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];

        // Initialize the grid with dead cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(false);
            }
        }
    }

    /**
     * Initializes the grid with a random distribution of live cells.
     * Each cell has approximately a 20% chance of starting alive.
     */
    public void initializeRandom() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 0.2 represents a 20% probability
                cells[i][j].setAlive(rand.nextDouble() < 0.2);
            }
        }
    }

    /**
     * Initializes the grid based on manual user input.
     *
     * @param inputLines An array of strings representing the rows. 
     * '1' sets a cell to alive; any other character sets it to dead.
     */
    public void initializeFromInput(String[] inputLines) {
        for (int i = 0; i < rows && i < inputLines.length; i++) {
            String line = inputLines[i];
            for (int j = 0; j < cols && j < line.length(); j++) {
                if (line.charAt(j) == '1') {
                    cells[i][j].setAlive(true);
                }
            }
        }
    }

    /**
     * Advances the grid to the next generation by applying Conway's 4 Rules simultaneously.
     * <ol>
     * <li>Underpopulation: Live cell with < 2 neighbors dies.</li>
     * <li>Survival: Live cell with 2 or 3 neighbors lives.</li>
     * <li>Overpopulation: Live cell with > 3 neighbors dies.</li>
     * <li>Reproduction: Dead cell with exactly 3 neighbors becomes alive.</li>
     * </ol>
     */
    public void update() {
        Cell[][] nextGen = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                boolean currentState = cells[i][j].isAlive();
                boolean nextState = false;

                // Rule Implementation
                if (currentState && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    nextState = false; // Dies
                } else if (currentState && (liveNeighbors == 2 || liveNeighbors == 3)) {
                    nextState = true;  // Survives
                } else if (!currentState && liveNeighbors == 3) {
                    nextState = true;  // Reproduction
                }

                nextGen[i][j] = new Cell(nextState);
            }
        }

        // Update the reference to the new generation
        this.cells = nextGen;
    }

    /**
     * Helper method to count the number of live neighbors for a specific cell coordinate.
     * It checks the 8 surrounding cells (horizontal, vertical, and diagonal).
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The number of live neighbors (0-8).
     */
    private int countLiveNeighbors(int row, int col) {
        int count = 0;

        // Loop through the 3x3 block surrounding the cell
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the center cell itself

                int r = row + i;
                int c = col + j;

                // Check boundaries to avoid IndexOutOfBoundsException
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    if (cells[r][c].isAlive()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Renders the current state of the entire grid into a String.
     *
     * @return A String representation of the grid suitable for console output.
     */
    public String render() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(cells[i][j].toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}