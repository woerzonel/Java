/**
 * Created by woerishofergw on 2/19/2016.
 */
public class ReadOnlyManipulator implements PixelManipulator {

    /**
     * Turns the Image into a red form by changing the pixels
     * @param rgb The initial RGB int of the pixel
     * @param row The row the RGB is in
     * @param col The Column the RGB is in
     * @return The new RGB value
     */
    public int transformPixel(int rgb, int row, int col){
        Pixel redPixel = new Pixel(rgb);
        redPixel.setBlue(0);
        redPixel.setGreen(0);

        return redPixel.getSRGB();
    }
}
