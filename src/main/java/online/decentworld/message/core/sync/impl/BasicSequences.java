package online.decentworld.message.core.sync.impl;

import online.decentworld.message.core.sync.Sequences;

/**
 * Created by Sammax on 2016/10/20.
 */
public class BasicSequences implements Sequences {

    private long currentSequenceNum;
    private long lastSetTime;


    @Override
    public long getCurrentSequenceNum() {
        return 0;
    }

    @Override
    public boolean setCurrentSequenceNum(long currentSequenceNum) {
        if(this.currentSequenceNum>currentSequenceNum){
            return false;
        }else{
            this.currentSequenceNum=currentSequenceNum;
            this.lastSetTime=System.currentTimeMillis();
            return true;
        }
    }

    @Override
    public long getLastSetTime() {
        return lastSetTime;
    }
}
