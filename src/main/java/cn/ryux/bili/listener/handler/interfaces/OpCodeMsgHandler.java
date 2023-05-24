package cn.ryux.bili.listener.handler.interfaces;

import cn.ryux.bili.common.BiliMsg;
import cn.ryux.bili.common.OpCode;

public abstract class OpCodeMsgHandler implements BiliMsgHandler {
    private final int[] opCodes;

    public OpCodeMsgHandler(int ...opCodes) {
        this.opCodes = opCodes;
    }

    public OpCodeMsgHandler(OpCode ...opCodes) {
        this.opCodes = new int[opCodes.length];
        for (int i = 0; i < opCodes.length; i++) {
            this.opCodes[i] = opCodes[i].getOpCode();
        }
    }

    @Override
    public boolean checkMsg(BiliMsg msg) {
        if (msg != null) {
            for (int opCode : opCodes) {
                if (msg.getOpCode() == opCode) {
                    return true;
                }
            }
        }

        return false;
    }

}
