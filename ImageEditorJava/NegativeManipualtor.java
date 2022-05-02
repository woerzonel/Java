/**
 * Name: Gabe Woerishofer
 * Date: 2/15/2016
 * Lab: 9&10
 * Class: SE 1021 032
 */
public class NegativeManipualtor implements PixelManipulator{

    /**]
     * Takes the negative version of a pciture bye taking the netative of every pixel
     * @param rgb The RGB value of the pixel
     * @param row The row of the pixel
     * @param col THe column of the pixel
     * @return Returns the new RGB picture
     */
    public int transformPixel(int rgb, int row, int col){
        Pixel negativePixel = new Pixel(rgb);
        negativePixel.setRed(255-negativePixel.getRed());
        negativePixel.setGreen(255-negativePixel.getGreen());
        negativePixel.setBlue(255-negativePixel.getBlue());


        return negativePixel.getSRGB();
    }
}
