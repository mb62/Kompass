package de.mb62.kompass;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link KompassWidgetConfigureActivity KompassWidgetConfigureActivity}
 */
public class KompassWidget extends AppWidgetProvider {

    public static String ACTION_WIDGET_CONFIGURE = "ConfigureWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            KompassWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = KompassWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.kompass_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Sets up the settings button to open the configuration activity
        Intent configIntent = new Intent(context, KompassWidgetConfigureActivity.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, appWidgetId, configIntent, 0);
        views.setOnClickPendingIntent(R.id.btnSettings, configPendingIntent);
        configIntent.setAction(ACTION_WIDGET_CONFIGURE + Integer.toString(appWidgetId));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

