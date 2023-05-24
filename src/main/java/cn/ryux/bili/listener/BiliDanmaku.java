package cn.ryux.bili.listener;

import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.CmdType;
import cn.ryux.bili.listener.config.BiliDanmakuConfig;
import cn.ryux.bili.listener.handler.*;
import cn.ryux.bili.listener.handler.interfaces.BiliMsgHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class BiliDanmaku {
    private final BiliDanmakuConfig config;
    private final List<BiliMsgHandler> handlers = new ArrayList<>();
    private Integer roomId;

    public BiliDanmaku() {
        this.config = new BiliDanmakuConfig();
    }

    public BiliDanmaku(BiliDanmakuConfig config) {
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public void start() {
        BiliWebsocket biliWebsocket = new BiliWebsocket(this, config, roomId);
        biliWebsocket.connect();
    }

    public BiliDanmaku registerMessageHandler(BiliMsgHandler handler) {
        this.handlers.add(handler);
        return this;
    }

    public BiliDanmaku room(int roomId) {
        this.roomId = roomId;
        return this;
    }

    public BiliDanmaku debug(boolean enable) {
        this.config.setDebugMode(enable);
        return this;
    }

    void handleMessage(BiliMsg msg) {
        if (config.isDebugMode()) log.debug("收到消息，开始依次加载消息处理器");
        for (BiliMsgHandler handler : handlers) {
            if (config.isDebugMode()) log.debug("加载消息处理器 {}", handler.getClass().getName());
            try {
                if (handler.checkMsg(msg)) {
                    try {
                        if (config.isDebugMode()) log.debug("消息处理器 {} 开始处理消息", handler.getClass().getName());
                        long start = System.currentTimeMillis();
                        handler.handleMsg(msg);
                        if (config.isDebugMode()) log.debug("消息处理器 {} 消息处理完毕，耗时{}ms", handler.getClass().getName(), System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        log.error("消息处理器 {} 处理消息时报错", handler.getClass().getSimpleName(), e);
                    }
                } else {
                    if (config.isDebugMode()) log.debug("消息处理器 {} 不处理此消息", handler.getClass().getName());
                }
            } catch (Exception e) {
                log.error("消息处理器 {} 检查消息时报错", handler.getClass().getSimpleName(), e);
            }
        }
    }

}
