package www.branch.com.contentproviderdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Branch on 16/7/31.
 */
public class ShowFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


  private Context mContext;

  private FileSystemType mType;

  private int mScreenWidth;

  private List<FileItem> mFileItemList;

  public ShowFileAdapter(Context mContext, FileSystemType mType) {
    this.mContext = mContext;
    this.mType = mType;

    DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

    mScreenWidth = metrics.widthPixels;

    mFileItemList = ContentDataControl.getFileItemListByType(mType);

  }


  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View itemView = null;

    RecyclerView.ViewHolder viewHolder = null;

    ViewGroup.LayoutParams layoutParams = null;

    switch (mType) {
      case photo:

        itemView = LayoutInflater.from(mContext).inflate(R.layout.showfile_grid_item_layout, null, false);

        layoutParams = itemView.getLayoutParams();

        if (layoutParams == null) {
          layoutParams = new ViewGroup.LayoutParams(mScreenWidth / ShowFileItemActivity.SPAN_COUNT, mScreenWidth / ShowFileItemActivity.SPAN_COUNT);
        } else {
          layoutParams.width = mScreenWidth / ShowFileItemActivity.SPAN_COUNT;
          layoutParams.height = mScreenWidth / ShowFileItemActivity.SPAN_COUNT;
        }
        itemView.setLayoutParams(layoutParams);

        viewHolder = new GridViewHolder(itemView);

        break;
      default:


        itemView = LayoutInflater.from(mContext).inflate(R.layout.showfile_list_item_layout, null, false);

        layoutParams = itemView.getLayoutParams();

        if (layoutParams == null) {
          layoutParams = new ViewGroup.LayoutParams(mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
          layoutParams.width = mScreenWidth;
        }

        itemView.setLayoutParams(layoutParams);

        viewHolder = new VerticalViewHolder(itemView);

        break;
    }

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


    FileItem fileItem = getItem(position);

    if (fileItem != null) {

      switch (mType) {
        case photo:

          GridViewHolder gridViewHolder = (GridViewHolder) holder;


          Glide.with(mContext).load(fileItem.getmFilePath()).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(gridViewHolder.mIcon);

          break;
        default:

          VerticalViewHolder verticalViewHolder = (VerticalViewHolder) holder;

          verticalViewHolder.mTitle.setText(fileItem.getmFileName());

          break;

      }
    }

  }


  public FileItem getItem(int position) {


    if (position >= 0 && position < getItemCount()) {

      return mFileItemList.get(position);
    }

    return null;

  }


  @Override
  public int getItemCount() {
    return mFileItemList == null ? 0 : mFileItemList.size();
  }


  public class GridViewHolder extends RecyclerView.ViewHolder {

    public final ImageView mIcon;


    public GridViewHolder(View itemView) {
      super(itemView);
      mIcon = (ImageView) itemView.findViewById(R.id.showfile_item_icon);
    }
  }


  public class VerticalViewHolder extends RecyclerView.ViewHolder {

    public final TextView mTitle;

    public VerticalViewHolder(View itemView) {
      super(itemView);
      mTitle = (TextView) itemView.findViewById(R.id.showfile_list_item_title);
    }
  }

}
