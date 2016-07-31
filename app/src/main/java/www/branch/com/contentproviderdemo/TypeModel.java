package www.branch.com.contentproviderdemo;

/**
 * Created by Branch on 16/7/30.
 */
public class TypeModel {

  private FileSystemType mType;

  private int mTypeIcon;

  private String mTitle;

  private int mCount;

  public TypeModel(FileSystemType type, int mTypeIcon, String mTitle, int mCount) {
    this.mType = type;
    this.mTypeIcon = mTypeIcon;
    this.mTitle = mTitle;
    this.mCount = mCount;
  }

  public FileSystemType getmType() {
    return mType;
  }

  public void setmType(FileSystemType mType) {
    this.mType = mType;
  }

  public int getmTypeIcon() {
    return mTypeIcon;
  }

  public void setmTypeIcon(int mTypeIcon) {
    this.mTypeIcon = mTypeIcon;
  }

  public String getmTitle() {
    return mTitle;
  }

  public void setmTitle(String mTitle) {
    this.mTitle = mTitle;
  }

  public int getmCount() {
    return mCount;
  }

  public void setmCount(int mCount) {
    this.mCount = mCount;
  }
}
