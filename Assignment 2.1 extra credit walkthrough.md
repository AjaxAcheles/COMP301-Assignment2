# COMP 301 Assignment 2 + Assignment 2.1 Extra Credit — Human Walkthrough

This walkthrough is written for a human student working in Eclipse/VS Code on this repository. It is intentionally detailed because this course grades not only behavior, but also **style, annotations, inheritance structure, package structure, legal imports, and LocalChecks/CheckStyle compliance**.

The current repository already contains a fairly complete Assignment 2 bridge-scene implementation under `src/grail/...` and `src/main/...`, but it **does not currently contain scanner/token code for Assignment 2.1**. Therefore:

- Use the Assignment 2 sections to understand, verify, and style-check the existing bridge-scene code.
- Use the Assignment 2.1 sections to add the extra-credit scanner/token work from scratch or from your Assignment 1.1 scanner solution.
- Do **not** blindly copy code from this walkthrough. Read each step, implement it in your own project, and run checks frequently.

---

## 0. What You Are Building

### Assignment 2 main goal
You extend the bridge-scene/avatar project so that it has:

1. A gorge and bridge.
2. Guard and knight standing areas.
3. Scene methods: `approach`, `say`, `passed`, `failed`, and optional `scroll`.
4. Refactored shape classes using inheritance.
5. A static factory class.
6. Observable atomic shapes using `PropertyChangeEvent`.
7. A console scene view that observes atomic shapes and prints every event.
8. A `main.Assignment2` demo that runs without explicit ObjectEditor refresh calls in Part 3.

### Assignment 2.1 extra credit main goal
You add a scanner/token subsystem that:

1. Recognizes regular token types from Assignment 1.1.
2. Recognizes command words as special command-token classes.
3. Stores tokens in a compact array property instead of printing them.
4. Animates the scanner in ObjectEditor.
5. Refactors token code using inheritance.
6. Optionally creates a clearable token history for advanced extra credit.

---

## 1. Project Setup Checklist

Before editing code, make sure the project is self-contained.

### 1.1 Required files and folders
Your project should contain at least:

```text
Assignment2/
├── .checkstyle
├── .classpath
├── images/
│   ├── arthur.jpg
│   ├── galahad.jpg
│   ├── guard.jpg
│   ├── lancelot.jpg
│   └── robin.jpg
├── src/
│   ├── grail/
│   │   ├── atomicShapes/
│   │   ├── compositeShapes/
│   │   ├── simpleShapes/
│   │   └── views/
│   └── main/
└── Logs/ or logs/ after LocalChecks runs
```

### 1.2 Check the classpath
Open `.classpath`. It should contain only legal libraries. In this repository it currently references:

```xml
<classpathentry kind="lib" path="/home/imarg/Apps/eclipse/COMP 301 stuff/Comp301All.jar"/>
<classpathentry kind="lib" path="/home/imarg/Apps/eclipse/COMP 301 stuff/JUnit4.jar"/>
```

For Gradescope, the assignment warns:

- Assignment 2: do not include libraries/projects other than `Comp301All.jar`.
- Assignment 2.1: `Comp301All.jar` and `JUnit4.jar` are allowed.
- Do **not** include JavaTeaching as a project or library.
- Do **not** copy the entire JavaTeaching directory into the project.

If you use any support class from JavaTeaching, copy only that individual class/interface into your own source tree if the assignment allows it.

### 1.3 CheckStyle file
The current `.checkstyle` in this repository points to:

```xml
unc_checks_301_A2.xml
```

For Assignment 2.1 extra credit, the document says the checkstyle file should be:

```text
unc_checks_301_A2_1.xml
```

So when you switch from Assignment 2 to Assignment 2.1 checks, confirm that Eclipse is using the correct UNC checkstyle configuration.

### 1.4 Run checks early and often
Do not wait until the end. A good rhythm is:

1. Add or change one feature.
2. Compile.
3. Run LocalChecks.
4. Run CheckStyle twice quickly on `src`.
5. Fix style warnings before adding the next feature.

The assignment explicitly says runtime checks depend on source constraints, and style constraints depend on runtime constraints. A program that does nothing can pass some style constraints, so you need all three: compile, runtime behavior, and style.

---

# Part A — Assignment 2 Walkthrough

The existing project already has these relevant files:

```text
src/grail/atomicShapes/classes/Locatable.java
src/grail/atomicShapes/classes/CartesianPoint.java
src/grail/atomicShapes/classes/PolarPoint.java
src/grail/atomicShapes/classes/Text.java
src/grail/simpleShapes/classes/RotatingLine.java
src/grail/simpleShapes/classes/Image.java
src/grail/compositeShapes/classes/BoundedShape.java
src/grail/compositeShapes/classes/Angle.java
src/grail/compositeShapes/classes/Avatar.java
src/grail/compositeShapes/classes/Gorge.java
src/grail/compositeShapes/classes/StandingArea.java
src/grail/compositeShapes/classes/BridgeScene.java
src/grail/views/classes/ConsoleSceneView.java
src/main/Factory.java
src/main/Assignment2.java
src/main/RunSS26A2Tests.java
```

Use the next sections as both an implementation guide and a verification guide.

---

## 2. Assignment 2 Part 1 — Bridge Scene Semantics

### 2.1 Add a gorge and bridge

Create a read-only `Gorge` property in the bridge scene.

Recommended files:

```text
src/grail/compositeShapes/interfaces/GorgeInterface.java
src/grail/compositeShapes/classes/Gorge.java
src/grail/compositeShapes/interfaces/BridgeSceneInterface.java
src/grail/compositeShapes/classes/BridgeScene.java
```

The current repository uses:

```java
GorgeInterface getGorge();
```

and implements `Gorge` with four `RotatingLine`s:

- `LeftWall`
- `RightWall`
- `BridgeTop`
- `BridgeBottom`

This is a good style choice because the assignment says **do not use an image file for the gorge**. Construct it from basic shapes.

### 2.2 Gorge style requirements

In `Gorge.java`:

- Use `@StructurePattern(StructurePatternNames.BEAN_PATTERN)`.
- Use `@PropertyNames({"LeftWall", "RightWall", "BridgeTop", "BridgeBottom"})`.
- Use `@EditablePropertyNames({})` if the gorge itself is read-only.
- Type properties as interfaces, not classes:

```java
private final LineInterface leftWall;
```

not:

```java
private final RotatingLine leftWall;
```

### 2.3 Add standing areas

Create two read-only standing-area properties in the bridge scene:

```java
StandingAreaInterface getKnightArea();
StandingAreaInterface getGuardArea();
```

The current project uses `StandingArea` built from four lines, which is fine:

- `TopLine`
- `RightLine`
- `BottomLine`
- `LeftLine`

The standing areas should appear on the left side of the gorge/bridge communication area, with:

- one area for whichever knight is interacting,
- one area for the guard.

### 2.4 Standing area helper methods

It is okay to add helper methods to the standing-area interface/class, such as:

```java
int getCenterX();
int getCenterY();
boolean contains(AvatarInterface avatar);
```

But be careful:

- If `CenterX` and `CenterY` are only helpers, hide them from ObjectEditor with `@Visible(false)`.
- Do not include invisible helper properties in `@PropertyNames`.
- If a helper method is public and belongs to the interface, the class method must have `@Override`.

### 2.5 Initialize avatar positions

In `BridgeScene` constructor:

1. Put all knights on the left side of the gorge.
2. Put no knight initially inside `KnightArea`.
3. Put the guard in or near `GuardArea`.
4. Initialize conversation state:

```java
private boolean knightTurn;
private AvatarInterface interactingKnight; // or derive it from position
```

The assignment requires:

- `Occupied` is initially false.
- `KnightTurn` is initially false.
- `InteractingKnight` should be `null` when no knight is occupying the area.

### 2.6 Add `Occupied` property

Add a read-only property:

```java
boolean getOccupied();
```

Important: even though Java Bean convention permits `isOccupied()`, the assignment specifically asks for `getOccupied()`.

In `BridgeSceneInterface.java`:

```java
boolean getOccupied();
```

In `BridgeScene.java`:

```java
@Override
public boolean getOccupied() {
    return this.getInteractingKnight() != null;
}
```

