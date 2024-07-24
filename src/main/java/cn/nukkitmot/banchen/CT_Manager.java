package cn.nukkitmot.banchen;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.TransferPacket;
import cn.nukkit.utils.TextFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CT_Manager extends Cubic_Transfer_Plugin {
    public Connection connection;

    // 创建数据库连接并初始化数据库表
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:ct_sqlite.db");
            createTable();
        } catch (SQLException var2) {
            System.out.println("连接数据库失败: " + var2.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("连接数据库失败: " + e.getMessage());
        }
    }

    // 创建数据库表
    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            // 创建立方体位置表
            String sql = "create table if not exists ct\n" +
                    "(\n" +
                    "    name            varchar(255) primary key,\n" +
                    "    a               varchar(255),\n" +
                    "    b               varchar(255),\n" +
                    "    is_cross_server boolean default false,\n" +
                    "    trigger_type    int default 0,\n" +
                    "    pos             varchar(255),\n" +
                    "    address         varchar(255),\n" +
                    "    port            int\n" +
                    ")";
            statement.executeUpdate(sql);
        } catch (SQLException var6) {
            System.out.println("连接数据库失败: " + var6.getMessage());

        }
    }

    //    初始化立方体
    public void initCubic(String newName) {
        // 这里应该包含更新数据库的逻辑
        String sql = "insert into ct(name)\n" +
                "values ($1)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }

    //    修改触发方式
    public void setCubicTriggerType(String newName, int triggerType) {
        // 这里应该包含更新数据库的逻辑
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET trigger_type = ? WHERE name = ?")) {
            statement.setInt(1, triggerType);
            statement.setString(2, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }

    // 修改立方体的名称
    public void setCubicName(String old_name, String newName) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET name = ? WHERE name = ?")) {
            statement.setString(1, newName);
            statement.setString(2, old_name);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }

    // 设置立方体的点A
    public void setCubicPositionA(String newName, Location newLocation) {
        // 这里应该包含更新数据库的逻辑
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET a = ? WHERE name = ?")) {
            statement.setString(1, extractLocationInfo(newLocation));
            statement.setString(2, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }

    // 设置立方体的点B
    public void setCubicPositionB(String newName, Location newLocation) {
        // 这里应该包含更新数据库的逻辑
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET b = ? WHERE name = ?")) {
            statement.setString(1, extractLocationInfo(newLocation));
            statement.setString(2, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }

    // 修改立方体的传送模式
    public void setCubicMode(String newName) {
        // 这里应该包含更新数据库的逻辑
        String sql = "UPDATE ct\n" +
                "SET is_cross_server = NOT is_cross_server\n" +
                "WHERE name = $1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }

    }

    // 设置立方体的传送地点
    public void setCubicTeleportPosition(String newName, Location newLocation) {
        // 这里应该包含更新数据库的逻辑
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET pos = ? WHERE name = ?")) {
            statement.setString(1, extractLocationInfo(newLocation));
            statement.setString(2, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }

    }

    // 设置立方体被传送的服务器IP
    public void setCubicAddress(String newName, String newAddress) {
        // 这里应该包含更新数据库的逻辑
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET address = ? WHERE name = ?")) {
            statement.setString(1, newAddress);
            statement.setString(2, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }

    // 设置立方体被传送的服务器端口
    public void setCubicPort(String newName, int newPort) {
        // 这里应该包含更新数据库的逻辑
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET port = ? WHERE name = ?")) {
            statement.setInt(1, newPort);
            statement.setString(2, newName);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }

    //    删除立方体
    public void removeCubic(String name) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM ct WHERE name = ?")) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
    }


    //    将坐标转换为字符串
// 从Location的toString()表示中提取坐标和世界名称
    public static String extractLocationInfo(Location location) {
        // 正则表达式匹配x, y, z坐标
        String x = location.getX() + "";
        String y = location.getY() + "";
        String z = location.getZ() + "";
        String yaw = location.getYaw() + "";
        String pitch = location.getPitch() + "";
        String headYaw = location.getYaw() + "";
        return x + "," + y + "," + z + "," + yaw + "," + pitch + "," + headYaw;
    }

    //    将字符串转换为坐标
    public static Location stringToLocation(String locationString) {
        // 反转字符串的顺序
        String[] parts = locationString.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid location string format");
        }
        // 转换坐标值
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        double yaw = Double.parseDouble(parts[3]);
        double pitch = Double.parseDouble(parts[4]);
        double headYaw = Double.parseDouble(parts[5]);
        return new Location(x, y, z, yaw, pitch, headYaw);
    }

    // 获取所有立方体
    public List<Cubic> getCubics() {
        List<Cubic> cubics = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ct")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String a = resultSet.getString("a");
                String b = resultSet.getString("b");
                boolean is_cross_server = resultSet.getBoolean("is_cross_server");
                int triggerType = resultSet.getInt("trigger_type");
                String pos = resultSet.getString("pos");
                String address = resultSet.getString("address");
                int port = resultSet.getInt("port");
                if (a == null) {
                    a = "未设置";
                }
                if (b == null) {
                    b = "未设置";
                }
                if (pos == null) {
                    pos = "未设置";
                }
                if (address == null) {
                    address = "未设置";
                }
                cubics.add(new Cubic(name, stringToLocation(a), stringToLocation(b), triggerType, is_cross_server,
                        stringToLocation(pos),
                        address, port));
            }
        } catch (SQLException e) {
            this.getLogger().info(e.getMessage());
        }
        return cubics;
    }

    //    查询指定坐标是否在立方体内
    public boolean Transfer(Player player, Location location, int i) {
        for (Cubic cubic : getCubics()) {
            if (isInCubic(location, cubic.getPositionA(), cubic.getPositionB())) {
                if (cubic.getTriggerType() == i) {
                    Transfer_Mode(player, cubic);
                }
            }
        }
        return false;
    }

    //    根据传送方式进行传送
    public void Transfer_Mode(Player player, Cubic cubic) {
        if (cubic.isCrossServer()) {
            // 获取传送服务器地址和端口
            String address = cubic.getAddress();
            int port = cubic.getPort();
            if (address != null && port != 0) {
                // 创建一个TeleportTask对象，并提交到线程池中执行
                TransferPacket transferPacket = new TransferPacket();
                transferPacket.address = address;
                transferPacket.port = port;
                player.dataPacket(transferPacket);
                player.sendMessage(TextFormat.YELLOW + "跨服传送:" + TextFormat.GREEN + address + ":" + port);
            } else {
                player.sendMessage("传送地址或端口未设置");
            }
        } else {
            player.sendMessage("坐标传送");
            if (cubic.getTeleportPosition() != null) {
                player.teleport(cubic.getTeleportPosition());
                player.sendMessage(TextFormat.YELLOW + "坐标传送:" + TextFormat.GREEN + cubic.getTeleportPosition().toString());
            } else {
                player.sendMessage("传送点未设置");
            }
        }
    }


    //    检查指定的位置是否在这个矩阵以内
    public static boolean isInCubic(Location location, Location a, Location b) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double ax = a.getX();
        double ay = a.getY();
        double az = a.getZ();

        double bx = b.getX();
        double by = b.getY();
        double bz = b.getZ();
        return x >= Math.min(ax, bx) && x <= Math.max(ax, bx) &&
                y >= Math.min(ay, by) && y <= Math.max(ay, by) &&
                z >= Math.min(az, bz) && z <= Math.max(az, bz);
    }
}
