package cn.ryux.bili.listener.handler.interfaces;

import cn.ryux.bili.common.BiliMsg;

public interface BiliMsgHandler {

    boolean checkMsg(BiliMsg msg);

    void handleMsg(BiliMsg msg);

}
