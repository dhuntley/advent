package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4A {
    private static class Passport {
        private Map<String, String> fields = new HashMap<>();

        private static final String[] MANDATORY_FIELDS = {
                "byr",
                "iyr",
                "eyr",
                "hgt",
                "hcl",
                "ecl",
                "pid"
        };

        private static final String[] VALID_EYE_COLORS = {
                "amb",
                "blu",
                "brn",
                "gry",
                "grn",
                "hzl",
                "oth"
        };

        public void addLine(String line) {
            String[] tokens = line.split(" ");
            for (String token : tokens) {
                String[] fieldTokens = token.split(":");
                addField(fieldTokens[0], fieldTokens[1]);
            }
        }

        public void addField(String fieldName, String value) {
            fields.put(fieldName, value);
        }

        public int getNumericValue(String fieldName) {
            String value = fields.get(fieldName);
            value = StringUtils.getDigits(value);
            return value != null ? Integer.parseInt(value) : -1;
        }

        public boolean isValid() {
            boolean isValid = true;

            isValid = isValid && fields.keySet().containsAll(Arrays.asList(MANDATORY_FIELDS));

            return isValid;
        }
    }

    public static void main(String[] args) {
        List<Passport> passports = new ArrayList<>();
        List<String> lines = Utilities.readLineList();

        Passport currentPassport = null;
        for (String line : lines) {
            if (!line.isEmpty()) {
                if (currentPassport == null) {
                    currentPassport = new Passport();
                }
                currentPassport.addLine(line);
            } else {
                if (currentPassport != null) {
                    passports.add(currentPassport);
                    currentPassport = null;
                }
            }
        }

        System.out.println(passports.stream().filter(passport -> passport.isValid()).count());
    }
}
