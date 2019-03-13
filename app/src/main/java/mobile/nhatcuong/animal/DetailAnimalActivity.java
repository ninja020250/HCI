package mobile.nhatcuong.animal;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class DetailAnimalActivity extends AppCompatActivity {

    //    =====================================activity element =====================================
    private TextView txtAnimalName;
    private GifImageView imgAnimalDetail;
    private boolean isMusicPlaying;
    private Animal currentAnimal;
    private int position = 0;
    ArrayList<Animal> animals = null;
    MediaPlayer mediaPlayer = null;
    //actionbar element
    TextView actionbar_title;
    LinearLayout actionbar;
    private int actionbarColor;
    ImageView backbutton;
    ImageView homeButton;
    private MediaPlayer mediaBackground;
    private MediaPlayer question_media;
    private Handler h;
    private Runnable stopPlaybackRun;
    //group 4button
    ImageView btn1;
    ImageView btn2;
    ImageView btn3;
    ImageView btn4;

    // ===============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_animal);
        // ======================================
        mapping();
        setAnimation();
//        playBackgroundMusic();
//        mediaBackground.stop();
        mediaBackground = MediaPlayer.create(DetailAnimalActivity.this, R.raw.henes_bgmusic);
        mediaBackground.start();
        mediaBackground.setVolume(0.0f, 0.0f);
        h = new Handler();
        stopPlaybackRun = new Runnable() {
            public void run() {
                mediaBackground.setVolume(1.0f, 1.0f);
            }
        };
        h.postDelayed(stopPlaybackRun, 3000);
        setupCurrentAnimal();
        setupActionbar();
        eventMapping();

    }


    private void setupCurrentAnimal() {
        WindowManager wm = getWindowManager();
        Display dp = wm.getDefaultDisplay();
        Point size = new Point();
        dp.getSize(size);
        int screenWidth = size.x;
        // setup current animal
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        animals = (ArrayList<Animal>) bundle.getSerializable("ANIMALS");
        position = bundle.getInt("POSITION");
        currentAnimal = animals.get(position);
        question_media = MediaPlayer.create(DetailAnimalActivity.this, currentAnimal.getHumanVoice());
        question_media.start();
        if (currentAnimal.getAnimalVoice() == 0) {
            btn2.setImageResource(R.drawable.voice_disable);
        } else {
            btn2.setImageResource(R.drawable.animal_speaker);
        }
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
        imgAnimalDetail.getLayoutParams().width = screenWidth;
        imgAnimalDetail.getLayoutParams().height = screenWidth;
    }

    private void setupActionbar() {
        //set up actionbar
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText("BÉ HỌC CON VẬT");
        actionbarColor = R.color.colorBlue;
        actionbar = findViewById(R.id.actionbar_body);
        actionbar.setBackgroundResource(actionbarColor);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void setAnimation() {
        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        btn1 = findViewById(R.id.btnDetail1);
        btn2 = findViewById(R.id.btnDetail2);
        btn3 = findViewById(R.id.btnDetail3);
        btn4 = findViewById(R.id.btnDetail4);
        btn1.setAnimation(zoom);
        btn2.setAnimation(zoom);
        btn3.setAnimation(zoom);
        btn4.setAnimation(zoom);
    }

    private void mapping() {
//        actionbar mapping
        homeButton = findViewById(R.id.actionbar_home);
        backbutton = findViewById(R.id.actionbar_back);
//        ==============================
        txtAnimalName = findViewById(R.id.txtAnimalName);
        imgAnimalDetail = findViewById(R.id.imgAnimalDetail);
    }

    private void playBackgroundMusic() {
//        music = new Intent(this, PlayMusicService.class);
//        music.putExtra("music", R.raw.undersea);
//        mediaBackground = MediaPlayer.create(DetailAnimalActivity.this, R.raw.henes_bgmusic);
//       mediaBackground.start();

    }

    public void playAnimalSound(View view) {
        if (currentAnimal.getAnimalVoice() != 0) {

           mediaPlayer = MediaPlayer.create(DetailAnimalActivity.this, currentAnimal.getAnimalVoice());
           mediaPlayer.start();
        }


    }

    private void nextAnimal() {
        position += 1;
        if (position >= animals.size()) {
            position = 0;
        }

        currentAnimal = animals.get(position);
        if (currentAnimal.getAnimalVoice() == 0) {
            btn2.setImageResource(R.drawable.voice_disable);
        } else {
            btn2.setImageResource(R.drawable.animal_speaker);
        }
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
        if (currentAnimal.getHumanVoice() != 0) {
            mediaBackground.setVolume(0.3f, 0.3f);
            h.postDelayed(stopPlaybackRun, 3000);
            mediaPlayer = MediaPlayer.create(DetailAnimalActivity.this, currentAnimal.getHumanVoice());
            mediaPlayer.start();
        }
    }

    public void clickToNextAnimal(View view) {
        nextAnimal();
    }

    private void backAnimal() {
        position -= 1;
        if (position <= 0) {
            position = animals.size() - 1;
        }
        currentAnimal = animals.get(position);
        if (currentAnimal.getAnimalVoice() == 0) {
            btn2.setImageResource(R.drawable.voice_disable);
        } else {
            btn2.setImageResource(R.drawable.animal_speaker);
        }
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
        if (currentAnimal.getHumanVoice() != 0) {
            mediaBackground.setVolume(0.3f, 0.3f);
            h.postDelayed(stopPlaybackRun, 3000);
            mediaPlayer = MediaPlayer.create(DetailAnimalActivity.this, currentAnimal.getHumanVoice());
            mediaPlayer.start();
        }
    }

    public void clickToBackAnimal(View view) {
        backAnimal();
    }

    private void eventMapping() {
        //        event swipping
        new SwipeDetector(imgAnimalDetail).setOnSwipeListener(new SwipeDetector.onSwipeEvent() {
            @Override
            public void SwipeEventDetected(View v, SwipeDetector.SwipeTypeEnum SwipeType) {
                if (SwipeType == SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT) {
                    backAnimal();
                }
                if (SwipeType == SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT) {
                    nextAnimal();
                }
            }
        });
        // event click to go home
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAnimalActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //        event back to pre activity
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //    @Override
//    protected void onDestroy() {
//        if(mediaPlayer != null){
//            mediaPlayer.stop();
//        }
//        super.onDestroy();
//    }
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

    public void clickToHearAnimalName(View view) {
        if (currentAnimal.getHumanVoice() != 0) {
            mediaBackground.setVolume(0.3f, 0.3f);
            h.postDelayed(stopPlaybackRun, 3000);
            String url = "https://sv4.onlinevideoconverter.com/download?file=f5e4d3f5c2h7g6b1";
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
          try {
              mediaPlayer.setDataSource(url);
              mediaPlayer.prepare(); // might take long! (for buffering, etc)
          }catch (Exception e){

          }
            mediaPlayer.start();
// Trigger an async preparation which will file listener when completed


        }
    }
}
