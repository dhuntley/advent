package puzzles;

import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11B {

    private static class SeatMap {

        private enum SeatState {
            FLOOR('.'),
            EMPTY('L'),
            OCCUPIED('#');

            private static final Map<Character, SeatState> BY_CODE = new HashMap<>();

            static {
                for (SeatState e : values()) {
                    BY_CODE.put(e.code, e);
                }
            }

            private final char code;

            SeatState(char code) {
                this.code = code;
            }

            public static SeatState valueOfCode(char code) {
                return BY_CODE.get(code);
            }
        };

        public List<List<SeatState>> seats = new ArrayList<>();

        public void addRow(String inputLine) {
            seats.add(inputLine.chars().mapToObj(code -> SeatState.valueOfCode((char)code)).collect(Collectors.toList()));
        }

        public int getNumVisibleOccupied(int row, int col) {
            int count = 0;
            int x, y;
            SeatState state;

            // Scan NW
            x = row - 1; y = col - 1;
            while (x >= 0 && y >= 0) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    x--; y--;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            // Scan N
            x = row - 1; y = col;
            while (x >= 0) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    x--;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            // Scan NE
            x = row - 1; y = col + 1;
            while (x >= 0 && y < seats.get(x).size()) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    x--; y++;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            // Scan W
            x = row; y = col - 1;
            while (y >= 0) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    y--;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            // Scan E
            x = row; y = col + 1;
            while (y < seats.get(x).size()) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    y++;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            // Scan SW
            x = row + 1; y = col - 1;
            while (x < seats.size() && y >= 0) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    x++; y--;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            // Scan S
            x = row + 1; y = col;
            while (x < seats.size()) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    x++;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            // Scan SE
            x = row + 1; y = col + 1;
            while (x < seats.size() && y < seats.get(x).size()) {
                state = seats.get(x).get(y);
                if (state.equals(SeatState.FLOOR)) {
                    x++; y++;
                } else {
                    if (state.equals(SeatState.OCCUPIED)) {
                        count++;
                    }
                    break;
                }
            }

            return count;
        }

        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            for (List<SeatState> row : seats) {
                for (SeatState seat : row) {
                    buffer.append(seat.code);
                }
                buffer.append("\n");
            }
            return buffer.toString();
        }

        public String getNeighbourString() {
            StringBuffer buffer = new StringBuffer();
            for (int row = 0; row < seats.size(); row++) {
                for (int col = 0; col < seats.get(row).size(); col++) {
                    buffer.append(seats.get(row).get(col).equals(SeatState.FLOOR) || seats.get(row).get(col).equals(SeatState.EMPTY) ? "." : getNumVisibleOccupied(row, col));
                }
                buffer.append("\n");
            }
            return buffer.toString();
        }

        public long getNumSeatsWithState(SeatState targetState) {
            long count = 0;
            for (List<SeatState> row : seats) {
                for (SeatState seatState : row) {
                    if (seatState.equals(targetState)) {
                        count++;
                    }
                }
            }
            return count;
        }

        public static SeatMap update(SeatMap oldSeatMap) {
            SeatMap seatMap = new SeatMap();

            for (int row = 0; row < oldSeatMap.seats.size(); row++) {
                List<SeatState> oldRowState = oldSeatMap.seats.get(row);
                List<SeatState> rowState = new ArrayList<>();

                for (int col = 0; col < oldSeatMap.seats.get(row).size(); col++) {
                    SeatState oldState = oldRowState.get(col);
                    SeatState state = oldState;
                    if (!oldState.equals(SeatState.FLOOR)) {
                        int numOccupied = oldSeatMap.getNumVisibleOccupied(row, col);

                        if (oldRowState.get(col).equals(SeatState.EMPTY) && numOccupied == 0) {
                            state = SeatState.OCCUPIED;
                        } else if (oldRowState.get(col).equals(SeatState.OCCUPIED) && numOccupied >= 5) {
                            state = SeatState.EMPTY;
                        }
                    }
                    rowState.add(state);
                }
                seatMap.seats.add(rowState);
            }

            return seatMap;
        }
    }

    public static void main(String[] args) {

        List<String> input = Utilities.readLineList();
        SeatMap seatMap = new SeatMap();

        for (String inputLine : input) {
            seatMap.addRow(inputLine);
        }

        SeatMap updatedSeatMap = SeatMap.update(seatMap);

        //System.out.println(seatMap.getNeighbourString());

        while (!updatedSeatMap.toString().equals(seatMap.toString())) {
            System.out.println(updatedSeatMap);
            System.out.println(updatedSeatMap.getNeighbourString());
            System.out.println("----");
            System.out.println("----");
            seatMap = updatedSeatMap;
            updatedSeatMap = SeatMap.update(seatMap);
        }

        System.out.println(updatedSeatMap);
        System.out.println(updatedSeatMap.getNumSeatsWithState(SeatMap.SeatState.OCCUPIED));
    }
}
