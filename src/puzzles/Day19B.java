package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19B {

    private interface MessageRule {
        boolean validateMessage(String message);

        boolean validatePrefix(String message);

        List<String> trimPrefix(String message);
    }

    private static class LiteralRule implements MessageRule {
        private String literal;

        public LiteralRule(String literal) {
            this.literal = literal;
        }

        @Override
        public boolean validateMessage(String message) {
            return message.equals(literal);
        }

        @Override
        public boolean validatePrefix(String message) {
            return StringUtils.startsWith(message, literal);
        }

        @Override
        public List<String> trimPrefix(String message) {
            return Arrays.asList(message.replaceFirst(literal, ""));
        }
    }

    private static class CompoundRule implements MessageRule {

        MessageRuleManager messageRuleManager;

        List<String> ruleIds;

        public CompoundRule(MessageRuleManager messageRuleManager, List<String> ruleIds) {
            this.messageRuleManager = messageRuleManager;
            this.ruleIds = ruleIds;
        }

        private boolean validateMessage(String message, int ruleIndex, boolean prefixValidation) {
            if (ruleIndex >= ruleIds.size()) {
                if (prefixValidation) {
                    return true;
                } else {
                    return message.isEmpty();
                }
            }

            MessageRule rule = messageRuleManager.getRule(ruleIds.get(ruleIndex));
            if (!rule.validatePrefix(message)) {
                return false;
            } else {
                List<String> tempMessages = rule.trimPrefix(message);
                for (String tempMessage : tempMessages) {
                    if (validateMessage(tempMessage, ruleIndex + 1, prefixValidation)) {
                        return true;
                    }
                }
                return false;
            }
        }

        @Override
        public boolean validateMessage(String message) {
            return validateMessage(message, 0, false);
        }

        @Override
        public boolean validatePrefix(String message) {
            return validateMessage(message, 0, true);
        }

        private List<String> trimPrefix(String message, int ruleIndex) {
            if (ruleIndex >= ruleIds.size()) {
                return Arrays.asList(message);
            }

            MessageRule rule = messageRuleManager.getRule(ruleIds.get(ruleIndex));
            List<String> tempMessages = rule.trimPrefix(message);
            List<String> trimmedStrings = new ArrayList<>();
            for (String tempMessage : tempMessages) {
                trimmedStrings.addAll(trimPrefix(tempMessage, ruleIndex + 1));
            }
            return trimmedStrings;
        }

        @Override
        public List<String> trimPrefix(String message) {
            return trimPrefix(message, 0);
        }
    }

    private static class OptionalRule implements MessageRule {
        CompoundRule lhs;
        CompoundRule rhs;

        public OptionalRule(CompoundRule lhs, CompoundRule rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public boolean validateMessage(String message) {
            return lhs.validateMessage(message) || rhs.validateMessage(message);
        }

        @Override
        public boolean validatePrefix(String message) {
            return lhs.validatePrefix(message) || rhs.validatePrefix(message);
        }

        @Override
        public List<String> trimPrefix(String message) {
            List<String> trimmedMessages = new ArrayList<>();
            if (lhs.validatePrefix(message)) {
                trimmedMessages.addAll(lhs.trimPrefix(message));
            }
            if (rhs.validatePrefix(message)) {
                trimmedMessages.addAll(rhs.trimPrefix(message));
            }
            return trimmedMessages;
        }
    }

    private static class MessageRuleManager {

        private Map<String, MessageRule> rules = new HashMap<>();

        public void addRule(String inputLine) {
            List<String> tokens = Arrays.asList(inputLine.split(" "));

            String ruleId = tokens.get(0).replace(":", "");

            MessageRule messageRule;
            if (tokens.get(1).contains("\"")) {
                messageRule = new LiteralRule(tokens.get(1).replaceAll("\"", ""));
            } else if (tokens.size() >= 4 && tokens.contains("|")) {
                List<String> lhsRuleIds = tokens.subList(1, tokens.indexOf("|"));
                List<String> rhsRuleIds = tokens.subList(tokens.indexOf("|") + 1, tokens.size());

                CompoundRule lhs = new CompoundRule(this, lhsRuleIds);
                CompoundRule rhs = new CompoundRule(this, rhsRuleIds);

                messageRule = new OptionalRule(lhs, rhs);
            } else {
                messageRule = new CompoundRule(this, tokens.subList(1, tokens.size()));
            }

            rules.put(ruleId, messageRule);
        }

        public MessageRule getRule(String ruleId) {
            return rules.get(ruleId);
        }

        public boolean validateMessage(String message) {
            return rules.get("0").validateMessage(message);
        }
    }

    public static void main(String[] args) {
        List<String> inputLines = Utilities.readLineList();
        MessageRuleManager ruleManager = new MessageRuleManager();

        int index = 0;
        while (!StringUtils.isEmpty(inputLines.get(index))) {
            ruleManager.addRule(inputLines.get(index));
            index++;
        }

        index ++;

        int validCount = 0;
        while (index < inputLines.size()) {
            String message = inputLines.get(index);
            if (ruleManager.validateMessage(message)) {
                validCount++;
            }
            System.out.println(index);
            index++;
        }

        System.out.println(validCount);
    }
}
