import online.decentworld.message.cache.LocalUserContactCache;
import online.decentworld.message.config.ApplicationRootConfig;
import online.decentworld.rpc.dto.message.types.ChatRelation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Sammax on 2016/9/8.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationRootConfig.class})
public class SpringTest {


    @Autowired
    private LocalUserContactCache chatCache;

    private static Logger logger= LoggerFactory.getLogger(SpringTest.class);


    @Test
    public void test() throws InterruptedException {
        Thread.sleep(15000);
        for(int i=0;i<10;i++){
            chatCache.checkContacts("123", "4656", ChatRelation.FRIEND);
        }
    }
}
