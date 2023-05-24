package cn.ryux.bili.listener.handler;

import cn.ryux.bili.common.BiliUserInfo;
import cn.ryux.bili.listener.handler.resolve.DanmakuMsgHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public final class DanmakuOutputHandler extends DanmakuMsgHandler {
    @Override
    public void handleDanmaku(String message, BiliUserInfo userInfo) {
        log.info("{}【{}】[{}#{}]: {}",
            userInfo.isGuard() ? "<房>" : "",
            userInfo.getUsername(),
            userInfo.getBudget(),
            userInfo.getBudgetLevel(),
            message
        );
    }
}
