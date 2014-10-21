package org.usfirst.frc2877.visiontracking;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Main {
   
	//Example program - Will retrieve a blank image from the team website, process it, then repeat.  1000 times.
	public static void asdfmain(String[] args) throws IOException {
		System.out.println("Initializing Debug Application...");
		System.out.println("Creating tracker...");
		Tracker tracker = new Tracker();
		System.out.println("Enabling debug components...");
		tracker.enableConsoleOutput(true);
		tracker.enableDebugInformation(true);
		tracker.enablePixelShading(true);
                tracker.setAllTargets(0,155,87);
                tracker.setAllTolerances(20,20,20);
		URL url = new URL("http://www.google.com/images/srpr/logo11w.png");
		System.out.println("Running tracker for 1000 frames:");
		for(int i = 0; i < 1000; i++) {
			BufferedImage image = ImageIO.read(url);
			tracker.processFrame(image);
		}
	}
}
