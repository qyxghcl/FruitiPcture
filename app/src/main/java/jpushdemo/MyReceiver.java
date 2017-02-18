package jpushdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.fruitpictures.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import static android.content.ContentValues.TAG;

/**
 * Created by hwj on 2017/2/18.
 */

public class MyReceiver extends BroadcastReceiver{
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            //获取通知管理者
            NotificationManager nm= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //创建通知
            Notification.Builder builder=new Notification.Builder(context);
            builder.setTicker(bundle.getString(JPushInterface.EXTRA_MESSAGE));
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("今天全网5折抢购。。。");
            builder.setContentText("原价999的衣服，现在只要9.99，还包邮。。。");
            //获取附加字段
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i("test", "json=" + json);// extras={"url":"http:\/\/www.baidu.com"}
            //解析json数据
            JSONObject jsonObject=null;
            String url="";
            try {
                jsonObject = new JSONObject(json);
                url = jsonObject.getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //创建一个延时意图
            Intent i=new Intent();
            i.setAction(Intent.ACTION_VIEW);
            if (!TextUtils.isEmpty(url)){
                i.setData(Uri.parse(url));
            }
            PendingIntent pendingIntent=PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            //发送通知
            nm.notify(99,builder.build());
            builder.setAutoCancel(true);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
          /*  Intent i = new Intent(context, TestActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);*/
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}
