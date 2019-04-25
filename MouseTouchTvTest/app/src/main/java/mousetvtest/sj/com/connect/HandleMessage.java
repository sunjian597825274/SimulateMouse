package mousetvtest.sj.com.connect;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import mousetvtest.sj.com.bean.TcpTouchBean;

/**
 * Created by sunjian on 2019/4/22.
 */

public class HandleMessage {
    public static void handleMessage(String data){
        TcpTouchBean tcpTouchBean = new TcpTouchBean();
        try {
            JSONObject jsonObject = new JSONObject(data);
            tcpTouchBean.eventAction = jsonObject.getInt("eventAction");
            tcpTouchBean.x = jsonObject.getInt("x");
            tcpTouchBean.y = jsonObject.getInt("y");
            EventBus.getDefault().post(tcpTouchBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
