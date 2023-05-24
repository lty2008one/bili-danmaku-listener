package cn.ryux.bili;

import cn.ryux.bili.common.CmdType;
import cn.ryux.bili.listener.BiliDanmaku;
import cn.ryux.bili.listener.handler.DanmakuOutputHandler;
import cn.ryux.bili.listener.handler.DanmakuDiangeHandler;
import cn.ryux.bili.listener.handler.defaults.FunctionalCmdMsgHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        new BiliDanmaku().room(Integer.parseInt(args[0]))     // 47867
//            .registerMessageHandler(new DebugMsgHandler())
            .registerMessageHandler(new DanmakuOutputHandler())
//            .registerMessageHandler(new WelcomeInfoLogHandler())
            .registerMessageHandler(new DanmakuDiangeHandler())
            .registerMessageHandler(new FunctionalCmdMsgHandler(CmdType.NOTICE_MSG, msgData -> {
                log.info("系统消息: {}", msgData.get("msg_common").toString());
            }))
            .start();
    }

}