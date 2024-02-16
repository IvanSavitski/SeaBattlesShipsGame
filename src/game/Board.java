package game;
import java.util.Scanner;

import field.GameField;
import field.ShipFieldImpl;
import field.WaterFieldImpl;
import ship.Ship;
import ship.ShipTypes;

import utils.*;
import java.awt.*;

public class Board {
    ClearConsoleUtil clearConsoleUtil = new ClearConsoleUtil();

    private static final char WATER = '~';
    public static final int BOARD_SIZE = 16;
    private static final char[] BOARD_LETTERS = {'A', 'B', 'C', 'D',
                                                 'E', 'F', 'G', 'H', 
                                                 'I', 'J', 'K', 'L',
                                                  'M', 'N', 'O', 'P'};
    private static final String HORIZONTAL = "H";
    private static final String VERTICAL = "V";

    private Scanner scanner;
    private GameField[][] board;
    private static final Ship[] ships;

    static {
        ships = new Ship[] {
                new Ship("Super Carrier", ShipTypes.SUPERCARRIER),

                new Ship("Carrier", ShipTypes.CARRIER),
                new Ship("Carrier", ShipTypes.CARRIER),

                new Ship("Battleship", ShipTypes.BATTLESHIP),
                new Ship("Battleship", ShipTypes.BATTLESHIP),
                new Ship("Battleship", ShipTypes.BATTLESHIP),

                new Ship("Cruiser", ShipTypes.CRUISER),
                new Ship("Cruiser", ShipTypes.CRUISER),
                new Ship("Cruiser", ShipTypes.CRUISER),
                new Ship("Cruiser", ShipTypes.CRUISER),

                new Ship("Submarine", ShipTypes.SUBMARINE),
                new Ship("Submarine", ShipTypes.SUBMARINE),
                new Ship("Submarine", ShipTypes.SUBMARINE),
                new Ship("Submarine", ShipTypes.SUBMARINE),
                new Ship("Submarine", ShipTypes.SUBMARINE),
                
                new Ship("Destroyer", ShipTypes.DESTROYER),
                new Ship("Destroyer", ShipTypes.DESTROYER),
                new Ship("Destroyer", ShipTypes.DESTROYER),
                new Ship("Destroyer", ShipTypes.DESTROYER),
                new Ship("Destroyer", ShipTypes.DESTROYER),
                new Ship("Destroyer", ShipTypes.DESTROYER)
        };
    }

    public Board() {
        this.scanner = new Scanner(System.in);
        this.board = new GameField[BOARD_SIZE][BOARD_SIZE];

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new WaterFieldImpl();
            }
        }

    }

    public void placeShipsOnBoard() {
        for(Ship ship : ships) {
            boolean horizontal = askValidShipDirection();
            Point startingPoint = askValidStartingPoint(ship, horizontal);
            placeValidShip(ship, startingPoint, horizontal);

            printBoard();
        }
    }


    public GameField getField(int x, int y) {
        if(!isInsideBoard(x, y)) {
            System.out.println("Outside board - try again:");
            throw new IllegalArgumentException("Outside board - try again: ");     
        }
        return board[y][x];
    }

    public void printBoard() {
        System.out.print("\t");

        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(BOARD_LETTERS[i] + "\t");
        }

        System.out.println();

        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((i+1) + "\t");
            for(int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j].getIcon() + "\t");
            }
            System.out.println();
        }
    }

    private boolean askValidShipDirection() {
        System.out.printf("%nDo you want to place the ship horizontally (H) or vertically (V)?");
        String direction;
        do {
             direction = scanner.nextLine().trim();
        } while (!HORIZONTAL.equals(direction) && !VERTICAL.equals(direction));

        clearConsoleUtil.clearConsole();

        return HORIZONTAL.equals(direction);
    }

    private Point askValidStartingPoint(Ship ship, boolean horizontal) {
        Point from;
        do {
            System.out.printf("%nEnter position of %s (length  %d): ", ship.getName(), ship.getSize());
            from = new Point(scanner.nextInt(), scanner.nextInt());
        } while(!isValidStartingPoint(from, ship.getSize(), horizontal));

        clearConsoleUtil.clearConsole();

        return from;
    }


    private boolean isValidStartingPoint(Point from, int length, boolean horizontal) {
        int xDiff = 0;
        int yDiff = 0;
        if(horizontal) {
            xDiff = 1;
        } else {
            yDiff = 1;
        }

        int x = (int)from.getX() - 1;
        int y = (int)from.getY() - 1;
        if(!isInsideBoard(x, y) ||
                (!isInsideBoard(x + length,y) && horizontal) ||
                (!isInsideBoard(x, y + length) && !horizontal)
        ) {
            return false;
        }

        for(int i = 0; i < length; i++) {
            if(board[(int)from.getY() + i *yDiff - 1]
                    [(int)from.getX() + i *xDiff - 1].getIcon() != WATER){
                return false;
            }
        }
        return true;
    }

    private void placeValidShip(Ship ship, Point startingPoint, boolean horizontal) {
        int xDiff = 0;
        int yDiff = 0;
        if(horizontal) {
            xDiff = 1;
        } else {
            yDiff = 1;
        }
        for(int i = 0; i < ship.getSize() ; i++) {
            board[(int)startingPoint.getY() + i*yDiff - 1]
                    [(int)startingPoint.getX()+ i*xDiff - 1] = new ShipFieldImpl(ship);
        }
    }
    private boolean isInsideBoard(int x, int y){
        return x <= BOARD_SIZE && x >= 0
                && y <= BOARD_SIZE && y >= 0;
    }
} 
