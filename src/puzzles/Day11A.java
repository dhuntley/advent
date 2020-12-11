package puzzles;

import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11A {

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

        public int getNumOccupiedNeighbours(int row, int col) {
            int count = 0;

            if (row > 0) {
                List<SeatState> upRow = seats.get(row - 1);
                if (col - 1 >= 0 && upRow.get(col - 1).equals(SeatState.OCCUPIED)) {
                    count++;
                }
                if (upRow.get(col).equals(SeatState.OCCUPIED)) {
                    count++;
                }
                if (col + 1 < upRow.size() && upRow.get(col + 1).equals(SeatState.OCCUPIED)) {
                    count++;
                }
            }

            List<SeatState> currRow = seats.get(row);
            if (col - 1 >= 0 && currRow.get(col - 1).equals(SeatState.OCCUPIED)) {
                count++;
            }
            if (col + 1 < currRow.size() && currRow.get(col + 1).equals(SeatState.OCCUPIED)) {
                count++;
            }

            if (row < seats.size() - 1) {
                List<SeatState> downRow = seats.get(row + 1);
                if (col - 1 >= 0 && downRow.get(col - 1).equals(SeatState.OCCUPIED)) {
                    count++;
                }
                if (downRow.get(col).equals(SeatState.OCCUPIED)) {
                    count++;
                }
                if (col + 1 < downRow.size() && downRow.get(col + 1).equals(SeatState.OCCUPIED)) {
                    count++;
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
                    buffer.append(seats.get(row).get(col).equals(SeatState.FLOOR) ? "." : getNumOccupiedNeighbours(row, col));
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
                        int numOccupied = oldSeatMap.getNumOccupiedNeighbours(row, col);

                        if (oldRowState.get(col).equals(SeatState.EMPTY) && numOccupied == 0) {
                            state = SeatState.OCCUPIED;
                        } else if (oldRowState.get(col).equals(SeatState.OCCUPIED) && numOccupied >= 4) {
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

        while (!updatedSeatMap.toString().equals(seatMap.toString())) {
            seatMap = updatedSeatMap;
            updatedSeatMap = SeatMap.update(seatMap);
        }

        System.out.println(updatedSeatMap);
        System.out.println(updatedSeatMap.getNumSeatsWithState(SeatMap.SeatState.OCCUPIED));
    }
}