or, if you derive occupancy from area containment:

```java
@Override
public boolean getOccupied() {
    return this.getOccupyingKnight() != null;
}
```

### 2.7 Add `KnightTurn` property

Add:

```java
boolean getKnightTurn();
```

It should be true only when:

1. A knight is currently occupying the knight area, and
2. it is the knight's turn to speak.

A safe implementation is:

```java
@Override
public boolean getKnightTurn() {
    return this.getOccupied() && this.knightTurn;
}
```

This avoids reporting a knight turn when no knight is actually interacting.

### 2.8 Add `InteractingKnight` property

The Assignment 2 document explicitly requires this property:

```java
AvatarInterface getInteractingKnight();
```

Rules:

- It returns `null` if the standing area is not occupied.
- It returns the knight currently in the knight area if occupied.
- It must be read-only.
- Put `@Visible(false)` on the getter to avoid a DAG in ObjectEditor.
- Do **not** include `InteractingKnight` in `@PropertyNames`.

Example:

```java
@Override
@Visible(false)
public AvatarInterface getInteractingKnight() {
    return this.getOccupyingKnight();
}
```

Also add it to `BridgeSceneInterface`:

```java
@Visible(false)
AvatarInterface getInteractingKnight();
```

Style note: import `util.annotations.Visible` explicitly. Do not use wildcard imports.

> Repository-specific warning: the current `BridgeScene.java` has a private `getOccupyingKnight()` helper, but no public `getInteractingKnight()` property. If LocalChecks expects this required property, add it.

---

## 3. Implement BridgeScene Methods

All public scene methods must be declared in `BridgeSceneInterface` and implemented in `BridgeScene` with `@Override`.

Required method names are exact:

```java
void approach(AvatarInterface avatar);
void say(String text);
void passed();
void failed();
```

Optional extra-credit method:

```java
void scroll(int changeInX, int changeInY);
```

### 3.1 `approach(AvatarInterface avatar)`

Requirements:

- If the argument is not one of the knights, do nothing.
- If the knight area is already occupied, do nothing.
- Otherwise move that knight into the knight standing area.
- After approach, `Occupied` should be true.
- After approach, the guard speaks first, so `KnightTurn` should be false.

Recommended logic:

```java
@Override
@Tags(Comp301Tags.APPROACH)
public void approach(AvatarInterface avatar) {
    if (!this.isKnight(avatar) || this.getOccupied()) {
        return;
    }

    this.clearSpeech();
    this.moveTo(avatar, this.knightArea.getCenterX(), this.knightArea.getCenterY());
    this.knightTurn = false;
}
```

Style details:

- Use early returns instead of huge nested `if` blocks.
- Do not use magic numbers in this method.
- Use helper methods like `isKnight()` and `moveTo()`.
- If you tag the method, use the correct tag: `Comp301Tags.APPROACH`.

### 3.2 `say(String text)`

Requirements:

- If no knight is occupying the knight area, do nothing.
- If occupied, guard and knight alternate speaking.
- The guard starts the conversation.
- There is no fixed limit on number of questions.
- Avatar speech means setting the avatar's string shape/text property.

Correct control flow:

```java
@Override
@Tags(Comp301Tags.SAY)
public void say(String text) {
    AvatarInterface knight = this.getInteractingKnight();
    if (knight == null) {
        return;
    }

    if (this.knightTurn) {
        this.sayAs(knight, text);
    } else {
        this.sayAs(this.guard, text);
    }

    this.knightTurn = !this.knightTurn;
}
```

> Repository-specific warning: the current `BridgeScene.say()` toggles and lets the guard speak even when no knight is occupying the area. The assignment says `say` should do nothing if the knight area is not occupied. Verify and fix this if you are completing the assignment for grading.

### 3.3 `passed()`

Requirements:

- Do nothing if no knight occupies the knight area.
- A knight can pass only if it is the guard's turn to speak.
- Move the knight to the right side of the gorge/bridge.
- Make the knight area unoccupied.
- Clear or reset speech if needed.
- Reset turn state.

Because the guard starts, the turn pattern is:

```text
After approach: guard turn  => knightTurn == false
After guard says: knight turn => knightTurn == true
After knight says: guard turn => knightTurn == false
```

So `passed()` should normally require `!knightTurn`.

Example:

```java
@Override
@Tags(Comp301Tags.PASSED)
public void passed() {
    AvatarInterface knight = this.getInteractingKnight();
    if (knight == null || this.knightTurn) {
        return;
    }

    this.clearSpeech();
    this.moveTo(knight, PASSED_X, this.knightArea.getCenterY());
    this.knightTurn = false;
}
```

### 3.4 `failed()`

Requirements:

- If no knight occupies the area, do nothing.
- If it is the guard's turn, the knight fails and falls into the gorge.
- If it is the knight's turn, the guard fails and falls into the gorge.
- If a knight fails, the knight area becomes unoccupied.
- It must be possible for a knight to fail immediately after approach, without any `say()` calls.

Simple assignment-faithful logic:

```java
@Override
@Tags(Comp301Tags.FAILED)
public void failed() {
    AvatarInterface knight = this.getInteractingKnight();
    if (knight == null) {
        return;
    }

    this.clearSpeech();

    if (this.knightTurn) {
        this.moveTo(this.guard, FAILED_X, FAILED_Y);
    } else {
        this.moveTo(knight, FAILED_X, FAILED_Y);
    }

    this.knightTurn = false;
}
```

Important gotcha from the assignment:

- Do **not** assume there must be a conversation before `failed()`.
- Do **not** require exactly three questions.
- Do **not** put any hard-coded limit on questions.

### 3.5 Optional `scroll(int changeInX, int changeInY)`

The assignment says positive scroll values mean the **scene components move in the negative direction**.

So:

```java
public void scroll(int changeInX, int changeInY) {
    int moveX = -changeInX;
    int moveY = -changeInY;
    // move every visible component by moveX, moveY
}
```

If you implement scroll, scroll the **whole scene**, not only avatars. That means:

- Arthur
- Lancelot
- Robin
- Galahad
- Guard
- Gorge lines
- KnightArea lines
- GuardArea lines

> Repository-specific warning: the current `BridgeScene.scroll()` moves the avatars but not the gorge or standing areas. That is not a full-scene scroll. If you submit scroll for extra credit, update it to move all scene components or remove it if not required by your checks.

---

## 4. Assignment 2 Part 2 — Inheritance and Factory

### 4.1 Shape inheritance goals

The assignment wants you to remove repeated code and obey IS-A relationships.

Minimum required relationships:

```text
Locatable
└── BoundedShape
    ├── RotatingLine
    ├── Image
    └── StandingArea, if it has X/Y/Width/Height
```

In this repository:

- `Locatable` has `X`, `Y`, and property-change listener storage.
- `BoundedShape` extends `Locatable` and adds `Width`, `Height`.
- `RotatingLine` extends `BoundedShape`.
- `Image` extends `BoundedShape`.
- `StandingArea` extends `BoundedShape`.
- `Avatar` extends `Locatable` because it has `X` and `Y`.

This is broadly aligned with the assignment.

### 4.2 Interface inheritance

Interfaces should mirror the class hierarchy:

```text
LocatableInterface
└── BoundedShapeInterface
    ├── LineInterface
    ├── ImageInterface
    └── StandingAreaInterface
```

Each class should implement **one interface**. That interface may extend other interfaces.

Good:

```java
public class RotatingLine extends BoundedShape implements LineInterface
```

Bad:

```java
public class RotatingLine extends BoundedShape implements LineInterface, BoundedShapeInterface, LocatableInterface
```

### 4.3 Tags in inheritance

Rules from the assignment:

- A class or interface should not have more than one tag.
- Do not tag a subtype with the tags of its supertypes.
- Do not tag a supertype with the tags of its subtypes.
- If a class declares all methods of an interface, the class and interface should have the same tag.

Examples:

```java
@Tags(Comp301Tags.LOCATABLE)
public class Locatable implements LocatableInterface
```

```java
@Tags(Comp301Tags.BOUNDED_SHAPE)
public class BoundedShape extends Locatable implements BoundedShapeInterface
```

