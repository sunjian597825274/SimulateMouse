package mousetvtest.sj.com.mousetouchtvtest;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import mousetvtest.sj.com.bean.TcpTouchBean;
import mousetvtest.sj.com.connect.TcpServer;
import mousetvtest.sj.com.utils.WifiUtils;
import mousetvtest.sj.com.widget.MouseView;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private TextView tv_startserver;
    private TextView tv_startclient;
    private TextView tv_sendclientmsg;
    private MouseView mMouseView;
    private ViewGroup contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        contentView = (ViewGroup) inflater.inflate(R.layout.activity_main, null);
        setContentView(contentView);
        EventBus.getDefault().register(this);
        mMouseView = new MouseView(MainActivity.this);
        tv_startserver = findViewById(R.id.tv_startserver);
        tv_startclient = findViewById(R.id.tv_startclient);
        tv_sendclientmsg = findViewById(R.id.tv_sendclientmsg);
        tv_startserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TcpServer.startServer();
            }
        });
        tv_startclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TouchActivity.class));
            }
        });
        tv_sendclientmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击事件模拟",Toast.LENGTH_LONG).show();
            }
        });
        showMouseView((ViewGroup) getWindow().getDecorView());
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(RecyclerviewAdapter.createAdapter(MainActivity.this));

    }
    public void showMouseView(ViewGroup mParentView) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);


        if(mMouseView != null) {
            mParentView.addView(mMouseView, lp);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "ip:"+WifiUtils.getWifiIp(MainActivity.this));
        thread.start();
        setMouseClick(300,600);
    }
    Thread thread =new Thread(){
        @Override
        public void run() {
            super.run();
            test();
        }
    };
    private void test(){
        Instrumentation inst = new Instrumentation();
        inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN, 200, 500, 0));
        inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP, 201, 500, 0));
    }
    public void setMouseClick(int x, int y){
        MotionEvent evenDownt = MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0);
        dispatchTouchEvent(evenDownt);
        MotionEvent eventUp = MotionEvent.obtain(SystemClock.uptimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_UP, x, y, 0);
        dispatchTouchEvent(eventUp);
        evenDownt.recycle();
        eventUp.recycle();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG+"-onTouchEvent",event.getAction()+"-"+event.getX());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,ev.getAction()+"-"+ev.getX());
        return super.dispatchTouchEvent(ev);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TcpTouchBean event){
        Log.e(TAG,"tcp-eventbus");
        mMouseView.moveMouseView(event.x,event.y);
        setMouseEvent(event.eventAction,mMouseView.getmMouseX(),mMouseView.getmMouseY());
    }
    public void setMouseEvent(int eventAction,int x, int y){
        MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis() + 100, eventAction, x, y, 0);
        dispatchTouchEvent(motionEvent);
        motionEvent.recycle();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
