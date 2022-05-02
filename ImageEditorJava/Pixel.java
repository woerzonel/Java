

import java.awt.Color;

/**
 * Represents a picture element, a.k.a., a pixel.
 * @author taylor
 * @version 20150128.1
 */
public class Pixel {
    /**
     * Red component of the pixel.
     */
    private int r;

    /**
     * Green component of the pixel.
     */
    private int g;

    /**
     * Blue component of the pixel.
     */
    private int b;

    /**
     * Alpha component of the pixel.  This specifies the
     * transparency of the pixel (0 - opaque, 255 - translucent).
     */
    private int alpha;

    /**
     * Constructs a new pixel with the specified color.
     * @param red  the red component of the pixel
     * @param green the green component of the pixel
     * @param blue the blue component of the pixel
     */
    public Pixel(int red, int green, int blue)
    {
        this(new Color(red & 0xFF, green & 0xFF, blue & 0xFF));
    }

    /**
     * Constructs a new pixel with the specified color.
     * @param rgb the color of the pixel in sRGB color format
     */
    public Pixel(int rgb) {
        alpha = (rgb >> 24) & 0xFF;
        r = (rgb >> 16) & 0xFF;
        g = (rgb >> 8) & 0xFF;
        b = rgb & 0xFF;
    }

    /**
     * Constructs a new pixel with the specified color.
     * @param color the color for the pixel
     */
    public Pixel(Color color) {
        alpha = color.getAlpha();
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }

    /**
     * Gets the red component of the pixel.
     * @return the red component of the pixel
     */
    public int getRed() {
        return r;
    }

    /**
     * Gets the green component of the pixel.
     * @return the green component of the pixel
     */
    public int getGreen() {
        return g;
    }

    /**
     * Gets the blue component of the pixel.
     * @return the blue component of the pixel
     */
    public int getBlue() {
        return b;
    }

    /**
     * Gets the alpha component of the pixel.
     * @return the alpha component of the pixel
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Gets the Color representation for the pixel.
     * @return the Color of the pixel
     */
    public Color getColor() {
        return new Color(r, g, b, alpha);
    }

    /**
     * Gets the color of the pixel in sRGB format.
     * @return The color in sRGB color format as an integer.
     */
    public int getSRGB() {
        return (alpha << 24) + (r << 16) + (g << 8) + b;
    }

    /**
     * Sets the red value of the pixel.
     * @param red the red component, must be in the range [0, 256).
     * @throws java.lang.IllegalArgumentException if parameter is out of range
     */
    public void setRed(int red) {
        rangeCheck(red);
        r = red;
    }

    /**
     * Sets the green value for the pixel.
     * @param green the green component, must be in the range [0, 256).
     * @throws java.lang.IllegalArgumentException if parameter is out of range
     */
    public void setGreen(int green) {
        rangeCheck(green);
        g = green;
    }

    /**
     * Sets the blue value for the pixel.
     * @param blue the blue component, must be in the range [0, 256).
     * @throws java.lang.IllegalArgumentException if parameter is out of range
     */
    public void setBlue(int blue) {
        rangeCheck(blue);
        b = blue;
    }

    /**
     * Sets the alpha for the pixel.
     * @param alpha The alpha component, must be in the range [0, 256).
     * @throws java.lang.IllegalArgumentException if parameter is out of range
     */
    public void setAlpha(int alpha) {
        rangeCheck(alpha);
        this.alpha = alpha;
    }

    /**
     * Ensures that the value is in the range [0, 256).
     * @param value The value to be checked
     * @throws java.lang.IllegalArgumentException if parameter is out of range
     */
    private void rangeCheck(int value) {
        if(value<0 || value>255) {
            throw new IllegalArgumentException("Invalid sRGB value: " + value + " must be in range [0, 256).");
        }
    }
}
