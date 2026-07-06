package grail.compositeShapes.interfaces;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.BRIDGE_SCENE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Arthur", "Lancelot", "Robin", "Galahad", "Guard", "Gorge", "KnightArea", "GuardArea", "Occupied", "KnightTurn"})
@EditablePropertyNames({})
public interface BridgeSceneInterface {
    AvatarInterface getArthur();
    AvatarInterface getLancelot();
    AvatarInterface getRobin();
    AvatarInterface getGalahad();
    AvatarInterface getGuard();
    GorgeInterface getGorge();
    StandingAreaInterface getKnightArea();
    StandingAreaInterface getGuardArea();
    boolean getOccupied();
    boolean getKnightTurn();
    void approach(AvatarInterface avatar);
    void say(String text);
    void passed();
    void failed();
}
