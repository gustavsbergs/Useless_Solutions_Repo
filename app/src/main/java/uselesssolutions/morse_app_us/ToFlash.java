package uselesssolutions.morse_app_us;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ToFlash extends AppCompatActivity {

    private Button cameraEnable;
    private Button flashEnable;
    private static final int CAMERA_REQUEST = 50;
    private boolean flashLightStatus = false;
    private Button back;
    private EditText editText;
    private String textToConv;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toflash);
        flashEnable = (Button) findViewById(R.id.button_on_off);
        cameraEnable = (Button) findViewById(R.id.buttonCameraEnable);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToMain();
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
                ActivityCompat.requestPermissions(ToFlash.this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        });

        editText = (EditText) findViewById(R.id.enterText1);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.getText().clear();
            }
        });


        flashEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               textToConv = editText.getText().toString();
               String moreString = MorseCode.alphaToMorse(textToConv);
                System.out.println("String START:"+ moreString +"ENDS");
               // pass String to flashlight
                for (int i = 0; i < moreString.length() ; i++) {
                    char c = moreString.charAt(i);
                    if (c == ' '){
                        System.out.println("EMPTY");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else if (c == '.' ){
                        System.out.println("DOT");
                        try {
                            Thread.sleep(1000);
                            flashLightOn();
                            Thread.sleep(1000);
                            flashLightOff();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else if (c == '-'){
                        System.out.println("DASH");
                        try {
                            Thread.sleep(1000);
                            flashLightOn();
                            Thread.sleep(3000);
                            flashLightOff();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

                //
//                if (hasCameraFlash) {
//                    if (flashLightStatus)
//                        flashLightOff();
//                    else
//                        flashLightOn();
//                } else {
//                    Toast.makeText(ToFlash.this, "No flash available on your device",
//                            Toast.LENGTH_SHORT).show();
//                }
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
        switch(requestCode) {
            case CAMERA_REQUEST :
                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraEnable.setEnabled(false);
                    cameraEnable.setText("Camera Enabled");
                    flashEnable.setEnabled(true);
                } else {
                    Toast.makeText(ToFlash.this, "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}