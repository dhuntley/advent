package puzzles;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day17A {

    private static class CubeMap {
        private static final int INDEX_OFFSET = 40;
        private static final int WIDTH = 2 * INDEX_OFFSET;

        private boolean[][][] cubes = new boolean[WIDTH][WIDTH][WIDTH];

        public void advance() {
            boolean[][][] oldCubes = cubes;
            cubes = new boolean[WIDTH][WIDTH][WIDTH];

            for (int x=1; x < cubes.length - 1; x++) {
                for (int y=1; y < cubes[x].length - 1; y++) {
                    for (int z=1; z < cubes[x][y].length - 1; z++) {
                        // Count ON neighbours
                        int onCount = 0;
                        for (int x2=x-1; x2<=x+1; x2++) {
                            for (int y2=y-1; y2<=y+1; y2++) {
                                for (int z2=z-1; z2<=z+1; z2++) {
                                    onCount += oldCubes[x2][y2][z2] ? 1 : 0;
                                }
                            }
                        }

                        if (oldCubes[x][y][z]) {
                            onCount--;
                            if (onCount == 2 || onCount == 3) {
                                cubes[x][y][z] = true;
                            }
                        } else if (onCount == 3) {
                            cubes[x][y][z] = true;
                        }
                    }
                }
            }
        }

        public void turnOnCube(int x, int y, int z) {
            cubes[x + INDEX_OFFSET][y + INDEX_OFFSET][z + INDEX_OFFSET] = true;
        }

        public int getEnabledCount() {
            int count = 0;
            for (int x=0; x < cubes.length; x++) {
                for (int y = 0; y < cubes[x].length; y++) {
                    for (int z = 0; z < cubes[x][y].length; z++) {
                        count += cubes[x][y][z] ? 1 : 0;
                    }
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
        List<String> inputLines = Utilities.readLineList();
        CubeMap cubeMap = new CubeMap();

        int y = 0;
        for (String inputLine : inputLines) {
            int x = 0;
            for (char c : inputLine.toCharArray()) {
                if (c == '#') {
                    cubeMap.turnOnCube(x, y, 0);
                }
                x++;
            }
            y++;
        }

        System.out.println("INIT COUNT: " + cubeMap.getEnabledCount());

        for (int i=0; i<6; i++) {
            cubeMap.advance();
            System.out.println("EPOCH " + (i + 1) + ": " + cubeMap.getEnabledCount());
        }
    }
}
