package com.mamba.vpn.free.unlimited.hivpn.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mamba.vpn.free.unlimited.hivpn.R;
import com.mamba.vpn.free.unlimited.hivpn.utils.ScreenUtils;

import androidx.annotation.NonNull;

/**
 * 评分引导弹窗
 */

public class RateDialog extends Dialog {
    private ImageView[] mIvStar = new ImageView[5];
    private OnStarListener mOnStarListener;
    private int mStarLevel;

    public RateDialog(@NonNull Context context, OnStarListener onStarListener) {
        super(context);
        initView(context);
        mOnStarListener = onStarListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(Context context) {
        View view = View.inflate(context, R.layout.rate_dialog, null);
        ImageView ivClose = view.findViewById(R.id.iv_close);
        mIvStar[0] = view.findViewById(R.id.iv_star_1);
        mIvStar[1] = view.findViewById(R.id.iv_star_2);
        mIvStar[2] = view.findViewById(R.id.iv_star_3);
        mIvStar[3] = view.findViewById(R.id.iv_star_4);
        mIvStar[4] = view.findViewById(R.id.iv_star_5);
        ivClose.setOnClickListener(v -> dismiss());
        for (ImageView iv : mIvStar) {
            iv.setOnTouchListener((v, event) -> {
                int actionMasked = event.getActionMasked();
                switch (actionMasked) {
                    case MotionEvent.ACTION_DOWN:
                        int index = findIndexByView(v);
                        if (index >= 0) {
                            mStarLevel = index + 1;
                            for (int i = 0; i < mIvStar.length; i++) {
                                if (i <= index) {
                                    mIvStar[i].setImageResource(R.drawable.ic_star_light);
                                } else {
                                    mIvStar[i].setImageResource(R.drawable.ic_star_default);
                                }
                            }
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int[] viewLocation = new int[2];
                        v.getLocationOnScreen(viewLocation);
                        int viewMaxX = viewLocation[0] + v.getWidth() - 1;
                        int viewMaxY = viewLocation[1] + v.getHeight() - 1;
                        boolean isInside = event.getRawX() <= viewMaxX && event.getRawX() >= viewLocation[0]
                                && event.getRawY() <= viewMaxY && event.getRawY() >= viewLocation[1];

                        if (!isInside) {
                            for (ImageView imageView : mIvStar) {
                                imageView.setImageResource(R.drawable.ic_star_default);
                            }
                        }
                        break;
                }


                return false;
            });
            iv.setOnClickListener(v -> {
                if (mOnStarListener != null) {
                    mOnStarListener.onStarClick(mStarLevel);
                }

                dismiss();
            });
        }

        setContentView(view);
        setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = ScreenUtils.dip2px(context,300);
            layoutParams.height = ScreenUtils.dip2px(context,160f);
            view.setLayoutParams(layoutParams);
        }
        if (getWindow() != null) {
            getWindow().setGravity(Gravity.CENTER);
        }
    }

    private int findIndexByView(View view) {
        int index = 0;
        for (ImageView iv : mIvStar) {
            if (iv == view) return index;
            index++;
        }

        return -1;
    }

    public interface OnStarListener {
        void onStarClick(int starLevel);
    }
}
