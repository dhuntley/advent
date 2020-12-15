package puzzles;

import util.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12A {

    private enum Bearing {
        E(0), N(90), W(180), S(270);

        private static final Map<Integer, Bearing> BY_DEGREES = new HashMap<>();

        static {
            for (Bearing b : values()) {
                BY_DEGREES.put(b.degrees, b);
            }
        }

        private int degrees;

        Bearing(int degrees) {
            this.degrees = degrees;
        }

        public static Bearing rotate(Bearing start, int degrees) {
            int end = (start.degrees + degrees) % 360;
            if (end < 0) {
                end = end + 360;
            }
            return BY_DEGREES.get(end);
        }
    }

    private static class Position {
        public long x;
        public long y;
        public Bearing r;

        public Position(long x, long y, Bearing r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    '}';
        }
    }

    private static Position executeInstruction(String inputLine, Position startCoords) {
        char instruction = inputLine.charAt(0);
        int arg = Integer.valueOf(inputLine.substring(1));

        if (instruction == 'S' || instruction == 'W' || instruction == 'R') {
            arg = -arg;
        }

        switch (instruction) {
            case 'N':
            case 'S':
                return new Position(startCoords.x + arg, startCoords.y, startCoords.r);
            case 'E':
            case 'W':
                return new Position(startCoords.x, startCoords.y + arg, startCoords.r);
            case 'L':
            case 'R':
                return new Position(startCoords.x, startCoords.y, Bearing.rotate(startCoords.r, arg));
            case 'F':
                switch (startCoords.r) {
                    case N:
                        return new Position(startCoords.x + arg, startCoords.y, startCoords.r);
                    case S:
                        return new Position(startCoords.x - arg, startCoords.y, startCoords.r);
                    case E:
                        return new Position(startCoords.x , startCoords.y + arg, startCoords.r);
                    case W:
                        return new Position(startCoords.x, startCoords.y - arg, startCoords.r);
                }
        }
        return null;
    }

    public static void main(String[] args) {

        List<String> input = Utilities.readLineList();
        Position coords = new Position(0, 0, Bearing.E);

        for (String inputLine : input) {
            coords = executeInstruction(inputLine, coords);
            System.out.println(coords);
        }

        System.out.println(Math.abs(coords.x) + Math.abs(coords.y));
    }
}
