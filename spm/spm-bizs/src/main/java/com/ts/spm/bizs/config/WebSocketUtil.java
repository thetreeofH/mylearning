package com.ts.spm.bizs.config;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.constants.CommonConstants;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Administrator on 2020/6/22.
 */
@IgnoreClientToken
@IgnoreUserToken
@ServerEndpoint(value = "/websocket/{userid}")
@Component
public class WebSocketUtil {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识。
    private static CopyOnWriteArraySet<WebSocketUtil> webSocketSet = new CopyOnWriteArraySet<WebSocketUtil>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据。
    private Session session;

    public String userid;

    public static Map<String,String> HOME_MAPS=new HashMap<>();

    public static String GOLBAL_USER_ID;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userid") String userid, Session session){
        this.userid = userid;
        this.session = session;
        webSocketSet.add(this);     //加入set中。
        addOnlineCount();           //在线数加1。
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(CloseReason reason){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        HOME_MAPS.remove(this.userid);
        System.out.println(HOME_MAPS.size());
    }

    public static void sendMessage(String message, String id) throws IOException {
        for(WebSocketUtil item: webSocketSet){
            if(item.userid.equalsIgnoreCase(id)) {
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public static void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);
        JSONObject obj = JSONObject.parseObject(message);
        String receiveId = obj.getString("receiveId");
        String deptId = obj.getString("deptId");
        String userId=obj.getString("userId");
        if(StringUtils.isNotEmpty(userId)){
            HOME_MAPS.put(userId,deptId);
        }
        //群发消息
        for(WebSocketUtil item: webSocketSet){
            try {
                if(StringUtils.isNotEmpty(receiveId)&&receiveId.contains(item.userid)) {

                    item.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        //session.getBasicRemote().sendText(message);
        synchronized (session) {
            session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketUtil.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketUtil.onlineCount--;
    }
}
