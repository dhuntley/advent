package puzzles;

import util.Utilities;

import java.util.List;
import java.util.stream.Stream;

public class Day2A {
    private static class Password {
        private long lower;
        private long upper;
        private char letter;
        private String passcode;

        public Password(String line) {
            String[] tokens = line.split(" ");

            String[] rangeTokens = tokens[0].split("-");
            lower = Integer.parseInt(rangeTokens[0]);
            upper = Integer.parseInt(rangeTokens[1]);

            letter = tokens[1].charAt(0);

            passcode = tokens[2];
        }

        public boolean isValid() {
            long letterCount = passcode.chars().filter(a -> a == letter).count();
            return letterCount >= lower && letterCount <= upper;
        }
    }

    public static void main(String[] args) {
        Stream<String> lines = Utilities.readLineStream();
        long validCount = lines.map(line -> new Password(line)).filter(password -> password.isValid()).count();
        System.out.println(validCount);
    }
}
