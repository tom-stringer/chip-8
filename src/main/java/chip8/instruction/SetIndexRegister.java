package chip8.instruction;

import chip8.IndexRegister;

public class SetIndexRegister implements Runnable
{
    private final IndexRegister indexRegister;
    private final int i;

    public SetIndexRegister(IndexRegister indexRegister, int i)
    {
        this.indexRegister = indexRegister;
        this.i = i;
    }

    @Override
    public void run()
    {
        indexRegister.set(i);
    }
}
