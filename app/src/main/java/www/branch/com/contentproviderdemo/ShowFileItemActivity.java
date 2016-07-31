package www.branch.com.contentproviderdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Branch on 16/7/31.
 */
public class ShowFileItemActivity extends AppCompatActivity {


  private static final String SHOW_FILEITEM_TYPE_KEY = "showtype";

  public static final int SPAN_COUNT = 3;

  public static Intent newIntent(Context context, FileSystemType type) {

    Intent intent = new Intent(context, ShowFileItemActivity.class);

    intent.putExtra(SHOW_FILEITEM_TYPE_KEY, type.ordinal());

    return intent;

  }


  private FileSystemType mShowType = FileSystemType.photo;

  private RecyclerView mRecyclerView;


  private ShowFileAdapter mShowFileAdapter;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.showfile_recyclerview);

    mShowType = FileSystemType.getFileTypeByOrdinal(getIntent().getIntExtra(SHOW_FILEITEM_TYPE_KEY, FileSystemType.photo.ordinal()));


    initView();
  }

  private void initView() {

    mRecyclerView = (RecyclerView) findViewById(R.id.showfile_recyclerview);
    RecyclerView.LayoutManager layoutManager = null;

    RecyclerviewItemDividerDeoration dividerDeoration = null;

    switch (mShowType) {
      case photo:

        layoutManager = new GridLayoutManager(getApplicationContext(), SPAN_COUNT);

        dividerDeoration = new RecyclerviewItemDividerDeoration(RecyclerviewItemDividerDeoration.TYPE_GRID, getResources().getDimensionPixelSize(R.dimen.grid_item_divider_size), -1);

        break;
      default:

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        dividerDeoration = new RecyclerviewItemDividerDeoration(RecyclerviewItemDividerDeoration.TYPE_VERTICAL, getResources().getDimensionPixelSize(R.dimen.vertical_item_divider_size), getResources().getColor(R.color.divider_color));


        break;
    }

    mRecyclerView.addItemDecoration(dividerDeoration);

    mRecyclerView.setLayoutManager(layoutManager);

    mShowFileAdapter = new ShowFileAdapter(this, mShowType);

    mRecyclerView.setAdapter(mShowFileAdapter);

  }


}
