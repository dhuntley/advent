package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.List;
import java.util.stream.Stream;

public class Day18A {

    private enum Operator {
        ADD, MULT;

        public static Operator parseOperator(char symbol) {
            switch(symbol) {
                case '+':
                    return ADD;
                case '*':
                    return MULT;
                default:
                    return null;
            }
        }
    }

    private interface Expression {
        long evaluate();
    }

    private abstract static class BinaryExpression implements Expression {
        protected Expression lhs;
        protected Expression rhs;

        public BinaryExpression(Expression lhs, Expression rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }
    }

    private static class MultExpression extends BinaryExpression {
        public MultExpression(Expression lhs, Expression rhs) {
            super(lhs, rhs);
        }

        @Override
        public long evaluate() {
            return lhs.evaluate() * rhs.evaluate();
        }
    }

    private static class AddExpression extends BinaryExpression {
        public AddExpression(Expression lhs, Expression rhs) {
            super(lhs, rhs);
        }

        @Override
        public long evaluate() {
            return lhs.evaluate() + rhs.evaluate();
        }
    }

    private static class Literal implements Expression {
        private long value;

        public Literal(long value) {
            this.value = value;
        }

        @Override
        public long evaluate() {
            return value;
        }
    }

    private static Expression parseExpression(String input) {
        //System.out.println("PASRSING EXPRESSION [" + input + "]");

        if (StringUtils.isNumeric(input)) {
            return new Literal(Long.parseLong(input));
        }

        Expression lhs;
        Operator operator;
        Expression rhs;

        if (input.charAt(input.length() - 1) == ')') {
            int index = input.length() - 1;

            int numOpenParenth = 1;
            while (numOpenParenth > 0) {
                index--;
                switch (input.charAt(index)) {
                    case ')':
                        numOpenParenth++;
                        break;
                    case '(':
                        numOpenParenth--;
                        break;
                }
            }

            if (index == 0) {
                return parseExpression(input.substring(1, input.length() - 1));
            }

            lhs = parseExpression(input.substring(0, index - 1));
            operator = Operator.parseOperator(input.charAt(index - 1));
            rhs = parseExpression(input.substring(index + 1, input.length() - 1));
        } else {
            // Assume single digit literals for simplicity
            lhs = parseExpression(input.substring(0, input.length() - 2));
            operator = Operator.parseOperator(input.charAt(input.length() - 2));
            rhs = new Literal(Long.parseLong(Character.toString(input.charAt(input.length() - 1))));
        }

        switch(operator) {
            case ADD:
                return new AddExpression(lhs, rhs);
            case MULT:
                return new MultExpression(lhs, rhs);
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Stream<String> inputLines = Utilities.readLineStream();
        System.out.println(inputLines.mapToLong(inputLine -> parseExpression(inputLine.replaceAll("\\s+", "")).evaluate()).sum());
    }
}
