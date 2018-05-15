package me.mundane.rxjavaanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void helloworld(View view) {

        Observable.just("Hello world")
                .subscribe(new Action1<String>() {


                    @Override
                    public void call(String word) {
                        System.out.println("got " + word + " @ " + Thread.currentThread().getName());
                    }
                });
    }
}
