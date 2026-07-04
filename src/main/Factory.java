package main;

import grail.compositeShapes.classes.BridgeScene;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import tags301.Comp301Tags;
import util.annotations.Tags;

@Tags({Comp301Tags.FACTORY_CLASS})
public class Factory {
    private static BridgeSceneInterface bridgeScene;

    public static BridgeSceneInterface getBridgeScene() {
        if (bridgeScene == null) {
            bridgeScene = new BridgeScene();
        }
        return bridgeScene;
    }
}
