package com.mwiacek.bryly;

import android.app.Dialog;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

public class MyGLSurfaceView extends GLSurfaceView {
    MyGLRenderer mRenderer;
    Dialog dialog = null;
    WebView webview;
    byte[] DisplayTotal;
    Context c;
    private float touchedX, touchedY;
    private final GestureDetector gestureDetector;

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        c = context;

        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchedX = event.getX();
            touchedY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mRenderer.xAngle += (touchedX - event.getX()) / 2f;
            mRenderer.xAngle = mRenderer.xAngle % 360;
            mRenderer.yAngle += (touchedY - event.getY()) / 2f;
            mRenderer.yAngle = mRenderer.yAngle % 360;
            touchedX = event.getX();
            touchedY = event.getY();
            requestRender();
        }
        return gestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (dialog == null) {
                dialog = new Dialog(c) {
                };
            }

            dialog.setContentView(R.layout.help);
            dialog.setCancelable(true);
            dialog.setTitle("Opis");

            webview = dialog.findViewById(R.id.webView1);

            webview.getSettings().setJavaScriptEnabled(false);
            webview.getSettings().setUseWideViewPort(false);
            webview.getSettings().setSupportZoom(true);
            webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webview.getSettings().setBuiltInZoomControls(true);
            //webview.getSettings().setAppCacheEnabled(false);

            try {
                InputStream stream = c.getAssets().open(mRenderer.spinner1.getSelectedItemPosition() + ".htm");
                DisplayTotal = new byte[stream.available()];
                stream.read(DisplayTotal, 0, DisplayTotal.length);
                int scale = (int) (100 * webview.getScale());
                webview.loadDataWithBaseURL("file:///android_asset/", new String(DisplayTotal, 0, DisplayTotal.length), "text/html", null, null);
                webview.setInitialScale(scale);
                stream.close();
            } catch (IOException ee) {
            }

            if (!dialog.isShowing()) {
                try {
                    dialog.show();
                } catch (Exception ee) {
                }
            }
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (dialog == null) {
                dialog = new Dialog(c) {
                };
            }

            dialog.setContentView(R.layout.help);
            dialog.setCancelable(true);
            dialog.setTitle("Opis");

            webview = dialog.findViewById(R.id.webView1);

            webview.getSettings().setJavaScriptEnabled(false);
            webview.getSettings().setUseWideViewPort(false);
            webview.getSettings().setSupportZoom(true);
            webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webview.getSettings().setBuiltInZoomControls(true);
            //webview.getSettings().setAppCacheEnabled(false);

            try {
                InputStream stream = c.getAssets().open(mRenderer.spinner1.getSelectedItemPosition() + ".htm");
                DisplayTotal = new byte[stream.available()];
                stream.read(DisplayTotal, 0, DisplayTotal.length);
                int scale = (int) (100 * webview.getScale());
                webview.loadDataWithBaseURL("file:///android_asset/", new String(DisplayTotal, 0, DisplayTotal.length), "text/html", null, null);
                webview.setInitialScale(scale);
                stream.close();
            } catch (IOException ee) {
            }


            if (!dialog.isShowing()) {
                try {
                    dialog.show();
                } catch (Exception ee) {
                }
            }
            return true;
        }
    }
}

