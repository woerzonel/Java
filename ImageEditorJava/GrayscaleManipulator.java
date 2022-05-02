/**
 * Name: Gabe Woerishofer
 * Date: 2/15/2016
 * Lab: 9&10
 * Class: SE 1021 032
 */
public class GrayscaleManipulator implements PixelManipulator {

    /**
     * Takes the pixels given and gives them a grey tint. This is done by shifting the the pixel's RGB colors by set amounts.
     * @param rgb RGB of the pixel
     * @param row The row the pixel is from
     * @param col The column the pixel is from
     * @return The new RGB file
     */
    public int transformPixel(int rgb, int row, int col){
        //Take the pixel rgb int and set it to a pixel varialbe
        Pixel greyPixel = new Pixel(rgb);
        //Alter the color of the RGB
        int gray = ((int)(.216*greyPixel.getRed())+(int)(.7152*greyPixel.getGreen())+(int)(.0722*greyPixel.getBlue()));
        //Save the RGB values
        greyPixel.setRed(gray);
        greyPixel.setGreen(gray);
        greyPixel.setBlue(gray);
        //return the pixel RBG value
        return greyPixel.getSRGB();
    }
}
