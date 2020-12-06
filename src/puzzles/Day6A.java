package puzzles;

import org.apache.commons.lang3.Range;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day6A {
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

        public int getNumCombinedAffirmatives() {
            Set<Character> combinedAffirmatives = new HashSet<>();
            for (CustomsDeclaration customsDeclaration : customsDeclarations) {
                combinedAffirmatives.addAll(customsDeclaration.affirmatives);
            };

            return combinedAffirmatives.size();
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

        long combinedAffirmativeCount = 0;

        for (TravelParty travelParty : travelParties) {
            combinedAffirmativeCount += travelParty.getNumCombinedAffirmatives();
        }

        System.out.println("Total Affirmatives: " + combinedAffirmativeCount);
    }
}
