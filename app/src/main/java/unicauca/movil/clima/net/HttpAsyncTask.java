package unicauca.movil.clima.net;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by Dario Chamorro on 9/12/2016.
 */

public class HttpAsyncTask extends AsyncTask<String, Integer, String> {

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;

    public interface OnResponseListener{
        void onResponse(String response);
    }

    int method;
    OnResponseListener onResponseListener;

    public HttpAsyncTask(int method, OnResponseListener onResponseListener) {
        this.method = method;
        this.onResponseListener = onResponseListener;
    }

    @Override
    protected String doInBackground(String... strings) {

        HttpConnection con = new HttpConnection();
        String rta = null;
        try {
            switch (method) {
                case GET:
                    rta = con.get(strings[0]);
                    break;
                case POST:
                    rta = con.post(strings[0], strings[1]);
                    break;
                case PUT:
                    rta = con.post(strings[0], strings[1]);
                    break;
                case DELETE:
                    rta = con.get(strings[0]);
                    break;
            }
        }catch (SocketTimeoutException e){

        }catch (IOException e){}

        return rta;
    }

    @Override
    protected void onPostExecute(String s) {
        onResponseListener.onResponse(s);
    }
}
