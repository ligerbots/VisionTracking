package org.usfirst.frc2877.visiontracking;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Tracker {

    //Variables
    //Target color
    private int red;
    private int green;
    private int blue;

    //Color offset tolerance
    private int redTolerance;
    private int greenTolerance;
    private int blueTolerance;

    //Debug values
    private boolean enableConsoleOutput = false;
    private boolean enableDebugInformation = true;
    private boolean enablePixelShading = false;
    private BufferedImage lastFrame;
    int frames = 0;

    //Center point
    private int x;
    private int y;

    //Constant
    private  String version = "0.0b";

    //Target color specification
    //Set the target red value
    public void setRed(int red) {
        this.red = red;
    }
    //Set the target green value
    public void setGreen(int green) {
        this.green = green;
    }
    //Set the target blue value
    public void setBlue(int blue) {
        this.blue = blue;
    }

    //Color offset tolerance specification
    //Set the red tolerance
    public void setRedTolerance(int redTolerance) {
        this.redTolerance = redTolerance;
    }
    //Set the green tolerance
    public void setGreenTolerance(int greenTolerance) {
        this.greenTolerance = greenTolerance;
    }
    //Set the blue tolerance
    public void setBlueTolerance(int blueTolerance) {
        this.blueTolerance = blueTolerance;
    }

    //Target color retrieval
    //Get the red target
    public int getRed() {
        return red;
    }
    //Get the green target
    public int getGreen() {
        return green;
    }
    //Get the blue target
    public int getBlue() {
        return blue;
    }

    //Color offset tolerance retrieval
    //Get the red tolerance
    public int getRedTolerance() {
        return redTolerance;
    }
    //Get the green tolerance
    public int getGreenTolerance() {
        return greenTolerance;
    }
    //Get the blue tolerance
    public int getBlueTolerance() {
        return blueTolerance;
    }

    //Sets all of the RGB tolerances at the same time
    public void setAllTolerances(int red, int green, int blue){
        this.redTolerance = red;
        this.greenTolerance = green;
        this.blueTolerance = blue;
    }
    //Sets all three RGB targets at the same time
    public void setAllTargets(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    //Indexes from a 2D array to a 1D array
    private int index(int row, int col, int width) {
        return (row * width) + col;
    }

    //Process a given frame
    public void processFrame(BufferedImage frame) {
        //Start frame timer, if debug is enabled

        long stime = 0;
        if(enableConsoleOutput || enableDebugInformation) {
            stime = System.nanoTime();
        }

        //Get the data stored within the BufferedImage
        byte[] allPixels = ((DataBufferByte) frame.getRaster().getDataBuffer()).getData();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int boundUp = frameHeight / 2, boundDown = frameHeight / 2;
        int boundLeft = frameWidth / 2, boundRight = frameWidth / 2;

        //Variables
        int cx = -1;		//Stores the target center x coordinate
        int cy = -1;		//Stores the target center y coordinate

        //Iterate through each pixel in the given image
        for(int x = 0; x < frameWidth; x++) {
            for(int y = 0; y < frameHeight; y++) {
                //Get the color of the pixel
                int rgb = allPixels[index(x, y, frameWidth)];

                //Get the individual colors of the pixel
                int red = getRed(rgb);
                int green = getGreen(rgb);
                int blue = getBlue(rgb);

                //Check if the pixel matches the criteria
                if(Math.abs(red - this.red) < this.redTolerance &&
                   Math.abs(green - this.green) < this.greenTolerance &&
                   Math.abs(blue - this.blue) < this.blueTolerance) {
                    boundRight = (x > boundRight) ? x : boundRight;
                    boundLeft = (x < boundLeft) ? x : boundLeft;
                    boundUp = (y < boundUp) ? y : boundUp;
                    boundDown = (y > boundDown) ? y : boundDown;
                    //Pixel is a match, add to the list
                    //Debug - shade pixels
                    if(enablePixelShading) { //Shade the pixel, to show it was a positive match
                        //Calculate the red dimness
                        int rdim = (255 - red) / 2;
                        //Tint the pixel red
                        int color = new Color(red + rdim, green, blue).getRGB();
                        //Set the pixel color
                        allPixels[index(x, y, frameWidth)] = (byte) color;
                    }
                }
            }
        }

        x = (boundLeft + boundRight) / 2;
        y = (boundUp + boundDown) / 2;

        //Calculate the end time
        long exectime = 0;
        double fps = 0;
        if(enableConsoleOutput || enableDebugInformation) {
            //Calculate the time
            exectime = System.nanoTime() - stime;
            //Calculate the FPS
            float frametime = exectime / (float) 1000000000;
            System.out.println(exectime + ", " + frametime);
            fps = 1 / frametime;
            if(enableConsoleOutput) {
                System.out.println("Processed frame in " + exectime + "ns, at " + fps + " fps!");
                System.out.println("Center: "+x + "," + y);
            }
        }

        //If requested, draw debug information to the frame
        if(enableDebugInformation) {
            //Get the graphics from the frame
            Graphics g = frame.getGraphics();
            //Set the draw information
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            //Draw text
            g.drawString("Debug Information:", 5, 17);
            //Change the font weight
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            //Draw more information
            g.drawString("Application version: " + version, 5, 29);
            g.drawString("Frame width: " + frame.getWidth(), 5, 41);
            g.drawString("Frame height: " + frame.getHeight(), 5, 53);
            g.drawString("Execution time: " + exectime + "ns", 5, 65);
            g.drawString("Center point: " + cx + "," + cy, 5, 77);
            g.drawString("FPS: " + fps, 5, 89);
            g.drawString("Frames: " + frames, 5, 101);
            //Calculate the center point
            int ch = frame.getHeight() / 2;
            int cw = frame.getWidth() / 2;
            g.drawLine(cw - 5, ch, cw + 5, ch);
            g.drawLine(cw, ch - 5, cw, ch + 5);
        }
        
        File f = new File("img" + frames + ".jpg");
        try {
            ImageIO.write(frame, "JPG", f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //Save the last frame
        this.lastFrame = frame;
        
        //Increment frame counter
        frames++;
    }
    
    //Gets the red value from a RGB value
    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }
    //Gets the green value from a RGB value
    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }
    //Get the blue value from a RGB value
    private int getBlue(int rgb) {
        return (rgb >> 0) & 0xFF;
    }
    
    //Get the last frame
    public BufferedImage getLastFrame() {
        return lastFrame;
    }
    
    //Get the center X value
    public int getX() {
        return x;
    }
    //Get the center Y value
    public int getY() {
        return y;
    }
    
    //Debug functions
    public void enableConsoleOutput(boolean enable) {
        this.enableConsoleOutput = enable;
    }
    public void enableDebugInformation(boolean enable) {
        this.enableDebugInformation = enable;
    }
    public void enablePixelShading(boolean enable) {
        this.enablePixelShading = enable;
    }
}
