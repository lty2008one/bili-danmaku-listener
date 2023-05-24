package cn.ryux.bili.listener.config;

import lombok.Data;

@Data
public class BiliDanmakuConfig {
    // request
    private String websocketURL = "wss://broadcastlv.chat.bilibili.com:2245/sub";
    private String fetchRoomIdURL = "https://api.live.bilibili.com/room/v1/Room/room_init?id=";

    // codec
    private short headLength = 16;
    private short protocolVer = 2;
    private short magicNumber = 1;

    // debug
    private boolean debugMode = false;
}
