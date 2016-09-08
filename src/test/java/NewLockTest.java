import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sammax on 2016/9/8.
 */
public class NewLockTest {
    private static Lock lock=new ReentrantLock();
    private static Condition condition=lock.newCondition();
    private static int k=0;

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<1000;i++){
            new Thread(()->{

                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(true){
                    if(lock.tryLock()){
                        try{
                            if(System.currentTimeMillis()%3==0){
                                condition.await();
                            }
                            if(System.currentTimeMillis()%4==0){
                                condition.signalAll();
                            }
                            k++;
                        }catch (Exception ex){

                        }finally {
                            lock.unlock();
                            break;
                        }
                    }
                }
            }).start();
        }
        Thread.currentThread().sleep(5000);
        System.out.println(k);
    }
}
