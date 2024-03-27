package chip8.instruction;

import chip8.Stack;

public class Return implements Runnable {
    private final Stack stack;

    public Return(Stack stack) {

        this.stack = stack;
    }

    @Override
    public void run() {
        stack.popProgramCounter();
    }
}
