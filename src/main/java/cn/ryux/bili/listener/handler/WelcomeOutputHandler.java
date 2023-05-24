package cn.ryux.bili.listener.handler;

import cn.hutool.core.util.StrUtil;
import cn.ryux.bili.common.BiliUserInfo;
import cn.ryux.bili.listener.handler.resolve.WelcomeMsgHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class WelcomeOutputHandler extends WelcomeMsgHandler {
    @Override
    public void handleWelcome(BiliUserInfo userInfo) {
        if (StrUtil.isNotBlank(userInfo.getBudget())) {
            log.info("用户 【{}】[{}#{}] 进入直播间", userInfo.getUsername(), userInfo.getBudget(), userInfo.getBudgetLevel());
        } else {
            log.info("用户 【{}】 进入直播间", userInfo.getUsername());
        }
    }
}
