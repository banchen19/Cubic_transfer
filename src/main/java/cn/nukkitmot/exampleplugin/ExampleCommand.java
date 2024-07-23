package cn.nukkitmot.exampleplugin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.LangCode;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.utils.TextFormat;

public class ExampleCommand extends PluginCommand<ExamplePlugin> {

    protected ExamplePlugin api;
    protected PluginI18n i18n;

    public ExampleCommand() {
        /*
        1.the name of the command must be lowercase
        2.Here the description is set in with the key in the language file,Look at en_US.lang or zh_CN.lang.
        This can send different command description to players of different language.
        You must extends PluginCommand to have this feature.
        */
        super("examplecommand", ExamplePlugin.getInstance());

        this.setDescription("exampleplugin.examplecommand.description");
        //Set the alias for this command
        this.setAliases(new String[]{"test"});

        /*
         * The following begins to set the command parameters, first need to clean,
         * because NK will fill in several parameters by default, we do not need.
         * */
        this.getCommandParameters().clear();

        /*
         * 1.getCommandParameters return a Map<String,cn.nukkit.command.data.Com mandParameter[]>,
         * in which each entry can be regarded as a subcommand or a command pattern.
         * 2.Each subcommand cannot be repeated.
         * 3.Optional arguments must be used at the end of the subcommand or consecutively.
         */
        this.getCommandParameters().put("pattern1", new CommandParameter[]{
                CommandParameter.newEnum("enum1", false, new CommandEnum("method", "say1", "sayone")),
        });
        this.getCommandParameters().put("pattern2", new CommandParameter[]{
                CommandParameter.newEnum("enum2", false, new String[]{"say2"}),
                CommandParameter.newType("player", false, CommandParamType.TARGET),
                CommandParameter.newType("message", true, CommandParamType.STRING)
        });
        api = ExamplePlugin.getInstance();
        i18n = ExamplePlugin.getI18n();
    }

    /**
     * This method is executed only if the command syntax is correct, which means you don't need to verify the parameters yourself.
     * In addition, before executing the command, will check whether the executor has the permission for the command.
     * If these conditions are not met, an error message is automatically displayed.
     *
     * @param sender       The sender of the command
     * @param commandLabel Command label. For example, if `/test 123` is used, the value is `test`
     * @param args         Command parameters
     */
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        LangCode langCode = sender.isPlayer() ? ((Player) sender).getLanguageCode() : ExamplePlugin.getServerLangCode();
        if (args.length < 1) {
            sender.sendMessage(i18n.tr(langCode, "exampleplugin.args.missing"));
            return false;
        }
        switch (args[0]) {
            case "say1", "sayone" -> {
                api.getLogger().info("execute say1");
            }
            case "say2" -> {
                api.getLogger().info("execute say2");
                if (args.length < 2) {// exist player args
                    sender.sendMessage(i18n.tr(langCode, "exampleplugin.args.missing"));
                    return false;
                }
                Player player = Server.getInstance().getPlayer(args[1]);
                LangCode playerLangCode = player.getLanguageCode();
                if (args.length > 2) {// exist message args
                    String message = args[2];
                    player.sendMessage(TextFormat.WHITE + i18n.tr(playerLangCode, "exampleplugin.helloworld", player.getName()) + " this is message:" + message);
                } else {
                    player.sendMessage(TextFormat.WHITE + i18n.tr(playerLangCode, "exampleplugin.helloworld", player.getName()));
                }
                return true;
            }
            default -> {
                return false;
            }
        }
        return true;
    }
}
