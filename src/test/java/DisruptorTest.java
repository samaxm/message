import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Sammax on 2016/9/7.
 */
public class DisruptorTest {

    public static void main(String[] args) {
        final Disruptor<LongValueEvent> disruptor=new Disruptor<LongValueEvent>(new EventFactory<LongValueEvent>() {
            @Override
            public LongValueEvent newInstance() {
                System.out.println("hello");
                return new LongValueEvent();
            }
        }, 8, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        disruptor.handleEventsWithWorkerPool(new LongValueEventHandler(),new LongValueEventHandler(),new LongValueEventHandler()).then(new LongValueEventHandler());
        disruptor.start();
        for(int i=0;i<10;i++){
            new Thread( new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<10;i++){
                        disruptor.publishEvent(new EventTranslator<LongValueEvent>() {
                            @Override
                            public void translateTo(LongValueEvent longValueEvent, long l) {
                                System.out.println("publish =========== thread#"+Thread.currentThread().getName()+" value#"+l);
                                longValueEvent.setValue(l);
                            }
                        });
                    }
                }
            },"producer"+i).start();
        }


    }

    public static class LongValueEvent{
        private long value;
        public void setValue(long value){
            this.value=value;
        }
        public long getValue(){
            return value;
        }
    }

    public static class LongValueEventHandler implements WorkHandler<LongValueEvent>,EventHandler<LongValueEvent>{
        @Override
        public void onEvent(LongValueEvent longValueEvent) throws Exception {
            System.out.println("name#"+Thread.currentThread().getName()+" value#"+longValueEvent.getValue()+" time#"+System.currentTimeMillis());
            longValueEvent.setValue(longValueEvent.getValue()*10);
        }

        @Override
        public void onEvent(LongValueEvent longValueEvent, long l, boolean b) throws Exception {
            System.out.println("i am after------>"+longValueEvent.getValue());
        }
    }


}
