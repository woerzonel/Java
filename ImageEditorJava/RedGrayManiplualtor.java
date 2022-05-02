/**
 * Created by woerishofergw on 2/19/2016.
 */
public class RedGrayManiplualtor implements PixelManipulator {
    private PixelManipulator gray = new GrayscaleManipulator();
    private PixelManipulator red = new ReadOnlyManipulator();

    /**
     * Alternates between Red and Grey pixels
     * @param rgb The RGB value of the pixel
     * @param row The row the value was found int
     * @param col The column the value was found
     * @return The new RGB value
     */
    public int transformPixel(int rgb, int row, int col){
        //Alternates between the pixels making 1 red scale and 1 gray scale.
        int redGray;

        if(col%2 == 0){
        redGray = gray.transformPixel(rgb,row,col);

        }else{
            redGray = red.transformPixel(rgb,row,col);
        }
        return redGray;
    }
}
