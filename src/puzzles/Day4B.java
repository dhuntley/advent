package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4B {
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

            int byr = getNumericValue("byr");
            int iyr = getNumericValue("iyr");
            int eyr = getNumericValue("eyr");

            int hgt = getNumericValue("hgt");
            String hgtRaw = fields.get("hgt");
            String hcl = fields.get("hcl");
            String ecl = fields.get("ecl");
            String pid = fields.get("pid");

            isValid = isValid && byr >= 1920 && byr <= 2002;
            isValid = isValid && iyr >= 2010 && iyr <= 2020;
            isValid = isValid && eyr >= 2020 && eyr <= 2030;

            if (hgtRaw != null && hgtRaw.endsWith("cm")) {
                isValid = isValid && hgt >= 150 && hgt <= 193;
            } else if (hgtRaw != null && hgtRaw.endsWith("in")) {
                isValid = isValid && hgt >= 59 && hgt <= 76;
            } else {
                isValid = false;
            }

            isValid = isValid && hcl != null && hcl.length() == 7 && hcl.charAt(0) == '#' && StringUtils.isAlphanumeric(hcl.substring(1, 7));

            isValid = isValid && Arrays.asList(VALID_EYE_COLORS).stream().anyMatch(color -> color.equals(ecl));

            isValid = isValid && StringUtils.isNumeric(pid) && pid.length() == 9;

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
