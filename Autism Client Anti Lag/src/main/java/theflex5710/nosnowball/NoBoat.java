package theflex5710.nosnowball;

import autismclient.api.AutismAddons;
import autismclient.api.module.BoolSetting;
import autismclient.modules.Module;
import autismclient.modules.ModuleCategory;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;

import java.util.ArrayList;
import java.util.List;

public final class NoBoat extends Module {
    public NoBoat() {
        super(
            AutismAddons.id("no-boat"),
            "NoBoat",
            ModuleCategory.registerAddon("nosnowball", "Anti Lag"),
            "Removes chosen boat types so they don't render or appear."
        );
        add(new BoolSetting("normal", "Normal boat", true));
        add(new BoolSetting("chest", "Chest boat", true));
    }

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (!isEnabled()) return false;
        if (packet instanceof ClientboundAddEntityPacket add) {
            Class<?> type = add.getType().getBaseClass();
            if (AbstractChestBoat.class.isAssignableFrom(type)) return bool("chest");
            if (AbstractBoat.class.isAssignableFrom(type)) return bool("normal");
        }
        return false;
    }

    @Override
    public void tick() {
        if (MC.level == null) return;
        List<Entity> boats = new ArrayList<>();
        for (Entity entity : MC.level.entitiesForRendering()) {
            if (entity instanceof AbstractBoat boat && shouldRemove(boat)) {
                boats.add(boat);
            }
        }
        for (Entity boat : boats) {
            boat.discard();
        }
    }

    private boolean shouldRemove(AbstractBoat boat) {
        return boat instanceof AbstractChestBoat ? bool("chest") : bool("normal");
    }
}
