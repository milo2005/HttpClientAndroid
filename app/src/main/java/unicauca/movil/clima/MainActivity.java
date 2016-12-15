package unicauca.movil.clima;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import unicauca.movil.clima.databinding.ActivityMainBinding;
import unicauca.movil.clima.net.HttpAsyncTask;

public class MainActivity extends AppCompatActivity implements HttpAsyncTask.OnResponseListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        HttpAsyncTask task = new HttpAsyncTask(HttpAsyncTask.GET, this);
        task.execute(getString(R.string.url));
    }

    private void setWeather(String text, String temp, String pres, String hum){
        binding.setHum(hum);
        binding.setPres(pres);
        binding.setTemp(temp);
        binding.setText(text);
    }

    @Override
    public void onResponse(String response) {

        //Gson gson = new Gson();
        //Objeto a json
        //String json =  gson.toJson(obj);

        //json a Objeto
        //Planeta p = gson.fromJson("...", Planeta.class);

        //json a Lista de Objetos
        //Type type = new TypeToken< List<Planeta> >(){}.getType();
        //List<Planeta> data = gson.fromJson("...", type);

        try {

            JSONObject obj =  new JSONObject(response);
            JSONObject query =  obj.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel =  results.getJSONObject("channel");
            JSONObject atmosphere = channel.getJSONObject("atmosphere");

            String hum = atmosphere.getString("humidity");
            String pres = atmosphere.getString("pressure");

            JSONObject item =  channel.getJSONObject("item");
            JSONObject condition =  item.getJSONObject("condition");

            String text = condition.getString("text");
            String temp =  condition.getString("temp");
            setWeather(text,temp,pres,hum);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
