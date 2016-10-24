//package online.decentworld.message.http;
//
//import com.lmax.disruptor.RingBuffer;
//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
//import online.decentworld.message.core.event.MessageEventTranslator;
//import online.decentworld.message.core.event.MessageReceiveEvent;
//import online.decentworld.message.core.ValidateInfo;
//import online.decentworld.message.core.ValidateType;
//import online.decentworld.rpc.codc.protos.SimpleProtosCodec;
//import online.decentworld.rpc.dto.message.BaseMessage;
//import online.decentworld.rpc.dto.message.protos.ProtosVedioLikeMessage;
//import online.decentworld.rpc.dto.message.protos.ProtosVedioLikeMessageBody;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.AsyncContext;
//import javax.servlet.AsyncEvent;
//import javax.servlet.AsyncListener;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.Part;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Collection;
//import java.util.Enumeration;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * Created by Sammax on 2016/9/6.
// */
//@Controller
//@RequestMapping("/test")
//public class TestController {
//
//    @Resource(name = "messageRingBuffer")
//    private RingBuffer buffer;
//
//    @RequestMapping(value = "/one",method = RequestMethod.POST)
//    @ResponseBody
//    public void test(HttpServletRequest request){
//        try {
//            request.getParameterMap().forEach((key,value)->{
//                System.out.println(key+":"+value);
//            });
//            Collection<Part> parts=request.getParts();
//            int size=parts.size();
//            System.out.println(size);
//            for(Part p:parts){
//                System.out.println(p.getName());
//                if(!p.getName().equals("name")){
//                    System.out.println("good");
//                    InputStream is=p.getInputStream();
//                    SimpleProtosCodec codec=new SimpleProtosCodec();
//                    ByteOutputStream bos=new ByteOutputStream();
//                    byte buff[] = new byte[ 1024 ];
//                    int read;
//                    while( ( read = is.read( buff ) ) > 0 ) {
//                        bos.write( buff, 0, read );
//                    }
//                    byte[] data=bos.toByteArray();
//                    BaseMessage msg=codec.decode(data);
//                    ProtosVedioLikeMessage l=(ProtosVedioLikeMessage)msg;
//                    ProtosVedioLikeMessageBody body=(ProtosVedioLikeMessageBody)l.getMessageBody();
//                    System.out.println(body.getName());
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ServletException e) {
//            e.printStackTrace();
//        }
//        System.out.println("new request");
//        buffer.publishEvent(new MessageEventTranslator(), new byte[1], new ValidateInfo() {
//            @Override
//            public ValidateType getType() {
//                return null;
//            }
//        });
//        Enumeration<String> h=request.getHeaderNames();
//        while(h.hasMoreElements()){
//            String key=h.nextElement();
//            System.out.println(key+":"+request.getHeader(key));
//        }
//        final AsyncContext ctx=request.startAsync();
//        System.out.println(ctx.getTimeout());
//        ctx.setTimeout(Integer.MAX_VALUE);
//        System.out.println(ctx.getTimeout());
//
//        ctx.addListener(new AsyncListener() {
//            @Override
//            public void onComplete(AsyncEvent asyncEvent) throws IOException {
//
//                System.out.println("complete");
//            }
//
//            @Override
//            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
//                System.out.println("timeout");
//            }
//
//            @Override
//            public void onError(AsyncEvent asyncEvent) throws IOException {
//                System.out.println("error");
//            }
//            @Override
//            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
//                System.out.println("start");
//            }
//        });
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("started");
//                try {
//                    ctx.getResponse().getWriter().write("test");
//                    ctx.getResponse().getWriter().flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("finished");
//            }
//        }, 120000);
//
//    }
//
//}
