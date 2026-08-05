package theflex5710.nosnowball;

import autismclient.api.AutismAddons;
import autismclient.modules.Module;
import autismclient.modules.ModuleCategory;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public final class NoArmorStand extends Module {
    public NoArmorStand() {
        super(
            AutismAddons.id("no-armor-stand"),
            "NoArmorStand",
            ModuleCategory.registerAddon("nosnowball", "Anti Lag"),
            "Removes armor stands so they don't render or appear."
        );
    }

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (!isEnabled()) return false;
        if (packet instanceof ClientboundAddEntityPacket add
            && add.getType().getBaseClass() == ArmorStand.class) {
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        if (MC.level == null) return;
        List<Entity> armorStands = new ArrayList<>();
        for (Entity entity : MC.level.entitiesForRendering()) {
            if (entity instanceof ArmorStand) {
                armorStands.add(entity);
            }
        }
        for (Entity armorStand : armorStands) {
            armorStand.discard();
        }
    }
}
