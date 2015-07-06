package res.truecaller.sample.com.truecallersample.control;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import res.truecaller.sample.com.truecallersample.model.ResponseDetails;

/**
 * Created by nitinraj.arvind on 7/6/2015.
 * All methods in this class would run on the thread that calls it
 */
public class RestRelatedWork {

    /**The url given in the test*/
    private static String TRUECALLER_SUPPORT_URL = "http://www.truecaller.com/support";
    private static RestRelatedWork instance = null;

    private Context context;

    private RestRelatedWork(){
        super();
    }

    private RestRelatedWork(Context mContext){
        super();
        this.context = mContext;
    }

    public static RestRelatedWork getInstance(Context mContext){
        if(instance==null){
            instance = new RestRelatedWork(mContext);
        }
        return instance;
    }


    /**
     * The version of getDetails that would get the webPage from true caller support url
     * {@Link TRUECALLER_SUPPORT_URL}
     * */
    public ResponseDetails getWebPage(Context mContext) throws ClientProtocolException, IOException{
        return getWebPage(mContext, TRUECALLER_SUPPORT_URL);
    }

    /**
     * The url we have to get
     * */
    public ResponseDetails getWebPage(Context context, String url) throws ClientProtocolException, IOException{
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        // replace with your url
        ResponseDetails responseDetails = null;
        HttpResponse response;
        response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = null;
        HttpEntity entity = response.getEntity();
        if(entity != null) {
            responseBody = EntityUtils.toString(entity);
        }


        return new ResponseDetails(statusCode, responseBody);
    }

}
