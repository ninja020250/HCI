package mobile.nhatcuong.animal;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailAnimalActivity extends AppCompatActivity {
    private TextView txtAnimalName;
    private ImageView imgAnimalDetail;
    private boolean isMusicPlaying;
    private Animal currentAnimal;
    private int position = 0;
    ArrayList<Animal> animals = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_animal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapping();
        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
         animals = (ArrayList<Animal>) bundle.getSerializable("ANIMALS");
         position =  bundle.getInt("POSITION");
        currentAnimal = animals.get(position);
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
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
        if(isMusicPlaying){
            isMusicPlaying = false;
            stopService(intent);
        }else{
            isMusicPlaying = true;
            startService(intent);

        }



    }

    public void clickToNextAnimal(View view) {
        position +=1;
        if(position >= animals.size()){
            position = 0;
        }
        currentAnimal = animals.get(position);
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
    }

    public void clickToBackAnimal(View view) {
        position -=1;
        if(position <= 0){
            position = animals.size()  -1 ;
        }
        currentAnimal = animals.get(position);
        txtAnimalName.setText(currentAnimal.getName());
        imgAnimalDetail.setImageResource(currentAnimal.getImage());
    }
}
