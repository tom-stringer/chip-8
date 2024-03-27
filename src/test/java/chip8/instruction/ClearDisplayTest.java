package chip8.instruction;

import chip8.ProgramCounter;
import chip8.display.DisplayModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ClearDisplayTest
{
    @Test
    void givenDisplayModel_whenClearDisplay_thenDisplayIsCleared()
    {
        DisplayModel displayModel = new DisplayModel();
        displayModel.pixels()[0][0] = true;

        new ClearDisplay(displayModel).run();

        assertThat(displayModel.pixels()).isEqualTo(new boolean[64][32]);
    }
}