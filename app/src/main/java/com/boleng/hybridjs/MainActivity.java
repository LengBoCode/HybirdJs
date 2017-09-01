package com.boleng.hybridjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private WebView webContainer;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webContainer = (WebView) findViewById(R.id.webContainer);
        WebSettings settings = webContainer.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webContainer.addJavascriptInterface(new Js(), "android");
        webContainer.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    Log.i(TAG, "   " + newProgress);
//                    testMethods();
                }
            }
        });
        webContainer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

//                testMethods();

            }
        });

        webContainer.loadUrl("file:///android_asset/js.html");


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void testMethods() {
        String call1 = "javascript:sayHello()";
        String call2 = "javascript:saySomething("+232+")";
        webContainer.loadUrl(call2);
        webContainer.evaluateJavascript("sayHello()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_LONG).show();
            }
        });
    }

    class Js {
        @JavascriptInterface
        public void toastHello() {
            Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void toastSomething(String something) {
            Toast.makeText(MainActivity.this, something, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void toastSomething(int something) {
            Toast.makeText(MainActivity.this, "lengbo"+something, Toast.LENGTH_LONG).show();
        }
    }
}
