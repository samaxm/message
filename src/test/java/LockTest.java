import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sammax on 2016/9/8.
 */
public class LockTest {
    private static Lock lock=new ReentrantLock();

    private static int k=0;

    public static void main(String[] args) throws InterruptedException {
        String path="helloworld123123";
        System.out.println(path.substring(path.lastIndexOf("world")+"world".length()));
    }

}
