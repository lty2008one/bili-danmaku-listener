package cn.ryux.bili.listener;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.OpCode;
import cn.ryux.bili.config.GsonConfig;
import cn.ryux.bili.listener.codec.BiliCodecUtil;
import cn.ryux.bili.listener.config.BiliDanmakuConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BiliWebsocket extends WebSocketClient {
    private static final String fetchRoomIDURL = "https://api.live.bilibili.com/room/v1/Room/room_init?id=";
    private static final Gson gson = GsonConfig.builder().create();
    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    private static final byte[] heartBeat = BiliCodecUtil.encode(new BiliMsg(OpCode.CLIENT_HEARTBEAT));

    private final BiliDanmakuConfig config;
    private final BiliDanmaku danmaku;
    private final int roomId;
    private final byte[] welcome;

    public BiliWebsocket(BiliDanmaku danmaku, BiliDanmakuConfig config, int roomId) {
        super(URLUtil.toURI(config.getWebsocketURL()));
        this.roomId = getRoomId(roomId);
        this.danmaku = danmaku;
        this.config = config;
        this.welcome = BiliCodecUtil.encode(new BiliMsg(OpCode.AUTHENTICATION, initMessage(this.roomId)));
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("成功建立连接, 状态码: {}", serverHandshake.getHttpStatus());
        this.send(welcome);         // 发送客户端加入直播间消息
        log.info("成功加入直播间 {}", roomId);
        schedule.submit(this::heartBeat);
    }

    public void heartBeat() {
        try {
            if (config.isDebugMode()) log.debug("发送心跳包");
            this.send(heartBeat);
            schedule.schedule(this::heartBeat, 20, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("发送心跳包失败, 原因:{}:{}", e.getClass().getSimpleName(), e.getMessage());
            schedule.submit(this::reconnect);
        }
    }

    public void reconnect() {
        try {
            this.reconnectBlocking();
        } catch (Exception e) {
            log.error("重连失败，原因:{}:{}，等待10s...", e.getClass().getSimpleName(), e.getMessage());
            schedule.schedule(this::reconnect, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onMessage(String s) {}

    @Override
    public void onMessage(ByteBuffer bytes) {
        byte[] array = bytes.array();
        if (config.isDebugMode()) log.debug("bili-websocket 接收到字节流消息, 长度 {}byte", array.length);
        BiliMsg msg = BiliCodecUtil.decode(array);
        danmaku.handleMessage(msg);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }

    private static String initMessage(int roomId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("uid", 0);
        payload.put("roomid", roomId);
        payload.put("protover", 1);
        payload.put("platform", "web");

        return gson.toJson(payload);
    }

    @SuppressWarnings("unchecked")
    private int getRoomId(int roomId) {
        try {
            String body = HttpRequest.get(fetchRoomIDURL + roomId).execute().body();
            Map<String, Object> resp = gson.fromJson(body, new TypeToken<Map<String, Object>>(){}.getType());
            assert "0".equals(resp.get("code") + "");
            Map<String, Object> data = (Map<String, Object>) resp.get("data");
            final int finalRoomId = ((Number)data.get("room_id")).intValue();
            if (config.isDebugMode() && roomId != finalRoomId) {
                log.debug("通过 roomId {} 获取到真正的 roomId 为 {}", roomId, finalRoomId);
            }
            return finalRoomId;
        } catch (Exception e) {
            log.warn("获取接口中的roomID失败, 使用配置的roomId");
            return roomId;
        }
    }
}
