package cn.nukkitmot.banchen;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.lang.LangCode;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;

public class CTCommand extends PluginCommand<Cubic_Transfer_Plugin> {

    private final CT_Manager ct_manager;
    protected Cubic_Transfer_Plugin api;
    public static PluginI18n i18n;

    public CTCommand(CT_Manager ct_manager) {
        super("ct", Cubic_Transfer_Plugin.instance);
        this.ct_manager = ct_manager;
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
                ct_manager.initCubic(name);
                sender.sendMessage(TextFormat.GREEN + "立方体创建成功！");
                return true;
            }
            case "remove" -> {
                String name = args[1];
                ct_manager.removeCubic(name);
                sender.sendMessage(TextFormat.GREEN + "立方体删除成功！");
                return true;
            }
//            修改立方体名字
            case "put_name" -> {
                String old_name = args[1];
                String new_name = args[2];
                ct_manager.setCubicName(old_name, new_name);
                sender.sendMessage(TextFormat.GREEN + "立方体名字修改成功！");
                return true;
            }
//          设置点A
            case "put_a" -> {
                String name = args[1];
                Location location = sender.getLocation();
                ct_manager.setCubicPositionA(name, location);
                sender.sendMessage(TextFormat.GREEN + "立方体点A设置成功！");
            }
//            设置点B
            case "put_b" -> {
                String name = args[1];
                Location location = sender.getLocation();
                ct_manager.setCubicPositionB(name, location);
                sender.sendMessage(TextFormat.GREEN + "立方体点B设置成功！");
            }
//          修改触发方式
            case "put_ics" -> {
                String name = args[1];
                switch (args[2]) {
                    case "0" -> {
                        ct_manager.setCubicTriggerType(name, 0);
                        sender.sendMessage(TextFormat.GREEN + "立方体触发模式设置成功: 走进范围内");
                    }
                    case "1" -> {
                        ct_manager.setCubicTriggerType(name, 1);
                        sender.sendMessage(TextFormat.GREEN + "立方体触发模式设置成功: 与范围内方块互动");
                    }
                    default -> {
                        sender.sendMessage(TextFormat.RED + "传送模式设置失败！");
                        return false;
                    }
                }
            }
//            修改立方体的传送模式
            case "put_mode" -> {
                String name = args[1];
                ct_manager.setCubicMode(name);
                sender.sendMessage(TextFormat.GREEN + "立方体传送模式设置成功！");
            }
//            修改立方体传送地点
            case "set_pos" -> {
                String name = args[1];
                Location location = sender.getLocation();
                ct_manager.setCubicTeleportPosition(name, location);
                sender.sendMessage(TextFormat.GREEN + "立方体传送地点设置成功！");
            }
//            修改立方体传送服务器IP
            case "set_address" -> {
                String name = args[1];
                String address = args[2];
                ct_manager.setCubicAddress(name, address);
            }
//            修改立方体传送服务器端口
            case "set_port" -> {
                String name = args[1];
                int port = Integer.parseInt(args[2]);
                ct_manager.setCubicPort(name, port);
            }
            case "list" -> {
                for (Cubic cubic : ct_manager.getCubics()) {
                    sender.sendMessage(TextFormat.BLUE + "立方体名:" + TextFormat.GREEN + cubic.getName());
                    sender.sendMessage(TextFormat.YELLOW + "立方体点A:\n" + TextFormat.GREEN + cubic.getPositionA().toString());
                    sender.sendMessage(TextFormat.YELLOW + "立方体点B:\n" + TextFormat.GREEN + cubic.getPositionB().toString());
//                    触发方式
                    switch (cubic.getTriggerType()) {
                        case 0 -> {
                            sender.sendMessage(TextFormat.YELLOW + "触发方式:" + TextFormat.GREEN + "当玩家走进范围内");
                        }
                        case 1 -> {
                            sender.sendMessage(TextFormat.YELLOW + "触发方式:" + TextFormat.GREEN + "当玩家交互范围内方块");
                        }
                    }
                    if (cubic.isCrossServer()) {
                        sender.sendMessage(TextFormat.YELLOW + "立方体传送服务器IP:" + TextFormat.GREEN + cubic.getAddress());
                        sender.sendMessage(TextFormat.YELLOW + "立方体传送服务器端口:" + TextFormat.GREEN + cubic.getPort());
                    } else {
                        sender.sendMessage(TextFormat.YELLOW + "立方体传送地点:\n" + TextFormat.GREEN + cubic.getTeleportPosition().toString());
                    }
                    sender.sendMessage(TextFormat.RED + "------------------------");
                }
            }
            default -> {
                return false;
            }
        }
        return true;
    }
}
