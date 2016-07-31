package www.branch.com.contentproviderdemo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Branch on 2016-7-25.
 *
 * recyclerview 分割线，三种类型 如果使用背景色则不用设置颜色，设置大小即可
 */
public class RecyclerviewItemDividerDeoration extends RecyclerView.ItemDecoration {


  public static final int TYPE_HORIZATION = 1;

  public static final int TYPE_VERTICAL = 2;

  public static final int TYPE_GRID = 3;

  private int mDividerSize;

  private int mColor;

  private int mType;

  private Drawable mDivider;

  public RecyclerviewItemDividerDeoration(int mType, int mDividerSize, int mColor) {
    super();
    this.mType = mType;
    this.mDividerSize = mDividerSize;
    this.mColor = mColor;
    mDivider = new ColorDrawable(mColor);
  }

  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);

    if (mColor == -1) {
      //没有设置颜色则使用背景色
    } else {

      final int count = parent.getChildCount();
      for (int i = 0; i < count; i++) {

        final View childView = parent.getChildAt(i);
        if (childView == null) {
          continue;
        }

        switch (mType) {
          case TYPE_HORIZATION:

            final int h_left = childView.getRight();
            final int h_right = h_left + mDividerSize;
            final int h_Top = childView.getTop();
            final int h_bottom = childView.getBottom();

            mDivider.setBounds(h_left, h_Top, h_right, h_bottom);
            mDivider.draw(c);

            break;
          case TYPE_VERTICAL:

            final int v_left = childView.getLeft();
            final int v_right = childView.getRight();
            final int v_Top = childView.getBottom();
            final int v_bottom = v_Top + mDividerSize;

            mDivider.setBounds(v_left, v_Top, v_right, v_bottom);
            mDivider.draw(c);

            break;

          case TYPE_GRID:

            final int g_left = childView.getLeft();
            final int g_right = childView.getRight();
            final int g_Top = childView.getTop();
            final int g_bottom = childView.getBottom();

            //left
            mDivider.setBounds(g_left - mDividerSize, g_Top, g_left, g_bottom);
            mDivider.draw(c);

            //right
            Drawable drawable = new ColorDrawable(mColor);
            drawable.setBounds(g_right, g_Top, g_right + mDividerSize, g_bottom);
            drawable.draw(c);
            //top
            drawable = new ColorDrawable(mColor);
            drawable.setBounds(g_left - mDividerSize, g_Top - mDividerSize, g_right + mDividerSize, g_Top);
            drawable.draw(c);
            //bottom
            drawable = new ColorDrawable(mColor);
            drawable.setBounds(g_left - mDividerSize, g_bottom, g_right + mDividerSize, g_bottom + mDividerSize);
            drawable.draw(c);

            break;
        }
      }

    }

  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    switch (mType) {
      case TYPE_HORIZATION:
        outRect.set(0, 0, mDividerSize, 0);
        break;
      case TYPE_VERTICAL:
        outRect.set(0, 0, 0, mDividerSize);
        break;
      case TYPE_GRID:
        outRect.set(mDividerSize, mDividerSize, 0, 0);
        break;
    }
  }
}
