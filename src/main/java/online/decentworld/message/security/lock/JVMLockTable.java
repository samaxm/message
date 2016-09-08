package online.decentworld.message.security.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sammax on 2016/9/8.
 */
public class JVMLockTable {
    private static ConcurrentHashMap<String,Lock> locks=new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,Long> timer=new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,Condition> condition=new ConcurrentHashMap<>();

    private static long period=5*60*60*1000;
    private static Logger logger= LoggerFactory.getLogger(JVMLockTable.class);

    public static Lock getWealthLock(String dwID){
        Lock nl=new ReentrantLock();
        Lock lock=locks.putIfAbsent(dwID,nl);
        timer.put(dwID, System.currentTimeMillis());
        if(lock==null){
            logger.error("[NEW LOCK!]");
            return nl;
        }else{
            return lock;
        }
    }

    public static Condition getLockCondition(String dwID){
        Lock lock=getWealthLock(dwID);
        Condition condition1=lock.newCondition();
        Condition condition2=condition.putIfAbsent(dwID, condition1);
        if(condition2!=null){
            return condition2;
        }else{
            logger.error("[NEW CONDITION!]");
            return condition1;
        }
    }

    static{
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timer.forEach((dwID,time)->{
                    try {
                        if (System.currentTimeMillis() - time > period) {
                            locks.remove(dwID);
                            timer.remove(dwID);
                        }
                    }catch (Exception ex){
                        logger.warn("[LOCK_CLEAN_FAILED] dwID#"+dwID,ex);
                    }
                });
            }
        },period);
    }


}
