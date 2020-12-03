package puzzles;

import util.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3A {
    private static class HillMap {
        private List<List<Boolean>> trees = new ArrayList<>();

        public void addLine(String line) {
            trees.add(line.chars().mapToObj(letter -> letter != '.').collect(Collectors.toList()));
        }

        public boolean hasTreeAtCoordinates(int x, int y) {
            return trees.get(x).get(y % trees.get(x).size());
        }

        public int getNumRows() {
            return trees.size();
        }
    }

    public static void main(String[] args) {
        HillMap hillMap = new HillMap();
        Stream<String> lines = Utilities.readLineStream();

        lines.forEach(line -> hillMap.addLine(line));

        int numTrees = 0;
        for (int row = 0; row < hillMap.getNumRows(); row++) {
            if (hillMap.hasTreeAtCoordinates(row, 3 * row)) {
                numTrees++;
            }
        }

        System.out.println(numTrees);
    }
}
