package androidx.fragment.app;


import android.content.Context;
import android.content.DialogInterface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jeremysun on 2019-09-23
 */
public class BaseDialogFragment extends DialogFragment {

    public boolean mIsForeground = false;

    private boolean mIsAttached = false;

    private long mLastShowTimeMillis = 0;
    private long mShowInterval = 1000;

    /**
     * Fix IllegalStateException : Can not perform this action after onSaveInstanceSate
     *
     * @param manager
     * @param tag
     */
    @Override
    public void show(@NotNull FragmentManager manager, String tag) {
        if (passShowInterval()) {
            mLastShowTimeMillis = System.currentTimeMillis();
            try {
                if (manager.isDestroyed()) {
                    return;
                }

                this.mDismissed = false;
                this.mShownByMe = true;
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(this, tag);
                ft.commitAllowingStateLoss(); // instead of ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean passShowInterval() {
        return System.currentTimeMillis() - mLastShowTimeMillis > mShowInterval;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mIsAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIsAttached = false;
    }

    public boolean isFragmentLive() {
        return isAdded() && mIsAttached && !isDetached() && getContext() != null;
    }

    @Override
    void dismissInternal(boolean allowStateLoss) {
        FragmentManager manager = this.getFragmentManager();
        if (manager == null || manager.isDestroyed()) {
            return;
        }
        if (!this.mDismissed) {
            this.mDismissed = true;
            this.mShownByMe = false;
            if (this.mDialog != null) {
                this.mDialog.dismiss();
            }

            this.mViewDestroyed = true;
            if (this.mBackStackId >= 0) {
                manager.popBackStack(this.mBackStackId, 1);
                this.mBackStackId = -1;
            } else {
                FragmentTransaction ft = manager.beginTransaction();
                ft.remove(this);
//                if (allowStateLoss) {
                ft.commitAllowingStateLoss(); // when dismiss, use ft.commitAllowingStateLoss instead of ft.commit
//                } else {
//                    ft.commit();
//                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsForeground = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsForeground = false;
    }

    @Override
    public void onDismiss(@Nullable DialogInterface dialog) {
        if (dialog != null) {
            super.onDismiss(dialog);
        }
    }
}
