VisionTracking
==============

Robot Vision Tracking code

This code is designed to provide basic vision tracking functionality to robots, viewing the game either through a USB or Network camera.

The program works by taking a frame from the robot, and scanning over the pixels, locating all that meet a certain set of criteria (RGB values, etc.) and adding them to a list.  Next, the program will take the locations of all the pixels in this list and average them together, calculating the center of the object, and therefore allowing the robot to calculate the offset from its target.

Known Issues
==============

The code, when loading in USB mode is known to take as much as 20 seconds to initialize, so measures should be taken to ensure that there is adequate time to initialize vision tracking before the round begins.

Usabe
==============

You are free to use this code as you wish, however you do so at your own risk, and FRC Team 2877 is Not to be held responsible for any damages that may be caused to your robot, or electronic hardware.