```java
@Tags(Comp301Tags.ROTATING_LINE)
public class RotatingLine extends BoundedShape implements LineInterface
```

Do **not** put `LOCATABLE` on `RotatingLine` just because it inherits X/Y.

### 4.4 Duplicate property annotations, not methods

The assignment says subclasses should duplicate property annotations if inherited properties still apply.

For example, `RotatingLine` should list inherited properties in `@PropertyNames`:

```java
@PropertyNames({"X", "Y", "Width", "Height", "Radius", "Angle", "End", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height", "Radius", "Angle"})
```

But you should **not** duplicate inherited methods like `getX()` and `setX()` in `RotatingLine` if they are already inherited from `Locatable`.

### 4.5 Avoid hidden fields

When refactoring, delete duplicate fields from subclasses.

Bad:

```java
public class Locatable {
    private int x;
}

public class Avatar extends Locatable {
    private int x; // BAD: hidden/shadowed field
}
```

Good:

```java
public class Avatar extends Locatable {
    // no x field here; use getX(), setX(), or protected hooks
}
```

In Eclipse, enable warnings:

```text
Window → Preferences → Java → Compiler → Errors/Warnings
→ Name shadowing and conflicts
→ Field declaration hides another field or variable
```

Set it to warning or error.

### 4.6 Use a factory class

Create one factory class tagged:

```java
@Tags(Comp301Tags.FACTORY_CLASS)
public class Factory
```

In this repository it is:

```text
src/main/Factory.java
```

Required methods:

```java
public static BridgeSceneInterface bridgeSceneFactoryMethod()
public static AngleInterface legsFactoryMethod(...)
```

Assignment 2 Part 3 also requires:

```java
public static ConsoleSceneViewInterface consoleSceneViewFactoryMethod()
```

### 4.7 Bridge scene factory method

The bridge scene must be a singleton and lazily created:

```java
private static BridgeSceneInterface bridgeScene;

public static BridgeSceneInterface bridgeSceneFactoryMethod() {
    if (bridgeScene == null) {
        bridgeScene = new BridgeScene();
    }
    return bridgeScene;
}
```

Important style points:

- Field type is the interface: `BridgeSceneInterface`.
- Return type is the interface.
- The constructor call is inside the factory method.
- Main should call the factory, not `new BridgeScene()`.

### 4.8 Legs factory method

If your `Angle` constructor for legs takes:

```java
int initialX, int initialY, double radius, double splitAngleRadians, double downDirectionRadians
```

then the factory should contain:

```java
public static AngleInterface legsFactoryMethod(
        int initialX,
        int initialY,
        double radius,
        double splitAngleRadians,
        double downDirectionRadians) {
    return new Angle(initialX, initialY, radius, splitAngleRadians, downDirectionRadians);
}
```

In `Avatar`, use:

```java
this.legs = Factory.legsFactoryMethod(...);
```

not:

```java
this.legs = new Angle(...);
```

Only legs are specifically required to use the factory. Arms may still use `new Angle(...)` unless your instructor/checks say otherwise.

---

## 5. Assignment 2 Part 3 — Observer/Observable

### 5.1 What must be observable?

Atomic shapes should be observable beans:

- `Locatable`
- `BoundedShape`
- `RotatingLine`
- `Image`
- `Text`
- point classes if ObjectEditor observes them

Each atomic shape should:

1. Implement `PropertyListenerRegisterer` through its interface hierarchy.
2. Store listeners in a read-only invisible property named `PropertyChangeListeners`.
3. Notify all listeners in setters for visible properties.

### 5.2 Listener storage

In `LocatableInterface`:

```java
public interface LocatableInterface extends PropertyListenerRegisterer {
    int getX();
    void setX(int newX);
    int getY();
    void setY(int newY);

    @Visible(false)
    List<PropertyChangeListener> getPropertyChangeListeners();
}
```

In `Locatable`:

```java
private final List<PropertyChangeListener> propertyChangeListeners;
```

Initialize it in the constructor:

```java
this.propertyChangeListeners = new AListenableVector<PropertyChangeListener>();
```

The assignment allows `java.util.List`. Avoid `ArrayList` unless allowed and necessary. The current repository uses `util.models.AListenableVector`, which comes from course utilities.

### 5.3 Notify in setters

For `X`:

```java
@Override
public void setX(int newX) {
    int oldX = this.xCoordinate;
    this.xCoordinate = newX;
    this.locationChanged(newX - oldX, 0);
    this.notifyAllListeners(new PropertyChangeEvent(this, "X", oldX, newX));
}
```

For `Y`:

```java
@Override
public void setY(int newY) {
    int oldY = this.yCoordinate;
    this.yCoordinate = newY;
    this.locationChanged(0, newY - oldY);
    this.notifyAllListeners(new PropertyChangeEvent(this, "Y", oldY, newY));
}
```

### 5.4 Notify only known visible properties

The assignment warns that ObjectEditor will complain if you notify it about invisible or unknown properties.

Examples of known visible properties:

- `X`
- `Y`
- `Width`
- `Height`
- `Text`
- `ImageFileName`

Be careful with `Angle` and `Radius`:

- If they are listed in `@PropertyNames`, notifications may be okay.
- If they are not ObjectEditor-visible for a class, do not notify them.
- For rotating lines, the assignment specifically wants changes in width/height when rotating/scaling.

### 5.5 RotatingLine notifications

When `RotatingLine.rotate()` changes angle, it should ultimately notify about changed `Width` and `Height`, because the drawn line changes shape.

Current repository style:

```java
@Override
public void setAngle(double newAngleRadians) {
    double oldAngle = this.angleRadians;
    int oldWidth = this.getWidth();
    int oldHeight = this.getHeight();
    this.angleRadians = newAngleRadians;
    this.endPoint.setAngle(newAngleRadians);
    this.setWidthWithoutNotification(this.endPoint.getX());
    this.setHeightWithoutNotification(this.endPoint.getY());
    this.notifyAllListeners(new PropertyChangeEvent(this, "Angle", oldAngle, newAngleRadians));
    this.notifyAllListeners(new PropertyChangeEvent(this, "Width", oldWidth, this.getWidth()));
    this.notifyAllListeners(new PropertyChangeEvent(this, "Height", oldHeight, this.getHeight()));
}
```

If LocalChecks complains about `Angle` notifications being unknown, keep width/height notifications and remove/adjust unknown property notifications according to ObjectEditor messages.

### 5.6 Composite shapes should usually not notify

The assignment says you should not send notifications about composite shapes. Instead, when a composite moves, it should move its atomic children, and the atomic children should send notifications.

Example: `Avatar.move()` should not replace the avatar. It should call `setX`/`setY` and move existing body parts.

Bad:

```java
this.arthur = new Avatar(...); // BAD inside movement logic
```

Good:

```java
avatar.move(dx, dy); // existing object, existing parts
```

### 5.7 Console scene view

Create:

```text
src/grail/views/interfaces/ConsoleSceneViewInterface.java
src/grail/views/classes/ConsoleSceneView.java
```

Interface:

```java
@Tags(Comp301Tags.CONSOLE_SCENE_VIEW)
public interface ConsoleSceneViewInterface extends PropertyChangeListener {
}
```

Class:

```java
@Tags(Comp301Tags.CONSOLE_SCENE_VIEW)
public class ConsoleSceneView implements ConsoleSceneViewInterface {
    public ConsoleSceneView() {
        BridgeSceneInterface bridgeScene = Factory.bridgeSceneFactoryMethod();
        // register this with every atomic shape in the scene
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        System.out.println(event);
    }
}
```

Critical printing rule:

```java
System.out.println(event);
```

Do **not** custom-format it. This can break tests.

### 5.8 Register every atomic shape

For each avatar, register with:

- speech text shape
- head image
- body line
- both arm lines
- both leg lines

For each line, register with:

- the line itself
- possibly its endpoint if the endpoint announces visible properties and LocalChecks expects it

For the gorge, register with:

- left wall
- right wall
- bridge top
- bridge bottom

For each standing area, register with:

- top line
- right line
- bottom line
- left line

The current repository's `ConsoleSceneView` does this with helper methods:

```java
registerAvatar(...)
registerAngle(...)
registerGorge(...)
registerStandingArea(...)
registerLine(...)
registerAtomicShape(...)
```

