package mobile.nhatcuong.animal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    private static final int RQ_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView wv = findViewById(R.id.wv);
//        Uri uri= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.hand_click);
//        Glide.with(getApplicationContext()).load(uri).into(wv);
    }

    private void goToAnimalsActivity(String title, int background){
        Intent intent = new Intent(this, AnimalsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("background", background);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void ClickToPlaySkyAnimal(View view) {

        goToAnimalsActivity("Animal on the Sky",  R.drawable.forest_background);
    }

    public void ClickToPlayEarhAnimal(View view) {
        goToAnimalsActivity("Animal on the Earth", R.drawable.forest_background);
    }

    public void ClickToPlaySeaAnimal(View view) {
        goToAnimalsActivity("Animal under The Sea", R.drawable.sea_background);
    }
}
