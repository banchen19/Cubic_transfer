package cn.nukkitmot.banchen;

import cn.nukkit.lang.PluginI18n;
import cn.nukkit.lang.PluginI18nManager;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;

import java.io.File;
import java.util.LinkedHashMap;

public class Cubic_Transfer_Plugin extends PluginBase {
    @Getter
    public static Cubic_Transfer_Plugin instance;
    @Getter
    public static PluginI18n i18n;
    @Getter
    public static CT_Manager ct_manager;

    @Override
    public void onLoad() {
        //save Plugin Instance
        instance = this;
        ct_manager = new CT_Manager();
        ct_manager.connect();
        //register the plugin i18n
        i18n = PluginI18nManager.register(this);
        //register the command of plugin
//        注册指令到服务器
        this.getServer().getCommandMap().register("ct", new CTCommand(ct_manager));
    }

    @Override
    public void onEnable() {
//        插件加载完毕
        this.getLogger().info(TextFormat.DARK_GREEN + "立方传送：onEnable");
        //        事件监听
        this.getServer().getPluginManager().registerEvents(new EventListener(this, ct_manager), this);
        //Config reading and writing
//        Config config = new Config(
//                new File(this.getDataFolder(), "config.yml"),
//                Config.YAML,
//                //Default values (not necessary)
//                new ConfigSection(new LinkedHashMap<>() {
//                    {
//                        put("transfer_server", "");
//                        put("transfer_pos", ""); //you can also put other standard objects!
//                    }
//                }));
//        config.save();
//        int period = (int) config.get("period");
//        当前检测频率
//        this.getLogger().info(TextFormat.GREEN + "当前检测频率: " + period + "ms");
//        this.getServer().getScheduler().scheduleDelayedRepeatingTask(new BroadcastPluginTask(this), delay, period);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.DARK_RED + "I've been disabled!");
    }
}
