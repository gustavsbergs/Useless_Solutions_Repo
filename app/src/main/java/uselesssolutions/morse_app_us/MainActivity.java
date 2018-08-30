package uselesssolutions.morse_app_us;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button textToMorse;
    private Button morseToText;
    private Button aboutMorse;

    // USED IN MORSE TO TEXT ACTIVITY TO TURN ON OR OFF SOUND PLAYBACK
    public boolean playbackNeeded = false;
    public void setPlaybackNeeded(boolean newValue){
        playbackNeeded = newValue;
    }
    public boolean getPlaybackNeeded(){
        boolean value = playbackNeeded;
        return value;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToMorse = (Button) findViewById(R.id.textToMorse);
        textToMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFlashLook();
            }
        });
        morseToText = (Button) findViewById(R.id.morseToText);
        morseToText.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                thirdLook();
            }
        });
        aboutMorse = (Button) findViewById(R.id.aboutMorse);
        aboutMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutMorseCode();
            }
        });
    }
    private void thirdLook(){
        Intent intent = new Intent(this, ThirdLook.class);
        startActivity(intent);

    }
    private void aboutMorseCode(){
        Intent intent = new Intent(this, AboutMorse.class);
        startActivity(intent);
    }

    private void toFlashLook(){
        Intent intent = new Intent(this, toMorseAllFunctions.class);
        startActivity(intent);
    }



}
