package com.example.demo.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.example.core.util.IntentUtils;
import com.example.ext.dialog.MessageDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请
 */
public class Permission {
    private final List<String> mList;
    private final Activity mActivity;
    private static final int REQUEST_CODE = 1;
    private ApplyListener listener;

    public Permission(Activity activity) {
        this.mList = new ArrayList<>();
        mActivity = activity;
    }

    /**
     * 获取权限，如果权限中有未授权的权限，回调apply返回未授权的权限，
     * 在该方法中执行requestPermissions(String[] permission, requestCode);
     * 如果有被拒绝的权限，将提示跳转到对应的手机权限获取设置界面
     */
    public void applyPermission(String[] permission,@NotNull ApplyListener applyListener){
        this.listener = applyListener;
        String[] notApplyPermission = getNotApplyPermission(permission);
        String[] refuse = getRefuse(permission);
        if(notApplyPermission.length == 0) {
            applyListener.success();
        }else if(refuse.length != 0){
            new MessageDialog.Builder(mActivity)
                        .setTitle("开启权限")
                        .setMessage("权限未开启，请手动授予" + getPermissionHint(mList))
                        .setConfirm("去开启")
                        .setListener(dialog -> IntentUtils.gotoPermission(mActivity))
                        .show();
        }else{
            mActivity.requestPermissions(notApplyPermission,REQUEST_CODE);
        }
    }

    /**
     * 处理权限请求结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void handlerPermission(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == REQUEST_CODE){
            List<String> refuse = new ArrayList<>();
            for(int i:grantResults){
                int result = grantResults[i];
                if(result != PackageManager.PERMISSION_GRANTED) refuse.add(permissions[i]);
            }
            //有权限被拒绝
            if(refuse.size() != 0) {
                new MessageDialog.Builder(mActivity)
                        .setTitle("温馨提醒")
                        .setMessage("权限拒绝后某些功能将不能使用，为了使用完整功能请打开"+getPermissionHint(refuse))
                        .setConfirm("去开启")
                        .setListener(dialog -> IntentUtils.gotoPermission(mActivity))
                        .show();
                return;
            }
            listener.success();
        }
    }
    /**
     * 获取未授权的权限
     */
    public String[] getNotApplyPermission(String[] permission) {
        mList.clear();
        for (String p:permission) {
            if(!(ContextCompat.checkSelfPermission(mActivity, p) == PackageManager.PERMISSION_GRANTED)) mList.add(p);
        }
        return mList.toArray(new String[]{});
    }

    /**
     * 获取被拒绝的权限
     */
    public String[] getRefuse(String[] permission) {
        mList.clear();
        for (String p : permission) {
            if(mActivity.shouldShowRequestPermissionRationale(p)) mList.add(p);
        }
        return mList.toArray(new String[]{});
    }

    /**
     * 权限获取回调
     */
    public interface ApplyListener {
        //授权成功
        void success();
    }

    /**
     * 获取权限失败时得到相应权限的消息
     * @param permissions 权限组
     * @return 消息
     */
    public String getPermissionHint(List<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return "获取权限失败，请手动授予权限";
        }
        List<String> hints = new ArrayList<>();
        for (String permission : permissions) {
            switch (permission) {
                case Permissions.READ_EXTERNAL_STORAGE:
                case Permissions.WRITE_EXTERNAL_STORAGE:
                case Permissions.MANAGE_EXTERNAL_STORAGE: {
                    String hint = "存储权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.CAMERA: {
                    String hint = "相机权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.RECORD_AUDIO: {
                    String hint = "麦克风权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.ACCESS_FINE_LOCATION:
                case Permissions.ACCESS_COARSE_LOCATION:
                case Permissions.ACCESS_BACKGROUND_LOCATION: {
                    String hint;
                    if (!permissions.contains(Permissions.ACCESS_FINE_LOCATION) &&
                            !permissions.contains(Permissions.ACCESS_COARSE_LOCATION)) {
                        hint = "后台定位权限";
                    } else {
                        hint = "定位权限";
                    }
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.READ_PHONE_STATE:
                case Permissions.CALL_PHONE:
                case Permissions.ADD_VOICEMAIL:
                case Permissions.USE_SIP:
                case Permissions.READ_PHONE_NUMBERS:
                case Permissions.ANSWER_PHONE_CALLS: {
                    String hint = "电话权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.GET_ACCOUNTS:
                case Permissions.READ_CONTACTS:
                case Permissions.WRITE_CONTACTS: {
                    String hint = "通讯录权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.READ_CALENDAR:
                case Permissions.WRITE_CALENDAR: {
                    String hint = "日历权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.READ_CALL_LOG:
                case Permissions.WRITE_CALL_LOG:
                case Permissions.PROCESS_OUTGOING_CALLS: {
                    String hint = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ?
                            "通话记录权限" : "电话权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.BODY_SENSORS: {
                    String hint = "身体传感权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.ACTIVITY_RECOGNITION: {
                    String hint = "健身运动权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.SEND_SMS:
                case Permissions.RECEIVE_SMS:
                case Permissions.READ_SMS:
                case Permissions.RECEIVE_WAP_PUSH:
                case Permissions.RECEIVE_MMS: {
                    String hint = "短信权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.REQUEST_INSTALL_PACKAGES: {
                    String hint = "安装应用权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.NOTIFICATION_SERVICE: {
                    String hint = "通知栏权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.SYSTEM_ALERT_WINDOW: {
                    String hint = "悬浮窗权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                case Permissions.WRITE_SETTINGS: {
                    String hint = "系统设置权限";
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        if (!hints.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String text : hints) {
                if (builder.length() == 0) {
                    builder.append(text);
                } else {
                    builder.append("、")
                            .append(text);
                }
            }
            builder.append(" ");
            return builder.toString();
        }
        return "";
    }
}
