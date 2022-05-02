import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Represents images that are to be stored in the
 * bitmap (.bmp) format.
 * <p></p>
 * This class is limited to
 * @author schilling, taylor, and FIX
 * @version FIX
 */
public class BMPPicture extends Picture {

    /**
     * The length of the header for BMP files that
     * this class is able to read.
     */
    public static final int HEADER_LENGTH = 54;

    /**
     * The length of the Device-Independent Binary
     * (DIB) header for BMP files that this class
     * is able to read.
     */
    public static final int DIB_HEADER_LENGTH = 40;

    @Override
    public void load(File file) throws IOException {
        // Open a File Input stream with try/using statement.
        try(FileInputStream fin = new FileInputStream(file)) {
            // Allocate a ByteBuffer to read the header into.
            ByteBuffer bBuffer = ByteBuffer.allocate(HEADER_LENGTH);

            // Set the byte order to Little Endian.
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            // Read in the file header, which is the first HEADER_LENGTH bytes of the bmp file.
            // Throw IOException if exactly HEADER_LENGTH bytes are not read.
            int bytesRead = fin.getChannel().read(bBuffer);
            if(bytesRead!=HEADER_LENGTH) {
                throw new IOException("Error reading file header: " + file.getName());
            }

            // Rewind the buffer read in.
            bBuffer.rewind();

            // 1) Ensure 'B' and 'M' are in the first two bytes of the file
            //    (indicating this is a BMP image).
            byte h1 = bBuffer.get();
            byte h2 = bBuffer.get();

            if((h1!='B') || (h2!='M')) {
                throw new IOException("Invalid header in file." + file.getName());
            }

            // 2) Read in the total file size as an integer.
            int fileSize = bBuffer.getInt();
            if(fileSize!=file.length()) {
                throw new IOException("Invalid file size.");
            }

            // 3) Read in 4 unused bytes as an integer and ignore them.
            bBuffer.getInt();

            // 4) Read in the offset where the pixel array starts.
            int pixelOffset = bBuffer.getInt();

            // Throw IOException if pixel offset is not exactly HEADER_LENGTH bytes.
            if(pixelOffset!=HEADER_LENGTH) {
                throw new IOException("Invalid header in file." + file.getName());
            }

            // 5) Read in the DIB header size.
            int dibHeaderSize = bBuffer.getInt();

            // Throw IOException if DIB header size is not exactly DIB_HEADER_LENGTH bytes.
            if(dibHeaderSize!=DIB_HEADER_LENGTH) {
                throw new IOException("Invalid header in file." + file.getName());
            }

            // 7) Read in the width and height.
            int width = bBuffer.getInt();
            int height = bBuffer.getInt();

            // 7) Instantiate a new BufferedImage of the given size
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Throw away the rest of the file header information, as we don't use it
            // and will not check it.

            // Each pixel is stored as three bytes.  One for each of R, G, and B.
            // 8) Calculate the length of each bmp line ( = 3 * image width).
            int lineLength = width*3;

            // The BMP format specifies that the number of bytes to represent a
            // line must be a multiple of four bytes.
            // 9) Ensure the line length is a multiple of 4 by adding additional
            //    bytes if needed.
            if(lineLength%4!=0) {
                lineLength += 4 - lineLength % 4;
            }

            // 10) Determine the data size of the image data
            //     (line length x height).
            int dataSize = lineLength * height;

            // 11) Allocate a new ByteBuffer array big enough to read all the data.
            bBuffer = ByteBuffer.allocate(dataSize);

            // 12) Set the byte order to Little Endian
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            // 13) Read image data from the file
            int readSize = fin.getChannel().read(bBuffer);

            // Throw IOException if the number of bytes read does not match
            // the number expected to be read.
            if(readSize!=dataSize) {
                throw new IOException("Error reading data in from the file.");
            }

            // Rewind the buffer read in.
            bBuffer.rewind();

            // TODO complete all of step 14
            // 14) Now read in the data for the image from the byte buffer
            //     and store it in the buffered image.
            //    a) Loop over all rows

            // b) Loop over each pixel in the row

            // i) Get blue, green, and then red bytes.




            // ii) Instantiate a new pixel with the RGB components we have.
            //     Note that even though they were read in as bytes, they
            //     will be cast upwards to integers in the constructor.

            // iii) Set the RGB value in the buffered image


            // c) Skip over any padding at the end of the line




        }
    }

