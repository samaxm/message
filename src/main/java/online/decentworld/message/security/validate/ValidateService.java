package online.decentworld.message.security.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by Sammax on 2016/9/7.
 */
@Component
public class ValidateService
{

    @Resource(name = "simpleTokenValidate")
    private ValidateStrategy simpleTokenValidate;



    public boolean validate(ValidateInfo info,byte[] data){
        ValidateStrategy strategy=null;
        switch (info.getType()){
            case HTTPBASE:
                strategy=simpleTokenValidate;
                break;
        }
        if(strategy!=null){
            return strategy.validate(info,data);
        }else{
            return false;
        }
    }


}
