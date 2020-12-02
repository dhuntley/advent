package puzzles;

import util.Utilities;

import java.util.List;

public class Day1B {
    public static void main(String[] args) {
        List<Integer> numbers = Utilities.readIntegerList();

        numbers.forEach(a -> {
            numbers.forEach(b -> {
                numbers.forEach(c -> {
                    if (a + b + c == 2020) {
                        System.out.println(a * b * c);
                    }
                });
            });
        });
    }
}
