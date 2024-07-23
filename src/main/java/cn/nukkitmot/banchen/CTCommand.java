package cn.nukkitmot.banchen;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.LangCode;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;

public class CTCommand extends PluginCommand<Cubic_Transfer_Plugin> {

    protected Cubic_Transfer_Plugin api;
    public static PluginI18n i18n;

    public CTCommand() {
        super("ct", Cubic_Transfer_Plugin.instance);
        this.setAliases(new String[]{"cubic_transfer"});
        this.getCommandParameters().clear();
        api = Cubic_Transfer_Plugin.instance;
        i18n = Cubic_Transfer_Plugin.i18n;
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

//    命令示例：
//    /ct create <立方体名> <传送类型> <服务器IP/>
        if (args.length < 1) {
            sender.sendMessage(i18n.tr(LangCode.zh_CN, "exampleplugin.args.missing"));
            return false;
        }
//        检查玩家是否为op
        if (!sender.isOp()) {
            sender.sendMessage(TextFormat.RED + "你没有权限执行此命令！");
            return false;
        }
        switch (args[0]) {
            case "create" -> {
                String name = args[1];
                boolean is_success = Boolean.parseBoolean(args[2]);
                api.getInstance().initCubic(name, is_success);
                sender.sendMessage(TextFormat.GREEN + "立方体创建成功！");
                return true;
            }
            case "remove" -> {
                String name = args[1];
                api.getInstance().removeCubic(name);
                sender.sendMessage(TextFormat.GREEN + "立方体删除成功！");
                return true;
            }
//            修改立方体名字
            case "put_name" -> {
                String old_name = args[1];
                String new_name = args[2];
                api.getInstance().setCubicName(old_name, new_name);
                sender.sendMessage(TextFormat.GREEN + "立方体名字修改成功！");
                return true;
            }
//          设置点A
            case "put_a" -> {
                String name = args[1];
                Location location = sender.getLocation();
                api.getInstance().setCubicPositionA(name, location);
                sender.sendMessage(TextFormat.GREEN + "立方体点A设置成功！");
            }
//            设置点B
            case "put_b" -> {
                String name = args[1];
                Location location = sender.getLocation();
                api.getInstance().setCubicPositionB(name, location);
                sender.sendMessage(TextFormat.GREEN + "立方体点B设置成功！");
            }
//          修改传送模式
            case "put_mode" -> {
                String name = args[1];
                api.getInstance().setCubicMode(name);
                sender.sendMessage(TextFormat.GREEN + "立方体传送模式设置成功！");
            }
//            修改立方体传送地点
            case "set_pos" -> {
                String name = args[1];
                Location location = sender.getLocation();
                api.getInstance().setCubicTeleportPosition(name, location);
                sender.sendMessage(TextFormat.GREEN + "立方体传送地点设置成功！");
            }
//            修改立方体传送服务器IP
            case "set_address" -> {
                String name = args[1];
                String address = args[2];
                api.getInstance().setCubicAddress(name, address);
            }
//            修改立方体传送服务器端口
            case "set_port" -> {
                String name = args[1];
                int port = Integer.parseInt(args[2]);
                api.getInstance().setCubicPort(name, port);
            }
            default -> {
                return false;
            }
        }
        return  true;
    }
}
