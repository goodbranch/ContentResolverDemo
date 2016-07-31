package www.branch.com.contentproviderdemo;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Branch on 16/7/30.
 */
public class ContentDataControl {


  private static final ArrayMap<FileSystemType, List<FileItem>> mAllFileItem = new ArrayMap<>();


  public static void addFileByType(FileSystemType type, FileItem fileItem) {

    if (type == null || fileItem == null) {
      return;
    }

    List<FileItem> fileItemList = mAllFileItem.get(type);

    if (fileItemList == null) {
      fileItemList = new ArrayList<>();
      mAllFileItem.put(type, fileItemList);
    }

    fileItemList.add(fileItem);

  }


  public static void addFileListByType(FileSystemType type, List<FileItem> fileItemList) {


    if (type == null || fileItemList == null) {
      return;
    }

    List<FileItem> fileItems = mAllFileItem.get(type);

    if (fileItems == null) {
      fileItems = new ArrayList<>();
      mAllFileItem.put(type, fileItems);
    }

    fileItems.addAll(fileItemList);

  }


  public static List<FileItem> getFileItemListByType(FileSystemType fileSystemType) {

    if (fileSystemType == null) {
      return null;
    }

    return mAllFileItem.get(fileSystemType);

  }


  public static int getTypeCount(FileSystemType fileSystemType) {

    List<FileItem> fileItemList = mAllFileItem.get(fileSystemType);


    return fileItemList == null ? 0 : fileItemList.size();

  }


  public static void destory() {
    mAllFileItem.clear();
  }

}
