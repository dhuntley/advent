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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16B {

    private static class Field {
        public Range<Integer> range1;
        public Range<Integer> range2;
        public String descriptor;
        public Set<Integer> candidateIndices = new HashSet<>();

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

    private static boolean cull(int numValues, List<Field> fields) {
        for (int index = 0; index < numValues; index++) {
            List<Field> filteredFields = new ArrayList<>();
            for (Field field : fields) {
                if (field.candidateIndices.contains(index)) {
                    filteredFields.add(field);
                }
            }
            if (filteredFields.size() == 1) {
                Field field = filteredFields.get(0);
                if (field.candidateIndices.size() > 1) {
                    field.candidateIndices.clear();
                    field.candidateIndices.add(index);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean recursivelyAssignFields(List<Field> fields, Map<String, Integer> fieldIndexMap, int index) {
        if (index >= fields.size()) {
            return true;
        }

        for (Field field : fields) {
            if (!fieldIndexMap.containsKey(field.descriptor)) {
                if (field.candidateIndices.contains(index)) {
                    fieldIndexMap.put(field.descriptor, index);
                    if (recursivelyAssignFields(fields, fieldIndexMap, index + 1)) {
                        return true;
                    } else {
                        fieldIndexMap.remove(field.descriptor);
                    }
                }
            }
        }

        return false;
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

        // Filter out invalid tickets
        List<Ticket> validTickets = new ArrayList<>();
        for (Ticket ticket : otherTickets) {
            boolean isValid = true;
            for (Integer value : ticket.values) {
                isValid = isValid && fields.stream().anyMatch(field -> field.isValueValid(value));
            }
            if (isValid) {
                validTickets.add(ticket);
            }
        }

        for (int i=0; i<myTicket.values.size(); i++) {
            for (Field field : fields) {
                boolean isValid = true;
                for (Ticket ticket : validTickets) {
                    isValid = isValid && field.isValueValid(ticket.values.get(i));
                }
                if (isValid) {
                    field.candidateIndices.add(i);
                }
            }
        }

        // Cull uniques
        while (cull(myTicket.values.size(), fields)) {}

        Map<String, Integer> fieldIndexMap = new HashMap<>();
        if (recursivelyAssignFields(fields, fieldIndexMap, 0)) {
            System.out.println(fieldIndexMap);
        } else {
            System.out.println("NO SOLUTION");
        }

        long product = 1;
        for (String descriptor : fieldIndexMap.keySet()) {
            if (StringUtils.startsWith(descriptor, "departure")) {
                System.out.println(descriptor + ": " + fieldIndexMap.get(descriptor));
                product = product * myTicket.values.get(fieldIndexMap.get(descriptor));
            }
        }

        System.out.println(product);
    }
}
