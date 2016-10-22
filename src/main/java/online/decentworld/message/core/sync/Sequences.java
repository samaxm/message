package online.decentworld.message.core.sync;

/**
 * Created by Sammax on 2016/10/20.
 */
public interface Sequences {

    long getTargetSequenceNum();

    long getCurrentSequenceNum();

    void setCurrentSequenceNum();

    void setTargetSequenceNum();
}
