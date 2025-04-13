import java.util.*;

public class Main {

    static final int WALL = 1;     // Стена
    static final int PATH = 0;     // Проход
    static final int VISITED = 2;  // Посещённая клетка
    static final int FINAL_PATH = 3; // Путь от старта до финиша

    static int[][] maze; // сам лабиринт
    static int rows = 10, cols = 10; // размер по умолчанию
    static int startX = 0, startY = 0;
    static int endX, endY;

    public static void main(String[] args) {
        // Ввод от пользователя
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество строк (минимум 10): ");
        rows = Math.max(10, scanner.nextInt());

        System.out.print("Введите количество столбцов (минимум 10): ");
        cols = Math.max(10, scanner.nextInt());

        endX = rows - 1;
        endY = cols - 1;

        maze = new int[rows][cols];

        generateMaze(); // генерация лабиринта
        System.out.println("Сгенерированный лабиринт:");
        printMaze();

        if (solveMaze(startX, startY)) {
            System.out.println("Путь найден:");
        } else {
            System.out.println("Путь не найден.");
        }

        printMaze(); // вывод с отмеченным путём
    }

    // 🔸 Рекурсивная генерация лабиринта
    static void generateMaze() {
        for (int[] row : maze) Arrays.fill(row, WALL); // все стены
        carvePath(startX, startY); // рекурсивно делаем проходы
        maze[startX][startY] = PATH;
        maze[endX][endY] = PATH;
    }

    // 🔸 Убираем стены и делаем лабиринт проходимым
    static void carvePath(int x, int y) {
        int[] dx = {0, 0, 2, -2}; // направления: вверх-вниз-право-лево
        int[] dy = {2, -2, 0, 0};

        maze[x][y] = PATH;

        List<Integer> dirs = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(dirs); // рандом

        for (int dir : dirs) {
            int nx = x + dx[dir], ny = y + dy[dir];

            if (isInBounds(nx, ny) && maze[nx][ny] == WALL) {
                maze[x + dx[dir] / 2][y + dy[dir] / 2] = PATH;
                carvePath(nx, ny);
            }
        }
    }

    // 🔸 Проверка, что не выходим за границы
    static boolean isInBounds(int x, int y) {
        return x > 0 && x < rows - 1 && y > 0 && y < cols - 1;
    }

    // 🔸 Рекурсивный поиск пути
    static boolean solveMaze(int x, int y) {
        if (x == endX && y == endY) {
            maze[x][y] = FINAL_PATH;
            return true;
        }

        if (!isSafe(x, y)) return false;

        maze[x][y] = VISITED;

        int[] dx = {-1, 1, 0, 0}; // вверх-вниз-лево-право
        int[] dy = {0, 0, -1, 1};

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d], ny = y + dy[d];
            if (solveMaze(nx, ny)) {
                maze[x][y] = FINAL_PATH;
                return true;
            }
        }

        return false;
    }

    // 🔸 Безопасна ли клетка?
    static boolean isSafe(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == PATH;
    }

    // 🔸 Вывод лабиринта
    static void printMaze() {
        for (int[] row : maze) {
            for (int cell : row) {
                switch (cell) {
                    case WALL -> System.out.print("#");
                    case PATH -> System.out.print(" ");
                    case VISITED -> System.out.print("·");
                    case FINAL_PATH -> System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
