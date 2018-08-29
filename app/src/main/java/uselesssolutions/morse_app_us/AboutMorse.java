package uselesssolutions.morse_app_us;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AboutMorse extends AppCompatActivity {

    private Button back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_morse);

        back2 = (Button) findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToMain2();
            }
        });
    }
    private void backToMain2(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAndRemoveTask();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
