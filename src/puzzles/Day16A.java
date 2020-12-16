package puzzles;

import org.apache.commons.lang3.Range;
import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day16A {

    private static class Field {
        public Range<Integer> range1;
        public Range<Integer> range2;
        public String descriptor;

        public Field(String inputLine) {
            String[] tokens = inputLine.split(" ");

            descriptor = tokens[0].substring(0, tokens[0].length() - 1);

            range1 = Range.between(Integer.parseInt(tokens[1].split("-")[0]),
                    Integer.parseInt(tokens[1].split("-")[1]));
            range2 = Range.between(Integer.parseInt(tokens[3].split("-")[0]),
                    Integer.parseInt(tokens[3].split("-")[1]));
        }

        public boolean isValueValid(int value) {
            return range1.contains(value) || range2.contains(value);
        }
    }

    private static class Ticket {
        public List<Integer> values = new ArrayList<>();

        public Ticket(String inputLine) {
            String[] tokens = inputLine.split(",");
            for (String token : tokens) {
                values.add(Integer.parseInt(token));
            }
        }
    }

    public static void main(String[] args) {
        List<String> inputLines = Utilities.readLineList();

        int cursor = 0;

        // Parse rules
        List<Field> fields = new ArrayList<>();
        while (!inputLines.get(cursor).isEmpty()) {
            fields.add(new Field(inputLines.get(cursor)));
            cursor++;
        }

        // Parse My Ticket
        cursor += 2;
        Ticket myTicket = new Ticket(inputLines.get(cursor));

        // Parse Other Tickets
        List<Ticket> otherTickets = new ArrayList<>();
        cursor += 3;
        while (cursor < inputLines.size()) {
            otherTickets.add(new Ticket(inputLines.get(cursor)));
            cursor++;
        }

        int scanningErrorRate = 0;
        List<Integer> invalids = new ArrayList<>();

        for (Ticket ticket : otherTickets) {
            for (Integer value : ticket.values) {
                boolean isValid = fields.stream().anyMatch(field -> field.isValueValid(value));
                if (!isValid) {
                    invalids.add(value);
                    scanningErrorRate += value;
                }
            }
        }

        System.out.println(invalids);
        System.out.println(scanningErrorRate);
    }
}
