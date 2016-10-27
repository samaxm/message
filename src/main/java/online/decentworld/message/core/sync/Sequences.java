package online.decentworld.message.core.sync;

/**
 * Created by Sammax on 2016/10/20.
 */
public interface Sequences {

    long getCurrentSequenceNum();

    boolean setCurrentSequenceNum(long currentSequenceNum);

    long getLastSetTime();
}
