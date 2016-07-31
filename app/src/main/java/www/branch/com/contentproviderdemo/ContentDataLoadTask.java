package www.branch.com.contentproviderdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Branch on 16/7/31.
 */
public class ContentDataLoadTask extends AsyncTask<Void, Void, Void> {

  private Context mContext;

  private ContentResolver mContentResolver;

  private OnContentDataLoadListener mOnContentDataLoadListener;

  public ContentDataLoadTask(Context mContext) {
    this.mContext = mContext;
  }

  public OnContentDataLoadListener getmOnContentDataLoadListener() {
    return mOnContentDataLoadListener;
  }

  public void setmOnContentDataLoadListener(OnContentDataLoadListener mOnContentDataLoadListener) {
    this.mOnContentDataLoadListener = mOnContentDataLoadListener;
  }


  @Override
  protected void onPreExecute() {
    super.onPreExecute();

    if (mOnContentDataLoadListener != null) {
      mOnContentDataLoadListener.onStartLoad();
    }


    mContentResolver = mContext.getContentResolver();
  }

  @Override
  protected Void doInBackground(Void... params) {


    ContentDataControl.addFileListByType(FileSystemType.photo, getAllPhoto());

    ContentDataControl.addFileListByType(FileSystemType.music, getAllMusic());

    ContentDataControl.addFileListByType(FileSystemType.video, getAllVideo());

    ContentDataControl.addFileListByType(FileSystemType.text, getAllText());


    ContentDataControl.addFileListByType(FileSystemType.zip, getAllZip());


    return null;
  }


  private List<FileItem> getAllPhoto() {

    List<FileItem> photos = new ArrayList<>();

    String[] projection = new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME};


    //asc 按升序排列
//    desc 按降序排列
    //projection 是定义返回的数据，selection 通常的sql 语句，例如  selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? " 那么 selectionArgs=new String[]{"jpg"};
    Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");


    String imageId = null;

    String fileName;

    String filePath;

    while (cursor.moveToNext()) {

      imageId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));

      fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));

      filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

      FileItem fileItem = new FileItem(imageId, filePath, fileName);

      Log.e("ryze_photo", imageId + " -- " + fileName + " -- " + filePath);


      photos.add(fileItem);


    }
    cursor.close();

    cursor = null;

    return photos;

  }


  private List<FileItem> getAllMusic() {

    List<FileItem> musics = new ArrayList<>();


    String[] projection = new String[]{MediaStore.Audio.AudioColumns._ID, MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.DISPLAY_NAME};


    Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.AudioColumns.DATE_MODIFIED + " desc");

    String fileId;

    String fileName;

    String filePath;

    while (cursor.moveToNext()) {

      fileId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID));

      fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME));

      filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));

      Log.e("ryze_music", fileId + " -- " + fileName + " -- " + filePath);


      FileItem fileItem = new FileItem(fileId, filePath, fileName);

      musics.add(fileItem);

    }

    cursor.close();

    cursor = null;

    return musics;

  }


  private List<FileItem> getAllVideo() {

    List<FileItem> videos = new ArrayList<>();


    String[] projection = new String[]{MediaStore.Video.VideoColumns._ID, MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns.DISPLAY_NAME};


    Cursor cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Video.VideoColumns.DATE_MODIFIED + " desc");


    String fileId;

    String fileName;

    String filePath;

    while (cursor.moveToNext()) {

      fileId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));

      fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME));

      filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));


      Log.e("ryze_video", fileId + " -- " + fileName + " -- " + filePath);

      FileItem fileItem = new FileItem(fileId, filePath, fileName);

      videos.add(fileItem);

    }


    cursor.close();
    cursor = null;

    return videos;

  }

  private List<FileItem> getAllText() {

    List<FileItem> texts = new ArrayList<>();

    String[] projection = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MIME_TYPE};

    String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
        + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
        + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
        + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
        + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";

    String[] selectionArgs = new String[]{"text/plain", "application/msword", "application/pdf", "application/vnd.ms-powerpoint", "application/vnd.ms-excel"};

    Cursor cursor = mContentResolver.query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_MODIFIED + " desc");


    String fileId;

    String fileName;

    String filePath;

    while (cursor.moveToNext()) {

      fileId = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));

      fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));

      filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));


      Log.e("ryze_text", fileId + " -- " + fileName + " -- " + "--" + cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)) + filePath);

      FileItem fileItem = new FileItem(fileId, filePath, fileName);

      texts.add(fileItem);

    }


    cursor.close();
    cursor = null;


    return texts;

  }

  private List<FileItem> getAllZip() {

    List<FileItem> zips = new ArrayList<>();


    String[] projection = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE};

    String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? ";

    String[] selectionArgs = new String[]{"application/zip"};

    Cursor cursor = mContentResolver.query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, MediaStore.Files.FileColumns.DATE_MODIFIED + " desc");


    String fileId;

    String fileName;

    String filePath;

    while (cursor.moveToNext()) {

      fileId = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));

      fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));

      filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));


      Log.e("ryze_zip", fileId + " -- " + fileName + " -- " + filePath);

      FileItem fileItem = new FileItem(fileId, filePath, fileName);

      zips.add(fileItem);

    }


    return zips;

  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    if (mOnContentDataLoadListener != null) {
      mOnContentDataLoadListener.onFinishLoad();
    }
  }


  @Override
  protected void onCancelled() {
    super.onCancelled();
    if (mOnContentDataLoadListener != null) {
      mOnContentDataLoadListener.onFinishLoad();
    }
  }

  public interface OnContentDataLoadListener {

    public void onStartLoad();

    public void onFinishLoad();

  }


}
