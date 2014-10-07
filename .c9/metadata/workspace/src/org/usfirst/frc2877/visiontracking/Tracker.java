{"changed":true,"filter":false,"title":"Tracker.java","tooltip":"/src/org/usfirst/frc2877/visiontracking/Tracker.java","value":"package org.usfirst.frc2877.visiontracking;\n\nimport java.awt.Color;\nimport java.awt.Font;\nimport java.awt.Graphics;\nimport java.awt.image.BufferedImage;\nimport java.util.ArrayList;\nimport java.util.List;\n\npublic class Tracker {\n\n\t//Variables\n\t\t//Target color\n\t\tprivate int red;\n\t\tprivate int green;\n\t\tprivate int blue;\n\t\n\t\t//Color offset tolerance\n\t\tprivate int redTolerance;\n\t\tprivate int greenTolerance;\n\t\tprivate int blueTolerance;\n\t\t\n\t\t//Debug values\n\t\tprivate boolean enableConsoleOutput = false;\n\t\tprivate boolean enableDebugInformation = true;\n\t\tprivate boolean enablePixelShading = false;\n\t\tprivate BufferedImage lastFrame;\n\t\tint frames = 0;\n\t\t\n\t\t//Center point\n\t\tprivate int x;\n\t\tprivate int y;\n\t\t\n\t\t//Constant\n\t\tprivate  String version = \"0.0b\";\n\t\n\t//Target color specification\n\t\t//Set the target red value\n\t\tpublic void setRed(int red) {\n\t\t\tthis.red = red;\n\t\t}\n\t\t//Set the target green value\n\t\tpublic void setGreen(int green) {\n\t\t\tthis.green = green;\n\t\t}\n\t\t//Set the target blue value\n\t\tpublic void setBlue(int blue) {\n\t\t\tthis.blue = blue;\n\t\t}\n\t\n\t//Color offset tolerance specification\n\t\t//Set the red tolerance\n\t\tpublic void setRedTolerance(int redTolerance) {\n\t\t\tthis.redTolerance = redTolerance;\n\t\t}\n\t\t//Set the green tolerance\n\t\tpublic void setGreenTolerance(int greenTolerance) {\n\t\t\tthis.greenTolerance = greenTolerance;\n\t\t}\n\t\t//Set the blue tolerance\n\t\tpublic void setBlueTolerance(int blueTolerance) {\n\t\t\tthis.blueTolerance = blueTolerance;\n\t\t}\n\t\n\t//Target color retrieval\n\t\t//Get the red target\n\t\tpublic int getRed() {\n\t\t\treturn red;\n\t\t}\n\t\t//Get the green target\n\t\tpublic int getGreen() {\n\t\t\treturn green;\n\t\t}\n\t\t//Get the blue target\n\t\tpublic int getBlue() {\n\t\t\treturn blue;\n\t\t}\n\t\n\t//Color offset tolerance retrieval\n\t\t//Get the red tolerance\n\t\tpublic int getRedTolerance() {\n\t\t\treturn redTolerance;\n\t\t}\n\t\t//Get the green tolerance\n\t\tpublic int getGreenTolerance() {\n\t\t\treturn greenTolerance;\n\t\t}\n\t\t//Get the blue tolerance\n\t\tpublic int getBlueTolerance() {\n\t\t\treturn blueTolerance;\n\t\t}\n            \n    //Sets all of the Tolerance values at the same time\n    public void setAllTolerances(int red, int green, int blue){\n        this.redTolerance = red;\n        this.greenTolerance = green;\n        this.blueTolerance = blue;\n    }\n    //Sets all of the RGB Targets at the same time\n    public void setRGBTargets(int red, int green, int blue){\n        this.red = red;\n        this.green = green;\n        this.blue = blue;\n    }\n\t\n\t//Process a given frame\n\tpublic void processFrame(BufferedImage frame) {\n\t\t//Start frame timer, if debug is enabled\n\t\tlong stime = 0;\n\t\tif(enableConsoleOutput || enableDebugInformation) {\n\t\t\tstime = System.nanoTime();\n\t\t}\n\t\t\n\t\t//Variables\n\t\tint cx = -1;\t\t//Stores the target center x coordinate\n\t\tint cy = -1;\t\t//Stores the target center y coordinate\n\t\t\n\t\tList<int[]> points = new ArrayList<int[]>();\t//Stores a list of pixels that matched the criteria\n\t\t\n\t\t//Iterate through each pixel in the given image\n\t\tfor(int x = 0; x < frame.getWidth(); x++) {\n\t\t\tfor(int y = 0; y < frame.getHeight(); y++) {\n\t\t\t\t//Get the color of the pixel\n\t\t\t\tint rgb = frame.getRGB(x, y);\n\t\t\t\t\n\t\t\t\t//Get the individual colors of the pixel\n\t\t\t\tint red = getRed(rgb);\n\t\t\t\tint green = getGreen(rgb);\n\t\t\t\tint blue = getBlue(rgb);\n\t\t\t\t\n\t\t\t\t//Check if the pixel matches the criteria\n\t\t\t\tif(Math.abs(red - this.red) < this.redTolerance && Math.abs(green - this.green) < this.greenTolerance && Math.abs(blue - this.blue) < this.blueTolerance) {\n\t\t\t\t\t//Pixel is a match, add to the list\n\t\t\t\t\tpoints.add(new int[]{x,y});\n\t\t\t\t\t//Debug - shade pixels\n\t\t\t\t\t\n\t\t\t\t\tif(enablePixelShading) { //Shade the pixel, to show it was a positive match\n\t\t\t\t\t\t//Calculate the red dimness\n\t\t\t\t\t\tint rdim = (255 - red) / 2;\n\t\t\t\t\t\t//Tint the pixel red\n\t\t\t\t\t\tint color = new Color(red + rdim, green, blue).getRGB();\n\t\t\t\t\t\t//Set the pixel color\n\t\t\t\t\t\tframe.setRGB(x, y, color);\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t\t\n\t\t//Calculate the center of the detected region\n\t\tint[][] pixels = points.toArray(new int[2][points.size()]);\n\t\tif(points.size() != 0) {\n\t\t\t//Add one to equalize\n\t\t\tx = cx+1;\n\t\t\ty = cy+1;\n\t\t\t//Sum the X and Y\n\t\t\tfor(int i = 0; i < points.size(); i++) {\n\t\t\t\tx += pixels[i][0];\n\t\t\t\ty += pixels[i][1];\n\t\t\t}\n\t\t\t//Divide to get average values\n\t\t\tx /= points.size();\n\t\t\ty /= points.size();\n\t\t}\n\t\t\n\t\t//Calculate the end time\n\t\tlong exectime = 0;\n\t\tdouble fps = 0;\n\t\tif(enableConsoleOutput || enableDebugInformation) {\n\t\t\t//Calculate the time\n\t\t\texectime = System.nanoTime() - stime;\n\t\t\t//Calculate the FPS\n\t\t\tfloat frametime = exectime / (float) 1000000000;\n\t\t\tSystem.out.println(exectime + \", \" + frametime);\n\t\t\tfps = 1 / frametime;\n\t\t\tif(enableConsoleOutput) {\n\t\t\t\tSystem.out.println(\"Processed frame in \" + exectime + \"ns, at \" + fps + \" fps!\");\n                                System.out.println(\"Center: \"+x + \",\" + y);\n\t\t\t}\n\t\t}\n\t\t\n\t\t//If requested, draw debug information to the frame\n\t\tif(enableDebugInformation) {\n\t\t\t//Get the graphics from the frame\n\t\t\tGraphics g = frame.getGraphics();\n\t\t\t//Set the draw information\n\t\t\tg.setColor(Color.WHITE);\n\t\t\tg.setFont(new Font(\"Arial\", Font.BOLD, 12));\n\t\t\t//Draw text\n\t\t\tg.drawString(\"Debug Information:\", 5, 17);\n\t\t\t//Change the font weight\n\t\t\tg.setFont(new Font(\"Arial\", Font.PLAIN, 12));\n\t\t\t//Draw more information\n\t\t\tg.drawString(\"Application version: \" + version, 5, 29);\n\t\t\tg.drawString(\"Frame width: \" + frame.getWidth(), 5, 41);\n\t\t\tg.drawString(\"Frame height: \" + frame.getHeight(), 5, 53);\n\t\t\tg.drawString(\"Execution time: \" + exectime + \"ns\", 5, 65);\n\t\t\tg.drawString(\"Center point: \" + cx + \",\" + cy, 5, 77);\n\t\t\tg.drawString(\"FPS: \" + fps, 5, 89);\n\t\t\tg.drawString(\"Frames: \" + frames, 5, 101);\n\t\t\t//Calculate the center point\n\t\t\tint ch = frame.getHeight() / 2;\n\t\t\tint cw = frame.getWidth() / 2;\n\t\t\tg.drawLine(cw - 5, ch, cw + 5, ch);\n\t\t\tg.drawLine(cw, ch - 5, cw, ch + 5);\n\t\t}\n\t\t\n\t\t//Save the last frame\n\t\tthis.lastFrame = frame;\n\t\t\n\t\t//Increment frame counter\n\t\tframes++;\n\t}\n\t\n\t//Gets the red value from a RGB value\n\tprivate int getRed(int rgb) {\n\t\treturn (rgb >> 16) & 0xFF;\n\t}\n\t//Gets the green value from a RGB value\n\tprivate int getGreen(int rgb) {\n\t\treturn (rgb >> 8) & 0xFF;\n\t}\n\t//Get the blue value from a RGB value\n\tprivate int getBlue(int rgb) {\n\t\treturn (rgb >> 0) & 0xFF;\n\t}\n\t\n\t//Get the last frame\n\tpublic BufferedImage getLastFrame() {\n\t\treturn lastFrame;\n\t}\n\t\n\t//Get the center X value\n\tpublic int getX() {\n\t\treturn x;\n\t}\n\t//Get the center Y value\n\tpublic int getY() {\n\t\treturn y;\n\t}\n\t\n\t//Debug functions\n\tpublic void enableConsoleOutput(boolean enable) {\n\t\tthis.enableConsoleOutput = enable;\n\t}\n\tpublic void enableDebugInformation(boolean enable) {\n\t\tthis.enableDebugInformation = enable;\n\t}\n\tpublic void enablePixelShading(boolean enable) {\n\t\tthis.enablePixelShading = enable;\n\t}\n}","undoManager":{"mark":-23,"position":100,"stack":[[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":10},"end":{"row":92,"column":11}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":11},"end":{"row":92,"column":12}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":12},"end":{"row":92,"column":13}},"text":"l"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":13},"end":{"row":92,"column":14}},"text":"l"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":14},"end":{"row":92,"column":15}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":15},"end":{"row":92,"column":16}},"text":"o"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":16},"end":{"row":92,"column":17}},"text":"f"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":17},"end":{"row":92,"column":18}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":18},"end":{"row":92,"column":19}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":19},"end":{"row":92,"column":20}},"text":"h"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":20},"end":{"row":92,"column":21}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":21},"end":{"row":92,"column":22}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"removeText","range":{"start":{"row":92,"column":21},"end":{"row":92,"column":22}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"removeText","range":{"start":{"row":92,"column":20},"end":{"row":92,"column":21}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":20},"end":{"row":92,"column":21}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":21},"end":{"row":92,"column":22}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":22},"end":{"row":92,"column":23}},"text":"T"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":23},"end":{"row":92,"column":24}},"text":"o"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":24},"end":{"row":92,"column":25}},"text":"l"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":25},"end":{"row":92,"column":26}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":26},"end":{"row":92,"column":27}},"text":"r"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":27},"end":{"row":92,"column":28}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":28},"end":{"row":92,"column":29}},"text":"n"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":29},"end":{"row":92,"column":30}},"text":"c"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":30},"end":{"row":92,"column":31}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":31},"end":{"row":92,"column":32}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":32},"end":{"row":92,"column":33}},"text":"v"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":33},"end":{"row":92,"column":34}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":34},"end":{"row":92,"column":35}},"text":"l"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":35},"end":{"row":92,"column":36}},"text":"u"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":36},"end":{"row":92,"column":37}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":37},"end":{"row":92,"column":38}},"text":"s"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":38},"end":{"row":92,"column":39}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":39},"end":{"row":92,"column":40}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":40},"end":{"row":92,"column":41}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":41},"end":{"row":92,"column":42}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":42},"end":{"row":92,"column":43}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":43},"end":{"row":92,"column":44}},"text":"h"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":44},"end":{"row":92,"column":45}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":45},"end":{"row":92,"column":46}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":46},"end":{"row":92,"column":47}},"text":"s"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":47},"end":{"row":92,"column":48}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":48},"end":{"row":92,"column":49}},"text":"m"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":49},"end":{"row":92,"column":50}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":50},"end":{"row":92,"column":51}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":51},"end":{"row":92,"column":52}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":52},"end":{"row":92,"column":53}},"text":"i"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":53},"end":{"row":92,"column":54}},"text":"m"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":92,"column":54},"end":{"row":92,"column":55}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"removeText","range":{"start":{"row":98,"column":4},"end":{"row":98,"column":46}},"text":"    //Sets all 3 target RGB values at once"},{"action":"insertText","range":{"start":{"row":98,"column":4},"end":{"row":98,"column":5}},"text":"/"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":5},"end":{"row":98,"column":6}},"text":"/"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":6},"end":{"row":98,"column":7}},"text":"S"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":7},"end":{"row":98,"column":8}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":8},"end":{"row":98,"column":9}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":9},"end":{"row":98,"column":10}},"text":"s"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":10},"end":{"row":98,"column":11}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":11},"end":{"row":98,"column":12}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":12},"end":{"row":98,"column":13}},"text":"l"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":13},"end":{"row":98,"column":14}},"text":"l"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":14},"end":{"row":98,"column":15}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":15},"end":{"row":98,"column":16}},"text":"o"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":16},"end":{"row":98,"column":17}},"text":"f"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":17},"end":{"row":98,"column":18}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":18},"end":{"row":98,"column":19}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":19},"end":{"row":98,"column":20}},"text":"h"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":20},"end":{"row":98,"column":21}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":21},"end":{"row":98,"column":22}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":22},"end":{"row":98,"column":23}},"text":"R"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":23},"end":{"row":98,"column":24}},"text":"G"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":24},"end":{"row":98,"column":25}},"text":"B"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":25},"end":{"row":98,"column":26}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":26},"end":{"row":98,"column":27}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":27},"end":{"row":98,"column":28}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"removeText","range":{"start":{"row":98,"column":27},"end":{"row":98,"column":28}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"removeText","range":{"start":{"row":98,"column":26},"end":{"row":98,"column":27}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":26},"end":{"row":98,"column":27}},"text":"T"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":27},"end":{"row":98,"column":28}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":28},"end":{"row":98,"column":29}},"text":"r"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":29},"end":{"row":98,"column":30}},"text":"g"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":30},"end":{"row":98,"column":31}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":31},"end":{"row":98,"column":32}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":32},"end":{"row":98,"column":33}},"text":"s"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":33},"end":{"row":98,"column":34}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":34},"end":{"row":98,"column":35}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":35},"end":{"row":98,"column":36}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":36},"end":{"row":98,"column":37}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":37},"end":{"row":98,"column":38}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":38},"end":{"row":98,"column":39}},"text":"h"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":39},"end":{"row":98,"column":40}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":40},"end":{"row":98,"column":41}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":41},"end":{"row":98,"column":42}},"text":"s"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":42},"end":{"row":98,"column":43}},"text":"a"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":43},"end":{"row":98,"column":44}},"text":"m"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":44},"end":{"row":98,"column":45}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":45},"end":{"row":98,"column":46}},"text":" "}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":46},"end":{"row":98,"column":47}},"text":"t"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":47},"end":{"row":98,"column":48}},"text":"i"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":48},"end":{"row":98,"column":49}},"text":"m"}]}],[{"group":"doc","deltas":[{"action":"insertText","range":{"start":{"row":98,"column":49},"end":{"row":98,"column":50}},"text":"e"}]}],[{"group":"doc","deltas":[{"action":"removeText","range":{"start":{"row":105,"column":0},"end":{"row":105,"column":1}},"text":"\t"}]}],[{"group":"doc","deltas":[{"action":"removeText","range":{"start":{"row":104,"column":1},"end":{"row":105,"column":0}},"text":"\n"}]}]]},"ace":{"folds":[],"scrolltop":0,"scrollleft":0,"selection":{"start":{"row":10,"column":0},"end":{"row":10,"column":0},"isBackwards":false},"options":{"guessTabSize":true,"useWrapMode":false,"wrapToView":true},"firstLineState":0},"timestamp":1412684731742}