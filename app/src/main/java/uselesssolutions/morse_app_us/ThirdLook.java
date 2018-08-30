package uselesssolutions.morse_app_us;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdLook extends AppCompatActivity {


    private Button back1;
    // NIKLAVS ADDED THIS:
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
    private boolean runWhileTrue = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_look);
        back1 = (Button) findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

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
                MorseTextClass summon = new MorseTextClass();
                if(morseForMethod.length() > 0) {
                    if (morseForMethod.charAt(morseForMethod.length()-1) != '/'){
                        morseForMethod = morseForMethod + "/";
                    }
                    morseText.setText(makeDisplayString(morseForMethod));
                    letterText.setText(summon.morseToText(morseForMethod));
                }
            }
        });
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        ThirdLook.super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        ThirdLook.super.onBackPressed();

    }
}
