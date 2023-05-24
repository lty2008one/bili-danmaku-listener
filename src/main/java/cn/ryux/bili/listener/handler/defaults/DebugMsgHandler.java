package cn.ryux.bili.listener.handler.defaults;

import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.OpCode;
import cn.ryux.bili.listener.handler.interfaces.OpCodeMsgHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public final class DebugMsgHandler extends OpCodeMsgHandler {
    public DebugMsgHandler() {
        super(OpCode.CMD);
    }

    @Override
    public void handleMsg(BiliMsg msg) {
        log.debug("{} {}", msg.getOpCode(), new String(msg.getBytes(), StandardCharsets.UTF_8));
    }
}
