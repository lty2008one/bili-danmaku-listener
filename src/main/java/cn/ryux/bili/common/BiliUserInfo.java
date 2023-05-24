package cn.ryux.bili.common;

import lombok.Data;

@Data
public class BiliUserInfo {
    private long uid;                   // 用户ID
    private String username;            // 用户名
    private int level;                  // 用户等级
    private boolean guard;              // 是否管理员

    private String budget;              // 徽章名称
    private int budgetRoomId;           // 徽章房间号
    private int budgetLevel;            // 徽章等级
    private boolean budgetLight;        // 徽章点亮状态
}
