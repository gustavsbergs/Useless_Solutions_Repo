package uselesssolutions.morse_app_us;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
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

    private Button cameraEnable;
    private Button flashEnable;
    private static final int CAMERA_REQUEST = 50;
    private boolean flashLightStatus = false;
    private Button back;
    private EditText editText;
    private String textToConv;
    private TextView translation;
    private Button btnToMorse;
    private Button vibrate;
    private String afterConversion1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toflash);
        flashEnable = (Button) findViewById(R.id.button_on_off);
        cameraEnable = (Button) findViewById(R.id.buttonCameraEnable);
        btnToMorse = (Button) findViewById(R.id.translateToMorse2);
        back = (Button) findViewById(R.id.back);
        translation = (TextView) findViewById(R.id.getTranslation2);


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
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                String morseCode = afterConversion1;
                for (int i = 0; i < morseCode.length(); i++) {
                    char ch = morseCode.charAt(i);


                    if (ch == ' ') {
                        try {
                            Thread.sleep(600);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else if (ch == '.') {
                        try {
                            Thread.sleep(200);
                            vibrator.vibrate(200);
                            Thread.sleep(200);
                            vibrator.cancel();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (ch == '-') {
                        try {
                            Thread.sleep(200);
                            vibrator.vibrate(600);
                            Thread.sleep(600);
                            vibrator.cancel();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        final boolean hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        cameraEnable.setEnabled(!isEnabled);
        flashEnable.setEnabled(isEnabled);

        cameraEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(toMorseAllFunctions.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        });

        editText = (EditText) findViewById(R.id.enterText1);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.getText().clear();
            }
        });

        btnToMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                String toConvert = editText.getText().toString();
                afterConversion1 = MorseCode.alphaToMorse(toConvert);
                translation.setText(afterConversion1);
            }
        });



        flashEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToConv = editText.getText().toString();
                String morseString = MorseCode.alphaToMorse(textToConv);

                translation.setText(morseString);

                System.out.println("String START:" + morseString + "ENDS");

                if (hasCameraFlash) {
                    for (int i = 0; i < morseString.length(); i++) {
                        char c = morseString.charAt(i);
                        if (c == ' ') {
                            System.out.println("EMPTY");
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (c == '.') {
                            System.out.println("DOT");
                            try {
                                Thread.sleep(400);
                                flashLightOn();
                                Thread.sleep(400);
                                flashLightOff();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else if (c == '-') {
                            System.out.println("DASH");
                            try {
                                Thread.sleep(400);
                                flashLightOn();
                                Thread.sleep(1200);
                                flashLightOff();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    public void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            flashLightStatus = true;
        } catch (CameraAccessException e) {
        }
    }

    public void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            flashLightStatus = false;
        } catch (CameraAccessException e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraEnable.setEnabled(false);
                    cameraEnable.setText("Camera Enabled");
                    flashEnable.setEnabled(true);
                } else {
                    Toast.makeText(toMorseAllFunctions.this, "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}