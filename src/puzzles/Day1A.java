package puzzles;

import util.Utilities;

import java.util.List;

public class Day1A {
    public static void main(String[] args) {
        List<Integer> numbers = Utilities.readIntegerList();

        numbers.forEach(a -> {
            numbers.forEach(b -> {
                if (a + b == 2020) {
                    System.out.println(a * b);
                }
            });
        });
    }
}
