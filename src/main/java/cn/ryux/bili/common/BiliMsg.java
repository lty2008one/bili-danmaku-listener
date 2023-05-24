package cn.ryux.bili.common;

import lombok.Getter;

import java.nio.charset.StandardCharsets;

public class BiliMsg {
    @Getter private final int opCode;
    @Getter private final byte[] bytes;

    public BiliMsg(OpCode opCode, String msg) {
        this.opCode = opCode.getOpCode();
        this.bytes = msg.getBytes(StandardCharsets.UTF_8);
    }

    public BiliMsg(OpCode opCode, byte[] bytes) {
        this.opCode = opCode.getOpCode();
        this.bytes = bytes;
    }

    public BiliMsg(OpCode opCode) {
        this.opCode = opCode.getOpCode();
        this.bytes = null;
    }

    public BiliMsg(int opCode, String msg) {
        this.opCode = opCode;
        this.bytes = msg.getBytes(StandardCharsets.UTF_8);
    }

    public BiliMsg(int opCode, byte[] bytes) {
        this.opCode = opCode;
        this.bytes = bytes;
    }

    public BiliMsg(int opCode) {
        this.opCode = opCode;
        this.bytes = null;
    }
}
