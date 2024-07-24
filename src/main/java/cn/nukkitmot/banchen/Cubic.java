package cn.nukkitmot.banchen;

import cn.nukkit.level.Location;

public class Cubic {
    public String name;
    public Location positionA;
    public Location positionB;
    public int triggerType;
    public boolean isCrossServer;
    public Location teleportPosition;
    public String address;
    public int port;

    public Cubic(String name,
                 Location positionA,
                 Location positionB,
                 int triggerType,
                 boolean isCrossServer,
                 Location teleportPosition,
                 String address,
                 int port
    ) {
        this.name = name;
        this.triggerType = triggerType;
        this.address = address;
        this.port = port;
        this.positionA = positionA;
        this.positionB = positionB;
        this.teleportPosition = teleportPosition;
        this.isCrossServer = isCrossServer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(int triggerType) {
        this.triggerType = triggerType;
    }

    public Location getPositionA() {
        return positionA;
    }

    public void setPositionA(Location positionA) {
        this.positionA = positionA;
    }

    public Location getPositionB() {
        return positionB;
    }

    public void setPositionB(Location positionB) {
        this.positionB = positionB;
    }

    public boolean isCrossServer() {
        return isCrossServer;
    }

    public void setCrossServer(boolean crossServer) {
        isCrossServer = crossServer;
    }

    public Location getTeleportPosition() {
        return teleportPosition;
    }

    public void setTeleportPosition(Location teleportPosition) {
        this.teleportPosition = teleportPosition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
