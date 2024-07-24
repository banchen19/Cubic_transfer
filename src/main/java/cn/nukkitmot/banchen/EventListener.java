package cn.nukkitmot.banchen;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityMotionEvent;
import cn.nukkit.event.entity.EntityPortalEnterEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMissedSwingEvent;
import cn.nukkit.event.player.PlayerMoveEvent;

/**
 * author: MagicDroidX
 * NukkitExamplePlugin Project
 */
public class EventListener implements Listener {
    private final Cubic_Transfer_Plugin plugin;
    private final CT_Manager ct_manager;

    public EventListener(Cubic_Transfer_Plugin plugin, CT_Manager ct_manager) {
        this.plugin = plugin;
        this.ct_manager = ct_manager;
    }

    //    监听玩家移动->0
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBreakBlock(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        this.ct_manager.Transfer(player, player.getLocation(), 0);
    }


    //    玩家与方块交互事件
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBreakBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        this.ct_manager.Transfer(player, event.getBlock().getLocation(), 1);
    }

}
