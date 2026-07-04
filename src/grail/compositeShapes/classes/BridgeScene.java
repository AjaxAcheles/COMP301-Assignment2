package grail.compositeShapes.classes;
import grail.compositeShapes.interfaces.AvatarInterface;
import grail.compositeShapes.interfaces.BridgeSceneInterface;
import grail.compositeShapes.interfaces.GorgeInterface;
import grail.compositeShapes.interfaces.StandingAreaInterface;
import tags301.Comp301Tags;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.BRIDGE_SCENE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Arthur", "Lancelot", "Robin", "Galahad", "Guard", "Gorge", "KnightArea", "GuardArea", "Occupied", "KnightTurn"})
public class BridgeScene implements BridgeSceneInterface {
    private static final int ARTHUR_X = 100;
    private static final int ARTHUR_Y = 230;
    private static final int LANCELOT_X = 230;
    private static final int LANCELOT_Y = 230;
    private static final int ROBIN_X = 360;
    private static final int ROBIN_Y = 230;
    private static final int GALAHAD_X = 490;
    private static final int GALAHAD_Y = 230;
    private static final int GUARD_X = 830;
    private static final int GUARD_Y = 230;
    private static final int GORGE_X = 660;
    private static final int GORGE_Y = 80;
    private static final int GORGE_WIDTH = 100;
    private static final int GORGE_HEIGHT = 330;
    private static final int BRIDGE_Y = 220;
    private static final int BRIDGE_HEIGHT = 55;
    private static final int KNIGHT_AREA_X = 575;
    private static final int KNIGHT_AREA_Y = 200;
    private static final int GUARD_AREA_X = 805;
    private static final int GUARD_AREA_Y = 200;
    private static final int AREA_WIDTH = 70;
    private static final int AREA_HEIGHT = 90;
    private static final int PASSED_X = 925;
    private static final int FAILED_X = GORGE_X + GORGE_WIDTH / 2;
    private static final int FAILED_Y = GORGE_Y + GORGE_HEIGHT - 30;
    private static final int GUARD_FAILED_X = FAILED_X;
    private static final int GUARD_FAILED_Y = FAILED_Y;
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
    private GorgeInterface gorge;
    private StandingAreaInterface knightArea;
    private StandingAreaInterface guardArea;
    private boolean knightTurn;

    public BridgeScene() {
        this.arthur = new Avatar(ARTHUR_X, ARTHUR_Y, ARTHUR_SPEECH, ARTHUR_IMAGE);
        this.lancelot = new Avatar(LANCELOT_X, LANCELOT_Y, LANCELOT_SPEECH, LANCELOT_IMAGE);
        this.robin = new Avatar(ROBIN_X, ROBIN_Y, ROBIN_SPEECH, ROBIN_IMAGE);
        this.galahad = new Avatar(GALAHAD_X, GALAHAD_Y, GALAHAD_SPEECH, GALAHAD_IMAGE);
        this.guard = new Avatar(GUARD_X, GUARD_Y, GUARD_SPEECH, GUARD_IMAGE);
        this.gorge = new Gorge(GORGE_X, GORGE_Y, GORGE_WIDTH, GORGE_HEIGHT, BRIDGE_Y, BRIDGE_HEIGHT);
        this.knightArea = new StandingArea(KNIGHT_AREA_X, KNIGHT_AREA_Y, AREA_WIDTH, AREA_HEIGHT);
        this.guardArea = new StandingArea(GUARD_AREA_X, GUARD_AREA_Y, AREA_WIDTH, AREA_HEIGHT);
        this.knightTurn = false;
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

    @Override
    public GorgeInterface getGorge() {
        return this.gorge;
    }

    @Override
    public StandingAreaInterface getKnightArea() {
        return this.knightArea;
    }

    @Override
    public StandingAreaInterface getGuardArea() {
        return this.guardArea;
    }

    @Override
    public boolean getOccupied() {
        return this.getOccupyingKnight() != null;
    }

    @Override
    public boolean getKnightTurn() {
        return this.knightTurn;
    }

    @Override
    @Tags(Comp301Tags.APPROACH)
    public void approach(AvatarInterface avatar) {
        if (!this.isKnight(avatar) || this.getOccupied()) {
            return;
        }
        this.moveTo(avatar, this.knightArea.getCenterX(), this.knightArea.getCenterY());
        this.knightTurn = false;
    }

    @Override
    @Tags(Comp301Tags.SAY)
    public void say(String text) {
        if (this.knightTurn) {
            AvatarInterface knight = this.getOccupyingKnight();
            if (knight != null) {
                knight.getStringShape().setText(text);
            }
        } else {
            this.guard.getStringShape().setText(text);
        }
        this.knightTurn = !this.knightTurn;
    }

    @Override
    @Tags(Comp301Tags.PASSED)
    public void passed() {
        AvatarInterface knight = this.getOccupyingKnight();
        if (knight != null && !this.knightTurn) {
            this.moveTo(knight, PASSED_X, this.knightArea.getCenterY());
            this.knightTurn = false;
        }
    }

    @Override
    @Tags(Comp301Tags.FAILED)
    public void failed() {
        AvatarInterface knight = this.getOccupyingKnight();
        if (this.knightTurn) {
            if (knight != null) {
                this.moveTo(knight, FAILED_X, FAILED_Y);
                this.knightTurn = false;
            }
        } else {
            if (knight != null) {
                this.moveTo(this.guard, GUARD_FAILED_X, GUARD_FAILED_Y);
            }
        }
    }

    private AvatarInterface getOccupyingKnight() {
        if (this.knightArea.contains(this.arthur)) {
            return this.arthur;
        }
        if (this.knightArea.contains(this.lancelot)) {
            return this.lancelot;
        }
        if (this.knightArea.contains(this.robin)) {
            return this.robin;
        }
        if (this.knightArea.contains(this.galahad)) {
            return this.galahad;
        }
        return null;
    }

    private boolean isKnight(AvatarInterface avatar) {
        return avatar == this.arthur
                || avatar == this.lancelot
                || avatar == this.robin
                || avatar == this.galahad;
    }

    private void moveTo(AvatarInterface avatar, int x, int y) {
        avatar.move(x - avatar.getX(), y - avatar.getY());
    }
}
