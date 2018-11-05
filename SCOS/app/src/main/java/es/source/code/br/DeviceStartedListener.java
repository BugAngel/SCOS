package es.source.code.br;

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
        if (ACTION_BOOT.equals(intent.getAction()))
        {
            Intent intentService = new Intent(context, UpdateService.class);
            intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(intentService);
            Log.d(Global.TAG.INTENT_SERVICE_TAG,"广播已打开");
        }
    }
}
