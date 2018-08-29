package uselesssolutions.morse_app_us;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class toMorseAllFunctions extends AppCompatActivity {

    private Button flashEnable;
    private static final int CAMERA_REQUEST = 50;
    private Button back;
    private EditText editText;
    private String textToConv;
    private TextView translation;
    private Button btnToMorse;
    private Button vibrate;
    private String afterConversion1;
    private String morseString;
    private boolean runWhileTrue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toflash);
        flashEnable = (Button) findViewById(R.id.button_on_off);
        btnToMorse = (Button) findViewById(R.id.translateToMorse2);
        back = (Button) findViewById(R.id.back);
        translation = (TextView) findViewById(R.id.getTranslation2);
        editText = (EditText) findViewById(R.id.enterText1);
        String charSequence = "";
        editText.setText(charSequence);
        

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToMain();
            }
        });

        vibrate = (Button) findViewById(R.id.vibrate);
        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToConv = editText.getText().toString();
                morseString = MorseCode.alphaToMorse(textToConv);
                new Thread (new Runnable() {
                    @Override
                    public void run() {
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
        });

        final Button morsesound = (Button)this.findViewById(R.id.morse_sound);
        final MediaPlayer longbe = MediaPlayer.create(this, R.raw.longbeep);
        final MediaPlayer shortbe = MediaPlayer.create(this, R.raw.shortbeep);
        //final ToneGenerator ob = new ToneGenerator();
        //final ToneGenerator op = new ToneGenerator();
        //final AudioTrack shortBeep = ob.generateTone(720, 500);
        //final AudioTrack longBeep = op.generateTone(720, 1500);
        morsesound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread (new Runnable() {
                    @Override
                    public void run() {
                        String morseCode = afterConversion1;
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
                                    shortbe.start();
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
                                    longbe.start();
                                    Thread.sleep(900);


                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();

            }
        });

        final boolean hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

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
                afterConversion1 = MorseCode.alphaToMorse(toConvert);
                translation.setText(afterConversion1);
            }
        });
        flashEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });
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

    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        runWhileTrue = false;
        finishAndRemoveTask();
    }

    //Hardware button "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        runWhileTrue = false;
        finishAndRemoveTask();
    }
}