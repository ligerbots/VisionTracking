/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.frc2877.TwoJagVisionBot.subsystems;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author ligerbots
 */
public class Camera extends Subsystem {
    public AxisCamera camera;
    public Camera(){
        camera = AxisCamera.getInstance();
    }
    public void initDefaultCommand() {
    }
}
