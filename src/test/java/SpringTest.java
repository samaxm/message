import online.decentworld.message.charge.IChargeService;
import online.decentworld.message.config.ApplicationRootConfig;
import online.decentworld.rdb.config.DBConfig;
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
@ContextConfiguration(classes={DBConfig.class, ApplicationRootConfig.class})
public class SpringTest {

    @Autowired
    private IChargeService chargeService;
    private static Logger logger= LoggerFactory.getLogger(SpringTest.class);


    @Test
    public void test() throws InterruptedException {



    }
}
