package cn.nukkitmot.banchen;

import cn.nukkit.lang.LangCode;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.lang.PluginI18nManager;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Cubic_Transfer_Plugin extends PluginBase {

/*
立方：坐标
立方传送
功能一：创建立方
 */

    @Getter
    public static Cubic_Transfer_Plugin instance;
    @Getter
    public static PluginI18n i18n;


    private static CT_Manager ct_instance = new CT_Manager();
    public static CT_Manager getInstance(){
        return ct_instance;
    }

    @Override
    public void onLoad() {
        //save Plugin Instance
        instance = this;
        //register the plugin i18n
        i18n = PluginI18nManager.register(this);
        //register the command of plugin
//        注册指令到服务器
        this.getServer().getCommandMap().register("exampleplugin", new CTCommand());

//        输出日志
        this.getLogger().info(TextFormat.WHITE + "I've been loaded!");
    }

    @Override
    public void onEnable() {
//        插件加载完毕
        this.getLogger().info(TextFormat.DARK_GREEN + "立方传送：onEnable");
        //        事件监听
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        //Config reading and writing
        Config config = new Config(
                new File(this.getDataFolder(), "config.yml"),
                Config.YAML,
                //Default values (not necessary)
                new ConfigSection(new LinkedHashMap<>() {
                    {
                        put("delay", 1000);
                        put("period", 20); //you can also put other standard objects!
                    }
                }));
        this.getLogger().info(String.valueOf(config.get("period", "无法获取循环时间,这将导致可能出现一些意想不到的报错")));
        config.save();

        int delay = (int) config.get("delay");
        int period = (int) config.get("period");
        this.getServer().getScheduler().scheduleDelayedRepeatingTask(new BroadcastPluginTask(this), delay, period);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.DARK_RED + "I've been disabled!");
    }
}
