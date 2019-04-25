package mousetvtest.sj.com.bean;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunjian on 2019/4/22.
 */

public class TcpTouchBean {
    public int eventAction;
    public int x;
    public int y;

    public String toJsonString(){
        return JSON.toJSONString(this);
    }
}
