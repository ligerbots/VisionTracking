package org.usfirst.frc2877.visiontracking.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JSpinner;
import javax.swing.JCheckBoxMenuItem;

import org.usfirst.frc2877.visiontracking.Tracker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;

public class TestingWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Tracker tracker;
	
	public JLabel red_target;
	public JLabel green_target;
	public JLabel blue_target;
	
	public JLabel red_tolerance;
	public JLabel green_tolerance;
	public JLabel blue_tolerance;
	
	public JLabel cross_red;
	public JLabel cross_green;
	public JLabel cross_blue;
	
	public JSpinner spinner_red_target;
	public JSpinner spinner_red_tolerance;
	
	public JSpinner spinner_green_target;
	public JSpinner spinner_green_tolerance;
	
	public JSpinner spinner_blue_target;
	public JSpinner spinner_blue_tolerance;
	
	public int cr = 0;
	public int cg = 0;
	public int cb = 0;
	
	public JPanel imagepanel;
	
	public JMenu mnFile;

	/**
	 * Create the frame.
	 */
	public TestingWindow(final Tracker tracker) {
		this.tracker = tracker;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 722, 423);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JLabel lblCurentTarget = new JLabel("Target: ");
		menuBar.add(lblCurentTarget);
		
		red_target = new JLabel("000");
		red_target.setForeground(Color.RED);
		menuBar.add(red_target);
		
		green_target = new JLabel("000");
		green_target.setForeground(new Color(0, 128, 0));
		menuBar.add(green_target);
		
		blue_target = new JLabel("000");
		blue_target.setForeground(new Color(0, 0, 255));
		menuBar.add(blue_target);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut);
		
		JLabel lblTolerance = new JLabel("Tolerance: ");
		menuBar.add(lblTolerance);
		
		red_tolerance = new JLabel("000");
		red_tolerance.setForeground(Color.RED);
		menuBar.add(red_tolerance);
		
		green_tolerance = new JLabel("000");
		green_tolerance.setForeground(new Color(0, 128, 0));
		menuBar.add(green_tolerance);
		
		blue_tolerance = new JLabel("000");
		blue_tolerance.setForeground(Color.BLUE);
		menuBar.add(blue_tolerance);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_1);
		
		JLabel lblCrosshairs = new JLabel("Crosshairs: ");
		menuBar.add(lblCrosshairs);
		
		cross_red = new JLabel("000");
		cross_red.setForeground(Color.RED);
		menuBar.add(cross_red);
		
		cross_green = new JLabel("000");
		cross_green.setForeground(new Color(0, 128, 0));
		menuBar.add(cross_green);
		
		cross_blue = new JLabel("000");
		cross_blue.setForeground(Color.BLUE);
		menuBar.add(cross_blue);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu calibration_menu = new JMenu("Calibration");
		mnFile.add(calibration_menu);
		
		JMenu calibration_red = new JMenu("Red");
		calibration_menu.add(calibration_red);
		
		JLabel lblTarget = new JLabel("Target: ");
		calibration_red.add(lblTarget);
		
		spinner_red_target = new JSpinner();
		//Red target value changed
		spinner_red_target.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				tracker.setRedTarget(Integer.parseInt(spinner_red_target.getValue() + ""));
			}
		});
		calibration_red.add(spinner_red_target);
		
		JLabel lblTolerance_1 = new JLabel("Tolerance:");
		calibration_red.add(lblTolerance_1);
		
		spinner_red_tolerance = new JSpinner();
		//Red tolerance value changed
		spinner_red_tolerance.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tracker.setRedTolerance(Integer.parseInt(spinner_red_tolerance.getValue() + ""));
			}
		});
		calibration_red.add(spinner_red_tolerance);
		
		JMenu calibration_green = new JMenu("Green");
		calibration_menu.add(calibration_green);
		
		JLabel label = new JLabel("Target: ");
		calibration_green.add(label);
		
		spinner_green_target = new JSpinner();
		//Green target value changed
		spinner_green_target.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tracker.setGreenTarget(Integer.parseInt(spinner_green_target.getValue() + ""));
			}
		});
		calibration_green.add(spinner_green_target);
		
		JLabel label_1 = new JLabel("Tolerance:");
		calibration_green.add(label_1);
		
		spinner_green_tolerance = new JSpinner();
		//Green tolerance value changed
		spinner_green_tolerance.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tracker.setGreenTolerance(Integer.parseInt(spinner_green_tolerance.getValue() + ""));
			}
		});
		calibration_green.add(spinner_green_tolerance);
		
		JMenu calibration_blue = new JMenu("Blue");
		calibration_menu.add(calibration_blue);
		
		JLabel label_2 = new JLabel("Target: ");
		calibration_blue.add(label_2);
		
		spinner_blue_target = new JSpinner();
		//Blue target value changed
		spinner_blue_target.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tracker.setBlueTarget(Integer.parseInt(spinner_blue_target.getValue() + ""));
			}
		});
		calibration_blue.add(spinner_blue_target);
		
		JLabel label_3 = new JLabel("Tolerance:");
		calibration_blue.add(label_3);
		
		spinner_blue_tolerance = new JSpinner();
		//Blue tolerance value changed
		spinner_blue_tolerance.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tracker.setBlueTolerance(Integer.parseInt(spinner_blue_tolerance.getValue() + ""));
			}
		});
		calibration_blue.add(spinner_blue_tolerance);
		
		JMenuItem mntmAutocalibrate = new JMenuItem("AutoCalibrate");
		//Auto calibrate activated
		mntmAutocalibrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Set the RGB values to the last values from the processing thread
				tracker.setRedTarget(cr);
				tracker.setGreenTarget(cg);
				tracker.setBlueTarget(cb);
			}
		});
		calibration_menu.add(mntmAutocalibrate);
		
		JMenu debug_menu = new JMenu("Debug");
		mnFile.add(debug_menu);
		
		final JCheckBoxMenuItem enable_pixel_shading = new JCheckBoxMenuItem("Enable Pixel Shading");
		//Called when enablePixelShading is selected or de-selected
		enable_pixel_shading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracker.enablePixelShading(enable_pixel_shading.isSelected());
			}
		});
		debug_menu.add(enable_pixel_shading);
		
		final JCheckBoxMenuItem enable_debug_output = new JCheckBoxMenuItem("Enable Debug Output");
		//Called when enableDebugOutput is selected or de-selected
		enable_debug_output.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracker.enableDebugInformation(enable_debug_output.isSelected());
			}
		});
		debug_menu.add(enable_debug_output);
		
		final JCheckBoxMenuItem enable_console_output = new JCheckBoxMenuItem("Enable Console Output");
		enable_console_output.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracker.enableConsoleOutput(enable_console_output.isSelected());
			}
		});
		debug_menu.add(enable_console_output);
		
		final JCheckBoxMenuItem enable_crosshairs = new JCheckBoxMenuItem("Enable Crosshairs");
		enable_crosshairs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracker.enableCrosshairs(enable_crosshairs.isSelected());
			}
		});
		debug_menu.add(enable_crosshairs);
		
		final JCheckBoxMenuItem chckbxmntmEnableSolidShading = new JCheckBoxMenuItem("Enable Solid Shading");
		chckbxmntmEnableSolidShading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracker.enableSolidShading(chckbxmntmEnableSolidShading.isSelected());
			}
		});
		debug_menu.add(chckbxmntmEnableSolidShading);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		imagepanel = new JPanel();
		contentPane.add(imagepanel, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	public void processFrame(BufferedImage image) {
		//Get the center pixel
		Color pixel = new Color(image.getRGB(image.getWidth() / 2, image.getHeight() / 2));
		//Set the center pixel data
		this.cross_red.setText(String.format("%03d", pixel.getRed()));
		this.cross_green.setText(String.format("%03d", pixel.getGreen()));
		this.cross_blue.setText(String.format("%03d", pixel.getBlue()));
		this.cr = pixel.getRed();
		this.cg = pixel.getGreen();
		this.cb = pixel.getBlue();
		//Process the frame
		tracker.processFrame(image);
		//Get the output
		BufferedImage result = tracker.getLastFrame();
		//Set the config values
		this.spinner_red_target.setValue(tracker.getRedTarget());
		this.spinner_red_tolerance.setValue(tracker.getRedTolerance());
		this.spinner_blue_target.setValue(tracker.getBlueTarget());
		this.spinner_blue_tolerance.setValue(tracker.getBlueTolerance());
		this.spinner_green_target.setValue(tracker.getGreenTarget());
		this.spinner_green_tolerance.setValue(tracker.getGreenTolerance());
		this.red_target.setText(String.format("%03d", tracker.getRedTarget()));
		this.red_tolerance.setText(String.format("%03d", tracker.getRedTolerance()));
		this.green_target.setText(String.format("%03d", tracker.getGreenTarget()));
		this.green_tolerance.setText(String.format("%03d", tracker.getGreenTolerance()));
		this.blue_target.setText(String.format("%03d", tracker.getBlueTarget()));
		this.blue_tolerance.setText(String.format("%03d", tracker.getBlueTolerance()));
		//Draw the frame
		imagepanel.getGraphics().drawImage(result, 0, 0, result.getHeight(), result.getHeight(), null);
	}

}
