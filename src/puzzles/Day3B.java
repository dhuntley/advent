package puzzles;

import util.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3B {
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

    private static class Slope {
        public int down;
        public int right;

        public Slope(int down, int right) {
            this.down = down;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        HillMap hillMap = new HillMap();
        Stream<String> lines = Utilities.readLineStream();
        lines.forEach(line -> hillMap.addLine(line));

        Slope[] slopes = {
                new Slope(1,1),
                new Slope(1,3),
                new Slope(1,5),
                new Slope(1,7),
                new Slope(2,1)
        };

        long treeProduct = 1;
        for (Slope slope : slopes) {
            int numTrees = 0;
            for (int row = 0; row < hillMap.getNumRows(); row = row + slope.down) {
                if (hillMap.hasTreeAtCoordinates(row, (slope.right * row) / slope.down)) {
                    numTrees++;
                }
            }

            treeProduct = treeProduct * numTrees;
        }

        System.out.println(treeProduct);
    }
}
