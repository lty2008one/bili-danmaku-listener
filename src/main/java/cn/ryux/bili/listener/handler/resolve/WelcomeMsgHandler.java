package cn.ryux.bili.listener.handler.resolve;

import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.BiliUserInfo;
import cn.ryux.bili.common.CmdType;
import cn.ryux.bili.listener.handler.interfaces.CmdMsgHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class WelcomeMsgHandler extends CmdMsgHandler {
    public WelcomeMsgHandler() {
        super(CmdType.WELCOME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleMsg(BiliMsg msg) {
        try {
            Map<String, Object> dataMap = getDataMap(msg);
            Map<String, Object> data = (Map<String, Object>) dataMap.get("data");

            BiliUserInfo biliUser = new BiliUserInfo();

            biliUser.setUid(((Number)data.get("uid")).longValue());
            biliUser.setUsername(data.get("uname").toString());

            Map<String, Object> budgetInfo = (Map<String, Object>) data.get("fans_medal");
            biliUser.setBudget(budgetInfo.get("medal_name").toString());
            biliUser.setBudgetLevel(((Number) budgetInfo.get("medal_level")).intValue());

            handleWelcome(biliUser);
        } catch (Exception e) {
            log.error("解析进入直播间消息失败", e);
        }
    }

    public abstract void handleWelcome(BiliUserInfo userInfo);
}
