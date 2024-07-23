package cn.nukkitmot.exampleplugin;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.lang.LangCode;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.lang.PluginI18nManager;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import cn.nukkitmot.exampleplugin.custom.item.CandyCaneSword;
import lombok.Getter;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * author: MagicDroidX
 * NukkitExamplePlugin Project
 */
public class ExamplePlugin extends PluginBase {
    @Getter
    public static ExamplePlugin instance;
    @Getter
    public static PluginI18n i18n;
    @Getter
    public static LangCode serverLangCode;


    @Override
    public void onLoad() {
        //save Plugin Instance
        instance = this;
        //register the plugin i18n
        i18n = PluginI18nManager.register(this);
        initServerLangCode();
        //register the command of plugin
        this.getServer().getCommandMap().register("exampleplugin", new ExampleCommand());

        //register the custom item of server
        registerItems();

        this.getLogger().info(TextFormat.WHITE + "I've been loaded!");
    }

    @Override
    public void onEnable() {
        this.getLogger().info(TextFormat.DARK_GREEN + "I've been enabled!");
        this.getLogger().info(String.valueOf(this.getDataFolder().mkdirs()));

        //Use the plugin's i18n output
        this.getLogger().info(i18n.tr(getServerLangCode(), "exampleplugin.helloworld", "世界"));

        //Register the EventListener
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

        //PluginTask
        this.getServer().getScheduler().scheduleDelayedRepeatingTask(new BroadcastPluginTask(this), 500, 200);

        //Save resources
        this.saveResource("string.txt");

        //Config reading and writing
        Config config = new Config(
                new File(this.getDataFolder(), "config.yml"),
                Config.YAML,
                //Default values (not necessary)
                new ConfigSection(new LinkedHashMap<>() {
                    {
                        put("this-is-a-key", "Hello! Config!");
                        put("another-key", true); //you can also put other standard objects!
                    }
                }));

        //Now try to get the value, the default value will be given if the key isn't exist!
        this.getLogger().info(String.valueOf(config.get("this-is-a-key", "this-is-default-value")));

        //Don't forget to save it!
        config.save();
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.DARK_RED + "I've been disabled!");
    }

    private void registerItems() {
        List<Class<? extends ItemCustom>> list = List.of(
                CandyCaneSword.class
        );
        for (Class<? extends ItemCustom> item : list) {
            Item.registerCustomItem(item);
        }
    }

    public void initServerLangCode() {
        switch (Server.getInstance().getLanguage().getLang()) {
            case "eng" -> {
                serverLangCode = LangCode.en_US;
            }
            case "chs" -> {
                serverLangCode = LangCode.zh_CN;
            }
            case "deu" -> {
                serverLangCode = LangCode.de_DE;
            }
            case "rus" -> {
                serverLangCode = LangCode.ru_RU;
            }
            default -> {
                try {
                    serverLangCode = LangCode.valueOf(Server.getInstance().getLanguage().getLang());
                } catch (IllegalArgumentException e) {
                    serverLangCode = LangCode.en_US;
                }
            }
        }
    }
}
