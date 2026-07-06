package main;

import bus.uigen.ObjectEditor;
import grail.compositeShapes.interfaces.AngleInterface;
import grail.compositeShapes.interfaces.AvatarInterface;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import grail.compositeShapes.classes.Angle;
import grail.compositeShapes.classes.Avatar;
import grail.simpleShapes.classes.RotatingLine;
import grail.simpleShapes.interfaces.LineInterface;
import grail.simpleShapes.classes.Image;
import grail.simpleShapes.interfaces.ImageInterface;
import util.misc.ThreadSupport;

public class Assignment1 {
    
    private static final int EDITOR_WIDTH = 1000;
    private static final int EDITOR_HEIGHT = 500;
    
    private static final int PAUSE_TIME_SHORT = 2000;
    private static final int PAUSE_TIME_MEDIUM = 3000;
    private static final int PAUSE_TIME_LONG = 5000;
    private static final int PAUSE_TIME_ANIMATION = 30;
    private static final int PAUSE_TIME_SLOW = 1000;
    
    private static final int IMAGE_MOVE_STEP = 5;
    private static final int DEFAULT_ORIGIN_X = 0;
    private static final int DEFAULT_ORIGIN_Y = 0;
    private static final double DEFAULT_ANGLE_RADIANS = 0;
    private static final int TEST_IMAGE_X = 100;
    private static final int TEST_IMAGE_Y = 100;
    private static final int TEST_IMAGE_WIDTH = 100;
    private static final int TEST_IMAGE_HEIGHT = 100;
    private static final int TEST_AVATAR_X = 200;
    private static final int TEST_AVATAR_Y = 200;
    private static final int TEST_ANGLE_X = 100;
    private static final int TEST_ANGLE_Y = 100;
    private static final int TEST_ANGLE_RADIUS = 50;
    private static final int TEST_LEGS_X = 200;
    private static final int TEST_LEGS_Y = 400;
    private static final int TEST_LEGS_RADIUS = 100;
    private static final String LINE_MODE = "Line";
    private static final String ROTATING_LINE_MODE = "RotatingLine";
    private static final String IMAGE_MODE = "Image";
    private static final String AVATAR_MODE = "Avatar";
    private static final String ANGLE_MODE = "Angle";
    private static final String WALKING_LEGS_MODE = "WalkingLegs";
    private static final String TEST_IMAGE_LABEL = "Test";
    private static final String TEST_AVATAR_SPEECH = "Hello!";
    private static final String ARTHUR_IMAGE_FILE = "images/arthur.jpg";

    private static final int ANIMATION_STEP = 1;
    private static final int ANIMATION_TARGET_X = 300;
    private static final int ANIMATION_TARGET_Y = 300;

    private static final double RADIANS_PER_DEGREE = Math.PI / 180;
    private static final int WALKING_CYCLE_FRAMES = 80;
    private static final double FULL_CIRCLE_RADIANS = 2 * Math.PI;
    private static final double RIGHT_ANGLE_RADIANS = Math.PI / 2;
    private static final double HALF_CYCLE_RADIANS = Math.PI;
    private static final double RIGHT_ANGLE_DEGREES = 90.0;
    private static final double SWING_SCALE_DENOMINATOR = 100.0;

    private static final int LINE_MODE_X = 100;
    private static final int LINE_MODE_Y = 100;
    private static final int LINE_MODE_RADIUS = 100;
    private static final int LINE_MODE_ANGLE = 0;
    private static final int LINE_ROTATE_90 = 90;
    private static final int LINE_ROTATE_MINUS_45 = -45;
    
    private static final int AVATAR_MOVE_X = 300;
    private static final int AVATAR_MOVE_Y = 300;
    
    public static void main(String[] args) {
        animateLine(LINE_MODE);
    }

    public static void demonstrateBridgeScene() {
        BridgeSceneInterface bridgeScene = Factory.bridgeSceneFactoryMethod();
        ObjectEditor.edit(bridgeScene).setSize(EDITOR_WIDTH, EDITOR_HEIGHT);
        ThreadSupport.sleep(PAUSE_TIME_LONG);
    }
    
