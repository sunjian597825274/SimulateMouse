package mousetvtest.sj.com.mousetouchtvtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import mousetvtest.sj.com.bean.TcpTouchBean;
import mousetvtest.sj.com.connect.TcpClient;

/**
 * Created by sunjian on 2019/4/22.
 */

public class TouchActivity extends Activity{
    private ViewGroup contentView;
    float lastX = 0;
    float lastY = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        contentView = (ViewGroup) inflater.inflate(R.layout.activity_touch, null);
        setContentView(contentView);
        TcpClient.startClient("172.16.199.107",8080);
        findViewById(R.id.v_touch).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                TcpTouchBean tcpTouchBean = new TcpTouchBean();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tcpTouchBean.eventAction = MotionEvent.ACTION_DOWN;
                        tcpTouchBean.x = 0;
                        tcpTouchBean.y = 0;
                        TcpClient.sendTcpMessage(tcpTouchBean.toJsonString());
                        lastX = event.getX();
                        lastY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        tcpTouchBean.eventAction = MotionEvent.ACTION_MOVE;
                        tcpTouchBean.x = (int) (event.getX() - lastX);
                        tcpTouchBean.y = (int) (event.getY() - lastY);
                        TcpClient.sendTcpMessage(tcpTouchBean.toJsonString());
                        lastX = event.getX();
                        lastY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        tcpTouchBean.eventAction = MotionEvent.ACTION_UP;
                        tcpTouchBean.x = (int) (event.getX() - lastX);
                        tcpTouchBean.y = (int) (event.getY() - lastY);
                        TcpClient.sendTcpMessage(tcpTouchBean.toJsonString());
                        lastX = event.getX();
                        lastY = event.getY();
                        break;
                }
                return true;
            }
        });
    }
}
