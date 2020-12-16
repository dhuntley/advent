package puzzles;

import util.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15B {

    public static void main(String[] args) {
        List<Long> numbers = Utilities.readLongList();
        Map<Long, Long> lastIndexMap = new HashMap<>();

        for (int i=0; i<numbers.size(); i++) {
            lastIndexMap.put(numbers.get(i), new Long(i));
        }

        Long lastNumber = numbers.get(numbers.size() - 1);
        long index = numbers.size();

        while (index < 30000000) {
            Long lastIndexOfLastNumber = lastIndexMap.get(lastNumber);
            lastIndexMap.put(lastNumber, index - 1);

            long currNumber;

            if (lastIndexOfLastNumber != null) {
                currNumber = index - 1 - lastIndexOfLastNumber;
            } else {
                currNumber = 0;
            }

            lastNumber = currNumber;
            index++;
        }

        System.out.println(lastNumber);
    }
}
