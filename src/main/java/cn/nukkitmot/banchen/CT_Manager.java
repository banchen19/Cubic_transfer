package cn.nukkitmot.banchen;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.TransferPacket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CT_Manager extends Cubic_Transfer_Plugin {
    public Connection connection;

    CT_Manager() {
        connect();
    }

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
            String sql = "CREATE TABLE IF NOT EXISTS ct (" +
                    "name VARCHAR(255) PRIMARY KEY," +
                    "a VARCHAR(255)," +
                    "b VARCHAR(255)," +
                    "is_cross_server BOOLEAN," +
                    "pos VARCHAR(255)," +
                    "address VARCHAR(255)," +
                    "port INTEGER" +
                    ")";
            statement.executeUpdate(sql);
        } catch (SQLException var6) {
            System.out.println("连接数据库失败: " + var6.getMessage());

        }
    }

    //    初始化立方体
    public void initCubic(String newName, boolean isCrossServer) {
        // 这里应该包含更新数据库的逻辑
        String sql = "insert into ct values ($1,null,null, $2,null,null,null)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setBoolean(2, isCrossServer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 修改立方体的名称
    public void setCubicName(String old_name, String newName) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE ct SET name = ? WHERE name = ?")) {
            statement.setString(1, newName);
            statement.setString(2, old_name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    //    删除立方体
    public void removeCubic(String name) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM ct WHERE name = ?")) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //    将坐标转换为字符串
// 从Location的toString()表示中提取坐标和世界名称
    public static String extractLocationInfo(Location location) {
        // 正则表达式匹配x, y, z坐标
        String regex = "x=([^,]+), y=([^,]+), z=([^,]+)";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(location.toString());
        if (matcher.find()) {
            String x = matcher.group(1);
            String y = matcher.group(2);
            String z = matcher.group(3);
            return x + "," + y + "," + z;
        } else {
            throw new IllegalArgumentException("Invalid location string format");
        }
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
        // 创建并返回Location对象
        return new Location(x, y, z);
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
                String pos = resultSet.getString("pos");
                String address = resultSet.getString("address");
                int port = resultSet.getInt("port");
                if (a != null && b != null) {
                    cubics.add(new Cubic(name, stringToLocation(a), stringToLocation(b), is_cross_server, stringToLocation(pos), address, port));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cubics;
    }

    //    查询指定坐标是否在立方体内
    public boolean Transfer(Player player, Location location) {
        for (Cubic cubic : getCubics()) {
            if (isInCubic(location, cubic.getPositionA(), cubic.getPositionB())) {
//                根据传送模式进行传送,如果是跨服传送,则传送到指定服务器
                if (cubic.isCrossServer()) {
                    player.sendMessage("跨服传送");
                    // 获取传送服务器地址和端口
                    String address = cubic.getAddress();
                    int port = cubic.getPort();
                    // 创建一个TeleportTask对象，并提交到线程池中执行
                    TransferPacket transferPacket = new TransferPacket();
                    transferPacket.address = address;
                    transferPacket.port = port;
                    player.dataPacket(transferPacket);
                } else {
                    player.sendMessage("坐标传送");
                    if (cubic.getTeleportPosition() != null) {
                        player.teleport(cubic.getTeleportPosition());
                    } else {
                        player.sendMessage("传送点未设置");
                    }
                }
                return true;
            }
        }
        return false;
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
