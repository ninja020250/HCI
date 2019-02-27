package mobile.nhatcuong.animal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeGameActivity extends AppCompatActivity {
    private int actionbarColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_game);

        //
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
                Intent intent = new Intent(HomeGameActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        Bundle bundle = getIntent().getExtras();
//        String title =  bundle.getString("title");
//        TextView txtTitle =  findViewById(R.id.txtTitle);
//        txtTitle.setText(title);
        ImageView bg = findViewById(R.id.animalsbg);
        bg.setImageResource((Integer) bundle.get("background"));
        actionbarColor = bundle.getInt("actionbarColor");
        TextView actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText((String) bundle.get("title"));
        LinearLayout actionbar = findViewById(R.id.actionbar_body);
        actionbar.setBackgroundResource(actionbarColor);
    }

    public void clicktoStart(View view) {
        Intent intent  = new Intent(this, QuizActivity.class);


    }
}
