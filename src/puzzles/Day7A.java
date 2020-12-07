package puzzles;

import util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day7A {

    private static class BagList {
        Map<String, Long> bags = new HashMap<>();

        public void addBags(String descriptor, Long quantity) {
            if (bags.containsKey(descriptor)) {
                bags.put(descriptor, bags.get(descriptor) + quantity);
            } else {
                bags.put(descriptor, quantity);
            }
        }

        public Set<String> getBagDescriptors() {
            return bags.keySet();
        }

        public void addAll(BagList other) {
            for (String descriptor : other.getBagDescriptors()) {
                addBags(descriptor, other.bags.get(descriptor));
            }
        }

        @Override
        public String toString() {
            return "BagList{" +
                    "bags=" + bags +
                    '}';
        }
    }

    private static class BagRule {
        public String containerDescriptor;

        public BagList bagContents = new BagList();

        public BagRule(String input) {
            String[] tokens = input.split(" ");

            containerDescriptor = tokens[0] + " " + tokens[1];

            int index = 4;

            if ("no".equals(tokens[index])) {
                return;
            }

            while (index < tokens.length) {
                bagContents.addBags(tokens[index + 1] + " " + tokens[index + 2], Long.parseLong(tokens[index]));
                index += 4;
            }
        }
    }

    private static class BagRuleManager {
       private Map<String, BagRule> bagRuleMap = new HashMap<>();

        public void addRule(BagRule bagRule) {
            bagRuleMap.put(bagRule.containerDescriptor, bagRule);
        }

        public Set<String> getBagDescriptors() {
            return bagRuleMap.keySet();
        }

        public void expandBagRecursively(String descriptor, long numContainers, BagList bagList) {
            BagRule containerRule = bagRuleMap.get(descriptor);

            for (int i=0; i<numContainers; i++) {
                bagList.addAll(containerRule.bagContents);
            }

            if (containerRule != null) {
                for (String contentDescriptor : containerRule.bagContents.getBagDescriptors()) {
                    long numBags = containerRule.bagContents.bags.get(contentDescriptor);
                    expandBagRecursively(contentDescriptor, numBags, bagList);
                }
            }
        }
    }

    public static void main(String[] args) {
        BagRuleManager bagRuleManager = new BagRuleManager();
        List<String> lines = Utilities.readLineList();

        lines.forEach(line -> bagRuleManager.addRule(new BagRule(line)));

        BagList possibleContainers = new BagList();

        for (String containerDescriptor : bagRuleManager.getBagDescriptors()) {
            System.out.println("Expanding " + containerDescriptor);
            BagList bagList = new BagList();
            bagRuleManager.expandBagRecursively(containerDescriptor, 1, bagList);

            if (bagList.getBagDescriptors().contains("shiny gold")) {
                possibleContainers.addBags(containerDescriptor, 1l);
            }
        }

        System.out.println(possibleContainers);
        System.out.println(possibleContainers.bags.size());
    }
}
