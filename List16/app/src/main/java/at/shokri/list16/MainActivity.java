package at.shokri.list16;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] buttons = new Button[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(this);

            buttons[i].setText(String.valueOf(i+1));

            buttons[i].setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            ll.addView(buttons[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        Toast.makeText(this, "Button " + b.getText(), Toast.LENGTH_SHORT).show();
    }
}
