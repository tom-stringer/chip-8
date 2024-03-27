package chip8;

public class VariableRegister {

    private int v;

    public int get() {
        return v;
    }

    public void set(int k) {
        this.v = k & 0xFF;
    }

    public void add(int k) {
        set(v + k);
    }

    public void subtract(int k) {
        set(v - k);
    }
}
