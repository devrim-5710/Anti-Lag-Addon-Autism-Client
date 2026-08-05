package theflex5710.nosnowball;

import autismclient.api.ApiVersion;
import autismclient.api.SimpleAddon;

public final class NoSnowballAddon extends SimpleAddon {
    public NoSnowballAddon() {
        super(ApiVersion.CURRENT, "theflex5710.nosnowball");
    }

    @Override
    protected void initialize() {
        registerModule(new NoSnowball());
        registerModule(new NoArmorStand());
        registerModule(new NoMinecart());
        registerModule(new NoBoat());
    }
}
