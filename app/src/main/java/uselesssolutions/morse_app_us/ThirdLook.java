package uselesssolutions.morse_app_us;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdLook extends AppCompatActivity {
    // NIKLAVS ADDED THIS:

    Toolbar toolbar;
    String morseForMethod = "";
    private Button wordSpaceBtn;
    private Button letterSpaceBtn;
    private Button deleteBtn;
    private Button longBtn;
    private Button shortBtn;
    private Button convertBtn;
    private TextView morseText;
    private TextView letterText;
    private SoundPool shortPool;
    private SoundPool longPool;
    private boolean threadNeeded = true; // USED TO STOP THE THREAD AFTER BACK BUTTON IS PRESSED
    MainActivity ma = new MainActivity();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.audio:
                if (ma.getPlaybackNeeded() == true){
                    ma.setPlaybackNeeded(false);
                    Toast.makeText(this, "Audio playback off", Toast.LENGTH_SHORT).show();
                }else{
                    ma.setPlaybackNeeded(true);
                    Toast.makeText(this, "Audio playback on", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.dictionary:
                startActivity(new Intent(ThirdLook.this,Pop.class));

                break;
            default: System.out.println("ACTION NOT ASSIGNED");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_look);

        toolbar = findViewById(R.id.my_toolbar);
        // setSupportActionBar(toolbar);
        if (toolbar != null)
            toolbar.setTitle("Morse to Text");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // NIKLAVS ADDED THIS:
        shortPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int shortID = shortPool.load(this, R.raw.shortbeep, 1);

        longPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int longID = longPool.load(this, R.raw.longbeep, 1);

        morseText = findViewById(R.id.morseCode);
        letterText = findViewById(R.id.textView2);

        wordSpaceBtn = findViewById(R.id.wordSpaceBtn);
        wordSpaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (morseForMethod.length() > 0){
                    if (morseForMethod.charAt(morseForMethod.length() - 1) != '/') {
                        morseForMethod = morseForMethod + "/%/";
                    }else{
                        morseForMethod = morseForMethod + "%/";
                    }
                    morseText.setText(makeDisplayString(morseForMethod));
                }
            }
        });

        letterSpaceBtn = findViewById(R.id.letterSpaceBtn);
        letterSpaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (morseForMethod.length() > 0) {
                    morseForMethod = morseForMethod + "/";
                    morseText.setText(makeDisplayString(morseForMethod));
                }
            }
        });

        deleteBtn = findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morseForMethod = deleteForMethod(morseForMethod);
                morseText.setText(makeDisplayString(morseForMethod));
            }
        });
        deleteBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                morseText.setText("");
                letterText.setText("");
                morseForMethod = "";
                return false;
            }
        });

        longBtn = findViewById(R.id.longButton);
        longBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        threadNeeded = false;
                        longPool.play(longID, 2, 2, 0, 0, 0);
                    }
                }).start();
                morseForMethod = morseForMethod + "-";
                morseText.setText(makeDisplayString(morseForMethod));
            }
        });

        shortBtn = findViewById(R.id.shortButton);
        shortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        threadNeeded = false;
                        shortPool.play(shortID, 2, 2, 0, 0, 0);
                    }
                }).start();
                morseForMethod = morseForMethod + ".";
                morseText.setText(makeDisplayString(morseForMethod));
            }
        });

        convertBtn = findViewById(R.id.convertButton);
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadNeeded = true;
                MorseTextClass summon = new MorseTextClass();
                if(morseForMethod.length() > 0) {
                    if (morseForMethod.charAt(morseForMethod.length()-1) != '/'){
                        morseForMethod = morseForMethod + "/";
                    }
                    morseText.setText(makeDisplayString(morseForMethod));
                    letterText.setText(summon.morseToText(morseForMethod));
                }
                if (ma.getPlaybackNeeded() == true){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < morseForMethod.length(); i++){
                                if (morseForMethod.charAt(i) == '.' && threadNeeded){
                                    shortPool.play(shortID, 2, 2, 0, 0, 0);
                                    try{
                                        Thread.sleep(200);
                                    }catch (Exception e){}
                                }else if (morseForMethod.charAt(i) == '-' && threadNeeded){
                                    longPool.play(longID, 2, 2, 0, 0, 0);
                                    try{
                                        Thread.sleep(200);
                                    }catch (Exception e){}
                                }else if (morseForMethod.charAt(i) == '/' && threadNeeded){
                                    try{
                                        Thread.sleep(600);
                                    }catch (Exception e){}
                                }else if (morseForMethod.charAt(i) == '%' && threadNeeded){
                                    try{
                                        Thread.sleep(1000);
                                    }catch (Exception e){}
                                }else{
                                    System.out.println("no good");
                                }
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        threadNeeded = false;
    }

    public String deleteForMethod(String str){
        if (str != null && str.length() > 0){
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public String makeDisplayString(String str){
        String result = "";
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == '.'){
                result += " . ";
            }else if (str.charAt(i) == '-'){
                result += " _ ";
            }else if (str.charAt(i) == '/'){
                result += "   ";
            }else if (str.charAt(i) == '%'){
                result += "       ";
            }else{
                result += "?";
            }
        }
        return result;
    }

    public void backToMain(){
        threadNeeded = false;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
