package uselesssolutions.morse_app_us;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class toMorseAllFunctions extends AppCompatActivity {

    Toolbar toolbar;
    private EditText editText;
    private String textToConv;
    private TextView translation;
    private Button btnToMorse;
    private Button vibrate;
    private String afterConversion1;
    private String morseString;
    private boolean runWhileTrue = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.vibrator:
                Toast.makeText(this, "Vibrator is on full power", Toast.LENGTH_SHORT).show();
                vibratorON();
                break;
            case R.id.flash:
                Toast.makeText(this, "Morse strobe light enabled", Toast.LENGTH_SHORT).show();
                flashBack();
                break;
            case R.id.sound:
                Toast.makeText(this, "Audio playback", Toast.LENGTH_SHORT).show();
                audioPlayback();
                break;

             default: System.out.println("ACTION NOT ASSIGNED");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toflash);

        toolbar = findViewById(R.id.my_toolbar);
        if (toolbar != null)
            toolbar.setTitle("Text to Morse");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnToMorse = (Button) findViewById(R.id.translateToMorse2);
        translation = (TextView) findViewById(R.id.getTranslation2);
        translation.setMovementMethod(new ScrollingMovementMethod());
        editText = (EditText) findViewById(R.id.enterText1);
        String charSequence = "";
        editText.setText(charSequence);


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.getText().clear();
            }
        });

        btnToMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String toConvert = editText.getText().toString();
                hideKeyboardFrom(getApplicationContext(), view);
                afterConversion1 = MorseCode.alphaToMorse(toConvert);
                translation.setText(afterConversion1);
            }
        });
    }

    protected void onStop() {
        super.onStop();
        runWhileTrue = false;
    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
        }
    }

    public void vibratorON(){
        textToConv = editText.getText().toString();
        morseString = MorseCode.alphaToMorse(textToConv);
        new Thread (new Runnable() {
            @Override
            public void run() {
                runWhileTrue = true;
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                for (int i = 0; i < morseString.length(); i++) {
                    char ch = morseString.charAt(i);
                    if (ch == ' ' && runWhileTrue) {
                        try {
                            Thread.sleep(200);
                            System.out.println("EMPTY");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else if (ch == '.' && runWhileTrue) {
                        try {
                            Thread.sleep(200);
                            System.out.println("DOT");
                            vibrator.vibrate(200);
                            Thread.sleep(200);
                            vibrator.cancel();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (ch == '-' && runWhileTrue) {
                        try {
                            Thread.sleep(200);
                            System.out.println("DASH");
                            vibrator.vibrate(600);
                            Thread.sleep(600);
                            vibrator.cancel();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public void flashBack(){
        final boolean hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        runWhileTrue = true;
        textToConv = editText.getText().toString();
        morseString = MorseCode.alphaToMorse(textToConv);
        System.out.println("String START:" + morseString + "ENDS");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (hasCameraFlash) {
                    for (int i = 0; i < morseString.length(); i++) {
                        char c = morseString.charAt(i);
                        if (c == ' ' && runWhileTrue) {
                            System.out.println("EMPTY");
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (c == '.' && runWhileTrue) {
                            System.out.println("DOT");
                            try {

                                flashLightOn();
                                Thread.sleep(300);
                                flashLightOff();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (c == '-' && runWhileTrue) {
                            System.out.println("DASH");
                            try {

                                flashLightOn();
                                Thread.sleep(900);
                                flashLightOff();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public void audioPlayback(){
        final MediaPlayer longbe2 = MediaPlayer.create(this, R.raw.longbeep);
        final MediaPlayer shortbe2 = MediaPlayer.create(this, R.raw.shortbeep);

        runWhileTrue = true;
        textToConv = editText.getText().toString();
        morseString = MorseCode.alphaToMorse(textToConv);
        new Thread (new Runnable() {
            @Override
            public void run() {
                String morseCode = morseString;
                for (int i = 0; i < morseCode.length(); i++) {
                    char ch = morseCode.charAt(i);
                    if (ch == ' ' && runWhileTrue) {
                        try {
                            Thread.sleep(300);
                            System.out.println("EMPTY");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else if (ch == '.' && runWhileTrue) {
                        try {
                            //Thread.sleep(200);
                            System.out.println("DOT");
                            //shortBeep.play();
                            shortbe2.start();
                            Thread.sleep(300);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (ch == '-' && runWhileTrue) {
                        try {
                            //Thread.sleep(200);
                            System.out.println("DASH");
                            //longBeep.play();
                            longbe2.start();
                            Thread.sleep(900);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}