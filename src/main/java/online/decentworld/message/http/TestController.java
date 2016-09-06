package online.decentworld.message.http;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import online.decentworld.message.core.MessageEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sammax on 2016/9/6.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource(name = "messageRingBuffer")
    private RingBuffer buffer;

    @RequestMapping("/one")
    @ResponseBody
    public void test(HttpServletRequest request){
        System.out.println("new request");
        buffer.publishEvent((event,sequence)->{
            System.out.println("test-distrupors");
            ((MessageEvent)event).set(1);
        });
        Enumeration<String> h=request.getHeaderNames();
        while(h.hasMoreElements()){
            String key=h.nextElement();
            System.out.println(key+":"+request.getHeader(key));
        }
        final AsyncContext ctx=request.startAsync();
        System.out.println(ctx.getTimeout());
        ctx.setTimeout(Integer.MAX_VALUE);
        System.out.println(ctx.getTimeout());

        ctx.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("complete");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                System.out.println("timeout");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println("error");
            }
            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println("start");
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("started");
                try {
                    ctx.getResponse().getWriter().write("test");
                    ctx.getResponse().getWriter().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("finished");
            }
        }, 120000);

    }

}
