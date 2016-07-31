package www.branch.com.contentproviderdemo;

import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRecyclerItemClickListener, ContentDataLoadTask.OnContentDataLoadListener {

  private static final int SPAN_COUNT = 3;

  RecyclerView mRecyclerView;

  TypeGridAdapter mTypeGridAdapter;


  ContentDataLoadTask mContentDataLoadTask;

  ProgressDialog mProgressDialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), SPAN_COUNT);
    mRecyclerView.setLayoutManager(gridLayoutManager);

    List<TypeModel> typeModels = ensureGridData();

    DisplayMetrics metrics = getResources().getDisplayMetrics();

    mTypeGridAdapter = new TypeGridAdapter(getApplicationContext(), typeModels, metrics.widthPixels / SPAN_COUNT);

    mTypeGridAdapter.setmOnRecyclerItemClickListener(this);

    mRecyclerView.setAdapter(mTypeGridAdapter);

    startLoadData();

  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

      startLoadData();

    }

  }


  private void startLoadData() {

    if (ContextCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

      mContentDataLoadTask = new ContentDataLoadTask(this);
      mContentDataLoadTask.setmOnContentDataLoadListener(this);
      mContentDataLoadTask.execute();
    } else {

      ActivityCompat.requestPermissions(this, new String[]{permission.READ_EXTERNAL_STORAGE}, 1);
    }
  }


  private List<TypeModel> ensureGridData() {

    List<TypeModel> typeModels = new ArrayList<>();

    TypeModel typeModel = new TypeModel(FileSystemType.photo, R.mipmap.filesystem_grid_icon_photo, getString(R.string.filesystem_grid_photo), 0);

    typeModels.add(typeModel);

    typeModel = new TypeModel(FileSystemType.music, R.mipmap.filesystem_grid_icon_music, getString(R.string.filesystem_grid_music), 0);

    typeModels.add(typeModel);

    typeModel = new TypeModel(FileSystemType.video, R.mipmap.filesystem_grid_icon_movie, getString(R.string.filesystem_grid_video), 0);

    typeModels.add(typeModel);

    typeModel = new TypeModel(FileSystemType.text, R.mipmap.filesystem_grid_icon_text, getString(R.string.filesystem_grid_text), 0);

    typeModels.add(typeModel);

    typeModel = new TypeModel(FileSystemType.zip, R.mipmap.filesystem_grid_icon_zip, getString(R.string.filesystem_grid_zip), 0);

    typeModels.add(typeModel);


    return typeModels;

  }


  @Override
  public void onRecyclerItemClick(View itemView, int position) {


    TypeModel typeModel = mTypeGridAdapter.getItem(position);

    if (typeModel != null) {


      startActivity(ShowFileItemActivity.newIntent(this, typeModel.getmType()));

    }


  }

  @Override
  public void onStartLoad() {

    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this);
    }
    mProgressDialog.show();

  }

  @Override
  public void onFinishLoad() {


    if (mTypeGridAdapter.getmTypeModelList() != null) {

      for (TypeModel typeModel : mTypeGridAdapter.getmTypeModelList()) {

        typeModel.setmCount(ContentDataControl.getTypeCount(typeModel.getmType()));
      }


      mTypeGridAdapter.notifyDataSetChanged();

    }


    if (mProgressDialog != null) {
      mProgressDialog.dismiss();
    }
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mContentDataLoadTask != null) {
      mContentDataLoadTask.cancel(true);
      mContentDataLoadTask.setmOnContentDataLoadListener(null);
      mContentDataLoadTask = null;
    }

    if (mProgressDialog != null) {
      mProgressDialog.dismiss();
    }
  }
}
