package chip8;

public class IndexRegister
{
    private int i;

    public int get()
    {
        return i;
    }

    public void set(int i)
    {
        this.i = i & 0xFFFF;
    }
}
