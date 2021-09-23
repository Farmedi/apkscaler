package iits.mamager;

import android.os.Environment;
import android.util.Log;
import iits.common.Constants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {
    private static final String TAG = "FileManager";

    /* JADX INFO: Multiple debug info for r0v4 long: [D('length' long), D('dir' java.io.File)] */
    /* JADX INFO: Multiple debug info for r0v8 long: [D('currentTime' long), D('txtwriter' java.io.FileWriter)] */
    /* JADX INFO: Multiple debug info for r2v4 java.util.Date: [D('date' java.util.Date), D('txtfile' java.io.File)] */
    public static void WriteLog(int level, String Tag, String error) {
        FileWriter txtwriter;
        if (level >= 3) {
            try {
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    File dir = new File(root + "/" + Constants.DIR_NAME_LOG);
                    if (dir.isDirectory() || dir.mkdirs()) {
                        File txtfile = new File(dir, Constants.FILE_NAME_LOG);
                        if (txtfile.length() > Constants.FILE_MAX_LENGTH) {
                            txtwriter = new FileWriter(txtfile, false);
                        } else {
                            txtwriter = new FileWriter(txtfile, true);
                        }
                        BufferedWriter out = new BufferedWriter(txtwriter);
                        Date date = new Date(System.currentTimeMillis());
                        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DD_MM_YYYY_HH_MM_SS);
                        StringBuilder builder = new StringBuilder();
                        builder.append("<br/> - ");
                        builder.append(formatter.format(date).toString());
                        builder.append(" | ");
                        switch (level) {
                            case 0:
                                builder.append("DEBUG | ");
                                break;
                            case 1:
                                builder.append("INFO | ");
                                break;
                            case 2:
                                builder.append("WARM | ");
                                break;
                            case 3:
                                builder.append("ERROR | ");
                                break;
                            case 4:
                                builder.append("FATAL | ");
                                break;
                        }
                        builder.append(String.valueOf(Tag) + " | ");
                        builder.append(error);
                        out.append((CharSequence) "<br/>");
                        out.append((CharSequence) builder.toString());
                        out.close();
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Could not write file " + e.getMessage());
            }
        }
    }
}
