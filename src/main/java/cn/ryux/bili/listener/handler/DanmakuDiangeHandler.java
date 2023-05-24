package cn.ryux.bili.listener.handler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrFormatter;
import cn.ryux.bili.common.BiliUserInfo;
import cn.ryux.bili.listener.handler.resolve.DanmakuMsgHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class DanmakuDiangeHandler extends DanmakuMsgHandler {
    private final List<DiangeInfo> list = new LinkedList<>();

    @Override
    public void handleDanmaku(String message, BiliUserInfo userInfo) {
        if (message.trim().startsWith("点歌 ")) {
            String name = message.substring(3);
            list.add(new DiangeInfo(name, userInfo));
            log.info("用户 【{}】[{}#{}] 点歌 {}", userInfo.getUsername(), userInfo.getBudget(), userInfo.getBudgetLevel(), name);
            FileUtil.writeUtf8String(list.subList(0, 5).stream().map(DiangeInfo::toString).collect(Collectors.joining("\n")), new File("output.txt"));
        }
    }

    @Data
    public static class DiangeInfo {
        private final String name;
        private final BiliUserInfo user;

        @Override
        public String toString() {
            return StrFormatter.format("歌名: {}, 点歌人: {}", name, user.getUsername());
        }
    }

}
