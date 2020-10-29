package com.anees4ever.iconbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconButton extends LinearLayout {
    protected ImageView mIconView;
    protected TextView mTextView;

    private int mIconResourceId= 0;
    private int mIconTint= 0, mIconTintChecked= 0;

    private String mText= "";
    private int mTextColor= 0, mTextColorChecked= 0;
    private float mTextSize= 0;
    private int mTextFont= 0;
    private int mMaxLines= 1;

    private boolean mChecked= false, mEnabled= true;

    @TargetApi(21)
    public IconButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }
    public IconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr, 0);
    }

    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0, 0);
    }

    public IconButton(Context context) {
        super(context);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        try {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconButton, defStyleAttr, defStyleRes);

            mIconResourceId = a.getResourceId(R.styleable.IconButton_icon, 0);
            mIconTint = a.getColor(R.styleable.IconButton_iconTint, 0);//context.getResources().getColor(R.color.dark_grey));
            mIconTintChecked = a.getColor(R.styleable.IconButton_iconTintChecked, 0);//context.getResources().getColor(R.color.dark_brown));
            mText = a.getString(R.styleable.IconButton_text);
            mTextColor = a.getColor(R.styleable.IconButton_textColor, context.getResources().getColor(R.color.dark_grey));
            mTextColorChecked = a.getColor(R.styleable.IconButton_textColorChecked, context.getResources().getColor(R.color.dark_brown));
            mMaxLines = a.getInt(R.styleable.IconButton_maxLines, 1);
            mTextSize = a.getDimensionPixelSize(R.styleable.IconButton_textSize, 0);
            mTextFont = a.getResourceId(R.styleable.IconButton_textFont, 0);
            mChecked = a.getBoolean(R.styleable.IconButton_checked, false);
            mEnabled = a.getBoolean(R.styleable.IconButton_enabled, true);

            setOrientation(VERTICAL);
            if(mEnabled) {
                setClickable(true);
                setFocusable(true);
                //setFocusableInTouchMode(true);
            }

            initIcon();
            initLabel();
            updateState();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initIcon() {
        LayoutParams params= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight= 1;
        mIconView= new ImageView(getContext());
        mIconView.setLayoutParams(params);
        mIconView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if(mIconResourceId > 0) {
            mIconView.setImageResource(mIconResourceId);
        }
        if(mIconTint != 0) {
            mIconView.setColorFilter(mIconTint);
        }
        addView(mIconView);
    }

    private void initLabel() {
        LayoutParams params= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER;
        mTextView= new TextView(getContext());
        mTextView.setLayoutParams(params);
        mTextView.setMaxLines(mMaxLines);
        mTextView.setLines(mMaxLines);
        if(mTextSize==0) {
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else {
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        }
        mTextView.setGravity(Gravity.CENTER);
        //mTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mTextView.setText(TextUtils.isEmpty(mText)?"":mText);
        mTextView.setTextColor(mTextColor);
        if(mTextFont != 0) {
            mTextView.setTypeface(ResourcesCompat.getFont(getContext(), mTextFont));
        }
        mTextView.setVisibility(TextUtils.isEmpty(mText)?GONE:VISIBLE);
        addView(mTextView);
    }

    public void toggleCheck() {
        mChecked= !mChecked;
        updateState();
    }

    public boolean isChecked() {
        return mChecked;
    }
    public void setChecked(boolean checked) {
        mChecked= checked;
        updateState();
    }

    public void setText(String text) {
        mTextView.setText(text);
        mTextView.setVisibility(TextUtils.isEmpty(text)?GONE:VISIBLE);
        invalidate();
    }
    public void setText(int text) {
        mTextView.setText(text);
        mTextView.setVisibility(text==0?GONE:VISIBLE);
        invalidate();
    }
    public String getText() {
        return mTextView.getText().toString();
    }

    public void setIcon(int icon) {
        mIconView.setImageResource(icon);
        invalidate();
    }

    public void setIcon(Drawable icon) {
        mIconView.setImageDrawable(icon);
        invalidate();
    }

    public void setIconTint(int color) {
        mIconTint= color;
        updateState();
    }

    private void updateState() {
        if(mIconTint != 0 && mIconTintChecked != 0) {
            mIconView.setColorFilter(mChecked ? mIconTintChecked : mIconTint);
        }
        mTextView.setTextColor(mChecked?mTextColorChecked:mTextColor);
        invalidate();
    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}