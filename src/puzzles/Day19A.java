package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19A {

    private interface MessageRule {
        boolean validateMessage(String message);

        boolean validatePrefix(String message);

        String trimPrefix(String message);
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
        public String trimPrefix(String message) {
            return message.replaceFirst(literal, "");
        }
    }

    private static class CompoundRule implements MessageRule {

        MessageRuleManager messageRuleManager;

        List<String> ruleIds;

        public CompoundRule(MessageRuleManager messageRuleManager, List<String> ruleIds) {
            this.messageRuleManager = messageRuleManager;
            this.ruleIds = ruleIds;
        }

        @Override
        public boolean validateMessage(String message) {
            String tempMessage = message;
            for (String ruleId : ruleIds) {
                MessageRule rule = messageRuleManager.getRule(ruleId);
                if (!rule.validatePrefix(tempMessage)) {
                    return false;
                }
                tempMessage = rule.trimPrefix(tempMessage);
            }

            return StringUtils.isEmpty(tempMessage);
        }

        @Override
        public boolean validatePrefix(String message) {
            String tempMessage = message;
            for (String ruleId : ruleIds) {
                MessageRule rule = messageRuleManager.getRule(ruleId);
                if (!rule.validatePrefix(tempMessage)) {
                    return false;
                }
                tempMessage = rule.trimPrefix(tempMessage);
            }

            return true;
        }

        @Override
        public String trimPrefix(String message) {
            String tempMessage = message;
            for (String ruleId : ruleIds) {
                MessageRule rule = messageRuleManager.getRule(ruleId);
                tempMessage = rule.trimPrefix(tempMessage);
            }

            return tempMessage;
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
        public String trimPrefix(String message) {
            if (lhs.validatePrefix(message)) {
                return lhs.trimPrefix(message);
            } else {
                return rhs.trimPrefix(message);
            }
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
            index++;
        }

        System.out.println(validCount);
    }
}
