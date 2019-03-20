package mobile.nhatcuong.animal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import mobile.nhatcuong.animal.utils.ConnectivityHelper;
import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity {
    private static final int RQ_CODE = 111; // request code
    private static final int RQ_DOWNLOAD = 001;
    AlertDialog.Builder builder1=null;
    // =====================================actionbar custome =====================================
    private ImageView backbutton;
    TextView actionbar_title;
    private Button btnSea;
    //    ==================================================================
    private ArrayList<Animal> animals = null;
    Intent music = null;
    // ===================================animated =================================
    private GifImageView bubble1;
    private GifImageView bubble2;
    private GifImageView bubble3;
    private int screenWidth;
    private int screenHeight;
    private Timer timer = new Timer();
    private float bubble1_X;
    private float bubble1_Y;
    private float bubble2_X;
    private float bubble2_Y;
    private float bubble3_X;
    private float bubble3_Y;
    private Handler handler = new Handler();
    private GifImageView ray;
    private GifImageView whale;
    private GifImageView turtle;
    private float ray_x;
    private float ray_y;
    private float whale_x;
    private float whale_y;
    private float turtle_x;
    private float turtle_y;
    //    ===================================================
    MediaPlayer mediaBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mapping();
        eventMapping();
        if (ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mobile.nhatcuong.database_preferences", 0);
            String downloaded = sharedPreferences.getString("downloaded", "");
            if (downloaded == ""
                    || downloaded == "false"
                    || downloaded == null
                    ) {
                builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Ứng dụng cần tải xuống 1 số con vật, bạn có đồng ý");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getDataOnline();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getDataOffline();
                                Toast.makeText(MainActivity.this, "ứng dụng sẽ sử dụng 1 số con vật có sẵn", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                builder1.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        getDataOffline();
//                        Toast.makeText(MainActivity.this, "ứng dụng sẽ sử dụng 1 số con vật có sẵn", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }


        } else {
            Toast.makeText(this, "không có mạng", Toast.LENGTH_SHORT).show();
        }
//        getData();
        playBackgroundMusic();
        setSeaAnimation();
    }

    private void mapping() {
        btnSea = findViewById(R.id.btnSea);
        bubble1 = findViewById(R.id.bubble);
        bubble2 = findViewById(R.id.bubble2);
        bubble3 = findViewById(R.id.bubble3);
        turtle = findViewById(R.id.animated_turtle);
        ray = findViewById(R.id.ray);
        whale = findViewById(R.id.whale);
        backbutton = findViewById(R.id.actionbar_back);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText("BÉ HỌC CON VẬT");
    }

    private void setSeaAnimation() {
        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        bubble1.setAnimation(zoom);
        bubble3.setAnimation(zoom);

        btnSea.setAnimation(zoom);
        WindowManager wm = getWindowManager();
        Display dp = wm.getDefaultDisplay();
        Point size = new Point();
        dp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
//        bubble1.setX(-80.0f);
        bubble1.setY(200f);
//        bubble2.setX(0f);
        bubble2.setY(-200.0f);
//        bubble3.setX(-80.0f);
        bubble3.setY(200f);

        //   whale.setX(-300f);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePostBubble();
                        changeFishPosition();
                    }
                });
            }
        }, 0, 20);

        // resizeButton();
    }

    private void changeFishPosition() {
        // change position of whale and ray.
        ray_x -= 10;
        if (ray.getX() + ray.getWidth() < 0) {
            ray_y = (float) Math.floor(Math.random() * (screenHeight - ray.getHeight()));
            ray_x = screenWidth + 100.0f;
        }
        ray.setX(ray_x);
        ray.setY(ray_y);
        whale_x += 10;
        if (whale.getX() > screenWidth) {
            whale_y = (float) Math.floor(Math.random() * (screenHeight - whale.getHeight()));
            whale_x = -500.0f;
        }
        whale.setX(whale_x);
        whale.setY(whale_y);
//        ============================================
        turtle_x -= 10;
        if (turtle.getX() + turtle.getWidth() < 0) {
            turtle_y = (float) Math.floor(Math.random() * (screenHeight - turtle.getHeight()));
            turtle_x = screenWidth + 100.0f;
        }
        turtle.setX(turtle_x);
        turtle.setY(turtle_y);
    }

    public void changePostBubble() {
        bubble1_Y -= 10;
        if (bubble1.getY() + bubble1.getHeight() < 0) {
            bubble1_X = (float) Math.floor(Math.random() * (screenWidth - bubble1.getWidth()));
            bubble1_Y = screenHeight + 200.0f;
        }
        bubble1.setX(bubble1_X);
        bubble1.setY(bubble1_Y);
        bubble2_Y -= 10;
        if (bubble2.getY() + bubble2.getHeight() < 0) {
            bubble2_X = (float) Math.floor(Math.random() * (screenWidth - bubble2.getWidth()));
            bubble2_Y = screenHeight + 0.0f;
        }
        bubble2.setX(bubble2_X);
        bubble2.setY(bubble2_Y);
        bubble3_Y -= 10;
        if (bubble3.getY() + bubble3.getHeight() < 0) {
            bubble3_X = (float) Math.floor(Math.random() * (screenWidth - bubble3.getWidth()));
            bubble3_Y = screenHeight + 200.0f;
        }
        bubble3.setX(bubble3_X);
        bubble3.setY(bubble3_Y);
    }


    private void playBackgroundMusic() {
//        music = new Intent(this, PlayMusicService.class);
//        music.putExtra("music", R.raw.henes_bgmusic);
        mediaBackground = MediaPlayer.create(MainActivity.this, R.raw.henes_bgmusic);
        mediaBackground.start();
//        startService(music);
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
        //save data to database (I use preferences)
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
        //  get sample data
        ArrayList a = new ArrayList<Animal>();
        a.add(new Animal("Con Cua", R.drawable.crab, 0, R.raw.crab_voice));
        a.add(new Animal("Cá Voi Xanh", R.drawable.blue_whale, 0, R.raw.whale_sound));
        a.add(new Animal("Ngọc Trai", R.drawable.clam, 0, R.raw.clam_voice));
        a.add(new Animal("Cá Đuối", R.drawable.gray_fish, 0, R.raw.stringray_sound));
        a.add(new Animal("Con Sứa", R.drawable.jellyfish, 0, R.raw.jellyfish_sound));
        a.add(new Animal("Bạch Tuộc", R.drawable.octopus, 0, R.raw.octopus_sound));
        a.add(new Animal("Cá Ngựa", R.drawable.seahorse, 0, R.raw.horse_fish_sound));
        a.add(new Animal("Cá Heo", R.drawable.dolphin, R.raw.dolphin, R.raw.dolphin_sound));
//        a.add(new Animal("Cá Mập", R.drawable.shark, 0, R.raw.shark_sound));
//        a.add(new Animal("Con Rùa", R.drawable.turtle, 0, R.raw.turtle_sound));
        return a;
    }

    private ArrayList<Animal> getDataFromResponse(JSONArray data) {
        ArrayList a = new ArrayList<Animal>();

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject jsonObject = data.getJSONObject(i);
                a.add(new Animal(jsonObject.getString("Id"), jsonObject.getString("Name"), jsonObject.getString("AniamlAudioUrl"), jsonObject.getString("HumanAudioUrl"), jsonObject.getString("ImageUrl")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return a;
    }

    private void getDataOffline() {
        animals = fakeData();
        saveDataToPreference(animals);
    }

    private void getDataOnline() {
        // get data from server
        btnSea.setEnabled(false);
        String url = "http://mobiledemo.azurewebsites.net/animal";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        animals = getDataFromResponse(response);
                        saveDataToPreference(animals);
                        Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                        btnSea.setEnabled(true);
                        startActivityForResult(intent, RQ_DOWNLOAD);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mobile.nhatcuong.database_preferences", 0);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getDataOffline();
                        Toast.makeText(MainActivity.this, "mạng lỗi hoặc quá yếu, ứng dụng sử dụng 1 số con vật có sẵn", Toast.LENGTH_SHORT).show();
                        btnSea.setEnabled(true);
                    }
                }

        );
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onPause() {
        mediaBackground.pause();
        super.onPause();

    }

    //
    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mobile.nhatcuong.database_preferences", 0);
        String downloaded = sharedPreferences.getString("downloaded", "");
        if (downloaded == ""
                || downloaded == "false"
                || downloaded == null
                ){
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

//        mediaBackground =  MediaPlayer.create(QuizActivity.this,R.raw.henes_bgmusic);
        mediaBackground.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mediaBackground.release();
        super.onDestroy();
    }

    public void clickToLearnAnimals(View view) {
        Intent intent = new Intent(MainActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RQ_DOWNLOAD) {
            if (resultCode == Activity.RESULT_OK) {

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mobile.nhatcuong.database_preferences", 0);
                SharedPreferences.Editor edt = sharedPreferences.edit();
                edt.putString("downloaded", "true");
                edt.apply();
                edt.commit();
                Toast.makeText(this, "Download success", Toast.LENGTH_SHORT).show();
            } else {
                getDataOffline();
                Toast.makeText(this, "Download failed, App will use available data", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
