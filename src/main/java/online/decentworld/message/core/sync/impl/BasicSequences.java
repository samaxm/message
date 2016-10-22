package online.decentworld.message.core.sync.impl;

import online.decentworld.message.core.sync.Sequences;

/**
 * Created by Sammax on 2016/10/20.
 */
public class BasicSequences implements Sequences {

    private long currentSequenceNum;
    private long targetSequenceNum;

    @Override
    public long getTargetSequenceNum() {
        return 0;
    }

    @Override
    public long getCurrentSequenceNum() {
        return 0;
    }

    @Override
    public void setCurrentSequenceNum() {

    }

    @Override
    public void setTargetSequenceNum() {

    }
}