    public static void animateLine(String mode) {
        if (LINE_MODE.equals(mode)) {
            LineInterface testLine = new RotatingLine(LINE_MODE_X, LINE_MODE_Y, LINE_MODE_RADIUS, LINE_MODE_ANGLE); 
            ObjectEditor.edit(testLine);
            ThreadSupport.sleep(PAUSE_TIME_SHORT);

            testLine.rotate(LINE_ROTATE_90);
            ThreadSupport.sleep(PAUSE_TIME_SHORT);

            testLine.rotate(LINE_ROTATE_MINUS_45);
        }

        else if (ROTATING_LINE_MODE.equals(mode)) {
            LineInterface testLine = new RotatingLine(DEFAULT_ORIGIN_X, DEFAULT_ORIGIN_Y, TEST_LEGS_RADIUS, DEFAULT_ANGLE_RADIANS); 
            ObjectEditor.edit(testLine);

            int nextY = testLine.getY() + ANIMATION_STEP;
            int nextX = testLine.getX() + ANIMATION_STEP;
            while (nextY <= ANIMATION_TARGET_Y && nextX <= ANIMATION_TARGET_X) {
                testLine.setX(nextX);
                testLine.setY(nextY);
                testLine.rotate(ANIMATION_STEP);
                ThreadSupport.sleep(PAUSE_TIME_ANIMATION);
                nextX = testLine.getX() + ANIMATION_STEP;
                nextY = testLine.getY() + ANIMATION_STEP;
            }
    
            testLine.setX(ANIMATION_TARGET_X);
            testLine.setY(ANIMATION_TARGET_Y);
    
            while (true) {
                testLine.rotate(ANIMATION_STEP);
                ThreadSupport.sleep(PAUSE_TIME_ANIMATION);
            }
        }

        else if (IMAGE_MODE.equals(mode)) {
            ImageInterface testImage = new Image(TEST_IMAGE_X, TEST_IMAGE_Y, TEST_IMAGE_WIDTH, TEST_IMAGE_HEIGHT,
                    TEST_IMAGE_LABEL, ARTHUR_IMAGE_FILE);
            ObjectEditor.edit(testImage);

            while (true) {
                testImage.setX(testImage.getX() + IMAGE_MOVE_STEP);
                testImage.setY(testImage.getY() + IMAGE_MOVE_STEP);
                ThreadSupport.sleep(PAUSE_TIME_SLOW);
            }
        }

        else if (AVATAR_MODE.equals(mode)) {
            AvatarInterface testAvatar = new Avatar(TEST_AVATAR_X, TEST_AVATAR_Y, TEST_AVATAR_SPEECH, ARTHUR_IMAGE_FILE);
            ObjectEditor.edit(testAvatar);
            ThreadSupport.sleep(PAUSE_TIME_MEDIUM);
            
            testAvatar.move(AVATAR_MOVE_X, AVATAR_MOVE_Y);
            ThreadSupport.sleep(PAUSE_TIME_MEDIUM);
            
            testAvatar.rotate(RIGHT_ANGLE_DEGREES);
        }

        else if (ANGLE_MODE.equals(mode)) {
            AngleInterface testAngle = new Angle(TEST_ANGLE_X, TEST_ANGLE_Y, TEST_ANGLE_RADIUS,
                    RIGHT_ANGLE_RADIANS, RIGHT_ANGLE_RADIANS);
            ObjectEditor.edit(testAngle);
        }

        else if (WALKING_LEGS_MODE.equals(mode)) {
            AngleInterface testLegs = new Angle(TEST_LEGS_X, TEST_LEGS_Y, TEST_LEGS_RADIUS,
                    RIGHT_ANGLE_RADIANS, RIGHT_ANGLE_RADIANS);

            ObjectEditor.edit(testLegs);

            double omegaRadians = FULL_CIRCLE_RADIANS / WALKING_CYCLE_FRAMES;
            double swingAmplitudeRadians =
                    ANIMATION_STEP / (SWING_SCALE_DENOMINATOR * omegaRadians) * RADIANS_PER_DEGREE * RADIANS_PER_DEGREE;

            int frame = 0;
            while (true) {
                testLegs.move(ANIMATION_STEP, 0);

                double phaseRadians = (frame % WALKING_CYCLE_FRAMES) * omegaRadians;

                double leftAngleRadians = RIGHT_ANGLE_RADIANS + swingAmplitudeRadians * Math.sin(phaseRadians);
                double rightAngleRadians = RIGHT_ANGLE_RADIANS + swingAmplitudeRadians * Math.sin(phaseRadians + HALF_CYCLE_RADIANS);

                testLegs.getLeftLine().setAngle(leftAngleRadians);
                testLegs.getRightLine().setAngle(rightAngleRadians);

                ThreadSupport.sleep(PAUSE_TIME_ANIMATION);
                frame++;
            }
        }
    }
}