This is good style because it avoids duplicate registration code.

---

## 6. Assignment 2 Demo Main

The required class is:

```text
src/main/Assignment2.java
```

### 6.1 Main method requirements

Assignment 2 Part 3 main should:

1. Get the scene through the factory.
2. Display it with ObjectEditor.
3. Get/create the console scene view through the factory.
4. Run an animation that calls every animation/scene method.
5. Avoid `oeFrame.refresh()` because ObjectEditor should update through observers.

Example pattern:

```java
public static void main(String[] args) {
    BridgeSceneInterface bridgeScene = Factory.bridgeSceneFactoryMethod();
    ObjectEditor.edit(bridgeScene).setSize(EDITOR_WIDTH, EDITOR_HEIGHT);
    consoleSceneView = Factory.consoleSceneViewFactoryMethod();
    runAnimation(bridgeScene);
}
```

### 6.2 Demo coverage

Your animation should demonstrate:

- legacy avatar movement from Assignment 1,
- limb rotation,
- one knight approaching and passing,
- one knight failing,
- the guard failing if implemented,
- all public scene methods required by the assignment.

Good sequence:

```text
1. Move Galahad slightly.
2. Rotate Galahad's arms.
3. Arthur approaches.
4. Guard asks Arthur a question.
5. Arthur answers.
6. Guard asks another question.
7. Arthur answers.
8. Arthur passes.
9. Lancelot approaches.
10. Conversation occurs.
11. Lancelot fails.
12. Robin approaches.
13. Conversation causes guard to fail.
```

### 6.3 Style in main

Use constants for all strings and timing values:

```java
private static final int PAUSE_TIME = 1800;
private static final String NAME_QUESTION = "What is your name?";
```

Do not write magic numbers throughout the animation:

Bad:

```java
ThreadSupport.sleep(1800);
```

Good:

```java
ThreadSupport.sleep(PAUSE_TIME);
```

---

# Part B — Assignment 2.1 Extra Credit Walkthrough

The current repository does **not** have scanner/token source files. You will either:

1. Copy your Assignment 1.1 scanner/token code into this project and refactor it, or
2. Create the scanner/token subsystem from scratch.

The assignment assumes you already completed Assignment 1.1. If you have that project, copy only the relevant scanner/token files and then refactor them here.

---

## 7. Recommended Package Structure for Assignment 2.1

Pick names consistent with your existing project. Since this project uses `grail` packages, a reasonable structure is:

```text
src/grail/tokens/interfaces/
src/grail/tokens/classes/
src/grail/scanner/interfaces/
src/grail/scanner/classes/
src/main/Assignment2_1_Part1.java
src/main/Assignment_2_1_Part_2.java
```

Example files:

```text
src/grail/tokens/interfaces/TokenInterface.java
src/grail/tokens/interfaces/WordInterface.java
src/grail/tokens/interfaces/NumberTokenInterface.java
src/grail/tokens/interfaces/QuotedStringInterface.java
src/grail/tokens/interfaces/CommandInterface.java

src/grail/tokens/classes/Token.java
src/grail/tokens/classes/Word.java
src/grail/tokens/classes/NumberToken.java
src/grail/tokens/classes/QuotedString.java
src/grail/tokens/classes/Move.java
src/grail/tokens/classes/Say.java
src/grail/tokens/classes/Repeat.java
src/grail/tokens/classes/Approach.java
src/grail/tokens/classes/Passed.java
src/grail/tokens/classes/Failed.java

src/grail/scanner/interfaces/ScannerBeanInterface.java
src/grail/scanner/classes/ScannerBean.java
```

Naming note:

- Avoid naming your number token class simply `Number`, because `java.lang.Number` already exists and can cause confusion.
- `NumberToken` is clearer.
- Use your exact Assignment 1.1 required names if LocalChecks expects different names.

---

## 8. Assignment 2.1 Part 1a — Command Token Classes

### 8.1 Required command tokens

Create a token class for each required command:

| Command tag | Suggested class |
|---|---|
| `Comp301Tags.MOVE` | `Move` |
| `Comp301Tags.SAY` | `Say` |
| `Comp301Tags.REPEAT` | `Repeat` |
| `Comp301Tags.APPROACH` | `Approach` |
| `Comp301Tags.PASSED` | `Passed` |
| `Comp301Tags.FAILED` | `Failed` |

Advanced extra credit commands:

| Command tag/name | Suggested class |
|---|---|
| `RotateLeftArm` | `RotateLeftArm` |
| `RotateRightArm` | `RotateRightArm` |
| `Define` | `Define` |
| `Call` | `Call` |
| `Thread` | `ThreadCommand` or `ThreadToken` |
| `Wait` | `Wait` |
| `ProceedAll` | `ProceedAll` |
| `Sleep` | `Sleep` |
| `Undo` | `Undo` |
| `Redo` | `Redo` |

Be careful with `Thread`: `java.lang.Thread` exists. Do not create confusing code. If LocalChecks requires the exact class name `Thread`, use it carefully. Otherwise `ThreadCommand` is safer.

### 8.2 Part 1 quick implementation option

Part 1 allows copy/paste from `Word`, but Part 2 requires inheritance. To save time and improve style, implement the inheritance design immediately.

Use this hierarchy:

```text
TokenInterface
└── WordInterface
    └── CommandInterface

Token
└── Word
    ├── Move
    ├── Say
    ├── Repeat
    ├── Approach
    ├── Passed
    └── Failed
```

### 8.3 Common token interface

Example:

```java
package grail.tokens.interfaces;

import tags301.Comp301Tags;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.TOKEN)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input"})
public interface TokenInterface {
    String getInput();
}
```

If your Assignment 1.1 token property is named something else, use the name expected by that assignment/local checks. The 2.1 document says command tokens have the same properties as the word token.

### 8.4 Base token class

```java
package grail.tokens.classes;

import grail.tokens.interfaces.TokenInterface;
import tags301.Comp301Tags;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.TOKEN)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input"})
public class Token implements TokenInterface {
    private final String input;

    public Token(String initialInput) {
        this.input = initialInput;
    }

    @Override
    public String getInput() {
        return this.input;
    }
}
```

Style notes:

- Use `private final` for token input if tokens are immutable.
- Do not add setters unless the assignment requires editable token properties.
- Do not print from token constructors.

### 8.5 Word interface and class

```java
@Tags(Comp301Tags.WORD)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input"})
public interface WordInterface extends TokenInterface {
}
```

```java
@Tags(Comp301Tags.WORD)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input"})
public class Word extends Token implements WordInterface {
    public Word(String initialInput) {
        super(initialInput);
    }
}
```

### 8.6 Command classes

For command classes, the assignment says they have the same properties and constructors as word tokens. With inheritance, each class is tiny.

Example `Move.java`:

```java
package grail.tokens.classes;

import grail.tokens.interfaces.CommandInterface;
import tags301.Comp301Tags;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.MOVE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input"})
public class Move extends Word implements CommandInterface {
    public Move(String initialInput) {
        super(initialInput);
    }
}
```

Repeat for `Say`, `Repeat`, `Approach`, `Passed`, and `Failed`, changing only:

- class name,
- constructor name,
- tag.

### 8.7 Single-interface rule for commands

If each command class implements `CommandInterface`, then `CommandInterface` should extend `WordInterface`:

```java
public interface CommandInterface extends WordInterface {
}
```

This way each command class implements one interface only.

Potential LocalChecks issue: some checks may expect each tagged command to have its own tagged interface. If so, create one interface per command:

```java
@Tags(Comp301Tags.MOVE)
public interface MoveInterface extends CommandInterface {
}
```

and then:

```java
public class Move extends Word implements MoveInterface
```

This still satisfies the single-interface rule. Each class implements only its own interface, while the interface inherits from `CommandInterface`/`WordInterface`/`TokenInterface`.

---

## 9. Assignment 2.1 Part 1b — Scanner Bean Stores Tokens

### 9.1 Scanner bean properties

The scanner bean needs at least two properties:

1. Editable scanned string property.
2. Read-only compact token array property named `Tokens`.

Example interface:

