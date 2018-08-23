package uselesssolutions.morse_app_us;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ThirdLook extends AppCompatActivity {

    private Button back1;
    private EditText editText1;
    private Button dot;
    private Button line;
    private Button space;

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
        editText1 = (EditText) findViewById(R.id.enterMorse);
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.getText().clear();

            }
        });
    }
    public void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
