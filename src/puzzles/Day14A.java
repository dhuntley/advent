package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14A {

    public static long applyMask(long value, String mask) {
        char[] binary = StringUtils.leftPad(Long.toBinaryString(value), 36, "0").toCharArray();

        for (int i = 0; i < binary.length; i++) {
            if (mask.charAt(i) != 'X') {
                binary[i] = mask.charAt(i);
            }
        }

        return Long.parseLong(new String(binary), 2);
    }

    public static void main(String[] args) {

        List<String> input = Utilities.readLineList();
        String mask = null;
        Map<String, Long> memory = new HashMap<>();

        for (String inputLine : input) {
            String[] tokens = inputLine.split(" ");
            if (tokens[0].equals("mask")) {
                mask = tokens[2];
            } else {
                memory.put(StringUtils.getDigits(tokens[0]), applyMask(Long.parseLong(tokens[2]), mask));
            }
        }

        System.out.println(memory.values().stream().mapToLong(a -> a).sum());
    }
}
