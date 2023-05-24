package cn.ryux.bili.listener.codec;

import cn.hutool.core.io.IoUtil;
import cn.ryux.bili.common.BiliMsg;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.zip.InflaterInputStream;

public class BiliCodecUtil {
    private static final short headLength = 16;
    private static final short protocolVer = 2;
    private static final int magicNumber = 1;

    public static byte[] encode(BiliMsg msg) {
        byte[] bytes = msg.getBytes();
        int packetLength = 16 + (bytes == null ? 0 : bytes.length);

        ByteBuffer byteBuffer = ByteBuffer.allocate(packetLength);
        byteBuffer.putInt(packetLength);
        byteBuffer.putShort(headLength);
        byteBuffer.putShort(protocolVer);
        byteBuffer.putInt(msg.getOpCode());
        byteBuffer.putInt(magicNumber);
        if (bytes != null) byteBuffer.put(bytes);

        return byteBuffer.array();
    }

    public static BiliMsg decode(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);

        int packetLength = byteBuffer.getInt();
        byteBuffer.getInt();        // 丢弃头长度与版本号
        int opCode = byteBuffer.getInt();
        byteBuffer.getInt();        // 丢弃魔数

        if (packetLength > 16) {
            byte[] bytes = new byte[packetLength - 16];
            byteBuffer.get(bytes);

            if (bytes[0] == 120) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream);
                byte[] writeData = IoUtil.readBytes(inflaterInputStream);
                return decode(writeData);
            }

            return new BiliMsg(opCode, bytes);
        }

        return new BiliMsg(opCode);
    }



}
