package org.usfirst.frc2877.visiontracking;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tracker {

	//Variables
	//Target color
	private int redTarget;
	private int greenTarget;
	private int blueTarget;

	//Color offset tolerance
	private int redTolerance;
	private int greenTolerance;
	private int blueTolerance;
	
	//Colors
	private int black = Color.BLACK.getRGB();
	private int red = Color.RED.getRGB();
	
	//Debug values
	private boolean enableConsoleOutput = false;
	private boolean enableDebugInformation = true;
	private boolean enablePixelShading = false;
	private boolean enableCrosshairs = false;
	private boolean enableSolidShading = false;
	private BufferedImage lastFrame;
	int frames = 0;
	
	//Center point
	private int x;
	private int y;
	
	//Constant
	private  String version = "0.0b";
	
	//Target color specification
	//Set the target red value
	public void setRedTarget(int redTarget) {
		this.redTarget = redTarget;
	}
	//Set the target green value
	public void setGreenTarget(int greenTarget) {
		this.greenTarget = greenTarget;
	}
	//Set the target blue value
	public void setBlueTarget(int blueTarget) {
		this.blueTarget = blueTarget;
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
	public int getRedTarget() {
		return redTarget;
	}
	//Get the green target
	public int getGreenTarget() {
		return greenTarget;
	}
	//Get the blue target
	public int getBlueTarget() {
		return blueTarget;
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
		this.redTarget = red;
		this.greenTarget = green;
		this.blueTarget = blue;
	}
	
	
	//Process a given frame
	public void processFrame(BufferedImage frame) {
		//Start frame timer, if debug is enabled
		long stime = 0;
		if(enableConsoleOutput || enableDebugInformation) {
			stime = System.nanoTime();
		}
		
		x = y = 0;
		int total = 0;
		
		//Iterate through each pixel in the given image
		for(int x = 0; x < frame.getWidth(); x++) {
			for(int y = 0; y < frame.getHeight(); y++) {
				//Get the color of the pixel
				int rgb = frame.getRGB(x, y);
				
				//Get the individual colors of the pixel
				int red = getRed(rgb);
				int green = getGreen(rgb);
				int blue = getBlue(rgb);
				
				//Check if the pixel matches the criteria
				if(Math.abs(red - this.redTarget) < this.redTolerance && Math.abs(green - this.greenTarget) < this.greenTolerance && Math.abs(blue - this.blueTarget) < this.blueTolerance) {
					//Pixel is a match, add to the list
					this.x += x;
					this.y += y;
					total++;
					//Shade the pixel, to show it was a positive match, if enabled
					if(enablePixelShading) {
						//Check if the pixel should be shaded with a solid color
						if(enableSolidShading) {
							frame.setRGB(x, y, red);
						} else {
							//Calculate the red dimness
							int rdim = (255 - red) / 2;
							//Tint the pixel red
							int color = new Color(red + rdim, green, blue).getRGB();
							//Set the pixel color
							frame.setRGB(x, y, color);
						}
					}
				} else if(enablePixelShading && enableSolidShading) {
					frame.setRGB(x, y, black);
				}
			}
		}
		
		x /= total;
		y /= total;
		
		//Draw crosshairs if enabled
		if(enableCrosshairs) {
			//Get the graphics from the frame
			Graphics g = frame.getGraphics();
			//Draw the crosshairs
			g.drawLine(x - 5, y, x + 5, y);
			g.drawLine(x, y - 5, x, y + 5);
		}
		
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
			g.drawString("Center point: " + x + "," + y, 5, 77);
			g.drawString("FPS: " + fps, 5, 89);
			g.drawString("Frames: " + frames, 5, 101);
			//Calculate the center point
			int ch = frame.getHeight() / 2;
			int cw = frame.getWidth() / 2;
			g.drawLine(cw - 5, ch, cw + 5, ch);
			g.drawLine(cw, ch - 5, cw, ch + 5);
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
	public void enableCrosshairs(boolean enable) {
		this.enableCrosshairs = enable;
	}
	public void enableSolidShading(boolean enable) {
		this.enableSolidShading = enable;
	}
}
