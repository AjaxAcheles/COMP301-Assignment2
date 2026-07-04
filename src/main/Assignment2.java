package main;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import grail.views.classes.ConsoleSceneView;
import util.misc.ThreadSupport;

public class Assignment2 {
    private static final int EDITOR_WIDTH = 1050;
    private static final int EDITOR_HEIGHT = 550;
    private static final int PAUSE_TIME = 1200;

    public static void main(String[] args) {
        BridgeSceneInterface bridgeScene = Factory.getBridgeScene();
        OEFrame editor = ObjectEditor.edit(bridgeScene);
        editor.setSize(EDITOR_WIDTH, EDITOR_HEIGHT);
        new ConsoleSceneView();
        runAnimation(bridgeScene);
    }

    private static void runAnimation(BridgeSceneInterface bridgeScene) {
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.getRobin().move(30, 0);
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.approach(bridgeScene.getArthur());
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say("Stop. Who would cross the bridge of death?");
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say("It is I, Arthur, king of the Britons.");
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.passed();
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.approach(bridgeScene.getLancelot());
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say("What is your quest?");
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.say("To seek the Holy Grail.");
        ThreadSupport.sleep(PAUSE_TIME);

        bridgeScene.failed();
    }
}
