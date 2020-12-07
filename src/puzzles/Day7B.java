package puzzles;

import util.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day7B {

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

        public Long getNumBags() {
            long sum = 0;
            for (Long count : bags.values()) {
                sum += count;
            }
            return sum;
        }

        public void addAll(BagList other, long numTimes) {
            for (String descriptor : other.getBagDescriptors()) {
                addBags(descriptor, numTimes * other.bags.get(descriptor));
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

        public void expandBagRecursively(String descriptor, long numContainers, BagList bagList) {
            BagRule containerRule = bagRuleMap.get(descriptor);

            bagList.addAll(containerRule.bagContents, numContainers);

            if (containerRule != null) {
                for (String contentDescriptor : containerRule.bagContents.getBagDescriptors()) {
                    long numBags = containerRule.bagContents.bags.get(contentDescriptor);
                    expandBagRecursively(contentDescriptor, numBags * numContainers, bagList);
                }
            }
        }
    }

    public static void main(String[] args) {
        BagRuleManager bagRuleManager = new BagRuleManager();
        List<String> lines = Utilities.readLineList();

        lines.forEach(line -> bagRuleManager.addRule(new BagRule(line)));

        BagList bagList = new BagList();
        bagRuleManager.expandBagRecursively("shiny gold", 1, bagList);

        System.out.println(bagList);
        System.out.println(bagList.getNumBags());
    }
}
