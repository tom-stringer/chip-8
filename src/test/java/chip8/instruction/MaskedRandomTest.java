package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaskedRandomTest
{
    @Mock
    private Random random;

    @Test
    void givenVariableRegisters_whenMaskedRandom_withZeroMask_thenVariableRegisterSetToMaskedRandom()
    {
        byte expected = 0x24;

        doAnswer(invocation -> {
            byte[] argument = invocation.getArgument(0, byte[].class);
            argument[0] = expected;

            return null;
        }).when(random).nextBytes(any());
        VariableRegisters variableRegisters = new VariableRegisters();

        new MaskedRandom(variableRegisters, random, 4, 0x0).run();

        int actual = variableRegisters.get(4).get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenVariableRegisters_whenMaskedRandom_withMask_thenVariableRegisterSetToMaskedRandom()
    {
        byte expected = 0x24;

        doAnswer(invocation -> {
            byte[] argument = invocation.getArgument(0, byte[].class);
            argument[0] = expected;

            return null;
        }).when(random).nextBytes(any());
        VariableRegisters variableRegisters = new VariableRegisters();

        new MaskedRandom(variableRegisters, random, 4, 0x0F).run();

        int actual = variableRegisters.get(4).get();
        assertThat(actual).isEqualTo(0x4);
    }
}