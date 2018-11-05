package es.source.code.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

//原网站https://itimetraveler.github.io/2017/05/03/%E3%80%90Android%E3%80%91%E5%88%A4%E6%96%AD%E5%BA%94%E7%94%A8Application%E3%80%81Activity%E6%98%AF%E5%90%A6%E5%A4%84%E4%BA%8E%E6%B4%BB%E5%8A%A8%E7%8A%B6%E6%80%81/
public class AliveUtil {
    /**
     * 判断进程是否正在运行
     * @param context 一个context
     * @param processName 要判断的进程
     * @return boolean
     */
    public boolean isProcessRunning(Context context, String processName) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
            if (processName.equals(info.processName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 判断Activity是否活动
     * @param context 一个context
     * @param activityName 要判断Activity
     * @return boolean
     */
    public boolean isMainActivityAlive(Context context, String activityName){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            // 注意这里的 topActivity 包含 packageName和className，可以打印出来看看
            if (info.topActivity.toString().equals(activityName) || info.baseActivity.toString().equals(activityName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测某Activity是否在当前Task的栈顶
     */
    public boolean isTopActivity(Context context,String activityName){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if(runningTaskInfos != null){
            cmpNameTemp = runningTaskInfos.get(0).topActivity.toString();
        }
        if(cmpNameTemp == null){
            return false;
        }
        return cmpNameTemp.equals(activityName);
    }

    /**
     * 用来判断服务是否运行.
     * @param context
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context,String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        //此处只在前30个中查找，大家根据需要调整
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
