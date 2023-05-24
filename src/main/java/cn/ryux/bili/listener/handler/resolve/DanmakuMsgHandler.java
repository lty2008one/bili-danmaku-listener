package cn.ryux.bili.listener.handler.resolve;

import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.BiliUserInfo;
import cn.ryux.bili.common.CmdType;
import cn.ryux.bili.listener.handler.interfaces.CmdMsgHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public abstract class DanmakuMsgHandler extends CmdMsgHandler {
    public DanmakuMsgHandler() {
        super(CmdType.DANMAKU);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleMsg(BiliMsg msg) {
        try {
            Map<String, Object> dataMap = getDataMap(msg);
            List<Object> infoList = (List<Object>) dataMap.get("info");

            String message = infoList.get(1).toString();
            BiliUserInfo biliUser = new BiliUserInfo();

            List<Object> userInfo = (List<Object>) infoList.get(2);
            biliUser.setUid(((Number) userInfo.get(0)).longValue());
            biliUser.setUsername(userInfo.get(1).toString());
            biliUser.setGuard(((Number)userInfo.get(2)).intValue() == 1);
            biliUser.setLevel(-1);

            List<Object> budgetInfo = (List<Object>) infoList.get(3);
            if (budgetInfo == null || budgetInfo.isEmpty()) {
                biliUser.setBudget("");
                biliUser.setBudgetLevel(0);
                biliUser.setBudgetLight(false);
                biliUser.setBudgetRoomId(-1);
            } else {
                biliUser.setBudget(budgetInfo.get(1).toString());
                biliUser.setBudgetLevel(((Number) budgetInfo.get(0)).intValue());
                biliUser.setBudgetLight(((Number) budgetInfo.get(11)).intValue() == 1);
                biliUser.setBudgetRoomId(((Number) budgetInfo.get(3)).intValue());
            }

            handleDanmaku(message, biliUser);
        } catch (Exception e) {
            log.error("解析弹幕信息失败", e);
        }
    }

    public abstract void handleDanmaku(String message, BiliUserInfo userInfo);
}
