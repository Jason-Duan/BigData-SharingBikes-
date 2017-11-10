package com.jason.service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.jason.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    //在service层调用Redis的api，将Redis中的数据查出来
    //操作Redis中string类型的k/v的
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean sendMsg(String nationCode, String phoneNum) {
        boolean flag = true;
        //调用腾讯云的短信api，实现短信发送功能
        try {
            //String appkey = stringRedisTemplate.opsForValue().get("appkey");
            //SmsSingleSender sender = new SmsSingleSender(1400048618, appkey);
            int code = (int)((new Random().nextDouble() * 9 + 1) * 1000);
            //将手机号和真实验证码保存到Redis中
            stringRedisTemplate.opsForValue().set(phoneNum,code+"",120, TimeUnit.SECONDS);
            //SmsSingleSenderResult result = sender.send(0, nationCode, phoneNum, "您的登陆密码为"+code, "", "");
            //System.out.print(result);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean verify(String phoneNum, String verifyCode) {
        boolean flag = false;
        String trueCode = stringRedisTemplate.opsForValue().get(phoneNum);
        if(trueCode != null && trueCode.equals(verifyCode)){
            flag = true;
        }
        return flag;
    }

    @Override
    public void register(String phoneNum) {
        User user = new User();
        user.setPhoneNum(phoneNum);
        user.setStatus("deposit");
        mongoTemplate.save(user);
    }
}
