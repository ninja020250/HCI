package mobile.nhatcuong.animal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private ArrayList<Animal> animals = null;
    List<DownloadFileAsync> asyncTasks = new ArrayList<DownloadFileAsync>();
    int finishTask = 0;
    private ProgressBar progressBar;
    private ArrayList<TaskDownload> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        getDataFromDatabase();
        setupDownload(animals);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(animals.size());
        DownloadFileAsync download = new DownloadFileAsync(tasks);

        download.execute();

        progressBar.setProgress(0);


    }

    private void setupDownload(ArrayList<Animal> animals) {
        for (Animal animal : animals) {
            if (animal.getAnimalVoiceURL() != "") {
                tasks.add(new TaskDownload(animal.getAnimalVoiceURL(), animal.getNameAnimalAudio()));
            }
            if (animal.getHumanVoiceURL() != "") {
                tasks.add(new TaskDownload(animal.getHumanVoiceURL(), animal.getNameHumanAudio()));
            }
        }
    }

    private void getDataFromDatabase() {

        SharedPreferences sharedPreferences = getSharedPreferences("mobile.nhatcuong.database_preferences", MODE_PRIVATE);
        String data = sharedPreferences.getString("animals", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Animal>>() {
        }.getType();
        animals = gson.fromJson(data, type);
    }


    private void saveDataToPreference(ArrayList<Animal> animals) {
//        set new url internal
        for (int i = 0; i < animals.size(); i++) {

            if (!animals.get(i).getAnimalVoiceURL().equals("")) {
                animals.get(i).setAnimalVoiceURL(getApplicationContext().getFilesDir().getPath()+"/mp3/"+ animals.get(i).getNameAnimalAudio());
            }
            if (!animals.get(i).getHumanVoiceURL().equals("")) {
                animals.get(i).setHumanVoiceURL(getApplicationContext().getFilesDir().getPath()+"/mp3/"+animals.get(i).getNameHumanAudio());

            }
        }
        //save data to database (I use preferences)
        try {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mobile.nhatcuong.database_preferences", 0);
            SharedPreferences.Editor edt = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(animals);
            edt.putString("animals", json);
            edt.apply();
            edt.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        private String url;
        private ArrayList<TaskDownload> tasks;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public DownloadFileAsync(ArrayList tasks) {
            super();
//            this.url = url;
            this.tasks = tasks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            int countTask = 0;
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    URL url = new URL(tasks.get(i).getUrl());
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                    File file = new File(getApplicationContext().getFilesDir().getPath(), "mp3");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(file + "/" + tasks.get(i).getName());
                    byte data[] = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
//                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {

                }
                countTask += 1;
                publishProgress(countTask + "");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            progressBar.setProgress(Integer.parseInt(progress[0]));
            Log.d("ANDRO_ASYNC", progress[0]);
            //    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            saveDataToPreference(animals);
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
//            Toast.makeText(DownloadActivity.this, "All downloaded", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
            //   dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }

    class TaskDownload {
        String url = "";
        String name = "";

        public TaskDownload(String url, String name) {
            this.url = url;
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


