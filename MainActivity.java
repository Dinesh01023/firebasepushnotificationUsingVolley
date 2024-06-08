package pcn.action.sunichith.developer.firebasepushnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="MainActivity";
    Button sendNotification;
    String token;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)
        {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }

        sendNotification=findViewById(R.id.sendnotification_btn);
        sendNotification.setOnClickListener(this);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("main send noti", "getInstanceId failed", task.getException());
                    return;
                }
                // Get new FCM registration token
                token = task.getResult();
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendnotification_btn:
                //here we send Notification
                SendNotifications();
                break;
            default:
                break;
        }
    }


    public void SendNotifications(){

        String postUrl= "https://fcm.googleapis.com/v1/projects/videoplayer-e7669/messages:send";

        String userFcmToken=token;
        String title="Hitesh ";
        String body="Sunichith Developer ";

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JSONObject mainObj=new JSONObject();
        try {
            JSONObject messageObject=new  JSONObject ();
            JSONObject notificationObject=new JSONObject();
            notificationObject.put("title",title);
            notificationObject.put("body",body);
            messageObject.put("token",userFcmToken);
            messageObject.put("notification",notificationObject);
            mainObj.put("message",messageObject);
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,postUrl,mainObj, response -> {
                Toast.makeText(MainActivity.this, "sended", Toast.LENGTH_SHORT).show();
            },volleyError->{
                Toast.makeText(MainActivity.this, "on error", Toast.LENGTH_SHORT).show();
            }){
                @NonNull
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Accesstoken accesstoken=new Accesstoken();
                    String accesskey= accesstoken.getAccessToken();

                    if (accesskey!=null){
                        Log.e(TAG,accesskey);
                    }else {
                        Log.e(TAG,"failed");
                    }
                    Map<String,String> header=new HashMap<>();
                    header.put("content-Type","application/json");
                    header.put("authorization","Bearer " + accesskey );
                    return header;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}