package cn.nukkitmot.banchen;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerBlockPickEvent;
import cn.nukkit.event.player.PlayerJumpEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.TransferPacket;

import static cn.nukkitmot.banchen.CT_Manager.extractLocationInfo;
import static cn.nukkitmot.banchen.CT_Manager.stringToLocation;

/**
 * author: MagicDroidX
 * NukkitExamplePlugin Project
 */
public class EventListener implements Listener {
    private final Cubic_Transfer_Plugin plugin;

    public EventListener(Cubic_Transfer_Plugin plugin) {
        this.plugin = plugin;
    }

    //    监听玩家破坏方块
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        this.plugin.getInstance().Transfer(event.getPlayer(), event.getBlock().getLocation());
    }
}
