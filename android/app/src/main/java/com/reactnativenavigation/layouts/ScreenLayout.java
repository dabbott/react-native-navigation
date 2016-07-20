package com.reactnativenavigation.layouts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.Window;
import android.widget.LinearLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.ScreenStyleParams;
import com.reactnativenavigation.utils.SdkSupports;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.ScrollDirectionListener;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ScreenLayout extends LinearLayout implements ScrollDirectionListener.OnScrollChanged {

    private final ScreenParams screenParams;
    private ContentView contentView;
    private TopBar topBar;

    public ScreenLayout(Context context, ScreenParams screenParams) {
        super(context);
        this.screenParams = screenParams;
        setOrientation(VERTICAL);

        createViews();
        setStyle(screenParams.styleParams);
    }

    private void createViews() {
        addTopBar();
        topBar.addTitleBarAndSetButtons(screenParams.buttons);
        topBar.setTitle(screenParams.title);
        addContentView();
    }

    private void addTopBar() {
        topBar = new TopBar(getContext());
        addView(topBar, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
    }

    private void addContentView() {
        contentView = new ContentView(getContext(), screenParams.screenId, screenParams.passProps, this);
        addView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        contentView.init();
    }

    private void setStyle(ScreenStyleParams styleParams) {
        setStatusBarColor(styleParams.statusBarColor);
        setTopBarColor(styleParams.topBarColor);
        setNavigationBarColor(styleParams.navigationBarColor);
        topBar.setTitleBarVisibility(styleParams.titleBarHidden);
        topBar.setVisibility(styleParams.topBarHidden ? GONE : VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(@ColorInt int statusBarColor) {
        if (!SdkSupports.lollipop()) {
            return;
        }

        final Activity context = (Activity) getContext();
        final Window window = context.getWindow();
        if (statusBarColor > 0) {
            window.setStatusBarColor(statusBarColor);
        } else {
            window.setStatusBarColor(Color.BLACK);
        }
    }

    private void setTopBarColor(@ColorInt int topBarColor) {
        if (topBarColor > 0) {
            topBar.setBackgroundColor(topBarColor);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setNavigationBarColor(int navigationBarColor) {
        if (!SdkSupports.lollipop()) {
            return;
        }

        final Activity context = (Activity) getContext();
        final Window window = context.getWindow();
        if (navigationBarColor > 0) {
            window.setNavigationBarColor(navigationBarColor);
        } else {
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    @Override
    public void onScrollChanged(ScrollDirectionListener.Direction direction) {

    }
}