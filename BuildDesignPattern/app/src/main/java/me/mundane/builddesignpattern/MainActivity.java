package me.mundane.builddesignpattern;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import me.mundane.builddesignpattern.EasyDialogFragment.Builder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_show).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    private void show() {
        DialogFragment dialogFragment = new Builder().setTitle("这是标题")
                .setPositiveButton(new EasyDialogFragment.OnClickListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(new EasyDialogFragment.OnClickListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        dialogFragment.show(getSupportFragmentManager(), "");
    }
}
