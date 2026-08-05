package theflex5710.nosnowball;

import autismclient.api.AutismAddons;
import autismclient.modules.Module;
import autismclient.modules.ModuleCategory;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;

import java.util.ArrayList;
import java.util.List;

public final class NoSnowball extends Module {
    public NoSnowball() {
        super(
            AutismAddons.id("no-snowball"),
            "NoSnowball",
            ModuleCategory.registerAddon("nosnowball", "Anti Lag"),
            "Removes thrown and airborne snowballs."
        );
    }

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (!isEnabled()) return false;
        if (packet instanceof ClientboundAddEntityPacket add
            && add.getType().getBaseClass() == Snowball.class) {
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        if (MC.level == null) return;
        List<Entity> snowballs = new ArrayList<>();
        for (Entity entity : MC.level.entitiesForRendering()) {
            if (entity instanceof Snowball) {
                snowballs.add(entity);
            }
        }
        for (Entity snowball : snowballs) {
            snowball.discard();
        }
    }
}
