package puzzles;

import org.apache.commons.lang3.StringUtils;
import util.Utilities;

import java.util.stream.Stream;

public class Day18B {

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
            System.out.println("EVALUATING MULT EXPRESSION");
            return lhs.evaluate() * rhs.evaluate();
        }
    }

    private static class AddExpression extends BinaryExpression {
        public AddExpression(Expression lhs, Expression rhs) {
            super(lhs, rhs);
        }

        @Override
        public long evaluate() {
            System.out.println("EVALUATING ADD EXPRESSION");
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
            System.out.println("EVALUATING LITERAL [" + value + "]");
            return value;
        }
    }

    private static Expression parseExpression(String input) {
        System.out.println("PASRSING EXPRESSION [" + input + "]");

        if (StringUtils.isNumeric(input)) {
            return new Literal(Long.parseLong(input));
        } else if (input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')') {
            // Check if we should strip outer parentheses
            int numOpenParenth = 1;
            boolean shouldStripOuterParenth = true;
            for (int i=1; i<input.length()-1; i++) {
                if (input.charAt(i) == '(') {
                    numOpenParenth++;
                } else if (input.charAt(i) == ')') {
                    numOpenParenth--;
                }
                if (numOpenParenth == 0) {
                    shouldStripOuterParenth = false;
                    break;
                }
            }

            if (shouldStripOuterParenth) {
                return parseExpression(input.substring(1, input.length() - 1));
            }
        }

        Expression lhs;
        Operator operator = null;
        Expression rhs;


        // Find the highest precedence operation not within ()s
        int operatorIndex = -1;
        int numOpenParenth = 0;
        for (int i=input.length() - 1; i >=0; i--) {
            if (input.charAt(i) == ')') {
                numOpenParenth++;
            } else if (input.charAt(i) == '(') {
                numOpenParenth--;
            }

            if (numOpenParenth == 0) {
                if (input.charAt(i) == '*') {
                    operatorIndex = i;
                    operator = Operator.MULT;
                } else if (input.charAt(i) == '+' && !Operator.MULT.equals(operator)) {
                    operatorIndex = i;
                    operator = Operator.ADD;
                }
            }
        }

        lhs = parseExpression(input.substring(0,operatorIndex));
        rhs = parseExpression(input.substring(operatorIndex+1));

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
