package uselesssolutions.morse_app_us;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondLook extends AppCompatActivity {

    private Button back;
    private EditText editText;
    private TextView translation;
    private Button translate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_look);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToMain();
            }
        });
        editText = (EditText) findViewById(R.id.enterText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().clear();

            }
        });
        translate = (Button) findViewById(R.id.translateToMorse);
        translation = (TextView) findViewById(R.id.getTranslation);
        translation.setMovementMethod(new ScrollingMovementMethod());
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toConvert =editText.getText().toString();
                String afterConversion = MorseCode.alphaToMorse(toConvert);
                translation.setText(afterConversion);
            }
        });


    }

    public void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
