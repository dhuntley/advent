package puzzles;

import org.apache.commons.lang3.Range;
import util.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5B {
    private static class BoardingPass {
        public int row;
        public int seat;

        public BoardingPass(String line) {
            char[] rowInput = line.substring(0,7).toCharArray();
            char[] seatInput = line.substring(7,10).toCharArray();

            Range<Integer> rowRange = Range.between(0, 127);
            Range<Integer> seatRange = Range.between(0, 7);

            for (char letter : rowInput) {
                rowRange = narrowRange(rowRange, letter == 'F');
            }

            for (char letter : seatInput) {
                seatRange = narrowRange(seatRange, letter == 'L');
            }

            row = rowRange.getMinimum();
            seat = seatRange.getMinimum();
         }

        private Range<Integer> narrowRange(Range<Integer> initialRange, boolean keepFirstHalf) {
            int initialMin = initialRange.getMinimum();
            int initialMax = initialRange.getMaximum();
            int initialMedian = initialMin + (initialMax - initialMin) / 2;

            return keepFirstHalf ? Range.between(initialMin, initialMedian) : Range.between(initialMedian + 1, initialMax);
        }

        public int getSeatId() {
            return row * 8 + seat;
        }
    }

    public static void main(String[] args) {
        Stream<String> lines = Utilities.readLineStream();
        List<BoardingPass> boardingPasses = new ArrayList<>();

        lines.forEach(line -> boardingPasses.add(new BoardingPass(line)));

        int[] seatIds = boardingPasses.stream().mapToInt(pass -> pass.getSeatId()).sorted().toArray();

        for (int i=1; i<seatIds.length; i++) {
            if (seatIds[i] != seatIds[i-1] + 1) {
                System.out.println("My Seat: " + (seatIds[i-1] + 1));
            }
        }
    }
}
