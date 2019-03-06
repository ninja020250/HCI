package mobile.nhatcuong.animal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class AnimalsActivity extends AppCompatActivity {
    // =============activity element======================

    private GridView gvAnimals;
    private ImageView bg;
//    Intent music = null;
    private ImageView btnGoGame;
    private Handler handler = new Handler();
    private ArrayList<Animal> animals;
    private ImageAdapter adapter;

    //==================================================================
    // ======================actionbar element======================

    private LinearLayout actionbar;
    private TextView actionbar_title;
    private int actionbarColor;
    private ImageView backbutton;
    private ImageView homeButton;

    // ==================================================================
    // ======================animation elements======================
    private GifImageView bubble1;
    private GifImageView bubble2;
    private GifImageView bubble3;
    private Timer timer = new Timer();

    private float bubble1_X;
    private float bubble1_Y;
    private float bubble2_X;
    private float bubble2_Y;
    private float bubble3_X;
    private float bubble3_Y;
    private int screenWidth;
    private int screenHeight;
    private GifImageView ray;
    private GifImageView whale;

    private float ray_x;
    private float ray_y;
    private float whale_x;
    private float whale_y;
    private  MediaPlayer mediaBackground;
    // ==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_animals);
        mapping();
        getDataFromDatabase();

        //get screen width
        WindowManager wm = getWindowManager();
        Display dp = wm.getDefaultDisplay();
        Point size = new Point();
        dp.getSize(size);
        int screenWidth = size.x;
        bg.setImageResource(R.drawable.sea_background);
        adapter = new ImageAdapter(this, R.layout.row_animal, animals, screenWidth);
        gvAnimals.setAdapter(adapter);
        mappingEvent();
        setupActionBar();
        setSeaAnimation();
        playBackgroundMusic();
//        playBackgroundMusic();
//        startService(music);
    }


    private void setupActionBar() {
        actionbar_title.setText("Động Vật Dưới Nước");
        actionbarColor = R.color.colorBlue;
        actionbar.setBackgroundResource(actionbarColor);

    }

    private void mapping() {
        bubble1 = findViewById(R.id.bubble);
        bubble2 = findViewById(R.id.bubble2);
        bubble3 = findViewById(R.id.bubble3);
        gvAnimals = findViewById(R.id.gvAnimals);
        bg = findViewById(R.id.animalsbg);
        backbutton = findViewById(R.id.actionbar_back);
        homeButton = findViewById(R.id.actionbar_home);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar = findViewById(R.id.actionbar_body);
        ray = findViewById(R.id.ray);
        whale = findViewById(R.id.whale);
        btnGoGame = findViewById(R.id.btnGotoGame);
    }

    private void setSeaAnimation() {
        // zoom animation
        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        bubble1.setAnimation(zoom);
        bubble2.setAnimation(zoom);
        bubble3.setAnimation(zoom);
        /////////////////////////////////////
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

        whale.setX(-300f);
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



    private void getDataFromDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("mobile.nhatcuong.database_preferences", MODE_PRIVATE);
        String data = sharedPreferences.getString("animals", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Animal>>() {
        }.getType();
        animals = gson.fromJson(data, type);
    }

    private void playBackgroundMusic() {
//        music = new Intent(this, PlayMusicService.class);
//        music.putExtra("music", R.raw.undersea);
        mediaBackground =  MediaPlayer.create(AnimalsActivity.this,R.raw.henes_bgmusic);
        mediaBackground.start();

    }


    private void mappingEvent() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gvAnimals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AnimalsActivity.this, DetailAnimalActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", position);
                bundle.putSerializable("ANIMALS", animals);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void clickToPlayGame(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivityForResult(intent, 00);
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

    @Override
    protected void onPause() {
        mediaBackground.pause();
        super.onPause();

    }
    //
    @Override
    protected void onResume() {
//        mediaBackground =  MediaPlayer.create(QuizActivity.this,R.raw.henes_bgmusic);
        mediaBackground.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mediaBackground.release();
        super.onDestroy();
    }
}
