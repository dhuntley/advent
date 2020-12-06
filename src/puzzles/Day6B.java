package puzzles;

import org.apache.commons.lang3.CharSetUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6B {
    private static class CustomsDeclaration {
        public Set<Character> affirmatives = new HashSet<>();

        public CustomsDeclaration(String input) {
            input.chars().forEach(affirmative -> affirmatives.add((char)affirmative));
        }
    }

    private static class TravelParty {
        List<CustomsDeclaration> customsDeclarations = new ArrayList<>();

        public void addCustomsDeclaration(CustomsDeclaration customsDeclaration) {
            customsDeclarations.add(customsDeclaration);
        }

        public long getUnanimousAffirmatives() {
            Set<Character> unanimousAffirmatives = new HashSet<>(customsDeclarations.get(0).affirmatives);

            for (CustomsDeclaration customsDeclaration : customsDeclarations) {
                unanimousAffirmatives.retainAll(customsDeclaration.affirmatives);
            };

            return unanimousAffirmatives.size();
        }
    }

    public static void main(String[] args) {
        List<TravelParty> travelParties = new ArrayList<>();
        List<String> lines = Utilities.readLineList();

        TravelParty currentParty = null;
        for (String line : lines) {
            if (!line.isEmpty()) {
                if (currentParty == null) {
                    currentParty = new TravelParty();
                }
                currentParty.addCustomsDeclaration(new CustomsDeclaration(line));
            } else {
                if (currentParty != null) {
                    travelParties.add(currentParty);
                    currentParty = null;
                }
            }
        }

        if (currentParty != null) {
            travelParties.add(currentParty);
        }

        long unanimousAffirmativeCount = 0;

        for (TravelParty travelParty : travelParties) {
            unanimousAffirmativeCount += travelParty.getUnanimousAffirmatives();
        }

        System.out.println("Total Unanimous Affirmatives: " + unanimousAffirmativeCount);
    }
}
