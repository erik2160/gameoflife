import java.util.Random;

public class GameOfLife {
    public String[][] grid;
    public int width, height, generations, speed;
    public GameOfLife(int width, int height, int generations, int speed, String pattern) {
        this.width = width;
        this.height = height;
        this.generations = generations;
        this.speed = speed;
        grid = new String[height][width];
        setPattern(pattern);
    }
    public void run() throws InterruptedException {
        int decreaseGenerations = generations;
        if (generations == 0) {
            while (generations == 0) {
                System.out.println("Dimension: " + height + "x" + width + " " +
                        "| Speed: " + speed + " | Generation: Infinity");
                printGrid();
                updateGrid();
                Thread.sleep(speed);
                pressKey();
            }
        } else {
            while (decreaseGenerations > 0) {
                System.out.println("Dimension: " + height + "x" + width + " " +
                        "| Speed: " + speed + " | Generation: " + (generations - decreaseGenerations + 1));
                printGrid();
                updateGrid();
                Thread.sleep(speed);
                decreaseGenerations--;
            }
        }
    }

    public void pressKey() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }


    public void setPattern(String pattern) {
        String[] choices = {"◼", " "};
        Random random = new Random();

        if (pattern.equals("rnd")) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = choices[random.nextInt(2)];
                }
            }
        } else {
            int x = 0, y = 0;
            for (char c : pattern.toCharArray()) {
                if (c == '1') {
                    grid[x][y] = "◼";
                    y++;
                } else if (c == '0') {
                    grid[x][y] = " ";
                    y++;
                } else if (c == '#') {
                    x++;
                    y = 0;
                } else {
                    System.out.println("Invalid pattern!");
                    System.exit(0);
                }
            }
        }
    }

    public void printGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                System.out.print("│");
                System.out.print(grid[x][y] != null
                        && grid[x][y].equals("◼") ? "◼" : " ");
            }
            System.out.println("│");
        }
    }
    public static void fillGrid(char[][] grid, char c) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = c;
            }
        }
    }

    public void updateGrid() {
        String[][] nextGrid = new String[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int neighbors = neighborsCount(x, y);
                if (grid[x][y] != null && grid[x][y].equals("◼")) {
                    if (neighbors == 2 || neighbors == 3) {
                        nextGrid[x][y] = "◼";
                    } else {
                        nextGrid[x][y] = " ";
                    }
                } else {
                    if (neighbors == 3) {
                        nextGrid[x][y] = "◼";
                    } else {
                        nextGrid[x][y] = " ";
                    }
                }
            }
        }
        grid = nextGrid;
    }

    public int neighborsCount(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && nx < width
                        && ny >= 0
                        && ny < height
                        && grid[nx][ny] != null
                        && grid[nx][ny].equals("◼")) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("You need to input 5 arguments!");
            System.exit(0);
        }
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int generations = Integer.parseInt(args[2]);
        int speed = Integer.parseInt(args[3]);
        String pattern = args[4];

        if (width == 10 || width == 20 || width == 40 || width == 80 ) {
        } else {
            System.out.println("Invalid grid width!");
            System.exit(0);
        }

        if (height == 10 || height == 20 || height == 40) {
        } else {
            System.out.println("Invalid grid height!");
            System.exit(0);
        }

        if (speed < 250 || speed > 1000) {
            System.out.println("Invalid speed!");
            System.exit(0);
        }

        char[][] grid = new char[height][width];
        fillGrid(grid, '@');

        GameOfLife game = new GameOfLife(width, height, generations, speed, pattern);

        try {
            game.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}