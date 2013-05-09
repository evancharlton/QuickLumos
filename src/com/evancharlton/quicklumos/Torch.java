
package com.evancharlton.quicklumos;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;

public class Torch extends Activity {
    public static PendingIntent get(Context context) {
        Intent intent = new Intent(context, LockHack.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private Camera mCamera;

    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.white);
        mSurfaceView = (SurfaceView) findViewById(R.id.fucking_samsung);

        findViewById(R.id.white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mCamera.setPreviewDisplay(holder);
                } catch (IOException e) {
                }
                Parameters p = mCamera.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(p);
                mCamera.startPreview();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.stopPreview();
        mCamera.release();
    }

    public static final class LockHack extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent torch = new Intent(context, Torch.class);
            torch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(torch);
        }
    }
}
