package puzzles;

import util.Utilities;

import java.util.List;

public class Day9B {

    private static final int PREAMBLE_LENGTH = 25;

    public static void main(String[] args) {

        List<Long> numbers = Utilities.readLongList();

        int firstSummandIndex = 0;
        int cursor = PREAMBLE_LENGTH;

        while (cursor < numbers.size()) {
            boolean sumFound = false;

            for (int i=firstSummandIndex; i<cursor; i++) {
                for (int j=i+1; j<cursor; j++) {
                    if ((numbers.get(i) + numbers.get(j) == numbers.get(cursor))) {
                        sumFound = true;
                    }
                }
            }

            if (!sumFound) {
                break;
            }

            firstSummandIndex++;
            cursor++;
        }

        long invalidNumber = numbers.get(cursor);

        System.out.println(invalidNumber);

        firstSummandIndex = 0;

        while (firstSummandIndex < numbers.size()) {
            cursor = firstSummandIndex - 1;
            long sum = 0;

            while (sum < invalidNumber) {
                cursor++;
                sum += numbers.get(cursor);
            }

            if (sum == invalidNumber) {
                break;
            }

            firstSummandIndex++;
        }

        long minInRange = Long.MAX_VALUE;
        long maxInRange = Long.MIN_VALUE;

        for (int i=firstSummandIndex; i<=cursor; i++) {
            minInRange = Math.min(minInRange, numbers.get(i));
            maxInRange = Math.max(maxInRange, numbers.get(i));
        }

        System.out.println(minInRange + " + " + maxInRange);
        System.out.println(minInRange + maxInRange);
    }
}
