package puzzles;

import util.Utilities;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10A {

    public static void main(String[] args) {

        List<Integer> numbers = Utilities.readIntegerList();
        numbers.sort(Integer::compare);
        numbers.add(numbers.get(numbers.size() - 1) + 3);

        Map<Integer, Integer> hopCounts = new HashMap<>();

        int currentJoltage = 0;

        for (Integer joltage : numbers) {
            int hopSize = joltage - currentJoltage;
            if (hopSize <= 3) {
                if (!hopCounts.containsKey(hopSize)) {
                    hopCounts.put(hopSize, 0);
                }
                hopCounts.put(hopSize, hopCounts.get(hopSize) + 1);
                currentJoltage = joltage;
            }
        }

        System.out.println(hopCounts);
    }
}
