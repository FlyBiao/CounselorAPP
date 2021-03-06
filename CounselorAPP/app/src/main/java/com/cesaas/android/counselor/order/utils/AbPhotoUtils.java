package com.cesaas.android.counselor.order.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;

public class AbPhotoUtils {

	public static final String TAG = "PhotoCrop";
	/***
	 * 4.4以下(也就是kitkat以下)的版本
	 */
	public static final int KITKAT_LESS = 0;
	/***
	 * 4.4以上(也就是kitkat以上)的版本,当然也包括最新出的5.0棒棒糖
	 */
	public static final int KITKAT_ABOVE = 1;
	/***
	 * 裁剪图片成功后返回
	 */
	public static final int INTENT_CROP = 2014;
	public static final int PHOTO_CAMEAR = 2015;

	private static AbPhotoUtils utils;

	public static AbPhotoUtils getInstance() {
		if (utils == null) {
			utils = new AbPhotoUtils();
		}
		return utils;
	}

	private static final String CROP_CACHE_FILE_NAME = "crop_cache_file.jpg";

	/***
	 * 选择一张图片 图片类型，这里是image/*，当然也可以设置限制 如：image/jpeg等
	 * 
	 * @param activity
	 *            Activity
	 */
	@SuppressLint("InlinedApi")
	public void selectPicture(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		// Intent intent = new Intent(Intent.ACTION_PICK,
		// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		if (Build.VERSION.SDK_INT < 19) {
			// Intent intent = new Intent();
			// intent.setType("image/*");
			// intent.setAction(Intent.ACTION_GET_CONTENT);
			// 由于startActivityForResult()的第二个参数"requestCode"为常量，
			// 个人喜好把常量用一个类全部装起来，不知道各位大神对这种做法有异议没？
			activity.startActivityForResult(intent, KITKAT_LESS);
		} else {
			// 由于Intent.ACTION_OPEN_DOCUMENT的版本是4.4以上的内容
			// 所以注意这个方法的最上面添加了@SuppressLint("InlinedApi")
			// 如果客户使用的不是4.4以上的版本，因为前面有判断，所以根本不会走else，
			// 也就不会出现任何因为这句代码引发的错误
			// intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			activity.startActivityForResult(intent, KITKAT_ABOVE);
		}
	}

	public Intent selectPicture() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		return intent;
	}

	public Intent selectCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri());
		return intent;
	}

	/***
	 * 裁剪图片
	 * 
	 * @param activity
	 *            Activity
	 * @param uri
	 *            图片的Uri
	 */
	public void cropPicture(Activity activity, Uri uri, int width) {
		Intent innerIntent = new Intent("com.android.camera.action.CROP");
		innerIntent.setDataAndType(uri, "image/*");
		innerIntent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
		innerIntent.putExtra("aspectX", 1); // 放大缩小比例的X
		innerIntent.putExtra("aspectY", 1);// 放大缩小比例的X 这里的比例为： 1:1
		innerIntent.putExtra("outputX", width); // 这个是限制输出图片大小
		innerIntent.putExtra("outputY", width);
		innerIntent.putExtra("return-data", false);
		innerIntent.putExtra("scale", true);
		innerIntent.putExtra("scaleUpIfNeeded", true); // 去掉黑边
		innerIntent.putExtra(MediaStore.EXTRA_OUTPUT, buildUri());
		activity.startActivityForResult(innerIntent, INTENT_CROP);
	}

	public Uri mCameraUri() {
		return Uri.fromFile(Environment.getExternalStorageDirectory()).buildUpon().appendPath("cache_file.jpg").build();
	}

	public Uri buildUri() {
		return Uri.fromFile(Environment.getExternalStorageDirectory()).buildUpon().appendPath(CROP_CACHE_FILE_NAME)
				.build();
		// return Uri.parse("file:///sdcard/temp.jpg");
	}

	public Bitmap decodeUriAsBitmap(Context context, Uri uri) {
		if (context == null || uri == null)
			return null;

		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
			int rote = getPicRotate(getRealFilePath(context, uri));
			if (rote > 0) {
				bitmap = rotaingImageView(rote,
						BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri)));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * 下面几个方法来自于stackoverflow,虽然来自大神，但大神的代码也不是就那样？ 看不懂的地方挨个百度。
	 * -----------------------割------------------------- Get a file path from a
	 * Uri. This will get the the path for Storage Access Framework Documents,
	 * as well as the _data field for the MediaStore and other file-based
	 * ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @author paulburke
	 */
	@SuppressLint("NewApi")
	public String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

	public boolean clearCachedCropFile(Uri uri) {
		if (uri == null)
			return false;

		File file = new File(uri.getPath());
		if (file.exists()) {
			boolean result = file.delete();
			if (result)
				Log.i(TAG, "Cached crop file cleared.");
			else
				Log.e(TAG, "Failed to clear cached crop file.");
			return result;
		} else {
			Log.w(TAG, "Trying to clear cached crop file but it does not exist.");
		}
		return false;
	}

	public boolean clearCachedCropFile() {
		File file = new File(buildUri().getPath());
		if (file.exists()) {
			boolean result = file.delete();
			if (result)
				Log.i(TAG, "Cached crop file cleared.");
			else
				Log.e(TAG, "Failed to clear cached crop file.");
			return result;
		} else {
			Log.w(TAG, "Trying to clear cached crop file but it does not exist.");
		}
		return false;
	}

	/**
	 * 获取图片文件的信息，是否旋转了90度，如果是则反转
	 * 
	 * @param bitmap
	 *            需要旋转的图片
	 * @param path
	 *            图片的路径
	 */
	public Bitmap reviewPicRotate(Bitmap bitmap, String path) {
		int degree = getPicRotate(path);
		if (degree != 0) {
			Matrix m = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			m.setRotate(degree); // 旋转angle度
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
		}
		return bitmap;
	}

	/**
	 * 读取图片文件旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return 图片旋转的角度
	 */
	public int getPicRotate(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * Try to return the absolute file path from the given Uri
	 *
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri, new String[] { ImageColumns.DATA }, null, null,
					null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

}