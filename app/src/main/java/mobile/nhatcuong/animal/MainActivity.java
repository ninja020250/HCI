package mobile.nhatcuong.animal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity {
    private static final int RQ_CODE = 111;
    private ImageView backbutton;
    TextView actionbar_title;
    private ArrayList<Animal> animals = null;
    private GifImageView flyingBird;
    float birdX;
    float birdY;
    Intent music =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mapping();
        playBackgroundMusic();
        eventMapping();
        getData();
        setAnimation();
        playBackgroundMusic();
    }
    private void setAnimation(){
        TranslateAnimation translateAnimationX =  new TranslateAnimation(0f, -2000f,0f,0f);
        translateAnimationX.setDuration(5000);
        translateAnimationX.setRepeatCount(Animation.INFINITE);
        flyingBird.setAnimation(translateAnimationX) ;
    }
    private void mapping() {

        backbutton = findViewById(R.id.actionbar_back);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText("BÉ HỌC CON VẬT");
        flyingBird =  findViewById(R.id.flying_bird);

        //        Uri uri= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.hand_click);
////        Glide.with(getApplicationContext()).load(uri).into(wv);



    }

    private void playBackgroundMusic(){
         music = new Intent(this, PlayMusicService.class);
        music.putExtra("music", R.raw.undersea);
            startService(music);
    }

    private void eventMapping() {
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void saveDataToPreference(ArrayList<Animal> animals) {
        // SharedPreferences sharedPreferences = getSharedPreferences("mobile.nhatcuong.database_preference", MODE_PRIVATE);
        try {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mobile.nhatcuong.database_preferences", 0);
            SharedPreferences.Editor edt = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(animals);
            edt.putString("animals", json);
            edt.apply();
            edt.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Animal> fakeData() {
        ArrayList a = new ArrayList<Animal>();
        a.add(new Animal("Con Cua", R.drawable.crab));
        a.add(new Animal("Voi Xanh", R.drawable.blue_whale));
        a.add(new Animal("Con Sò", R.drawable.clam));
        a.add(new Animal("Cá Đuối", R.drawable.gray_fish));
        a.add(new Animal("Con Sứa", R.drawable.jellyfish));
        a.add(new Animal("Con Bạch Tuộc", R.drawable.octopus));
        a.add(new Animal("Cá Ngựa", R.drawable.seahorse));
        a.add(new Animal("Cá Heo", R.drawable.dolphin));
        a.add(new Animal("Cá Mập", R.drawable.shark));
        a.add(new Animal("Con rùa", R.drawable.turtle));
        return a;

    }

    private void getData() {
        String url = "https://virtserver.swaggerhub.com/ninja020250/Apartment/2/user";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        animals = fakeData();
                        saveDataToPreference(animals);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "calling api fail", Toast.LENGTH_SHORT).show();
                    }
                }

        );
        requestQueue.add(objectRequest);
    }

    private void goToAnimalsActivity(String title, int background, int color, String type) {
        Intent intent = new Intent(this, AnimalsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("background", background);
        bundle.putInt("actionbarColor", color);
        bundle.putString("type", type );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ClickToPlaySkyAnimal(View view) {
        int colorGreen = R.color.colorGreen;
        goToAnimalsActivity("Động vật biết bay", R.drawable.forest_background, colorGreen, "sky");
    }

    public void ClickToPlayEarhAnimal(View view) {
        int colorGreen = R.color.colorGreen;
        goToAnimalsActivity("Động vật trên cạn", R.drawable.forest_background, colorGreen, "earth");
    }

    public void ClickToPlaySeaAnimal(View view) {
        int colorBlue = R.color.colorBlue;
        goToAnimalsActivity("Động vật dưới nước", R.drawable.sea_background, colorBlue, "sea");
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(music);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(music);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(music);
    }
}
