/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.frc2877.TwoJagVisionBot.commands;

/**
 *
 * @author ligerbots
 */

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.RGBImage;
import org.usfirst.frc2877.TwoJagVisionBot.Robot;
import org.usfirst.frc2877.TwoJagVisionBot.subsystems.Camera;

public class FrameTracker {

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
		int frames = 0;
		
		//Center point
		private int x;
		private int y;
		
		//Constant
		private  String version = "0.0b";
                
                AxisCamera theCamera = Robot.camera.camera;
                
                
	
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
                
                public void setAllTolerances(int red, int green, int blue){
                    this.redTolerance = red;
                    this.greenTolerance = green;
                    this.blueTolerance = blue;
                }
	                //Sets all 3 target RGB values at once
                public void setRGBTargets(int red, int green, int blue){
                    this.red = red;
                    this.green = green;
                    this.blue = blue;
                }
	
	
	//Process a given frame
	public void processFrame(){
            
            
                //TO ALL NEW LIGERBOTS CODERS:
                //Below is VERY POOR CODE.
                //Do not do this. EVER.
                
                RGBImage theImage;
                
                try{
                    theImage = (RGBImage) theCamera.getImage();

                    //Start frame timer, if debug is enabled
                    long stime = 0;
                    //Variables
                    int cx = -1;		//Stores the target center x coordinate
                    int cy = -1;		//Stores the target center y coordinate
                    
                    //Image dimensions
                    int width = theImage.getWidth();
                    int height = theImage.getHeight();
                    
                    //List<int[]> points = new ArrayList<int[]>();	//Stores a list of pixels that matched the criteria
                    int[] rawData = new int[width * height];
                    theImage.image.getInts(0, rawData, 0, width * height);
                    //Add one to set to 0
                    x = cx+1;
                    y = cy+1;   
                    
                    int amtWithinTolerance=0;
                    
                    //ASSUMPTION: There's at least 1 pixel within tolerance. Fix this.
                    
                    for(int place = 0; place < rawData.length; place++){
                        if(Math.abs(rawData[place] - red)<redTolerance 
                                && Math.abs(rawData[place+1] - green)<greenTolerance
                                && Math.abs(rawData[place+2] - blue)<blueTolerance){
                            //Then it's within the target; Sum the X and Y
                                x += place%width;
                                y += place/width;
                                amtWithinTolerance++;
                            }
                        }
                        if(amtWithinTolerance!=0){
                            //Divide to get average values
                            x /= amtWithinTolerance;
                            y /= amtWithinTolerance;
                        }else{
                            x=-1;
                            y=-1;
                        }
                    
                    /*if(rawData.size() != 0) {
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
                    }*/
                    
                    //Calculate the end time
                    long exectime = 0;
                    double fps = 0;
                    if(enableConsoleOutput || enableDebugInformation) {
                            //Calculate the time
                            //exectime = System.nanoTime() - stime;
                            //Calculate the FPS
                            //float frametime = exectime / (float) 1000000000;
                            //System.out.println(exectime + ", " + frametime);
                            //fps = 1 / frametime;
                            if(enableConsoleOutput) {
                            //	System.out.println("Processed frame in " + exectime + "ns, at " + fps + " fps!");
                                    System.out.println("Center: " + x + "," + y);
                            }
                    }

                    //Increment frame counter
                    frames++;

                }catch(Exception ex){
                    
                    //This is the reason it's VERY POOR CODE: It catches all exceptions then does nothing.
                    //I am a terrible person.
                    
                    System.out.println("HELP ME I'M PANICKING");
                    System.out.println(ex);
                    //Then do nothing...
                }
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