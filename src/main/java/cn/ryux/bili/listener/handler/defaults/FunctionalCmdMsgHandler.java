package cn.ryux.bili.listener.handler.defaults;

import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.CmdType;
import cn.ryux.bili.listener.handler.interfaces.CmdMsgHandler;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Consumer;

public final class FunctionalCmdMsgHandler extends CmdMsgHandler {
    private final Consumer<Map<String, Object>> handler;

    public FunctionalCmdMsgHandler(String command, Consumer<Map<String, Object>> handler) {
        super(command);
        this.handler = handler;
    }

    public FunctionalCmdMsgHandler(CmdType cmdType, Consumer<Map<String, Object>> handler) {
        super(cmdType.getCommand());
        this.handler = handler;
    }

    @Override
    public void handleMsg(BiliMsg msg) {
        if (handler != null) {
            handler.accept(getDataMap(msg));
        }
    }
}
