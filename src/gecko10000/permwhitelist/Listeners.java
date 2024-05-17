package gecko10000.permwhitelist;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Listeners implements Listener {

    private final PermWhitelist plugin;
    private final LuckPerms luckPerms = LuckPermsProvider.get();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public Listeners(PermWhitelist plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onLogin(AsyncPlayerPreLoginEvent e) throws ExecutionException, InterruptedException {
        UUID uuid = e.getUniqueId();
        User user = luckPerms.getUserManager().loadUser(uuid).get();
        boolean hasPerm = user.getCachedData().getPermissionData().checkPermission(plugin.getConfig().getString("permission")).asBoolean();
        if (!hasPerm) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, miniMessage.deserialize(plugin.getConfig().getString("deny-message")));
        }
    }

}
