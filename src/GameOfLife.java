import java.util.Scanner;

/**
 * The Main Controller for the Game of Life application.
 * <p>
 * This class is responsible for interactions with the user (Input/Output)
 * and managing the main simulation loop timing. It adheres to OOP by delegating
 * logic to the Grid class and state to the Cell class.
 * </p>
 *
 * @author Gemini
 * @version 1.0
 */
public class GameOfLife {

    /** The fixed width and height of the grid (square). */
    private static final int GRID_SIZE = 30;

    /** The delay between generations in milliseconds (2 seconds). */
    private static final int DELAY_MS = 2000;

    /**
     * The main method to start the application.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grid gameGrid = new Grid(GRID_SIZE, GRID_SIZE);

        // Header
        System.out.println("-------------------------------------------");
        System.out.println("   CONWAY'S GAME OF LIFE (" + GRID_SIZE + "x" + GRID_SIZE + ")");
        System.out.println("-------------------------------------------");
        System.out.println("1. Type 'Random' to generate a random start.");
        System.out.println("2. Type any other key to enter the grid manually.");
        System.out.print("> Choice: ");

        String choice = scanner.nextLine().trim();

        // Initialization Logic
        if (choice.equalsIgnoreCase("Random")) {
            gameGrid.initializeRandom();
            System.out.println("Grid initialized randomly.");
        } else {
            handleManualInput(scanner, gameGrid);
        }

        // Start Simulation
        System.out.println("Starting simulation... (Press Ctrl+C to stop)");
        try {
            // Small pause before rendering starts
            Thread.sleep(1000);
            runSimulationLoop(gameGrid);
        } catch (InterruptedException e) {
            System.out.println("Simulation stopped.");
        } finally {
            scanner.close();
        }
    }

    /**
     * Helper method to handle manual user input for the grid configuration.
     *
     * @param scanner  The active Scanner object.
     * @param gameGrid The Grid object to populate.
     */
    private static void handleManualInput(Scanner scanner, Grid gameGrid) {
        System.out.println("\n[MANUAL INPUT MODE]");
        System.out.println("Enter " + GRID_SIZE + " lines of text.");
        System.out.println("Use '1' for LIVE cells and '0' for DEAD cells.");
        System.out.println("Example: 001000101...");

        String[] lines = new String[GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.printf("Row %02d: ", i + 1);
            lines[i] = scanner.nextLine();
        }
        gameGrid.initializeFromInput(lines);
    }

    /**
     * The main simulation loop.
     * Clears the console, renders the grid, updates the logic, and waits.
     *
     * @param gameGrid The Grid object to simulate.
     * @throws InterruptedException If the thread sleep is interrupted.
     */
    private static void runSimulationLoop(Grid gameGrid) throws InterruptedException {
        while (true) {
            // 1. Clear Console (Using ANSI escape codes)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // 2. Render Grid
            System.out.println(gameGrid.render());

            // 3. Update Logic for next generation
            gameGrid.update();

            // 4. Wait
            Thread.sleep(DELAY_MS);
        }
    }
}