package cn.ryux.bili.listener.handler.interfaces;

import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.CmdType;
import cn.ryux.bili.common.OpCode;
import cn.ryux.bili.config.GsonConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class CmdMsgHandler extends OpCodeMsgHandler {
    private final Gson gson = GsonConfig.builder().create();
    private final String command;

    public CmdMsgHandler(String command) {
        super(OpCode.CMD);
        this.command = command;
    }

    public CmdMsgHandler(CmdType command) {
        super(OpCode.CMD);
        this.command = command.getCommand();
    }

    @Override
    public boolean checkMsg(BiliMsg msg) {
        if (super.checkMsg(msg)) {
            Map<String, Object> data = getDataMap(msg);
            return command.equals(data.get("cmd"));
        }

        return false;
    }

    protected Map<String, Object> getDataMap(BiliMsg msg) {
        String content = new String(msg.getBytes(), StandardCharsets.UTF_8);
        return gson.fromJson(content, new TypeToken<Map<String, Object>>(){}.getType());
    }
}
