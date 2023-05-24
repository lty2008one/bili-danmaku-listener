package cn.ryux.bili.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CmdType {
    DANMAKU("DANMU_MSG", "弹幕消息"),
    WELCOME("INTERACT_WORD", "用户进入直播间提醒"),
    NOTICE_MSG("NOTICE_MSG", "消息通知"),
    STOP_LIVE_ROOM_LIST("STOP_LIVE_ROOM_LIST", "关闭直播直播间列表")
    ;

    @Getter private final String command;
    private final String desc;
}