```java
package grail.scanner.interfaces;

import grail.tokens.interfaces.TokenInterface;

public interface ScannerBeanInterface {
    String getScannedString();
    void setScannedString(String newValue);
    TokenInterface[] getTokens();
}
```

If Assignment 1.1 used a different property name, keep the expected name from that assignment. The 2.1 examples use `setScannedString(...)`.

### 9.2 Scanner bean annotations

Example:

```java
@Tags(Comp301Tags.SCANNER)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"ScannedString", "Tokens"})
@EditablePropertyNames({"ScannedString"})
public class ScannerBean implements ScannerBeanInterface {
    ...
}
```

If `Comp301Tags.SCANNER` does not exist in your library, use the scanner tag required by Assignment 1.1. Check your previous solution and LocalChecks output.

### 9.3 Initialize properties to non-null values

The assignment is very explicit:

- The scanned string should initially be `""`.
- The compact token array should initially be an empty array.
- Getters must never return `null`.

Example:

```java
private String scannedString = "";
private TokenInterface[] tokens = new TokenInterface[0];
```

Do **not** call the setter from the constructor just to initialize an empty string. Your scanner may not handle empty scanning yet, and the assignment says initialize directly.

### 9.4 Do not use ArrayList or Vector

For this assignment, token storage must use arrays.

Forbidden for the scanner token array:

```java
ArrayList<TokenInterface>
Vector<TokenInterface>
List<TokenInterface>
```

Correct:

```java
TokenInterface[] largeTokens = new TokenInterface[MAX_TOKENS];
TokenInterface[] compactTokens = new TokenInterface[tokenCount];
```

### 9.5 Large array and compact array algorithm

Inside `setScannedString`:

1. Store the new string.
2. Create a large temporary array.
3. Scan tokens into the large array.
4. Count how many tokens were added.
5. Create compact array of exactly that length.
6. Copy token references from large array to compact array.
7. Assign compact array to the instance variable returned by `getTokens()`.

Example structure:

```java
@Override
public void setScannedString(String newValue) {
    this.scannedString = newValue;

    TokenInterface[] largeTokens = new TokenInterface[this.scannedString.length()];
    int tokenCount = 0;

    // scanning loop adds tokens to largeTokens[tokenCount]
    // then increments tokenCount

    this.tokens = new TokenInterface[tokenCount];
    int index = 0;
    while (index < tokenCount) {
        this.tokens[index] = largeTokens[index];
        index++;
    }
}
```

Why `this.scannedString.length()` is safe: the number of tokens cannot exceed the number of characters in the input.

If the input is empty, length is `0`, and the compact array remains length `0`.

### 9.6 Token recognition order

Your scanner from Assignment 1.1 likely recognizes:

- words,
- numbers,
- quoted strings,
- plus/minus or other symbols,
- `{` and `}` or other command-language punctuation.

When scanning, use an order like:

1. Skip whitespace.
2. If current char starts a word, scan full word.
3. If current char starts a number, scan full number.
4. If current char is a quote, scan quoted string.
5. If current char is a special symbol, create the appropriate token.
6. Otherwise handle illegal/unrecognized chars as your Assignment 1.1 required.

### 9.7 Command classification

When you scan a word, do not immediately create `Word`. Instead:

1. Extract the word substring.
2. Compare it case-insensitively to each command.
3. Create the command token if it matches.
4. Otherwise create a regular `Word` token.

Example:

```java
private TokenInterface createWordOrCommand(String wordText) {
    if (wordText.equalsIgnoreCase(Comp301Tags.MOVE)) {
        return new Move(wordText);
    }
    if (wordText.equalsIgnoreCase(Comp301Tags.SAY)) {
        return new Say(wordText);
    }
    if (wordText.equalsIgnoreCase(Comp301Tags.REPEAT)) {
        return new Repeat(wordText);
    }
    if (wordText.equalsIgnoreCase(Comp301Tags.APPROACH)) {
        return new Approach(wordText);
    }
    if (wordText.equalsIgnoreCase(Comp301Tags.PASSED)) {
        return new Passed(wordText);
    }
    if (wordText.equalsIgnoreCase(Comp301Tags.FAILED)) {
        return new Failed(wordText);
    }
    return new Word(wordText);
}
```

Style note: if CheckStyle complains about too many returns or method length, split advanced commands into helper methods.

### 9.8 Preserve original input spelling

If the input says:

```text
MoVE
```

The token should be a `Move` token, but its input property should probably preserve `"MoVE"`, because the token represents what was scanned.

Do this:

```java
return new Move(wordText);
```

not:

```java
return new Move(Comp301Tags.MOVE);
```

unless your previous assignment explicitly normalized token text.

### 9.9 Getter must not create compact array

The assignment says the compact array should be created in the setter, not in the getter.

Good:

```java
@Override
public TokenInterface[] getTokens() {
    return this.tokens;
}
```

Bad:

```java
@Override
public TokenInterface[] getTokens() {
    return compactTokensFromLargeArray(); // BAD for this assignment
}
```

---

## 10. Assignment 2.1 Part 1c — Animate Scanner

Create:

```text
src/main/Assignment2_1_Part1.java
```

The class name must be exactly:

```java
package main;

public class Assignment2_1_Part1 {
    ...
}
```

### 10.1 Main and animateScanner

The assignment requires a separate method tagged `animateScanner()` that main calls.

Example:

```java
public static void main(String[] args) {
    ScannerBeanInterface scannerBean = new ScannerBean();
    animateScanner(scannerBean);
}

@Tags(Comp301Tags.ANIMATE_SCANNER)
public static void animateScanner(ScannerBeanInterface scannerBean) {
    OEFrame oeFrame = ObjectEditor.edit(scannerBean);
    ...
}
```

If `Comp301Tags.ANIMATE_SCANNER` does not exist, check the exact tag name in your course library or assignment notes. The document says the method should be tagged `animateScanner()`; in UNC assignments this often means a tag constant exists, but verify.

### 10.2 ObjectEditor animation pattern

Part 1c allows/uses `refresh()`:

```java
OEFrame oeFrame = ObjectEditor.edit(scannerBean);
scannerBean.setScannedString("MoVe 050 { saY \"hi!\" }");
oeFrame.refresh();
ThreadSupport.sleep(3000);
```

Imports:

```java
import bus.uigen.ObjectEditor;
import bus.uigen.OEFrame;
import util.misc.ThreadSupport;
```

### 10.3 Test strings must cover every command

Include every required command in at least one string:

```java
private static final String TEST_REQUIRED_COMMANDS =
        "move say repeat approach passed failed";
```

Also include mixed case:

```java
private static final String TEST_MIXED_CASE =
        "MoVe 050 { saY \"hi!\" }";
```

If you implement advanced commands, include them too:

```java
private static final String TEST_ADVANCED_COMMANDS =
        "RotateLeftArm RotateRightArm Define Call Thread Wait ProceedAll Sleep Undo Redo";
```

The assignment says if you do not demonstrate a feature, graders may assume it is not implemented.

### 10.4 Scanner main should not read from console

For Assignment 2.1:

- Do not use `java.util.Scanner` in this main class.
- Do not ask the user for input.
- Do not print token results instead of displaying ObjectEditor.
- The scanner setter should store tokens, not print them.

Printing as a backup is allowed only if ObjectEditor refresh has issues, and even then it should be supplementary.

---

## 11. Assignment 2.1 Part 2 — Refactor Tokens for Inheritance

If you already used the hierarchy above, this part should be mostly done.

### 11.1 Create Part 2 main

Create:

```text
src/main/Assignment_2_1_Part_2.java
```

The assignment name includes underscores exactly:

```java
public class Assignment_2_1_Part_2
```

Copy the Part 1 scanner animation code. It should still work after token refactoring.

### 11.2 Required token inheritance

The assignment says:

- Create a class tagged `Comp301Tags.TOKEN`.
- It implements the common token interface.
- All other token classes are direct or indirect subclasses of it.
- Command token classes are subclasses of the word class.
- Each class implements a single interface.

Target hierarchy:

