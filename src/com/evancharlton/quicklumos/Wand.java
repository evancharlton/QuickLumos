
package com.evancharlton.quicklumos;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

public class Wand extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            draw(context, appWidgetManager, appWidgetId, true);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId, Bundle newOptions) {
        draw(context, appWidgetManager, appWidgetId, true);
    }

    public static void draw(Context context, boolean turnOn) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName self = new ComponentName(context, Wand.class);
        for (int appWidgetId : appWidgetManager.getAppWidgetIds(self)) {
            draw(context, appWidgetManager, appWidgetId, turnOn);
        }
    }

    private static void draw(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
            boolean turnOn) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
        rv.setOnClickPendingIntent(R.id.white, Torch.get(context));
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }
}
