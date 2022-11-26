package ru.mirea.mobilefront.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.mobilefront.R;

public class MenuFragment extends Fragment {

    WebView webPageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater.inflate(R.layout.fragment_menu,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        webPageView = view.findViewById(R.id.web_view_page);
        webPageView.setWebViewClient(new WebViewClient());
        webPageView.setBackgroundResource(R.drawable.log_back_gradient);
        webPageView.getSettings().setJavaScriptEnabled(true);
        webPageView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url)
            {
                // hide element by class name
                webPageView.loadUrl("javascript:(function() " +
                        "{ document.getElementsByClassName('popmechanic-android')[0].style.display='none'; })()");
                webPageView.loadUrl("javascript:(function() " +
                        "{ document.getElementsByClassName('journal-page__container')[0].style.display='inline'; })()");
            }
        });
        webPageView.loadUrl("https://book24.ru/journal/");
    }
}