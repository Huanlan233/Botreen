package top.htext.botreen.bot;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Bot {
    private String name;
    private String description;
    private Vec3d pos;
    private Vec2f rotation;
    private Identifier dimensionId;
    private boolean isDetained;
    private Action defaultAction;

    private final int id;

    public Bot(String name, String description, Vec3d pos, Vec2f rotation, Identifier dimensionId, Action defaultAction, boolean isDetained) {
        this.name = name;
        this.description = description;
        this.pos = pos;
        this.rotation = rotation;
        this.dimensionId = dimensionId;
        this.defaultAction = defaultAction;
        this.isDetained = isDetained;

        this.id = (name + pos + rotation + dimensionId).hashCode();
    }

    public Bot(String name, Vec3d pos, Vec2f rotation, Identifier dimensionId) {
        this.name = name;
        this.description = "";
        this.pos = pos;
        this.rotation = rotation;
        this.dimensionId = dimensionId;
        this.defaultAction = Action.NONE;
        this.isDetained = false;

        this.id = (name + pos + rotation + dimensionId).hashCode();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPos(Vec3d pos) {
        this.pos = pos;
    }

    public void setRotation(Vec2f rotation) {
        this.rotation = rotation;
    }

    public void setDimensionId(Identifier dimensionId) {
        this.dimensionId = dimensionId;
    }

    public void setDetained(boolean detained) {
        this.isDetained = detained;
    }

    public void setDefaultAction(Action defaultAction) {
        this.defaultAction = defaultAction;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Vec3d getPos() {
        return this.pos;
    }

    public Vec2f getRotation() {
        return this.rotation;
    }

    public Identifier getDimensionId() {
        return this.dimensionId;
    }

    public Action getDefaultAction() {
        return defaultAction;
    }

    public boolean isDetained() {
        return this.isDetained;
    }

    public int getId() {
        return this.id;
    }

    public enum Action {
        NONE,
        USE_ONCE,
        USE_CONTINUE,
        ATTACK_ONCE,
        ATTACK_CONTINUE
    }
}
