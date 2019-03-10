package mobile.nhatcuong.animal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class QuizActivity extends AppCompatActivity {
    private ImageView bg;
    private int actionbarColor;
    private TextView actionbar_title;
    private ImageView backbutton;
    private ImageView homeButton;
    private LinearLayout actionbar;
    private ArrayList<Animal> animals;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private Button btnAnswer4;
    private GifImageView gifSuccess;
    private ArrayList<Button> buttons;
    private MediaPlayer mediaPlayer;
    Quiz quiz;
    private GifImageView imgCurrentAnimal;
    private MediaPlayer mediaBackground;
    private MediaPlayer question_media;
    private Handler h;
    private Runnable stopPlaybackRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz);
        mapping();
        //setup action bar custom
        Bundle bundle = getIntent().getExtras();
        bg.setImageResource(R.drawable.sea_background);
        actionbarColor = R.color.colorBlue;
        actionbar_title.setText("Đố vui");
        actionbar.setBackgroundResource(actionbarColor);
        //done setup
        mappingEvent();
        setAnimated();
        playBackgroundMusic();
        mediaBackground.setVolume(0.0f, 0.0f);
        h = new Handler();
        stopPlaybackRun = new Runnable() {
            public void run() {
                mediaBackground.setVolume(1.0f, 1.0f);
            }
        };
        h.postDelayed(stopPlaybackRun, 2500);
        getDataFromDatabase();
        setupGame();
        HandleClickAnswer();
        reSizeImg();
        question_media = MediaPlayer.create(QuizActivity.this, R.raw.question);
        question_media.start();
    }
    private void playBackgroundMusic() {
//        music = new Intent(this, PlayMusicService.class);
//        music.putExtra("music", R.raw.undersea);
        mediaBackground =  MediaPlayer.create(QuizActivity.this,R.raw.henes_bgmusic);
        mediaBackground.start();

    }
    private void mappingEvent(){
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
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
    }
    private void reSizeImg() {
        WindowManager wm = getWindowManager();
        Display dp = wm.getDefaultDisplay();
        Point size = new Point();
        dp.getSize(size);
        int screenWidth = size.x;
        imgCurrentAnimal.getLayoutParams().width = screenWidth;
        imgCurrentAnimal.getLayoutParams().height = screenWidth;
    }

    private void mapping() {
        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer3 = findViewById(R.id.btnAnswer3);
        btnAnswer4 = findViewById(R.id.btnAnswer4);
        imgCurrentAnimal = findViewById(R.id.imgCurrentAnimal);
        gifSuccess= findViewById(R.id.gifSuccess);
        bg = findViewById(R.id.animalsbg);
        homeButton = findViewById(R.id.actionbar_home);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar = findViewById(R.id.actionbar_body);
        backbutton =  findViewById(R.id.actionbar_back);
    }

    private void setupGame() {

        ArrayList<Animal> list = getRandomAnimal();
        quiz = new Quiz(list);
        imgCurrentAnimal.setImageResource(list.get(0).getImage());


        ArrayList<Animal> a = quiz.getAnwsers();
        btnAnswer1.setText(a.get(0).getName());
        btnAnswer2.setText(a.get(1).getName());
        btnAnswer3.setText(a.get(2).getName());
        btnAnswer4.setText(a.get(3).getName());

        btnAnswer1.setBackgroundResource(R.drawable.blue_box);
        btnAnswer2.setBackgroundResource(R.drawable.blue_box);
        btnAnswer3.setBackgroundResource(R.drawable.blue_box);
        btnAnswer4.setBackgroundResource(R.drawable.blue_box);

        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnAnswer4.setEnabled(true);
    }

    private void handleSuccess() {
        gifSuccess.setVisibility(View.VISIBLE);
        mediaPlayer = MediaPlayer.create(QuizActivity.this, R.raw.yay);
        mediaPlayer.start();
        Timer t = new Timer(false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        gifSuccess.setVisibility(View.INVISIBLE);
                        mediaPlayer.stop();
                        mediaBackground.setVolume(0.0f, 0.0f);
                        h.postDelayed(stopPlaybackRun, 2500);
                        question_media = MediaPlayer.create(QuizActivity.this, R.raw.question);
                        question_media.start();
                    }
                });
            }
        }, 2000);

        setupGame();
    }

    private void handleFail(Button button) {
        mediaPlayer = MediaPlayer.create(QuizActivity.this, R.raw.splat);
        mediaPlayer.start();
        button.setBackgroundResource(R.drawable.wrong_box);
        button.setEnabled(false);

    }

    private void HandleClickAnswer() {
        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (quiz.hanldeClick(button.getText().toString())) {
                    handleSuccess();
                } else {
//                    button.setEnabled(false);
                    handleFail(button);
                }
            }
        });
        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (quiz.hanldeClick(button.getText().toString())) {
                    handleSuccess();
                } else {
//                    button.setEnabled(false);
                    handleFail(button);
                }
            }
        });
        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (quiz.hanldeClick(button.getText().toString())) {
                    handleSuccess();
                } else {
//                    button.setEnabled(false);
                    handleFail(button);
                }
            }
        });
        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (quiz.hanldeClick(button.getText().toString())) {

                    handleSuccess();
                } else {
//                    button.setEnabled(false);
                    handleFail(button);
                }
            }
        });
    }
    private void setAnimated(){
        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        btnAnswer1.setAnimation(zoom);
        btnAnswer2.setAnimation(zoom);
        btnAnswer3.setAnimation(zoom);
        btnAnswer4.setAnimation(zoom);
    }
    private ArrayList<Animal> getRandomAnimal() {
        ArrayList<Animal> list = new ArrayList<>();
        Random rand = new Random();
        int trueIndex = rand.nextInt(((animals.size() - 1) - 0) + 1) + 0;
        list.add(animals.get(trueIndex));
        int index2 = -1;
        int index3 = -1;
        int index4 = -1;
        int count =  1;
        while (list.size() < 4) {
            int falseIndex = rand.nextInt(((animals.size() - 1) - 0) + 1) + 0;
            if (falseIndex != trueIndex && falseIndex != index2 && falseIndex != index3 &&falseIndex != index4 ) {
                if(count == 1){
                    count ++;
                    index2 = falseIndex;
                    list.add(animals.get(falseIndex));
                }else if(count == 2){
                    count ++;
                    index3 = falseIndex;
                    list.add(animals.get(falseIndex));
                }else if(count == 3){
                    count ++;
                    index4 = falseIndex;
                    list.add(animals.get(falseIndex));
                }else{

                }

            }
        }
        return list;
    }

    private void getDataFromDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("mobile.nhatcuong.database_preferences", MODE_PRIVATE);
        String data = sharedPreferences.getString("animals", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Animal>>() {
        }.getType();
        animals = gson.fromJson(data, type);
    }


    private int randomInRange(int max, int min) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private int getRandomFromList() {
        return randomInRange(animals.size(), 0);

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
