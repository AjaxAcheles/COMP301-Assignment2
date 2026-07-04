package grail.compositeShapes.classes;
import grail.compositeShapes.interfaces.AvatarInterface;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import tags301.Comp301Tags;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.BRIDGE_SCENE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Arthur", "Lancelot", "Robin", "Galahad", "Guard"})
public class BridgeScene implements BridgeSceneInterface {
    private static final int ARTHUR_X = 100;
    private static final int ARTHUR_Y = 200;
    private static final int LANCELOT_X = 300;
    private static final int LANCELOT_Y = 200;
    private static final int ROBIN_X = 500;
    private static final int ROBIN_Y = 200;
    private static final int GALAHAD_X = 700;
    private static final int GALAHAD_Y = 200;
    private static final int GUARD_X = 900;
    private static final int GUARD_Y = 200;
    private static final String ARTHUR_SPEECH = "I am Arthur";
    private static final String ARTHUR_IMAGE = "images/arthur.jpg";
    private static final String LANCELOT_SPEECH = "I am Lancelot";
    private static final String LANCELOT_IMAGE = "images/lancelot.jpg";
    private static final String ROBIN_SPEECH = "I am Robin";
    private static final String ROBIN_IMAGE = "images/robin.jpg";
    private static final String GALAHAD_SPEECH = "I am Galahad";
    private static final String GALAHAD_IMAGE = "images/galahad.jpg";
    private static final String GUARD_SPEECH = "I am random guard";
    private static final String GUARD_IMAGE = "images/guard.jpg";
	
    private AvatarInterface arthur;
    private AvatarInterface lancelot;
    private AvatarInterface robin;
    private AvatarInterface galahad;
    private AvatarInterface guard;

    public BridgeScene() {
        this.arthur = new Avatar(ARTHUR_X, ARTHUR_Y, ARTHUR_SPEECH, ARTHUR_IMAGE);
        this.lancelot = new Avatar(LANCELOT_X, LANCELOT_Y, LANCELOT_SPEECH, LANCELOT_IMAGE);
        this.robin = new Avatar(ROBIN_X, ROBIN_Y, ROBIN_SPEECH, ROBIN_IMAGE);
        this.galahad = new Avatar(GALAHAD_X, GALAHAD_Y, GALAHAD_SPEECH, GALAHAD_IMAGE);
        this.guard = new Avatar(GUARD_X, GUARD_Y, GUARD_SPEECH, GUARD_IMAGE);
    }

    @Override
    public AvatarInterface getArthur() {
        return this.arthur;
    }

    @Override
    public AvatarInterface getLancelot() {
        return this.lancelot;
    }

    @Override
    public AvatarInterface getRobin() {
        return this.robin;
    }

    @Override
    public AvatarInterface getGalahad() {
        return this.galahad;
    }
    
    @Override
    public AvatarInterface getGuard() {
        return this.guard;
    }
}