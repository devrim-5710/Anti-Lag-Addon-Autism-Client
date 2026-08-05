package theflex5710.nosnowball;

import autismclient.api.AutismAddons;
import autismclient.api.module.BoolSetting;
import autismclient.modules.Module;
import autismclient.modules.ModuleCategory;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartChest;
import net.minecraft.world.entity.vehicle.minecart.MinecartFurnace;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;

import java.util.ArrayList;
import java.util.List;

public final class NoMinecart extends Module {
    public NoMinecart() {
        super(
            AutismAddons.id("no-minecart"),
            "NoMinecart",
            ModuleCategory.registerAddon("nosnowball", "Anti Lag"),
            "Removes chosen minecart types so they don't render or appear."
        );
        add(new BoolSetting("normal", "Normal minecart", true));
        add(new BoolSetting("chest", "Chest minecart", true));
        add(new BoolSetting("furnace", "Furnace minecart", true));
        add(new BoolSetting("tnt", "TNT minecart", true));
        add(new BoolSetting("hopper", "Hopper minecart", true));
    }

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (!isEnabled()) return false;
        if (packet instanceof ClientboundAddEntityPacket add) {
            return shouldRemove(add.getType().getBaseClass());
        }
        return false;
    }

    @Override
    public void tick() {
        if (MC.level == null) return;
        List<Entity> carts = new ArrayList<>();
        for (Entity entity : MC.level.entitiesForRendering()) {
            if (entity instanceof AbstractMinecart cart && shouldRemove(cart.getClass())) {
                carts.add(cart);
            }
        }
        for (Entity cart : carts) {
            cart.discard();
        }
    }

    private boolean shouldRemove(Class<?> type) {
        if (type == Minecart.class) return bool("normal");
        if (type == MinecartChest.class) return bool("chest");
        if (type == MinecartFurnace.class) return bool("furnace");
        if (type == MinecartTNT.class) return bool("tnt");
        if (type == MinecartHopper.class) return bool("hopper");
        return false;
    }
}
