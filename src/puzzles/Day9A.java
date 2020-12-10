package puzzles;

import org.apache.commons.lang3.Range;
import util.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9A {

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

        System.out.println(numbers.get(cursor));
    }
}
