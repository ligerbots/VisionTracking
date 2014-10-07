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
	
	
	//Process a given frame
	public void processFrame(BufferedImage frame) {
		//Start frame timer, if debug is enabled
		long stime = 0;
		if(enableConsoleOutput || enableDebugInformation) {
			stime = System.nanoTime();
		}
		
		//Variables
		int cx = -1;		//Stores the target center x coordinate
		int cy = -1;		//Stores the target center y coordinate
		
		List<int[]> points = new ArrayList<int[]>();	//Stores a list of pixels that matched the criteria
		
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
				if(Math.abs(red - this.red) < this.redTolerance && Math.abs(green - this.green) < this.greenTolerance && Math.abs(blue - this.blue) < this.blueTolerance) {
					//Pixel is a match, add to the list
					points.add(new int[]{x,y});
					//Debug - shade pixels
					
					if(enablePixelShading) { //Shade the pixel, to show it was a positive match
						//Calculate the red dimness
						int rdim = (255 - red) / 2;
						//Tint the pixel red
						int color = new Color(red + rdim, green, blue).getRGB();
						//Set the pixel color
						frame.setRGB(x, y, color);
					}
				}
			}
		}
		
		//Calculate the center of the detected region
		int[][] pixels = points.toArray(new int[2][points.size()]);
		if(points.size() != 0) {
			//Add one to equalize
			x = cx+1;
			y = cy+1;
			//Sum the X and Y
			for(int i = 0; i < points.size(); i++) {
				x += pixels[i][0];
				y += pixels[i][1];
			}
			//Divide to get average values
			x /= points.size();
			y /= points.size();
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
			g.drawString("Center point: " + cx + "," + cy, 5, 77);
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
}
