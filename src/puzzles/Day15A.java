package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15A {

    public static void main(String[] args) {

        List<Integer> numbers = Utilities.readIntegerList();

        while (numbers.size() < 2020) {
            Integer lastNumber = numbers.remove(numbers.size() - 1);
            int lastIndex = numbers.lastIndexOf(lastNumber);

            numbers.add(lastNumber);
            if (lastIndex == -1) {
                numbers.add(0);
            } else {
                numbers.add(numbers.size() - 1 - lastIndex);
            }
        }

        System.out.println(numbers.get(2019));
    }
}
