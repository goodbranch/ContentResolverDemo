### 项目开发中我们要使用到本地SD卡中的媒体文件，ContentResolver 可以很方便的帮助我们查询所有信息。
--
#### 1.ContentResolver 中我们要使用到的两个方式的讲解
>
> 通过 `mContext.getContentResolver();`获取ContentResolver 实例，查询使用`query` 插入使用`insert`
>
>`query(...)` 搜索指定Uri下的媒体文件，后面是sql语句
>
>`insert(...)` 把新文件插入到指定Uri表中，后面跟数据库键值对。

#### 2.媒体文件的Uri是如何获取的
>
>找到`MediaStore`，里面内部类有`Images`,`Audio`,`Video`,`Files`这几个包含了所有Android媒体类型，例如我们要查询图片则通过`Images` 得到对应的`EXTERNAL_CONTENT_URI`就能按照`ContentResolver` 的方法查询图片，同时`Images` 中还有缩略图类，可以通过查询到图片的缩略图，表的字段名都一样，关键也是`Uri`,可以通过`Images`中的`Thumbnails`获取。以此类推可以去看看其他几种媒体类型中的相关Uri以及字段名和能查询到的信息。
>
>
#### 3. 具体的方法实现
>
> 1. 查询图片
>
> 	

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
>
>
>2 .查询文本文件
>

		 private List<FileItem> getAllText() {

	    List<FileItem> texts = new ArrayList<>();

	    String[] projection = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MIME_TYPE};

        //相当于我们常用sql where 后面的写法
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

>
>
#### 4. 实现效果
>
>![分类展示](https://raw.githubusercontent.com/goodbranch/AndroidNote/master/note/image/contentresolver_1.png)
>
>![图片展示](https://raw.githubusercontent.com/goodbranch/AndroidNote/master/note/image/contentresolver_2.png)
>
>
#### 5.源码和apk
>
> [demo](https://github.com/goodbranch/ContentResolverDemo.git)
>
> [APK](https://raw.githubusercontent.com/goodbranch/AndroidNote/master/note/Apk/contentresolverdemo.apk)