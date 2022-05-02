import java.awt.image.BufferedImage;

/**
 * Name: Gabe Woerishofer
 * Date: 2/15/2016
 * Lab: 9&10
 * Class: SE 1021 032
 */
public class ImageTransform {
    protected BufferedImage buffer;
    protected PixelManipulator transform;

    /**
     * Takes in and save the buffered Image and the manipulator
     * @param buffer The buffered Image
     * @param transform The manipulator
     */
   public ImageTransform(BufferedImage buffer, PixelManipulator transform) {
       this.buffer = buffer;
       this.transform = transform;
    }

    /**
     * Using the buffered image and manipulator goes through every pixel and edits them
     */
    public void transformImage(){


        for(int fileHeight = 0; fileHeight < buffer.getHeight(); fileHeight++){
            for(int fileWidth = 0; fileWidth < buffer.getWidth(); fileWidth++){
                buffer.setRGB(fileWidth,fileHeight,transform.transformPixel(buffer.getRGB(fileWidth,fileHeight),fileWidth,fileHeight));

            }
        }



    }
}
