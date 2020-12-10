package puzzles;

import util.Utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10B {

    public static void main(String[] args) {

        List<Integer> numbers = Utilities.readIntegerList();
        numbers.sort(Integer::compare);
        numbers.add(numbers.get(numbers.size() - 1) + 3);
        Collections.reverse(numbers);
        numbers.add(0);

        // Number of possible paths from the given joltage to the end of the chain
        Map<Integer, Long> pathCountMap = new HashMap<>();
        pathCountMap.put(numbers.get(0), 1l);

        for (int i=1; i<numbers.size(); i++) {
            int currentJoltage = numbers.get(i);
            long numPaths = 0;

            // Consider all the adapters we can go to from here
            int j = i - 1;
            while (j >= 0 && (numbers.get(j) - currentJoltage <= 3)) {
                numPaths += pathCountMap.get(numbers.get(j));
                j--;
            }

            pathCountMap.put(currentJoltage, numPaths);
        }

        System.out.println(numbers);
        System.out.println(pathCountMap);
    }
}
