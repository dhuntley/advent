package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day14B {

    public static List<Long> applyMask(long value, String mask) {

        Deque<String> maskedNumbers = new ArrayDeque<>();
        List<Long> result = new ArrayList<>();

        char[] binary = StringUtils.leftPad(Long.toBinaryString(value), 36, "0").toCharArray();
        for (int i = 0; i < binary.length; i++) {
            if (mask.charAt(i) != '0') {
                binary[i] = mask.charAt(i);
            }
        }
        maskedNumbers.push(new String(binary));

        while (!maskedNumbers.isEmpty()) {
            String binString = maskedNumbers.pop();
            if (binString.contains("X")) {
                int index = binString.indexOf("X");
                binary = binString.toCharArray();

                binary[index] = '0';
                maskedNumbers.push(new String(binary));
                binary[index] = '1';
                maskedNumbers.push(new String(binary));

            } else {
                result.add(Long.parseLong(binString, 2));
            }
        }

        return result;
    }

    public static void main(String[] args) {

        List<String> input = Utilities.readLineList();
        String mask = null;
        Map<Long, Long> memory = new HashMap<>();

        for (String inputLine : input) {
            String[] tokens = inputLine.split(" ");
            if (tokens[0].equals("mask")) {
                mask = tokens[2];
            } else {
                for (Long address : applyMask(Long.parseLong(StringUtils.getDigits(tokens[0])), mask)) {
                    memory.put(address, Long.parseLong(tokens[2]));
                }
            }
        }

        System.out.println(memory.values().stream().mapToLong(a -> a).sum());
    }
}
