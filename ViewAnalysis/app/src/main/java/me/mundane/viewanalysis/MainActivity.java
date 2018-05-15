package me.mundane.viewanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View mTestView;
    private View mTestViewgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTestView = findViewById(R.id.mytestview);
        mTestViewgroup = findViewById(R.id.mytestviewgroup);

        getWindow().getDecorView();
        //ArrayList<? extends User> list = new ArrayList<>();
        //list.add(new User());

        new TestGen<User>();
    }
}
