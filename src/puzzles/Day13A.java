package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13A {

    public static void main(String[] args) {

        List<String> input = Utilities.readLineList();

        int arrivalTime = Integer.parseInt(input.get(0));

        List<Integer> buses = new ArrayList<>();
        String[] tokens = input.get(1).split(",");

        for (String token : tokens) {
            if (StringUtils.isNumeric(token)) {
                buses.add(Integer.parseInt(token));
            }
        }

        Map<Integer, Integer> firstBusTimes = new HashMap<>();

        int bestBusTime = Integer.MAX_VALUE;
        int bestBusId = -1;

        for (Integer bus : buses) {
            int firstBusTime = ((arrivalTime / bus) * bus) + bus;
            firstBusTimes.put(bus, firstBusTime);

            if (firstBusTime < bestBusTime) {
                bestBusTime = firstBusTime;
                bestBusId = bus;
            }
        }

        System.out.println(bestBusId);
        System.out.println(bestBusId * (firstBusTimes.get(bestBusId) - arrivalTime));
    }
}
