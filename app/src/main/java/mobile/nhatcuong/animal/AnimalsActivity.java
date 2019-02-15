package mobile.nhatcuong.animal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AnimalsActivity extends AppCompatActivity {
    GridView gvAnimals;
    ArrayList<Image> animals;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        Bundle bundle = getIntent().getExtras();
//        String title =  bundle.getString("title");
//        TextView txtTitle =  findViewById(R.id.txtTitle);
//        txtTitle.setText(title);
        ImageView bg = findViewById(R.id.animalsbg);
        bg.setImageResource((Integer) bundle.get("background"));
        mapping();
        adapter = new ImageAdapter(this, R.layout.row_animal, animals);
        gvAnimals.setAdapter(adapter);
        gvAnimals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AnimalsActivity.this, DetailAnimalActivity.class);
                intent.putExtra("ANIMAL", animals.get(position));
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        gvAnimals = findViewById(R.id.gvAnimals);
        animals = new ArrayList<>();
        animals.add(new Image("hinh 1", R.drawable.sea1));
        animals.add(new Image("hinh 2", R.drawable.sea2));
        animals.add(new Image("hinh 3", R.drawable.sea3));
        animals.add(new Image("hinh 4", R.drawable.sea4));
        animals.add(new Image("hinh 5", R.drawable.sea5));
        animals.add(new Image("hinh 6", R.drawable.sea6));
        animals.add(new Image("hinh 7", R.drawable.sea7));
        animals.add(new Image("hinh 8", R.drawable.sea8));
        animals.add(new Image("hinh 9", R.drawable.sea9));
        animals.add(new Image("hinh 10", R.drawable.sea10));
        animals.add(new Image("hinh 11", R.drawable.sea6));
        animals.add(new Image("hinh 12", R.drawable.sea7));
        animals.add(new Image("hinh 13", R.drawable.sea8));
        animals.add(new Image("hinh 14", R.drawable.sea9));
        animals.add(new Image("hinh 15", R.drawable.sea10));

    }

}
