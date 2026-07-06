package main;

import grail.compositeShapes.classes.Angle;
import grail.compositeShapes.classes.BridgeScene;
import grail.compositeShapes.interfaces.AngleInterface;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import grail.views.classes.ConsoleSceneView;
import grail.views.interfaces.ConsoleSceneViewInterface;
import tags301.Comp301Tags;
import util.annotations.Tags;

@Tags({Comp301Tags.FACTORY_CLASS})
public class Factory {
    private static BridgeSceneInterface bridgeScene;
    private static ConsoleSceneViewInterface consoleSceneView;

    public static BridgeSceneInterface bridgeSceneFactoryMethod() {
        if (bridgeScene == null) {
            bridgeScene = new BridgeScene();
        }
        return bridgeScene;
    }

    public static ConsoleSceneViewInterface consoleSceneViewFactoryMethod() {
        if (consoleSceneView == null) {
            consoleSceneView = new ConsoleSceneView();
        }
        return consoleSceneView;
    }

    public static AngleInterface legsFactoryMethod(int x, int y, double radius,
            double splitAngleRadians, double downDirectionRadians) {
        return new Angle(x, y, radius, splitAngleRadians, downDirectionRadians);
    }
}
