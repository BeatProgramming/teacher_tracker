package beatprogramming.github.com.teacker_tracker.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

/**
 * Clase para csv
 */
public class FileUtil {

    public static String getImagePath(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);

        Cursor cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int idx = 0;
        if (cursor != null) {
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        return cursor.getString(idx);
    }

}
