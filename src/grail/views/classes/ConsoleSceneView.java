package grail.views.classes;

import java.beans.PropertyChangeEvent;

import grail.atomicShapes.interfaces.PointInterface;
import grail.compositeShapes.interfaces.AngleInterface;
import grail.compositeShapes.interfaces.AvatarInterface;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import grail.compositeShapes.interfaces.GorgeInterface;
import grail.compositeShapes.interfaces.StandingAreaInterface;
import grail.simpleShapes.interfaces.LineInterface;
import grail.views.interfaces.ConsoleSceneViewInterface;
import main.Factory;
import tags301.Comp301Tags;
import util.annotations.Tags;
import util.models.PropertyListenerRegisterer;

@Tags({Comp301Tags.CONSOLE_SCENE_VIEW})
public class ConsoleSceneView implements ConsoleSceneViewInterface {
    public ConsoleSceneView() {
        BridgeSceneInterface bridgeScene = Factory.getBridgeScene();
        this.registerAvatar(bridgeScene.getArthur());
        this.registerAvatar(bridgeScene.getLancelot());
        this.registerAvatar(bridgeScene.getRobin());
        this.registerAvatar(bridgeScene.getGalahad());
        this.registerAvatar(bridgeScene.getGuard());
        this.registerGorge(bridgeScene.getGorge());
        this.registerStandingArea(bridgeScene.getKnightArea());
        this.registerStandingArea(bridgeScene.getGuardArea());
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        System.out.println(event);
    }

    private void registerAvatar(AvatarInterface avatar) {
        this.registerAtomicShape(avatar.getSpeechBubble());
        this.registerAtomicShape(avatar.getHead());
        this.registerLine(avatar.getBody());
        this.registerAngle(avatar.getArms());
        this.registerAngle(avatar.getLegs());
    }

    private void registerAngle(AngleInterface angle) {
        this.registerLine(angle.getLeftLine());
        this.registerLine(angle.getRightLine());
    }

    private void registerGorge(GorgeInterface gorge) {
        this.registerLine(gorge.getLeftWall());
        this.registerLine(gorge.getRightWall());
        this.registerLine(gorge.getBridgeTop());
        this.registerLine(gorge.getBridgeBottom());
    }

    private void registerStandingArea(StandingAreaInterface standingArea) {
        this.registerLine(standingArea.getTopLine());
        this.registerLine(standingArea.getRightLine());
        this.registerLine(standingArea.getBottomLine());
        this.registerLine(standingArea.getLeftLine());
    }

    private void registerLine(LineInterface line) {
        this.registerAtomicShape(line);
        PointInterface end = line.getEnd();
        this.registerAtomicShape(end);
    }

    private void registerAtomicShape(PropertyListenerRegisterer shape) {
        shape.addPropertyChangeListener(this);
    }
}
