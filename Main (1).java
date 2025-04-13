import java.util.Random;

public class Main {
    private int rows;
    private int cols;
    private char[][] maze;
    private final char WALL = '#';
    private final char PATH = ' ';
    private final char START = 'S';
    private final char EXIT = 'E';

    public Main(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new char[rows][cols];
        generateMaze();
    }

    private void generateMaze() {
        Random rand = new Random();

        // Заполнение лабиринта стенами
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = (rand.nextInt(4) == 0) ? WALL : PATH;
            }
        }

        // Создание внешних стен
        for (int i = 0; i < rows; i++) {
            maze[i][0] = WALL;
            maze[i][cols - 1] = WALL;
        }
        for (int j = 0; j < cols; j++) {
            maze[0][j] = WALL;
            maze[rows - 1][j] = WALL;
        }

        // Установка стартовой и конечной точки
        maze[1][1] = START;
        maze[rows - 2][cols - 2] = EXIT;
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public char[][] getMaze() {
        return maze;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int[] getStart() {
        return new int[]{1, 1}; // Стартовая точка
    }

    public int[] getExit() {
        return new int[]{rows - 2, cols - 2}; // Конечная точка
    }
}