```text
TokenInterface                          @TOKEN
├── WordInterface                       @WORD
│   └── command-specific interfaces     @MOVE, @SAY, etc. if needed
├── NumberTokenInterface                @NUMBER or required tag
├── QuotedStringInterface               required quote/string tag
└── symbol-token interfaces             required tags

Token                                   @TOKEN
├── Word                                @WORD
│   ├── Move                            @MOVE
│   ├── Say                             @SAY
│   ├── Repeat                          @REPEAT
│   ├── Approach                        @APPROACH
│   ├── Passed                          @PASSED
│   └── Failed                          @FAILED
├── NumberToken
├── QuotedString
└── symbol-token classes
```

### 11.3 Delete duplicate variables and methods

After refactoring:

- `Word` should not have its own `input` field if `Token` already has it.
- `Move` should not have its own `input` field.
- `Move` should not re-implement `getInput()` if inherited from `Token` through `Word`.

Command classes should look like this:

```java
public class Move extends Word implements MoveInterface {
    public Move(String initialInput) {
        super(initialInput);
    }
}
```

That is all.

### 11.4 Annotation duplication

Even if the command class has no methods except a constructor, keep relevant ObjectEditor annotations if they apply:

```java
@Tags(Comp301Tags.MOVE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input"})
public class Move extends Word implements MoveInterface
```

Do not duplicate superclass tags.

### 11.5 Do not use classes as types

The assignment says public methods and variables should use interfaces as types.

Good:

```java
private TokenInterface[] tokens;
public TokenInterface[] getTokens()
```

Bad:

```java
private Token[] tokens;
public Token[] getTokens()
```

Constructor calls naturally use classes:

```java
return new Move(wordText);
```

That is okay.

---

## 12. Assignment 2.1 Advanced Extra Credit — Clearable Token History

This section is optional and worth advanced extra credit.

### 12.1 Goal

Create a clearable history structure that stores tokens using arrays, not Java collection classes.

The scanner should expose another read-only property:

```java
TokenHistoryInterface getTokenList();
```

The assignment calls this property `TokenList`.

### 12.2 Structure

Suggested files:

```text
src/grail/tokens/history/interfaces/TokenHistoryInterface.java
src/grail/tokens/history/interfaces/ClearableTokenHistoryInterface.java
src/grail/tokens/history/classes/TokenHistory.java
src/grail/tokens/history/classes/ClearableTokenHistory.java
```

Use the Praxis `BaseStringHistory` / `ABaseStringHistory` idea, but store `TokenInterface` instead of `String`.

### 12.3 Required clear operation

Add:

```java
void clear();
```

Tag it with the clear tag if the course library provides one:

```java
@Tags(Comp301Tags.CLEAR)
void clear();
```

The class should be tagged:

```java
@Tags(Comp301Tags.CLEARABLE_HISTORY)
```

Use the exact constant names available in your `Comp301Tags`. If a constant name differs, follow the course jar.

### 12.4 Vector pattern

The assignment says to use:

```java
@StructurePattern(StructurePatternNames.VECTOR_PATTERN)
```

because the history follows vector-like naming conventions.

### 12.5 Scanner integration

Each time the scanner scans a new string:

1. Clear the history.
2. Create a token for the compact array.
3. Create a second token object for the history.
4. Add the second token to history.

Important: the assignment says do **not** put the same token object in both the array and history, because that creates a DAG.

So do not do this:

```java
TokenInterface token = createWordOrCommand(wordText);
largeTokens[tokenCount] = token;
tokenHistory.addElement(token); // BAD for this assignment's DAG warning
```

Instead:

```java
largeTokens[tokenCount] = createWordOrCommand(wordText);
tokenHistory.addElement(createWordOrCommand(wordText));
```

---

# Part C — Style Guide for This Assignment

This section is extremely important. The Assignment 2 document repeatedly emphasizes style, and your grade can suffer even if the animation looks correct.

---

## 13. Universal Java Style Rules

### 13.1 No wildcard imports

Bad:

```java
import java.util.*;
import util.annotations.*;
```

Good:

```java
import java.util.List;
import util.annotations.Tags;
import util.annotations.PropertyNames;
```

### 13.2 Legal imports only

Assignment 2.1 lists legal import categories:

- packages beginning with your internal prefixes such as `mp` or `grail`,
- `bus.uigen`,
- `util`,
- `shapes`,
- `java.util.Scanner`,
- `java.util.List`,
- `java.util.Iterator`,
- `java.util.NoSuchElementException`,
- any explicitly authorized course imports.

Do not import unapproved convenience libraries.

For Assignment 2.1 arrays, do not import or use:

```java
java.util.ArrayList
java.util.Vector
```

### 13.3 No magic numbers

Bad:

```java
this.guard = new Avatar(830, 230, "I am random guard", "images/guard.jpg");
```

Good:

```java
private static final int GUARD_X = 830;
private static final int GUARD_Y = 230;
private static final String GUARD_SPEECH = "I am random guard";
private static final String GUARD_IMAGE = "images/guard.jpg";

this.guard = new Avatar(GUARD_X, GUARD_Y, GUARD_SPEECH, GUARD_IMAGE);
```

The current repository generally follows this pattern in `BridgeScene.java` and `Assignment2.java`.

### 13.4 Encapsulation

Do not make non-final fields public.

Bad:

```java
public int x;
```

Good:

```java
private int xCoordinate;
```

or, if subclass access is needed:

```java
protected int xCoordinate;
```

But prefer private plus protected helper hooks when possible.

### 13.5 Use interfaces as types

Bad:

```java
private Avatar arthur;
public Avatar getArthur()
```

Good:

```java
private final AvatarInterface arthur;
public AvatarInterface getArthur()
```

The existing repository follows this style in many places.

### 13.6 Every public method from an interface gets `@Override`

Bad:

```java
public void move(int x, int y) {
    ...
}
```

Good:

```java
@Override
public void move(int x, int y) {
    ...
}
```

### 13.7 Avoid overly complicated booleans

Bad:

```java
if (this.getOccupied() == true) {
```

Good:

```java
if (this.getOccupied()) {
```

Bad:

```java
return this.getOccupied() ? true : false;
```

Good:

```java
return this.getOccupied();
```

### 13.8 Avoid duplicate code with helpers

Good helpers in this kind of assignment:

```java
private void moveTo(AvatarInterface avatar, int targetX, int targetY)
private void clearSpeech()
private void sayAs(AvatarInterface avatar, String text)
private boolean isKnight(AvatarInterface avatar)
private AvatarInterface getOccupyingKnight()
```

Good scanner helpers:

```java
private TokenInterface createWordOrCommand(String text)
private int scanWord(String input, int startIndex, TokenInterface[] largeTokens, int tokenCount)
private boolean isWordStart(char character)
private boolean isWordPart(char character)
```

### 13.9 Keep methods reasonably short

If a method gets long, split it.

For example, scanner `setScannedString` can call:

```java
private int scanTokensInto(TokenInterface[] largeTokens)
private void compactTokens(TokenInterface[] largeTokens, int tokenCount)
```

### 13.10 Do not create objects in getters

The assignment explicitly says getters and setters should not create objects, with the scanner setter being an exception for token arrays.

Bad:

```java
public GorgeInterface getGorge() {
    return new Gorge(...); // BAD
}
```

Good:

```java
public GorgeInterface getGorge() {
    return this.gorge;
}
```

### 13.11 Do not replace shape objects to move them

Bad:

```java
this.head = new Image(newX, newY, ...); // BAD movement strategy
```

Good:

```java
this.head.setX(this.head.getX() + changeInX);
this.head.setY(this.head.getY() + changeInY);
```

This matters because observers are registered with existing atomic shape objects. If you replace objects, observers keep listening to old objects and animation/notifications break.

---

## 14. Annotation Checklist

For each bean class, ask:

### 14.1 Does it need `@Tags`?
Most assignment-recognized classes/interfaces need tags:

- `BridgeScene` and `BridgeSceneInterface`: `BRIDGE_SCENE`
- `Avatar` and `AvatarInterface`: `AVATAR`
- `Angle` and `AngleInterface`: `ANGLE`
- `RotatingLine` and `LineInterface`: `ROTATING_LINE`
- `Locatable` and `LocatableInterface`: `LOCATABLE`
- `BoundedShape` and `BoundedShapeInterface`: `BOUNDED_SHAPE`
- `Factory`: `FACTORY_CLASS`
- `ConsoleSceneView` and interface: `CONSOLE_SCENE_VIEW`
- token classes/interfaces for Assignment 2.1: token-specific tags

