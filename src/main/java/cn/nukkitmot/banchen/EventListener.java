package cn.nukkitmot.banchen;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityMotionEvent;
import cn.nukkit.event.entity.EntityPortalEnterEvent;

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
    public void onPlayerBreakBlock(EntityMotionEvent event) {
        if (event.getEntity().isPlayer) {
            Entity entity = event.getEntity();
            Player player = entity.getServer().getPlayer(entity.getName());
            this.ct_manager.Transfer(player, player.getLocation());
        }
    }


//    监听玩家攻击方块(非破坏方块)
     @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
     public void onPlayerBreakBlock(EntityPortalEnterEvent event) {
         if (event.getEntity().isPlayer) {
             Entity entity = event.getEntity();
             Player player = entity.getServer().getPlayer(entity.getName());
             this.ct_manager.Transfer(player, player.getLocation());
         }
     }
    //    监听玩家破坏方块->3
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        this.ct_manager.Transfer(event.getPlayer(), event.getBlock().getLocation());
    }

}
