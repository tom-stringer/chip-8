package chip8.display;

public class DisplayPresenter
{
    private final DisplayModel displayModel;
    private final DisplayView displayView;

    public DisplayPresenter(DisplayModel displayModel, DisplayView displayView) {
        this.displayModel = displayModel;
        this.displayView = displayView;
    }

    public void refresh()
    {
        displayView.refresh(displayModel.pixels());
    }
}
