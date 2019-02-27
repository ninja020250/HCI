package mobile.nhatcuong.animal;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import pl.droidsonroids.gif.GifImageView;

public class QuizActivity extends AppCompatActivity {
    private String type;
    private ArrayList<Animal> animals;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private Button btnAnswer4;
    private ArrayList<Button> buttons;
    Quiz quiz;
    private GifImageView imgCurrentAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mapping();
        getDataFromDatabase();
        setupGame();
        HandleClickAnswer();
        reSizeImg();
//        imgCurrentAnimal.set
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
    }

    private void setupGame() {

        ArrayList<Animal> list = getRandomAnimal();
        quiz = new Quiz(list);
        imgCurrentAnimal.setImageResource(list.get(0).getImage());
        btnAnswer1.setText(quiz.getAnwsers().get(0).getName());
        btnAnswer2.setText(quiz.getAnwsers().get(1).getName());
        btnAnswer3.setText(quiz.getAnwsers().get(2).getName());
        btnAnswer4.setText(quiz.getAnwsers().get(3).getName());
        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnAnswer4.setEnabled(true);
    }

    private void handleSuccess() {
        setupGame();
//        Toast.makeText(this, "Đúng", Toast.LENGTH_SHORT).show();
    }

    private void handleFail() {
        Toast.makeText(this, "Sai", Toast.LENGTH_SHORT).show();
    }

    private void HandleClickAnswer() {
        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (quiz.hanldeClick(button.getText().toString())) {
                    handleSuccess();
                } else {
                    button.setEnabled(false);
                    handleFail();
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
                    button.setEnabled(false);
                    handleFail();
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
                    button.setEnabled(false);
                    handleFail();
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
                    button.setEnabled(false);
                    handleFail();
                }
            }
        });
    }

    private ArrayList<Animal> getRandomAnimal() {
        ArrayList<Animal> list = new ArrayList<>();
        Random rand = new Random();
        int trueIndex = rand.nextInt(((animals.size() - 1) - 0) + 1) + 0;
        list.add(animals.get(trueIndex));

        //Toast.makeText(this, trueIndex, Toast.LENGTH_SHORT).show();
        while (list.size() < 4) {
            int falseIndex = rand.nextInt(((animals.size() - 1) - 0) + 1) + 0;
            if (falseIndex != trueIndex) {
                list.add(animals.get(falseIndex));
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
}
