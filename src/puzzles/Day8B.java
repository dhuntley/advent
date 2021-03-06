package puzzles;

import util.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8B {

    private static class Program {

        private long accumulator = 0;

        private int cursor = 0;

        private List<Instruction> instructions = new ArrayList<>();

        private Set<Integer> cursorHistory = new HashSet<>();

        private abstract class Instruction {
            protected int arg;

            public abstract void execute();
        }

        private class Acc extends Instruction {
            @Override
            public void execute() {
                accumulator += arg;
                cursor += 1;
            }
        }

        private class Jmp extends Instruction {
            @Override
            public void execute() {
                cursor += arg;
            }
        }

        private class Nop extends Instruction {
            @Override
            public void execute() {
                cursor += 1;
            }
        }

        public void appendInstruction(String inputLine) {
            String[] tokens = inputLine.split(" ");
            int arg = Integer.parseInt(tokens[1]);

            Instruction currentInstruction;

            switch(tokens[0]) {
                case "nop":
                    currentInstruction = new Nop();
                    break;
                case "acc":
                    currentInstruction = new Acc();
                    break;
                case "jmp":
                    currentInstruction = new Jmp();
                    break;
                default:
                    currentInstruction = null;
            }

            currentInstruction.arg = arg;
            instructions.add(currentInstruction);
        }

        public Long executeUntilRepeat(int modifyCursor) {
            accumulator = 0;
            cursor = 0;
            cursorHistory.clear();

            while (!cursorHistory.contains(cursor) && cursor < instructions.size()) {
                cursorHistory.add(cursor);

                Instruction currentInstruction = instructions.get(cursor);

                if (cursor == modifyCursor) {
                    int tempArg = currentInstruction.arg;
                    if (currentInstruction instanceof Nop) {
                        currentInstruction = new Jmp();
                        currentInstruction.arg = tempArg;
                    } else if (currentInstruction instanceof Jmp) {
                        currentInstruction = new Nop();
                        currentInstruction.arg = tempArg;
                    }
                }

                currentInstruction.execute();
            }

            if (cursor == instructions.size()) {
                return accumulator;
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {

        List<String> inputLines = Utilities.readLineList();
        Program program = new Program();

        for (String inputLine : inputLines) {
            program.appendInstruction(inputLine);
        }

        for (int i=0; i < inputLines.size(); i++) {
            Long result = program.executeUntilRepeat(i);
            if (result != null) {
                System.out.println(result);
            }
        }
    }
}
