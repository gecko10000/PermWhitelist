package gecko10000.permwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PermWhitelist extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new Listeners(this);
    }
}
