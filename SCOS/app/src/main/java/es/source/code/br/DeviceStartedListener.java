package es.source.code.br;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import es.source.code.service.UpdateService;
import es.source.code.utils.Global;

public class DeviceStartedListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
        Log.d(Global.TAG.SIX_TAG,intent.getAction());
        if (ACTION_BOOT.equals(intent.getAction()))
        {
            Intent intentService = new Intent(context, UpdateService.class);
            intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //避免重复打开
            context.startService(intentService);
        }else if (Global.CLOSE_NOTIFICATION.equals(intent.getAction())) {
            Log.d(Global.TAG.SIX_TAG,"进入第二个");
            NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context
                    .NOTIFICATION_SERVICE);
            notifyManager.cancel(intent.getIntExtra(Global.NOTIFICATION_ID, 0));
            Log.d(Global.TAG.SIX_TAG,Global.NOTIFICATION_ID);
            Log.d(Global.TAG.SIX_TAG,String.valueOf(intent.getIntExtra(Global.NOTIFICATION_ID, 0)));
        }
    }
}
