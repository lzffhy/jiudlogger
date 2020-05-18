package com.jiud.eum;

public enum LogType {
    INFO(1),
    WARNING(2),
    DEBUG(3),
    ERROR(4);

    private int statenum;

    LogType(int num) {
        this.statenum = num;
    }

    public  int getStatenum() {
        return statenum;
    }

    public static void main(String[] args) {
        System.out.println(LogType.INFO.getStatenum());
        System.out.println(LogType.WARNING.getStatenum());
    }
}
