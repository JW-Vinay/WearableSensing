package com.wearables.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wearables.R;

public class WebViewActivity extends Activity
{
	private WebView mWebView;
	private int mId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_layout);
	
		if(getIntent().getExtras() != null)
		mWebView = (WebView) findViewById(R.id.webView);
		String url = getIntent().getStringExtra("url");
		mId = getIntent().getIntExtra("id", -1);
		mWebView.getSettings().setBuiltInZoomControls(true);
		
		mWebView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.startsWith("http://codingthecrowd"))
				{					
					//extract the code to send back
					String code=(url.split("=")[1]);
					Intent intent = new Intent();
					intent.putExtra("code", code);
					intent.putExtra("id", mId);
					setResult(RESULT_OK, intent);
					finish();
					
				}
	            
				return super.shouldOverrideUrlLoading(view, url);
			}
			
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
//				flag = true;
			}
			
			
		});
		
		mWebView.loadUrl(url);
		

	}
}
