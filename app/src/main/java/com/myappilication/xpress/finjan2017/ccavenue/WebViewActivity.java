package com.myappilication.xpress.finjan2017.ccavenue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.UserAlreadyCouponRes;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WebViewActivity extends Activity {
	private ProgressDialog dialog, dialog1;

	String html, encVal;
	Integer randomNum;

	SharedPreferences sharedpreferences;
	SharedPreferences.Editor editor;

	public static String status = null;

	String ccResponse;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_webview);

		sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();

		randomNum = ServiceUtility.randInt(0, 9999999);

		ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(WebViewActivity.this);
				View bView = getLayoutInflater().inflate(R.layout.custom_dialog1, null);
				dialogBuilder.setView(bView);
				dialogBuilder.setCancelable(false);

				TextView txt1 = (TextView) bView.findViewById(R.id.dialog_text1);
				TextView txtCancel = (TextView) bView.findViewById(R.id.cancel_btn1);
				TextView txtOk = (TextView) bView.findViewById(R.id.ok_btn1);

				txt1.setText("Do you want to close this process?");

				final AlertDialog al = dialogBuilder.create();

				txtCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						al.dismiss();
					}
				});

				txtOk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});


				al.show();


			}
		});

		//webService("dflsdjafeka");
		
		// Calling async task to get display content
		new RenderView().execute();
	}
	
	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class RenderView extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			dialog = new ProgressDialog(WebViewActivity.this);
			dialog.setMessage("Please wait...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("access_code",
					StaticConfig.ACCESS_CODE));
			params.add(new BasicNameValuePair("order_id", randomNum.toString()));
	
			String vResponse = sh.makeServiceCall(StaticConfig.RSA_KEY_URL,
					ServiceHandler.POST, params);
			System.out.println(vResponse);
			if(!ServiceUtility.chkNull(vResponse).equals("") 
					&& ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR")==-1){
				StringBuffer vEncVal = new StringBuffer("");

				String bAmount = sharedpreferences.getString("buymoduleamount", "");

				if(bAmount.length()>0&&
						bAmount!=null){
					vEncVal.append(ServiceUtility.addToPostParams("amount",
							bAmount));
					vEncVal.append(ServiceUtility.addToPostParams("currency",
							"INR"));
				}

				encVal = RSAUtility.encrypt(vEncVal.substring(0,vEncVal.length()-1), vResponse);
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			
			@SuppressWarnings("unused")
			class MyJavaScriptInterface
			{
				@JavascriptInterface
			    public void processHTML(String html)
			    {
			        // process the html as needed by the app

					if(html.length()>0&&html!=null){
						if(html.indexOf("Failure")!=-1){
							status = "Sorry, Your transaction declined!";
						}else if(html.indexOf("Success")!=-1){
							status = "Thank you, Your transaction successfully completed!";
						}else if(html.indexOf("Aborted")!=-1){
							status = "Sorry, Your transaction cancelled!";
						}else if(html.indexOf("order_id")!=-1){
							status = "Sorry, Your status not known!";
						}else if(html.indexOf("status_message")!=-1){
							status = "Status Not Known!";
						}
						ccResponse = Jsoup.parse(html).text();

						webService(ccResponse);
					}else{
						if(dialog.isShowing()){

						}
					}



					//Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

			    	/*Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
					intent.putExtra("transStatus", status);
					startActivity(intent);*/
			    }
			}
			
			final WebView webview = (WebView) findViewById(R.id.webview);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

			//if (dialog.isShowing())

			webview.setWebViewClient(new WebViewClient(){
				@Override
	    	    public void onPageFinished(WebView view, String url) {
	    	        super.onPageFinished(webview, url);
	    	        if(url.indexOf("/ccavResponseHandler.php")!=-1){
						//finish();
	    	        	webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

						try{
							Dialog loadingDialog = new Dialog(WebViewActivity.this,
									android.R.style.Theme_Black_NoTitleBar);
							loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							loadingDialog.setContentView(R.layout.ccavenue_progressbar);
							/*loadingDialog.getWindow().setBackgroundDrawable(new
									ColorDrawable(android.graphics.Color.TRANSPARENT));*/

							loadingDialog.setCancelable(false);
							loadingDialog.show();

						}catch (Exception e){

						}
	    	           }
					dialog.dismiss();


	    	    }  

	    	    @Override
	    	    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

	    	        Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
					webService(description);
					status = description;
					if(dialog.isShowing()) {
						dialog.dismiss();

					}
	    	    }
			});
			
			/* An instance of this class will be registered as a JavaScript interface */
			StringBuffer params = new StringBuffer();
			params.append(ServiceUtility.addToPostParams("access_code", StaticConfig.ACCESS_CODE));
			params.append(ServiceUtility.addToPostParams("merchant_id", StaticConfig.MERCHANT_ID));
			params.append(ServiceUtility.addToPostParams("order_id", randomNum.toString()));
			params.append(ServiceUtility.addToPostParams("redirect_url", StaticConfig.REDIRECT_URL));
			params.append(ServiceUtility.addToPostParams("cancel_url", StaticConfig.CANCEL_URL));
			params.append(ServiceUtility.addToPostParams("enc_val", URLEncoder.encode(encVal)));
			
			String vPostParams = params.substring(0,params.length()-1);
			try {
				webview.postUrl(StaticConfig.TRANS_URL, EncodingUtils.getBytes(vPostParams, "UTF-8"));
			} catch (Exception e) {
				showToast("Exception occured while opening webview.");
			}
		}
	}

	private void webService(final String html) {

		String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken,"");
		//String kk = "order_id=6340595|tracking_id=106281752842|bank_ref_no=null|order_status=Aborted|failure_message=|payment_mode=null|card_name=null|status_code=|status_message=I have second thoughts about making this payment|currency=INR|amount=1.0|billing_name=|billing_address=|billing_city=|billing_state=|billing_zip=|billing_country=|billing_tel=|billing_email=|delivery_name=|delivery_address=|delivery_city=|delivery_state=|delivery_zip=|delivery_country=|delivery_tel=|merchant_param1=|merchant_param2=|merchant_param3=|merchant_param4=|merchant_param5=|vault=N|offer_type=null|offer_code=null|discount_value=0.0|mer_amount=1.0|eci_value=|retry=null|response_code=|billing_notes= |trans_date=null|bin_country= ";

		RxClient.get(getApplicationContext()).ccavenueres(token, new CcavenueReq(html),
				new Callback<CcavenueResponse>() {
			@Override
			public void success(CcavenueResponse ccavenueResponse, Response response) {

				Log.d("","");

				showToast(ccavenueResponse.getResult());
				finish();
				//dialog1.dismiss();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.d("","");

				try{
					CcavenueResponse usere = (CcavenueResponse)
							error.getBodyAs(CcavenueResponse.class);

					if(usere.getStatus().equalsIgnoreCase("402")){
						mtd_refresh_token(html);
					}
				}catch (Exception e){
					finish();
					//dialog1.dismiss();
				}
			}
		});

	}


	private void mtd_refresh_token(final String html) {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
		RxClient.get(WebViewActivity.this).Login(new loginreq(sharedpreferences.
				getString(SharedPrefUtils.SpEmail, ""),
				sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
			@Override
			public void success(loginresp loginresp, Response response) {

				if (loginresp.getStatus().equals("200")){

					editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());
					editor.commit();

					final Handler handler = new Handler();
					final Runnable runnable = new Runnable() {
						@Override
						public void run() {
							webService(html);
						}
					};
					handler.postDelayed(runnable, 500);

				}
			}

			@Override
			public void failure(RetrofitError error) {
				//progressBar.setVisibility(View.INVISIBLE);
				Log.d("refresh token", "refresh token error");
				Toast.makeText(WebViewActivity.this, "Service not response",
						Toast.LENGTH_LONG).show();
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {

	}

	public void showToast(String msg) {
		//Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}
} 