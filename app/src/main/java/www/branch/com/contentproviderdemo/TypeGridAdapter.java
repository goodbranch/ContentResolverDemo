package www.branch.com.contentproviderdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Branch on 16/7/30.
 */
public class TypeGridAdapter extends RecyclerView.Adapter<TypeGridAdapter.TypeHolder> {


  private Context mContext;

  private List<TypeModel> mTypeModelList;

  private int mItemSize;


  private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

  public TypeGridAdapter(Context mContext, List<TypeModel> mTypeModelList, int mItemSize) {
    this.mContext = mContext;
    this.mTypeModelList = mTypeModelList;
    this.mItemSize = mItemSize;
  }

  public OnRecyclerItemClickListener getmOnRecyclerItemClickListener() {
    return mOnRecyclerItemClickListener;
  }

  public void setmOnRecyclerItemClickListener(OnRecyclerItemClickListener mOnRecyclerItemClickListener) {
    this.mOnRecyclerItemClickListener = mOnRecyclerItemClickListener;
  }

  @Override
  public TypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View itemView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_layout, null, false);

    ViewGroup.LayoutParams params = itemView.getLayoutParams();

    if (params == null) {
      params = new ViewGroup.LayoutParams(mItemSize, mItemSize);
    } else {
      params.width = mItemSize;
      params.height = mItemSize;
    }
    itemView.setLayoutParams(params);

    return new TypeHolder(itemView);
  }

  @Override
  public void onBindViewHolder(TypeHolder holder, int position) {


    TypeModel typeModel = getItem(position);

    if (typeModel != null) {

      Glide.with(mContext).load(typeModel.getmTypeIcon()).asBitmap().centerCrop().into(holder.mIcon);
      holder.mTitle.setText(typeModel.getmTitle());
      holder.mCount.setText(String.valueOf(typeModel.getmCount()));


    }

    holder.itemView.setTag(position);

  }

  public TypeModel getItem(int position) {

    if (position >= 0 && position < getItemCount()) {
      return mTypeModelList.get(position);
    }

    return null;

  }

  public List<TypeModel> getmTypeModelList() {
    return mTypeModelList;
  }

  @Override
  public int getItemCount() {
    return mTypeModelList == null ? 0 : mTypeModelList.size();
  }

  public class TypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final ImageView mIcon;

    public final TextView mTitle;

    public final TextView mCount;


    public TypeHolder(View itemView) {
      super(itemView);
      mIcon = (ImageView) itemView.findViewById(R.id.grid_item_icon);
      mTitle = (TextView) itemView.findViewById(R.id.grid_item_title);
      mCount = (TextView) itemView.findViewById(R.id.grid_item_count);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

      if (mOnRecyclerItemClickListener != null && v.getTag() != null) {

        mOnRecyclerItemClickListener.onRecyclerItemClick(v, (Integer) v.getTag());
      }


    }
  }


}
