package main;

import bus.uigen.ObjectEditor;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import grail.views.interfaces.ConsoleSceneViewInterface;
import util.misc.ThreadSupport;

public class Assignment2 {
    private static final int EDITOR_WIDTH = 1050;
    private static final int EDITOR_HEIGHT = 550;
    private static final int PAUSE_TIME = 1800;
    private static final int LEGACY_MOVE_X = 70;
    private static final int LEGACY_MOVE_Y = 0;
    private static final int LEGACY_ROTATION_DEGREES = 20;
    private static final String NAME_QUESTION = "What is your name?";
    private static final String ARTHUR_NAME = "It is I, King Arthur!";
    private static final String QUEST_QUESTION = "What is your quest?";
    private static final String ARTHUR_QUEST = "To seek the Holy Grail.";
    private static final String LANCELOT_COLOR_QUESTION = "Halt! What is your favorite color?";
    private static final String LANCELOT_COLOR_ANSWER = "Blue!";
    private static final String ROBIN_NAME = "Sir Robin of Camelot.";
    private static final String SWALLOW_QUESTION = "What is the airspeed velocity of an unladen swallow?";
    private static final String SWALLOW_REPLY = "African or European swallow?";
    private static ConsoleSceneViewInterface consoleSceneView;

    public static void main(String[] args) {
        BridgeSceneInterface bridgeScene = Factory.bridgeSceneFactoryMethod();
        ObjectEditor.edit(bridgeScene).setSize(EDITOR_WIDTH, EDITOR_HEIGHT);
        consoleSceneView = Factory.consoleSceneViewFactoryMethod();
        runAnimation(bridgeScene);
    }

    private static void runAnimation(BridgeSceneInterface bridgeScene) {
        ThreadSupport.sleep(PAUSE_TIME);
        runLegacyAnimation(bridgeScene);
        runArthurPasses(bridgeScene);
        runLancelotFails(bridgeScene);
        runGuardFails(bridgeScene);
    }

    private static void runLegacyAnimation(BridgeSceneInterface bridgeScene) {
        bridgeScene.getGalahad().move(LEGACY_MOVE_X, LEGACY_MOVE_Y);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.getGalahad().getArms().rotate(LEGACY_ROTATION_DEGREES);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.getGalahad().getArms().rotate(-LEGACY_ROTATION_DEGREES);
        bridgeScene.getGalahad().move(-LEGACY_MOVE_X, -LEGACY_MOVE_Y);
        ThreadSupport.sleep(PAUSE_TIME);
    }

    private static void runArthurPasses(BridgeSceneInterface bridgeScene) {
        bridgeScene.approach(bridgeScene.getArthur());
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(NAME_QUESTION);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(ARTHUR_NAME);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(QUEST_QUESTION);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(ARTHUR_QUEST);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.passed();
        ThreadSupport.sleep(PAUSE_TIME);
    }

    private static void runLancelotFails(BridgeSceneInterface bridgeScene) {
        bridgeScene.approach(bridgeScene.getLancelot());
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(LANCELOT_COLOR_QUESTION);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(LANCELOT_COLOR_ANSWER);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.failed();
        ThreadSupport.sleep(PAUSE_TIME);
    }

    private static void runGuardFails(BridgeSceneInterface bridgeScene) {
        bridgeScene.approach(bridgeScene.getRobin());
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(NAME_QUESTION);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(ROBIN_NAME);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(SWALLOW_QUESTION);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say(SWALLOW_REPLY);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.failed();
        ThreadSupport.sleep(PAUSE_TIME);
    }
}
