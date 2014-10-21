package org.usfirst.frc2877.visiontracking.test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import org.usfirst.frc2877.visiontracking.Tracker;

public class Test {

	public static void main(String[] args) throws IOException {
		//Create a tracker object
		Tracker tracker = new Tracker();
		
		//Create the tracking window
		TestingWindow window = new TestingWindow(tracker);
		
		//Create the object for getting frames
		URL url = new URL("http://localhost:8080/cam_1.jpg?uniq=0.383619308560726");
		
		//Process for one million frames
		for(int i = 0; i < 10000; i++) {
			BufferedImage image = ImageIO.read(url);
			window.processFrame(image);
		}
	}
}
