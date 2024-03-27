package chip8.display;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DisplayModelTest
{
    @Test
    void givenDisplayModel_whenPixels_thenSizeIs64By32()
    {
        DisplayModel displayModel = new DisplayModel();

        boolean[][] pixels = displayModel.pixels();

        assertThat(pixels).hasDimensions(64, 32);
    }

    @Test
    void givenDisplayModel_whenClear_thenAllPixelsAreOff()
    {
        DisplayModel displayModel = new DisplayModel();
        displayModel.pixels()[0][0] = true;

        displayModel.clear();

        assertThat(displayModel.pixels()).isEqualTo(new boolean[64][32]);
    }

    @Test
    void givenDisplayModel_whenTogglePixel_thenPixelIsOn()
    {
        DisplayModel displayModel = new DisplayModel();

        displayModel.togglePixel(10, 10);

        boolean[][] expected = new boolean[64][32];
        expected[10][10] = true;
        assertThat(displayModel.pixels()).isEqualTo(expected);
    }

    @Test
    void givenDisplayModelWithPixelOn_whenTogglePixel_thenPixelIsOff()
    {
        DisplayModel displayModel = new DisplayModel();
        displayModel.togglePixel(10, 10);

        displayModel.togglePixel(10, 10);

        assertThat(displayModel.pixels()).isEqualTo(new boolean[64][32]);
    }

    @Test
    void givenDisplayModelWithToggledPixel_whenGetPixel_thenPixelOn()
    {
        DisplayModel displayModel = new DisplayModel();
        displayModel.togglePixel(10, 10);

        boolean pixel = displayModel.pixel(10, 10);

        assertThat(pixel).isTrue();
    }

    @Test
    void givenDisplayModelWithToggledPixelTwice_whenGetPixel_thenPixelOff()
    {
        DisplayModel displayModel = new DisplayModel();
        displayModel.togglePixel(10, 10);
        displayModel.togglePixel(10, 10);

        boolean pixel = displayModel.pixel(10, 10);

        assertThat(pixel).isFalse();
    }
}