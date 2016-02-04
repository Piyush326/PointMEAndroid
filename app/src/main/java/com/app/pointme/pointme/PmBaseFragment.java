package com.app.pointme.pointme;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.pointme.pointme.databackend.DbHelper;
import com.app.pointme.pointme.networktask.PmServer;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by goparties on 30/1/16.
 */
public abstract class PmBaseFragment extends Fragment {


    private DbHelper dbHelper;

    public enum MODE {
        HOME, SEARCH, NEAR, TRENDING, DEALS, MASTER_CAL, MY_PROFILE, MY_PARTIES
    }

    MODE currentMode;

    public PmBaseActivity pmBaseActivity;

//    GpFragmentListener gpFragmentListener;

    ModeListener modeListener;

    public boolean isUpdateNecessary() {
        return false;
    }

    public interface ModeListener {
        void onModeChanged(MODE mode);
    }

    public abstract void onBackendConnected(DbHelper dbHelper);

    public PmBaseFragment() {
//
//        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

//    public int getAnimationType() {
//        return R.style.DialogAnimation;
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.getWindow().getAttributes().windowAnimations = getAnimationType();
//        return dialog;
//    }

    public View view;
    View progress;
    Object progressTag;

    public void setModeListener(ModeListener modeListener) {
        this.modeListener = modeListener;
    }

//    public void setGpFragmentListener(GpFragmentListener gpFragmentListener) {
//        this.gpFragmentListener = gpFragmentListener;
//    }

    /**
     * called when the fragment needs to update the UI
     */
    public void refreshUI() {

    }

    public boolean setCurrentMode(MODE mode) {
//        if (currentMode != null && currentMode == mode)
//            return true;
        currentMode = mode;
        return false;
    }

    public MODE getCurrentMode() {
        return this.currentMode;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbHelper = DbHelper.getInstance(pmBaseActivity);
        if (modeListener != null && getCurrentMode() != null) {
            modeListener.onModeChanged(getCurrentMode());
        }
        if (pmBaseActivity != null && isUpdateNecessary())
            pmBaseActivity.setHeading(getTitle());
    }

    private void makeBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            setArguments(bundle);
        }
    }

    public PmBaseFragment addBundleLong(String key, long value) {
        makeBundle();
        getArguments().putLong(key, value);
        return this;
    }

    public PmBaseFragment addBundleInt(String key, int value) {
        makeBundle();
        getArguments().putInt(key, value);
        return this;
    }

    public PmBaseFragment addBundleSting(String key, String value) {
        makeBundle();
        getArguments().putString(key, value);
        return this;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pmBaseActivity = (PmBaseActivity) activity;
    }

    public abstract int getLayoutId();

    public abstract void onLayoutCreated();

    public String getTitle() {
        return "";
    }

    public void showToast(Object object) {
        pmBaseActivity.showToast(object);
    }

    public void showApologies() {
        pmBaseActivity.showApologies();
    }

    public void showProgress(Object progressTag) {
        if (progress == null) {
            progress = View.inflate(pmBaseActivity, R.layout.progress, null);
            if (view instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) view;
                RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                lprams.addRule(RelativeLayout.CENTER_IN_PARENT);
                progress.setLayoutParams(lprams);
                relativeLayout.addView(progress);
            }
            progress.setOnClickListener(null);
        } else {
            progress.setVisibility(View.VISIBLE);
        }
        this.progressTag = progressTag;
    }

    public boolean isProgressing() {
        return progress != null && progress.getVisibility() == View.VISIBLE;
    }

    public boolean hideProgress() {
        pmBaseActivity.hideProgress();
        if (isProgressing()) {
           PmServer.getInstance().cancel(progressTag);
            progress.setVisibility(View.GONE);
            return true;
        } else
            return false;
    }

    @Override
    public void onStop() {
        super.onStop();
//        GpEventManager.getInstance().unSubscribe(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, view);
            onBackendConnected(DbHelper.getInstance(pmBaseActivity));
            onLayoutCreated();
        }
        return view;
    }

    @Nullable
    @OnClick(R.id.frag_close)
    public void close() {
        showToast("cancle");
    }


}
