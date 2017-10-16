package com.example.shundai.valueanimationtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Main2Activity extends Activity {

    private LinearLayout mFoldedView;
    private float mDensity;
    private int mFoldedViewMeasureHeight;
    private ImageView iBtn;

    private boolean isFold = false;//是否是收起状态
    boolean isAnimating = false;//是否正在执行动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iBtn = (ImageView) findViewById(R.id.iBtn);
        mFoldedView = (LinearLayout) findViewById(R.id.content);

        showIbtn();

        //获取像素密度
        mDensity = getResources().getDisplayMetrics().density;
        //获取布局的高度
        mFoldedViewMeasureHeight = (int) (mDensity * 140 + 0.5);

        iBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果动画正在执行,直接return,相当于点击无效了,不会出现当快速点击时,
                // 动画的执行和ImageButton的图标不一致的情况
                if (isAnimating) return;
                //如果动画没在执行,走到这一步就将isAnimating制为true , 防止这次动画还没有执行完毕的
                //情况下,又要执行一次动画,当动画执行完毕后会将isAnimating制为false,这样下次动画又能执行
                isAnimating = true;

                if (mFoldedView.getVisibility() == View.GONE) {
                    //打开动画
                    animateOpen(mFoldedView);
                } else {
                    //关闭动画
                    animateClose(mFoldedView);
                }
            }


        });
    }

    /**
     * 展示ImageButton图标
     */
    private void showIbtn() {
        if (isFold) {
            iBtn.setImageResource(R.drawable.ic_shouqi);
        } else {
            iBtn.setImageResource(R.drawable.ic_zhankai);
        }
        isFold = !isFold;
    }


    private void animateOpen(LinearLayout view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, mFoldedViewMeasureHeight);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        animator.start();
    }

    private void animateClose(final LinearLayout view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        showIbtn();
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
