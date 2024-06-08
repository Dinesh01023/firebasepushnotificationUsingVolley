package pcn.action.sunichith.developer.firebasepushnotification;


import android.util.Log;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Accesstoken {

    private static final String firebaseMessagingscope="https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken(){
        try{
            String jsonString="Here Your Access Token getfile";

            InputStream stream=new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials=GoogleCredentials.fromStream(stream).createScoped(Lists.newArrayList(firebaseMessagingscope));
            googleCredentials.refresh();
            return googleCredentials.getAccessToken().getTokenValue();

        } catch (IOException e) {
            Log.e("EROR",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
