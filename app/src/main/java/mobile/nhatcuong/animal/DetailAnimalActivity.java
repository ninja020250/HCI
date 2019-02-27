package mobile.nhatcuong.animal;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class DetailAnimalActivity extends AppCompatActivity {
    private TextView txtAnimalName;
    private GifImageView imgAnimalDetail;
    private boolean isMusicPlaying;
    private Animal currentAnimal;
    private int position = 0;
    private int actionbarColor;
    private String animalType;
    ArrayList<Animal> animals = null;
    private float x1;
    private float x2;
    final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_animal);
        ImageView backbutton = findViewById(R.id.actionbar_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView homeButton = findViewById(R.id.actionbar_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAnimalActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        mapping();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        actionbarColor = bundle.getInt("actionbarColor");
        animals = (ArrayList<Animal>) bundle.getSerializable("ANIMALS");
        position = bundle.getInt("POSITION");
        currentAnimal = animals.get(position);
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
        reSize();
        TextView actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText("BÉ HỌC CON VẬT");
        LinearLayout actionbar = findViewById(R.id.actionbar_body);
        actionbar.setBackgroundResource(actionbarColor);
        new SwipeDetector(imgAnimalDetail).setOnSwipeListener(new SwipeDetector.onSwipeEvent() {
            @Override
            public void SwipeEventDetected(View v, SwipeDetector.SwipeTypeEnum SwipeType) {
                if(SwipeType ==SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT){
                    backAnimal();
                }
                if(SwipeType ==SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT){

                    nextAnimal();
                }

            }
        });
    }


    private void reSize(){
        WindowManager wm = getWindowManager();
        Display dp = wm.getDefaultDisplay();
        Point size = new Point();
        dp.getSize(size);
        int screenWidth = size.x;
        imgAnimalDetail.getLayoutParams().width = screenWidth;
        imgAnimalDetail.getLayoutParams().height = screenWidth;
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

    private void mapping() {
        txtAnimalName = findViewById(R.id.txtAnimalName);
        imgAnimalDetail = findViewById(R.id.imgAnimalDetail);
    }

    public void playAnimalSound(View view) {
        Intent intent = new Intent(this, PlayMusicService.class);
        intent.putExtra("music", R.raw.animal_sounds_17_dolphin);
        if (isMusicPlaying) {
            isMusicPlaying = false;
            stopService(intent);
        } else {
            isMusicPlaying = true;
            startService(intent);

        }


    }

    private void nextAnimal() {
        position += 1;
        if (position >= animals.size()) {
            position = 0;
        }
        currentAnimal = animals.get(position);
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
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
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
    }

    public void clickToBackAnimal(View view) {
        backAnimal();
    }
}
