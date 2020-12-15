package puzzles;

import util.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12B {

    private static class Position {
        public long x;
        public long y;

        public Position(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private static void executeInstruction(String inputLine, Position shipCoords, Position wayPointCoords) {
        char instruction = inputLine.charAt(0);
        int arg = Integer.valueOf(inputLine.substring(1));

        if (instruction == 'S' || instruction == 'W') {
            arg = -arg;
        } else if (instruction == 'R') {
            arg = 360 - arg;
        }

        switch (instruction) {
            case 'N':
            case 'S':
                wayPointCoords.x = wayPointCoords.x + arg;
                break;
            case 'E':
            case 'W':
                wayPointCoords.y = wayPointCoords.y + arg;
                break;
            case 'L':
            case 'R':
                long origX = wayPointCoords.x;
                long origY = wayPointCoords.y;
                switch (arg) {
                    case 90:
                        wayPointCoords.x = origY;
                        wayPointCoords.y = -origX;
                        break;
                    case 180:
                        wayPointCoords.x = -origX;
                        wayPointCoords.y = -origY;
                        break;
                    case 270:
                        wayPointCoords.x = -origY;
                        wayPointCoords.y = origX;
                        break;
                }
                break;
            case 'F':
                shipCoords.x = shipCoords.x + arg * wayPointCoords.x;
                shipCoords.y = shipCoords.y + arg * wayPointCoords.y;
                break;
        }
    }

    public static void main(String[] args) {

        List<String> input = Utilities.readLineList();
        Position ship = new Position(0, 0);
        Position waypoint = new Position(1, 10);

        for (String inputLine : input) {
            executeInstruction(inputLine, ship, waypoint);
            System.out.println(ship);
            System.out.println(waypoint);
            System.out.println("----");
        }

        System.out.println(Math.abs(ship.x) + Math.abs(ship.y));
    }
}