    @Override
    public void store(File file) throws IOException {
        // Open a File Output stream.
        try (FileOutputStream fout = new FileOutputStream(file)) {

            // Each pixel is stored as three bytes.  One for each of R, G, and B.
            // 1) Calculate the length of each bmp line ( = 3 * image width).
            int lineLength = getWidth()*3;

            // The BMP format specifies that the number of bytes to represent a
            // line must be a multiple of four bytes.
            // 2) Ensure the line length is a multiple of 4 by adding additional
            //    bytes if needed.
            if(lineLength%4!=0) {
                lineLength += 4 - lineLength % 4;
            }

            // Determine the file size, which is HEADER_LENGTH bytes plus the
            // amount of data in the image.
            // 3) Calculate the file size as the size of the image data
            //     (line length x height) plus the header size.
            int fileSize = HEADER_LENGTH + (lineLength * getHieght());

            // Steps 4-16 are to configure the byte buffer with the correct data
            //  (to be written to the file in step 17).

            // 4) Allocate a new ByteBuffer array big enough to write the header info
            ByteBuffer bBuffer = ByteBuffer.allocate(HEADER_LENGTH);

            // 5) Set the byte order to Little Endian
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            // 6) Insert 'B' and 'M' as the first two bytes of the buffer
            //    (indicating this is a BMP image).
            bBuffer.put((byte) 'B');
            bBuffer.put((byte) 'M');

            // 7) Insert the file size (as an int)
            bBuffer.putInt(fileSize);

            // 8) Insert a 32 bit int with a value of 0. (The value is not used)
            bBuffer.putInt(0);

            // 9) Insert the offset where the pixel array starts (as an int).
            bBuffer.putInt(HEADER_LENGTH);

            // 10) Insert the size of the DIB header (as an int)
            bBuffer.putInt(DIB_HEADER_LENGTH);

            // 11) Insert the width and height of the image (as ints).
            bBuffer.putInt(getWidth());
            bBuffer.putInt(getHieght());

            // 12) Insert a 1 for 1 color plane (as a short)
            bBuffer.putShort((short) 1);
            // 13) Insert a 24 for 24 bits per pixel. (as a short)
            bBuffer.putShort((short) 24);
            // 14) Insert a zero to indicate no pixel array compression (as an int)
            bBuffer.putInt(0);

            // 15) Insert the size of the raw bitmap data (as an int)
            bBuffer.putInt(lineLength * getHieght());

            // 16) Insert four 32 bit 0 values for unused fields (as ints)
            bBuffer.putInt(0);
            bBuffer.putInt(0);
            bBuffer.putInt(0);
            bBuffer.putInt(0);

            // 17) Write the byte array out to the file.
            fout.write(bBuffer.array());

            // 18) Allocate a new byte array for the pixel data.
            bBuffer = ByteBuffer.allocate(lineLength * getHieght());
            // 19) Set the byte order to Little Endian
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            // TODO You must complete all of steps 20 and 21
            // 20) Insert the pixel data into the byte buffer
            //    a) Loop over all rows

            // b) Loop over each pixel in the row

            // i) Get pixel from the buffered image

            // ii) Insert blue, green, and then red bytes (as bytes).
            for(int fileHeight = 0; fileHeight < buffer.getHeight(); fileHeight++){
                for(int fileWidth = 0; fileWidth < buffer.getWidth(); fileWidth++){


                }
            }



            // c) Add padding so that the number of bytes for each row is
            //    evenly divisible by 4.  Add additional bytes with the value
            //    of 0 for the padding bytes.





            // 21) Write the pixel data out to the file.

        }
    }
}
