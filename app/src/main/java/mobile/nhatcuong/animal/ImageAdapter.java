package mobile.nhatcuong.animal;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Animal> animals ;
    private int intScreenWidth ;
    private boolean isOnline;
    public ImageAdapter(Context context, int layout, List<Animal> animals, int intScreenWidth, boolean isOnline) {
        this.context = context;
        this.layout = layout;
        if(animals == null){
            animals=  new ArrayList<>();
        }else{
            this.animals = animals;
        }
        this.isOnline = isOnline;
        this.intScreenWidth =  intScreenWidth;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        GifImageView image;
    }

    public int getIntScreenWidth() {
        return intScreenWidth;
    }

    public void setIntScreenWidth(int intScreenWidth) {
        this.intScreenWidth = intScreenWidth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
                holder.image = convertView.findViewById(R.id.imgViewAnimal);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Animal image =  animals.get(position);
        if (isOnline){
            Glide.with(context).load(image.getImageURL()).into( holder.image);
        }else{
                    holder.image.setImageResource(image.getImage());
        }
        holder.image.setBackgroundResource(R.drawable.bubble_background);
        holder.image.getLayoutParams().width = 100;
        holder.image.getLayoutParams().height = (intScreenWidth - 180)/2;
        return convertView;
    }
}
