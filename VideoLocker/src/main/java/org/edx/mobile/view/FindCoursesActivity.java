package org.edx.mobile.view;

import android.os.Bundle;
import android.webkit.WebView;

import com.google.inject.Inject;

import org.edx.mobile.R;
import org.edx.mobile.base.FindCoursesBaseActivity;
import org.edx.mobile.logger.Logger;
import org.edx.mobile.model.api.EnrolledCoursesResponse;
import org.edx.mobile.module.analytics.ISegment;

import roboguice.inject.ContentView;

@ContentView(R.layout.activity_find_courses)
public class FindCoursesActivity extends FindCoursesBaseActivity {

    private WebView webView;

    static public String TAG = FindCoursesActivity.class.getCanonicalName();
    static public String ENROLLMENT = TAG + ".enrollment";

    protected final Logger logger = new Logger(getClass().getName());

    @Inject
    ISegment segIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getArguments();
        EnrolledCoursesResponse courseData = (EnrolledCoursesResponse) bundle
                .getSerializable(ENROLLMENT);

        // configure slider layout. This should be called only once and
        // hence is shifted to onCreate() function
        configureDrawer();




        environment.getSegment().trackScreenView(ISegment.Screens.FIND_COURSES);


        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(environment.getConfig().getEnrollmentConfig().getCourseSearchUrl());
    }

    @Override
    protected void onOnline() {
        super.onOnline();
        if (!isWebViewLoaded()) {
            webView.reload();
        }
    }
}
