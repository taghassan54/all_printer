
package com.example.all_printer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.RemoteException;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View.MeasureSpec;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mobiiot.androidqapi.api.MobiiotAPI;
import com.mobiiot.androidqapi.api.Utils.PrinterServiceUtil;
import com.mobiiot.androidqapi.api.Utils.ServiceUtilIOPrint;
import com.mobiiot.androidqapi.api.Utils.Utils;
import com.mobiwire.printraw.PrintIOInterface;
import com.sagereal.printer.PrinterInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class CsPrinterQ {
    public CsPrinterQ() {
    }

    public static boolean printText(String data) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print text");
                return printInterfaceService.printText_r(data);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static boolean printText_isBold_r(String data, boolean isBold) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print text bold");
                return printInterfaceService.printText_bold_r(data, isBold);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean printText_isUnderline(String data, boolean isUnderline) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print text undeline");
                return printInterfaceService.printText_underline_r(data, isUnderline);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean printText_FullParm(String data, int textSize, int textDirectiion, int textFont, int alignment, boolean isBold, boolean isUnderline) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print text full param");
                return printInterfaceService.printText_FullParam_r(data, textSize, textDirectiion, textFont, alignment, isBold, isUnderline);
            }
        } catch (RemoteException var8) {
            var8.printStackTrace();
            return false;
        }
    }

    public static int getPrinterStatus() {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return -1;
            } else {
                int value = printInterfaceService.getPrinterStatus();
                Utils.log(MobiiotAPI.TAG, "printer status : " + value);
                return value;
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return -1;
        }
    }

    public static int getCurrentVoltageStatus() {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return -1;
            } else {
                int value = printInterfaceService.getCurrentVoltageStatus();
                Utils.log(MobiiotAPI.TAG, "current voltage status : " + value);
                return value;
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return -1;
        }
    }

    public static boolean printText_size(String data, int textSize) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print text size");
                return printInterfaceService.printText_size_r(data, textSize);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean printText_font(String data, int textFont) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print text font");
                return printInterfaceService.printText_font_r(data, textFont);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean printText_size_font(String data, int textSize, int textFont) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print text font");
                return printInterfaceService.printText_sizefont_r(data, textSize, textFont);
            }
        } catch (RemoteException var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public static boolean printBitmap(String filePath) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print bitmap");
                return printInterfaceService.printBitmap_path_r(filePath);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static boolean printBitmap(String filePath, int speed) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print bitmap");
                return printInterfaceService.printBitmap_path_speed_r(filePath, speed);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean printBitmap(byte[] byt) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print bitmap byte");
                return printInterfaceService.printBitmap_bytes_r(byt);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static boolean printBitmap(byte[] byt, int speed) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print bitmap byte with speed");
                return printInterfaceService.printBitmap_bytes_speed_r(byt, speed);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean printBitmap(Bitmap bitmap) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print bitmap bitmap");
                return printInterfaceService.printBitmap_btm_r(bitmap);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static boolean printBitmap(Bitmap bitmap, int speed) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print bitmap bitmap with speed");
                return printInterfaceService.printBitmap_btm_speed_r(bitmap, speed);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean printEndLine() {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                Utils.log(MobiiotAPI.TAG, "print end line");
                return printInterfaceService.printEndLine_r();
            }
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return false;
        }
    }

    @SuppressLint("WrongConstant")
    public static void printTextHTML(final Context context, String html) {
        Utils.log("ismail", "printTextFormat");
        TextView view = new TextView(context);
        view.setTextColor(-16777216);
        view.setBackgroundColor(-1);
        view.setText(Html.fromHtml(html, new ImageGetter() {
            public Drawable getDrawable(String source) {
                int dourceId = context.getResources().getIdentifier(source, "drawable", context.getPackageName());
                Drawable drawable = context.getResources().getDrawable(dourceId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        }, (TagHandler)null));
        view.setDrawingCacheEnabled(true);
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
        Bitmap b2 = Bitmap.createBitmap(384, 384, Config.ARGB_8888);
        Canvas canvas = new Canvas(b2);
        canvas.drawColor(-1);
        canvas.drawBitmap(getResizedBitmap(b, b.getWidth(), 384), 0.0F, 0.0F, (Paint)null);
        view.setDrawingCacheEnabled(false);
        Bitmap bitm = getResizedBitmap(b2, 384, 240);
        printBitmap(bitm);
    }

    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int ch;
        while((ch = is.read(buffer)) != -1) {
            bytestream.write(buffer, 0, ch);
        }

        byte[] data = bytestream.toByteArray();
        bytestream.close();
        return data;
    }

    public static byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = (float)newWidth / (float)width;
        float scaleHeight = (float)newHeight / (float)height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static File savebitmap(Bitmap bmp,Context context) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, bytes);
        File f = new File(context.getExternalFilesDir(String.valueOf(Environment.getRootDirectory())) + "/test1.bmp");
        f.mkdir();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

    public Bitmap drawText(String text, String textTwo, int textSize, boolean isBold, boolean isUnderline, int align, Typeface ttf) {
        int textWidth = 384;
        if (textTwo != null) {
            int llen = 20;
            int rlen = 20;
            String text1 = this.padRight(text, rlen);
            String text2 = this.padLeft(textTwo, llen);
            text = text1 + text2;
        }

        TextPaint textPaint = new TextPaint(65);
        textPaint.setStyle(Style.FILL);
        textPaint.setColor(-16777216);
        textPaint.setTextAlign(Align.LEFT);
        textPaint.setTextSize((float)textSize);
        textPaint.setFakeBoldText(isBold);
        textPaint.setTypeface(ttf);
        if (isUnderline) {
//            textPaint.setFlags(8);
        }

        StaticLayout mTextLayout = new StaticLayout(text, textPaint, textWidth, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        if (align == 0) {
            mTextLayout = new StaticLayout(text, textPaint, textWidth, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        } else if (align == 1) {
            mTextLayout = new StaticLayout(text, textPaint, textWidth, Alignment.ALIGN_CENTER, 1.0F, 0.0F, false);
        } else if (align == 2) {
            mTextLayout = new StaticLayout(text, textPaint, textWidth, Alignment.ALIGN_OPPOSITE, 1.0F, 0.0F, false);
        }

        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Config.RGB_565);
        Canvas c = new Canvas(b);
        Paint paint = new Paint(65);
        paint.setStyle(Style.FILL);
        paint.setColor(-1);
        c.drawPaint(paint);
        c.save();
        c.translate(0.0F, 0.0F);
        mTextLayout.draw(c);
        c.restore();
        return b;
    }

    public String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    private Bitmap createSingleImageFromMultipleImages(ArrayList<Bitmap> listBitmap) {
        int hight = 0;

        for(int i = 0; i < listBitmap.size(); ++i) {
            hight += ((Bitmap)listBitmap.get(i)).getHeight();
        }

        Bitmap result = Bitmap.createBitmap(((Bitmap)listBitmap.get(0)).getWidth(), hight, ((Bitmap)listBitmap.get(0)).getConfig());
        Canvas canvas = new Canvas(result);
        int hightToDraw = 0;

        for(int i = 0; i < listBitmap.size(); ++i) {
            canvas.drawBitmap((Bitmap)listBitmap.get(i), 0.0F, (float)hightToDraw, (Paint)null);
            hightToDraw += ((Bitmap)listBitmap.get(i)).getHeight();
        }

        return result;
    }

    public static Bitmap createBarQrCode(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
        BitMatrix matrix = null;

        try {
            matrix = (new MultiFormatWriter()).encode(str, type, bmpWidth, bmpHeight);
        } catch (WriterException var10) {
            var10.printStackTrace();
        }

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = -16777216;
                } else {
                    pixels[y * width + x] = -1;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static boolean getPaperStatus() {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                int printerStatus = printInterfaceService.getPrinterStatus();
                Utils.log(MobiiotAPI.TAG, "printer status : " + printerStatus);
                if (printerStatus != 1 && printerStatus != 0 && printerStatus != 16 && printerStatus != 17) {
                    Utils.log(MobiiotAPI.TAG, "The printer status is KO, you can restart the device!");
                    return false;
                } else if (printerStatus != 1 && printerStatus != 17) {
                    return printerStatus != 16 && printerStatus != 0 ? false : false;
                } else {
                    return true;
                }
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static boolean getTempStatus() {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return false;
            } else {
                int printerStatus = printInterfaceService.getPrinterStatus();
                Utils.log(MobiiotAPI.TAG, "printer status : " + printerStatus);
                if (printerStatus != 1 && printerStatus != 0 && printerStatus != 16 && printerStatus != 17) {
                    Utils.log(MobiiotAPI.TAG, "The printer status is KO, you can restart the device!");
                    return false;
                } else if (printerStatus == 1) {
                    return true;
                } else {
                    return printerStatus != 17 && printerStatus != 16 ? false : false;
                }
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static void printSetDarkness(int darkness) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
            } else {
                Utils.log(MobiiotAPI.TAG, "printSetDarkness : " + darkness);
                printInterfaceService.printSetDarkness(darkness);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

    }

    public static int printGetPrintedLength() {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return -1;
            } else {
                Utils.log(MobiiotAPI.TAG, "printGetPrintedLength");
                return printInterfaceService.printGetPrintedLength();
            }
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return -1;
        }
    }

    public static Boolean printText_inverse_r(String data, boolean isInverse) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return null;
            } else {
                Utils.log(MobiiotAPI.TAG, "printGetPrintedLength");
                return printInterfaceService.printText_inverse_r(data, isInverse);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Boolean printText_FullParam2_r(String data, int textSize, int textDirection, int textFont, int alignment, boolean isBold, boolean isUnderline, boolean isInverse) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return null;
            } else {
                Utils.log(MobiiotAPI.TAG, "printText_FullParam2_r");
                return printInterfaceService.printText_FullParam2_r(data, textSize, textDirection, textFont, alignment, isBold, isUnderline, isInverse);
            }
        } catch (RemoteException var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public static void printText_FullParam(String data, int textSize, int textDirection, int textFont, int alignment, boolean isBold, boolean isUnderline) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
            } else {
                Utils.log(MobiiotAPI.TAG, "printText_FullParam2_r");
                printInterfaceService.printText_FullParm(data, textSize, textDirection, textFont, alignment, isBold, isUnderline);
            }
        } catch (RemoteException var8) {
            var8.printStackTrace();
        }

    }

    public static Boolean printSetSyncMode(boolean sync) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return null;
            } else {
                Utils.log(MobiiotAPI.TAG, "printSetSyncMode");
                return printInterfaceService.printSetSyncMode(sync);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static int getLastError() {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return -1;
            } else {
                Utils.log(MobiiotAPI.TAG, "printText_FullParam2_r");
                return printInterfaceService.getLastError();
            }
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return -1;
        }
    }

    public static byte[] transmit(byte[] sendBuf, int sendLen) {
        try {
            PrintIOInterface printInterfaceService = ServiceUtilIOPrint.getiMyAidlInterface();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return null;
            } else {
                Utils.log(MobiiotAPI.TAG, "transmit");
                return printInterfaceService.transmit(sendBuf, sendLen);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Boolean getPowerState() {
        try {
            PrintIOInterface printInterfaceService = ServiceUtilIOPrint.getiMyAidlInterface();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
                return null;
            } else {
                Utils.log(MobiiotAPI.TAG, "getPowerState");
                return printInterfaceService.getPowerState();
            }
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static void printBitmapMPE(byte[] byt, int speed) {
        try {
            PrinterInterface printInterfaceService = PrinterServiceUtil.getPrinterService();
            if (printInterfaceService == null) {
                Utils.log(MobiiotAPI.TAG, "service printer is KO");
            } else {
                Utils.log(MobiiotAPI.TAG, "print bitmap byte with speed");
                printInterfaceService.printBitmap_bDate_speed(byt, speed);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }

    }
}
