package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utilities {
    public static Stream<String> readLineStream() {
        Stream<String> lines = null;

        try {
            lines = Files.lines(Paths.get("input/input.txt"));
        }
        catch (IOException e) {
            System.err.println("Error reading from file.");
            System.exit(1);
        }

        return lines;
    }

    public static List<String> readLineList() {
        return readLineStream().collect(Collectors.toList());
    }

    public static Stream<Long> readLongStream() {
        return readLineStream().filter(line -> line != null && !"".equals(line)).map(line -> new Long(line));
    }

    public static List<Long> readLongList() {
        return readLongStream().collect(Collectors.toList());
    }

    public static Stream<Integer> readIntegerStream() {
        return readLineStream().filter(line -> line != null && !"".equals(line)).map(line -> new Integer(line));
    }

    public static List<Integer> readIntegerList() {
        return readIntegerStream().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        readIntegerStream().forEach(num -> {
            System.out.println(num);
        });
    }
}