### 14.2 Does it need `@StructurePattern`?
Usually yes.

Examples:

```java
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
```

For graphical primitives:

```java
@StructurePattern(StructurePatternNames.LINE_PATTERN)
@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
@StructurePattern(StructurePatternNames.STRING_PATTERN)
@StructurePattern(StructurePatternNames.POINT_PATTERN)
```

For advanced token history:

```java
@StructurePattern(StructurePatternNames.VECTOR_PATTERN)
```

### 14.3 Are `@PropertyNames` complete and ordered?
Subclasses should list inherited visible properties too.

For example, `Image` extends `BoundedShape`, so it lists:

```java
@PropertyNames({"X", "Y", "Width", "Height", "Text", "ImageFileName", "PropertyChangeListeners"})
```

### 14.4 Are editable properties correct?
Read-only composites often have:

```java
@EditablePropertyNames({})
```

Editable atomic shapes often have:

```java
@EditablePropertyNames({"X", "Y", "Width", "Height"})
```

Scanner bean should have only the scanned string editable:

```java
@EditablePropertyNames({"ScannedString"})
```

### 14.5 Are invisible helper properties excluded?
If a getter has:

```java
@Visible(false)
```

then usually do not include it in `@PropertyNames`, except for specific course-required invisible properties like `PropertyChangeListeners` if the examples/checks expect it.

---

## 15. Common Bugs and How to Fix Them

### Bug 1: `say()` works when no knight has approached

Symptom:

- Guard speech changes before `approach()`.
- LocalChecks says `say` should do nothing when unoccupied.

Fix:

```java
AvatarInterface knight = this.getInteractingKnight();
if (knight == null) {
    return;
}
```

at the top of `say()`.

### Bug 2: Missing `InteractingKnight`

Symptom:

- LocalChecks cannot find property/method.

Fix:

- Add `AvatarInterface getInteractingKnight();` to the interface.
- Add a public `@Override @Visible(false)` getter to `BridgeScene`.
- Do not include it in `@PropertyNames`.

### Bug 3: Knight cannot fail immediately

Symptom:

- After `approach(knight)`, calling `failed()` does nothing.

Fix:

- Make sure `failed()` supports the guard-turn case immediately after approach.
- Since guard starts, `knightTurn` should be false immediately after approach.
- If `!knightTurn`, the knight should fail.

### Bug 4: `passed()` works on knight's turn

Symptom:

- Knight passes immediately after the guard asks a question.

Fix:

- Require `!knightTurn` for `passed()`.

### Bug 5: CheckStyle says hidden fields

Symptom:

- A subclass declares a field already declared in a superclass.

Fix:

- Delete duplicate subclass field.
- Use inherited getter/setter.
- Add protected hook methods if subclasses need to react to changes.

### Bug 6: ObjectEditor does not update during Assignment 2 Part 3 animation

Causes:

- Atomic shape setters are not notifying listeners.
- Console view/ObjectEditor is registered with old objects because you replaced shapes.
- You are moving composites without moving atomic children.

Fix:

- Notify in atomic setters.
- Move existing components instead of replacing them.
- Register console view with every atomic child.

### Bug 7: Console view output format is wrong

Bad:

```java
System.out.println("Changed " + event.getPropertyName());
```

Fix:

```java
System.out.println(event);
```

### Bug 8: Scanner `getTokens()` returns null

Fix:

```java
private TokenInterface[] tokens = new TokenInterface[0];
```

### Bug 9: Scanner compact array has null slots

Symptom:

```text
{ Move token, Say token, null, null, null }
```

Fix:

- Return only the compact array.
- Compact length must equal token count.

### Bug 10: Scanner recognizes `MoVE` as Word, not Move

Fix:

Use:

```java
wordText.equalsIgnoreCase(Comp301Tags.MOVE)
```

not case-sensitive `equals`.

### Bug 11: Using `ArrayList` in Assignment 2.1 scanner

Fix:

Use arrays only for scanner token storage.

### Bug 12: Command tokens duplicate all Word code

Part 1 allows it, but Part 2 requires inheritance. Fix by making command classes extend `Word` and contain only a constructor.

---

# Part D — Suggested Work Order From Start to Finish

Use this order if you are completing the assignment cleanly.

## 16. Assignment 2 Work Order

### Step 1: Compile the copied Assignment 1 project

Before adding Assignment 2 features, make sure your copied A1 project compiles.

### Step 2: Add/verify inherited base classes

Implement or verify:

```text
LocatableInterface / Locatable
BoundedShapeInterface / BoundedShape
```

Check:

- `Locatable` has X/Y.
- `BoundedShape` extends `Locatable` and has Width/Height.
- No subclass duplicates these fields.

### Step 3: Refactor atomic/simple shapes

Implement or verify:

```text
CartesianPoint
PolarPoint
Text
RotatingLine
Image
```

Check annotations and property names.

### Step 4: Refactor composite shapes

Implement or verify:

```text
Angle
Avatar
Gorge
StandingArea
BridgeScene
```

Check that composite shapes use interfaces for child fields.

### Step 5: Add bridge-scene properties

In `BridgeSceneInterface` and `BridgeScene`, add:

```java
getGorge()
getKnightArea()
getGuardArea()
getOccupied()
getKnightTurn()
getInteractingKnight()
```

Remember `getInteractingKnight()` is hidden and not in `@PropertyNames`.

### Step 6: Add scene methods

Add:

```java
approach(...)
say(...)
passed()
failed()
```

Then manually test the state sequence:

```text
Initially: Occupied false, KnightTurn false
After approach Arthur: Occupied true, KnightTurn false
After say guard question: Occupied true, KnightTurn true
After say Arthur answer: Occupied true, KnightTurn false
After passed: Occupied false, KnightTurn false
```

### Step 7: Add factory

Implement:

```java
bridgeSceneFactoryMethod()
legsFactoryMethod(...)
```

Update `Avatar` to use `Factory.legsFactoryMethod(...)` for legs.

Update `Assignment2.main` to use `Factory.bridgeSceneFactoryMethod()`.

### Step 8: Add observer support

Implement listener registration and notification in atomic shapes.

Start with `Locatable`, then `BoundedShape`, then classes with additional properties:

- `Text.setText`
- `Image.setText`
- `Image.setImageFileName`
- `RotatingLine.setRadius`
- `RotatingLine.setAngle`

### Step 9: Add console scene view

Implement:

```text
ConsoleSceneViewInterface
ConsoleSceneView
Factory.consoleSceneViewFactoryMethod()
```

Register with every atomic shape.

### Step 10: Finalize `main.Assignment2`

Main should:

- get scene from factory,
- edit scene in ObjectEditor,
- get console scene view from factory,
- animate all required behavior,
- not call `refresh()`.

### Step 11: Run Assignment 2 checks

Run:

```text
main.RunSS26A2Tests
```

Then fix:

1. compile errors,
2. source constraint errors,
3. runtime errors,
4. checkstyle warnings.

---

## 17. Assignment 2.1 Extra Credit Work Order

### Step 1: Bring in or create scanner/token packages

Create packages under `src/grail` or copy your A1.1 scanner/token code.

Do not disturb existing Assignment 2 bridge-scene packages unless necessary.

### Step 2: Create base token hierarchy

Implement:

```text
TokenInterface
Token
WordInterface
Word
```

Then refactor existing token types from A1.1 to extend `Token`.

### Step 3: Add command interfaces/classes

Implement required commands:

```text
Move
Say
Repeat
Approach
Passed
Failed
```

Optional advanced commands:

```text
RotateLeftArm
RotateRightArm
Define
Call
Thread/ThreadCommand
Wait
ProceedAll
Sleep
Undo
Redo
```

### Step 4: Implement scanner compact token array

Scanner bean should have:

```java
String getScannedString();
void setScannedString(String newValue);
TokenInterface[] getTokens();
```

Setters scan and compact.

Getters do not create objects.

### Step 5: Implement command detection

In word scanning, call a helper like:

```java
private TokenInterface createWordOrCommand(String wordText)
```

Use `equalsIgnoreCase`.

