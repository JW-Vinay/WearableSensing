package com.wearables;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity
{
	private WebView mWebView;
	private String mUrl = "";
	private boolean flag= false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_layout);
	
		if(getIntent().getExtras() != null)
		mWebView = (WebView) findViewById(R.id.webView);
		String url = getIntent().getStringExtra("url");
		mWebView.getSettings().setBuiltInZoomControls(true);
		
		mWebView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if(url.startsWith("http://codingthecrowd"))
				{					
					//extract the code to send back
					String code=(url.split("=")[1]);
					Intent intent = new Intent();
					intent.putExtra("code", code);
					setResult(RESULT_OK, intent);
					finish();
					
				}
//				URL aURL;
//				try {
//					aURL = new URL(url);
//					URLConnection conn = aURL.openConnection(); 
//		            conn.connect(); 
//		            InputStream is = conn.getInputStream();
//		            int b;
//		            System.out.println("print startd");
//		            while((b = is.read()) != -1){
//		            	System.out.println(b);
//		            }
//		            System.out.println("print end");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
	            
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
