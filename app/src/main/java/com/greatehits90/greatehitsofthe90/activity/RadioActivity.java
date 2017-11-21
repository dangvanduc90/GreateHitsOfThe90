package com.greatehits90.greatehitsofthe90.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.greatehits90.greatehitsofthe90.R;
import com.greatehits90.greatehitsofthe90.object.ObjectRadio;
import com.greatehits90.greatehitsofthe90.parsehtml.ParseAudioSourc;

import static com.greatehits90.greatehitsofthe90.common.Ultils.BUNDLE_RADIO_OBJECT;

public class RadioActivity extends AppCompatActivity {

    ObjectRadio mRadio;
    ImageView ivPlaying;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        mRadio = (ObjectRadio) getIntent().getExtras().getSerializable(BUNDLE_RADIO_OBJECT);
        Log.d("doc", mRadio.getUrlSourc());
        new ParseAudioSourc().execute(mRadio.getUrlSourc());

        initView();

        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        ivPlaying.startAnimation(animation);
    }

    private void initView() {
        ivPlaying = findViewById(R.id.ivPlaying);
    }
}