### Step 6: Create `main.Assignment2_1_Part1`

Animate scanner in ObjectEditor with refresh and sleep.

Demonstrate every command.

### Step 7: Refactor token inheritance fully

Make sure:

- common token code is in `Token`,
- command classes extend `Word`,
- each class implements one interface,
- duplicate fields/methods are deleted,
- annotations are correct.

### Step 8: Create `main.Assignment_2_1_Part_2`

Copy Part 1 demo. It should still work.

### Step 9: Optional clearable history

Implement only if time allows after required extra credit passes.

### Step 10: Run checks and inspect ObjectEditor

For every scanner test string, verify ObjectEditor shows:

- scanned string property,
- token compact array,
- each token object with properties,
- command tokens have command-specific runtime classes.

---

# Part E — Manual Testing Scripts/Scenarios

## 18. Assignment 2 manual behavior tests

Use the ObjectEditor window and console output.

### Test 1: Initial scene

Expected:

- All knights left of gorge.
- Guard near guard area.
- Knight area empty.
- `Occupied` false.
- `KnightTurn` false.
- Gorge and standing areas visible.

### Test 2: Approach while empty

Call:

```java
bridgeScene.approach(bridgeScene.getArthur());
```

Expected:

- Arthur moves into knight area.
- `Occupied` true.
- `KnightTurn` false.

### Test 3: Approach while occupied

Call:

```java
bridgeScene.approach(bridgeScene.getLancelot());
```

while Arthur is still occupying.

Expected:

- Lancelot does not move into knight area.
- Arthur remains the interacting knight.

### Test 4: Alternating speech

Call:

```java
bridgeScene.say("What is your name?");
bridgeScene.say("Arthur, King of the Britons.");
```

Expected:

- First text appears on guard.
- Second text appears on Arthur.
- Turns alternate.

### Test 5: Pass

After the knight answers and it is guard's turn:

```java
bridgeScene.passed();
```

Expected:

- Knight moves to right side of gorge.
- `Occupied` false.
- `KnightTurn` false.

### Test 6: Knight fails immediately

```java
bridgeScene.approach(bridgeScene.getLancelot());
bridgeScene.failed();
```

Expected:

- Lancelot falls into gorge.
- `Occupied` false.

### Test 7: Guard fails

```java
bridgeScene.approach(bridgeScene.getRobin());
bridgeScene.say("Question?");
bridgeScene.failed();
```

Expected:

- Since it is knight's turn after guard speaks, guard falls into gorge.
- Depending on your implementation, the knight may still occupy the area until passed/failed later. Follow assignment semantics and LocalChecks expectations.

## 19. Assignment 2.1 scanner manual tests

### Test 1: Empty input

```java
scanner.setScannedString("");
```

Expected:

```java
scanner.getTokens().length == 0
```

No null pointer exception.

### Test 2: Required commands

Input:

```text
move say repeat approach passed failed
```

Expected runtime classes:

```text
Move, Say, Repeat, Approach, Passed, Failed
```

not six `Word` tokens.

### Test 3: Mixed case

Input:

```text
MoVE saY RePeAt
```

Expected:

- command token classes,
- input text preserves original case if your token stores scanned input.

### Test 4: Mixed token types

Input:

```text
MoVe 050 { saY "hi!" }
```

Expected:

- Move command token,
- number token,
- start token for `{` if implemented in A1.1,
- Say command token,
- quoted string token,
- end token for `}` if implemented in A1.1,
- compact array contains no null slots.

### Test 5: Non-command words

Input:

```text
hello moveable failedx
```

Expected:

- `hello` is Word.
- `moveable` is Word, not Move.
- `failedx` is Word, not Failed.

Use whole-word equality, not substring matching.

---

# Part F — Final Submission Checklist

Before submitting Assignment 2:

- [ ] Project compiles from a clean build.
- [ ] `main.Assignment2` is public and runnable.
- [ ] `main.Assignment2` uses the factory method for the bridge scene.
- [ ] No `ObjectEditor.refresh()` calls remain in Assignment 2 Part 3 demo.
- [ ] Console scene view prints raw `PropertyChangeEvent` objects.
- [ ] Atomic shapes notify observers for visible property changes.
- [ ] Scene has `Gorge`, `KnightArea`, `GuardArea`, `Occupied`, `KnightTurn`.
- [ ] Scene has hidden `InteractingKnight` getter if required by checks.
- [ ] `approach`, `say`, `passed`, and `failed` exactly match assignment names/signatures.
- [ ] No magic numbers remain in new code.
- [ ] No wildcard imports.
- [ ] No hidden/shadowed fields.
- [ ] No public non-final fields.
- [ ] Images are included in `images/`.
- [ ] Classpath does not reference JavaTeaching or previous projects.
- [ ] LocalChecks run and screenshot saved if required.
- [ ] CheckStyle run twice quickly on `src` and warnings resolved.

Before submitting Assignment 2.1 extra credit:

- [ ] Correct A2.1 CheckStyle configuration is used.
- [ ] Scanner/token files are included in `src`.
- [ ] Required command classes exist and are tagged.
- [ ] Scanner has non-null initial string and token array.
- [ ] Scanner stores compact `TokenInterface[]` with no null slots.
- [ ] Scanner uses arrays, not `ArrayList`/`Vector`, for token storage.
- [ ] Command matching is case-insensitive.
- [ ] Part 1 scanner main is named `main.Assignment2_1_Part1`.
- [ ] Part 1 scanner main has separate `animateScanner` method.
- [ ] Part 1 demo demonstrates every command implemented.
- [ ] Part 2 scanner main is named `main.Assignment_2_1_Part_2`.
- [ ] Token inheritance removes duplicate code.
- [ ] Each class implements one interface.
- [ ] Command classes subclass `Word` and mostly contain only constructors.
- [ ] Optional clearable history uses arrays and does not reuse token objects from compact array.
- [ ] LocalChecks/CheckStyle output is saved or screenshotted as required.

---

## 20. Repository-Specific Notes From Review

These notes are based on the current files in this repository.

### Existing strengths

The current project already has:

- `Locatable`/`BoundedShape` inheritance.
- Interface-typed fields in many places.
- `Factory.bridgeSceneFactoryMethod()`.
- `Factory.legsFactoryMethod(...)`.
- `Factory.consoleSceneViewFactoryMethod()`.
- `ConsoleSceneView` observing many atomic shapes.
- `Assignment2.main` using the bridge-scene factory and console-scene-view factory.
- No scanner/token code yet, so Assignment 2.1 can be added cleanly.

### Things to verify/fix before grading

1. `BridgeScene` should expose hidden `getInteractingKnight()` if LocalChecks expects the required property.
2. `BridgeScene.say()` should do nothing when no knight occupies the knight area.
3. Optional `scroll()` should scroll the whole scene, including gorge and standing areas, not only avatars.
4. If ObjectEditor complains about unknown property notifications such as `Angle` or `Radius`, adjust notifications to only known visible properties for that structure pattern.
5. `.checkstyle` currently references A2 checks, not A2.1 checks.
6. `Main.java` currently calls `Assignment1.main(args)`. That may be okay if not graded, but for demo convenience you may want to run `main.Assignment2` directly rather than relying on `Main`.

---

## 21. Best Strategy for Getting Style Points

1. **Compile constantly.** Never make 20 files of changes before compiling.
2. **Run CheckStyle before LocalChecks when style is obviously broken.** Fix easy style warnings early.
3. **Prefer small helper methods.** This improves readability and reduces duplicated logic.
4. **Use constants for every coordinate, size, sleep time, and demo string.**
5. **Keep the object graph stable.** Move existing objects; do not replace them.
6. **Use interfaces as types everywhere except constructor calls.**
7. **Read every ObjectEditor warning.** The one about complete refresh is okay in Assignment 2.1 Part 1, but unknown-property and missing-structure-pattern warnings should be fixed.
8. **For scanner tokens, implement inheritance immediately.** It saves time because Part 2 requires it anyway.
9. **Take screenshots of LocalChecks results.** Assignment 2.1 specifically says the autograder may be broken and asks for a screenshot of LocalChecks scores in the submission directory.
10. **Zip the entire project folder** including `src`, `bin`, and logs as required by the assignment instructions.
