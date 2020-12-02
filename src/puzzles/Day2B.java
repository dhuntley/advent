package puzzles;

import util.Utilities;

import java.util.stream.Stream;

public class Day2B {
    private static class Password {
        private int firstIndex;
        private int secondIndex;
        private char letter;
        private String passcode;

        public Password(String line) {
            String[] tokens = line.split(" ");

            String[] rangeTokens = tokens[0].split("-");
            firstIndex = Integer.parseInt(rangeTokens[0]);
            secondIndex = Integer.parseInt(rangeTokens[1]);

            letter = tokens[1].charAt(0);

            passcode = tokens[2];
        }

        public boolean isValid() {
            return (passcode.charAt(firstIndex - 1) == letter) ^ (passcode.charAt(secondIndex - 1) == letter);
        }
    }

    public static void main(String[] args) {
        Stream<String> lines = Utilities.readLineStream();
        long validCount = lines.map(line -> new Password(line)).filter(password -> password.isValid()).count();
        System.out.println(validCount);
    }
}
