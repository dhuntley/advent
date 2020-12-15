package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13B {

    public static int getValidSequenceLength(long t, List<Integer> buses, Map<Integer, Integer> busOffsets) {
        for (int i = 0; i < buses.size(); i++) {
            int bus = buses.get(i);
            if ((t + busOffsets.get(bus)) % bus != 0) {
                return i;
            }
        }
        return buses.size();
    }

    public static void main(String[] args) {

        List<String> input = Utilities.readLineList();
        List<Integer> buses = new ArrayList<>();
        String[] tokens = input.get(1).split(",");

        Map<Integer, Integer> busOffsets = new HashMap<>();

        int offset = 0;

        for (String token : tokens) {
            if (StringUtils.isNumeric(token)) {
                int busId = Integer.parseInt(token);
                buses.add(busId);
                busOffsets.put(busId, offset);
            }
            offset++;
        }

        long t = 0;
        long tDelta = buses.get(0);

        int maxValid = 1;

        while (true) {
            int numValid = getValidSequenceLength(t, buses, busOffsets);

            if (numValid == buses.size()) {
                System.out.println("VALID T: " + t);
                break;
            } else if (numValid > maxValid) {
                for (int i = maxValid; i < numValid; i++) {
                    tDelta = tDelta * buses.get(i);
                }
                maxValid = numValid;
            }

            t = t + tDelta;
        }
    }
}
