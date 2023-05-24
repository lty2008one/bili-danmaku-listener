package cn.ryux.bili.common;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OpCode {
    CLIENT_HEARTBEAT(2, "客户端发送心跳包"),
    POPULARITY_VALUE(3, "人气值"),
    CMD(5, "命令"),
    AUTHENTICATION(7, "认证并加入房间"),
    SERVER_HEARTBEAT(8, "服务器发送心跳包")

    ;

    @Getter private final int opCode;
    private final String desc;
}
