package mobile.nhatcuong.animal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailAnimalActivity extends AppCompatActivity {
    private TextView txtAnimalName;
    private ImageView imgAnimalDetail;
    private boolean isMusicPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_animal);
        mapping();
        Intent intent = getIntent();
        Image animal = (Image) intent.getSerializableExtra("ANIMAL");
        txtAnimalName.setText(animal.getName());
        imgAnimalDetail.setImageResource(animal.getImage());
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
}
