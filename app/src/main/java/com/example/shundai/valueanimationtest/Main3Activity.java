package com.example.shundai.valueanimationtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Main3Activity extends AppCompatActivity {

    private LinearLayout lliner;
    private ImageView ivArrow;
    private boolean isOpen=true;
    private int llHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

      findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              initAnimation();
          }
      });
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        lliner = (LinearLayout) findViewById(R.id.ll_arrow);
        ViewTreeObserver TreeObserver = lliner.getViewTreeObserver();
        TreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llHeight = lliner.getHeight();
                lliner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        ivArrow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              toggle();
          }
      });

    }

    private void toggle() {
        ValueAnimator animator=null;
        if (isOpen){
            isOpen=false;
            animator=ValueAnimator.ofInt(llHeight,0);
        }else {
            isOpen=true;
            animator=ValueAnimator.ofInt(0,llHeight);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer animatedValue = (Integer) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lliner.getLayoutParams();
                layoutParams.height=animatedValue;
                lliner.setLayoutParams(layoutParams);
                Log.d("onAnimationUpdate",animatedValue+"");
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
             if (isOpen){
                 ivArrow.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_shouqi));
             }else {
                 ivArrow.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_zhankai));
             }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.setDuration(200);
        animator.start();




    }

    private void initAnimation() {
        final ValueAnimator value=ValueAnimator.ofFloat(0,400);
        value.setDuration(1000);
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float cureValue = (float) valueAnimator.getAnimatedValue();
              //  Log.d("onAnimationUpdate",cureValue+"");
            }
        });
        value.start();
    }
}
