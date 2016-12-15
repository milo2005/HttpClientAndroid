package unicauca.movil.clima.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Dario Chamorro on 9/12/2016.
 */

public class HttpConnection {

    static final int CONNECT_TIMEOUT=10000;
    static final int READ_TIMEOUT=7000;

    public String get(String url) throws IOException {
        return request("GET", url, null);
    }

    public String post(String url, String json) throws IOException {
        return request("POST", url, json);
    }

    public String put(String url, String json) throws IOException {
        return request("PUT", url, json);
    }

    public String delete(String url) throws IOException {
        return request("DELETE", url, null);
    }

    private String request(String method, String url, String json) throws IOException {
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        con.setRequestMethod(method);
        con.setDoInput(true);

        con.setConnectTimeout(CONNECT_TIMEOUT);
        con.setReadTimeout(READ_TIMEOUT);

        if(json != null)
            con.setDoOutput(true);

        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Accept","application/json");

        con.connect();

        if(json != null){
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(json);
            writer.flush();
            writer.close();
        }

        return inputToString(con.getInputStream());
    }

    private String inputToString(InputStream in) throws IOException {

        InputStreamReader reader = new InputStreamReader(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int ch;

        while ((ch = reader.read())!= -1){
            out.write(ch);
        }
        String rta = new String(out.toByteArray());

        reader.close();
        out.close();
        in.close();

        return  rta;

    }

}

