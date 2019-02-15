package mobile.nhatcuong.animal;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlayMusicService extends Service {
    MediaPlayer mp_object;
    int music;
    public PlayMusicService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        music = (int) intent.getExtras().get("music");
        mp_object =  MediaPlayer.create(getApplicationContext(), music);
        mp_object.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mp_object.release();
    }
}
