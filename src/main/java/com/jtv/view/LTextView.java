package com.helloworld.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 解决半角符和全角符号导致文本对齐显示错乱
 * 
 * @author 财神
 *
 */
public class LTextView extends TextView {

	protected float textSize;

	protected float paddingLeft;

	protected float paddingRight;

	protected int textColor;// 文字颜色

	protected Paint paint = new Paint();

	protected float textShowWidth;// 文本展示宽度

	protected int paddingBottom;// 设置一个padding来保证显示不完整

	protected int height = 0;

	protected int length = 0;// 文字长度

	protected char[] textCharArray = null;

	protected CharSequence old = null;

	protected int spaceVertical = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
			getResources().getDisplayMetrics());// 折行间隙 下一行与上一行的间距

	public LTextView(Context context) {
		super(context);
		init();
	}

	public LTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	protected void init() {
		textSize = this.getTextSize();
		textColor = this.getTextColors().getDefaultColor();
		paddingLeft = this.getPaddingLeft();
		paddingRight = this.getPaddingRight();
		paddingBottom = this.getPaddingBottom();
		paint.setTextSize(textSize);
		textHeight = getFontHeight();
		// textSize += 2;// 至于为什么要加2 是因为有点对不齐
		paint.setColor(textColor);
		paint.setAntiAlias(true);
	}

	@Override
	public void setTextColor(int color) {
		this.textColor = color;
		paint.setColor(textColor);
		super.setTextColor(color);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		// System.out.println("HH"+text);
		// if("BX-S01505570800-20160125083556".equals(text)){
		// System.out.println("HH");
		// }

		if (TextUtils.isEmpty(text)) {
			length = 0;
			textCharArray = null;
		} else if (text.equals(old)) {

		} else {// 文本和上次的不一致
			textCharArray = text.toString().toCharArray();
			length = textCharArray.length;
		}
	}

	@Override
	public void setTextSize(float size) {
		textHeight = getFontHeight();
		paint.setTextSize(size);
		super.setTextSize(size);
	}

	private float textHeight = 0;
	private FontMetrics fm;

	// 获取字体y位置
	public float getFontHeight() {
		fm = paint.getFontMetrics();
		// float ceil=paint.ascent()+paint.descent();
		// float ceil =0+ fm.ascent;

		float descent = Math.abs(fm.leading);
		float ascent = Math.abs(fm.ascent);

		// float ceil =(ascent+descent-fm.bottom+6);
		float ceil = descent + ascent;
		// ceil = (float) Math.abs(ceil);
		ceil = (float) (Math.ceil(fm.descent - fm.top - fm.descent));
		return ceil;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		textShowWidth = this.getMeasuredWidth() - paddingLeft - paddingRight;// 获取文本展示的宽度

		int lineCount = 0;// 展示的行数

		float drawedWidth = 0;
		float charWidth;
		for (int i = 0; i < length; i++) {
			charWidth = paint.measureText(textCharArray, i, 1);
			// paint.getFontMetrics().
			if (textCharArray[i] == '\n') {
				lineCount++;
				drawedWidth = 0;
				continue;
			}

			if (textShowWidth - drawedWidth < charWidth) {
				lineCount++;
				drawedWidth = 0;
			}

			float y = (lineCount + 1) * textHeight;

			y += spaceVertical * lineCount;
			canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth, y, paint);

			drawedWidth += charWidth;
		}
		// paddingBottom=0;
		// int h = (int) ((lineCount + 1) * (int) textSize) + paddingBottom + (lineCount * spaceVertical);
		int h = (int) ((lineCount + 1) * (int) textHeight) + (int) fm.descent + (lineCount * spaceVertical);

		if (height != h) {
			setHeight(h);
			height = h;
		}
	}
}