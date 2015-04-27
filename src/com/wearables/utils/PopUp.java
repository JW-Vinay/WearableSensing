package com.wearables.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wearables.R;

@SuppressLint("SetJavaScriptEnabled")
public class PopUp extends Dialog implements android.view.View.OnClickListener
{

	private Context			mContext				= null;
	private PopUpListener	mListener				= null;

	private TextView		mDislayText				= null;
	private Button			positiveButton			= null;
	private Button			negativeButton			= null;
	private Button			neutralButton			= null;

	private String			mdisplayStr				= null;
	private String			positiveBtnText			= null;
	private String			negativeBtnText			= null;
	private String			neutralBtnText			= null;

	private boolean			isNegativeBtnVisible	= true;
	private boolean			isNeutralBtnVisible		= true;

	/**
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 */
	public PopUp(Context mContext, PopUpListener mListener, String mdisplayStr, String positiveBtnText)
	{
		super(mContext);
		this.mContext = mContext;
		this.mListener = mListener;
		this.mdisplayStr = mdisplayStr;
		this.positiveBtnText = positiveBtnText;
		isNegativeBtnVisible = false;
		isNeutralBtnVisible = false;
	}

	/**
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 */
	public PopUp(Context mContext, PopUpListener mListener, String mdisplayStr, int positiveBtnText)
	{
		super(mContext);
		this.mContext = mContext;
		this.mListener = mListener;
		Resources res = mContext.getResources();
		this.mdisplayStr = mdisplayStr;
		this.positiveBtnText = res.getString(positiveBtnText);
		isNegativeBtnVisible = false;
		isNeutralBtnVisible = false;
	}

	/**
	 * @param context
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 */
	public PopUp(Context mContext, PopUpListener mListener, int mdisplayStr, int positiveBtnText)
	{
		super(mContext);
		this.mContext = mContext;
		this.mListener = mListener;
		Resources res = mContext.getResources();
		this.mdisplayStr = res.getString(mdisplayStr);
		this.positiveBtnText = res.getString(positiveBtnText);
		isNegativeBtnVisible = false;
		isNeutralBtnVisible = false;
	}

	/**
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 * @param negativeBtnText
	 */
	public PopUp(Context mContext, PopUpListener mListener, String mdisplayStr, int positiveBtnText, int negativeBtnText)
	{
		super(mContext);
		this.mContext = mContext;
		this.mListener = mListener;
		Resources res = mContext.getResources();
		this.mdisplayStr = mdisplayStr;
		this.positiveBtnText = res.getString(positiveBtnText);
		this.negativeBtnText = res.getString(negativeBtnText);
		isNeutralBtnVisible = false;
	}

	/**
	 * @param context
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 * @param negativeBtnText
	 */
	public PopUp(Context mContext, PopUpListener mListener, int mdisplayStr, int positiveBtnText, int negativeBtnText)
	{
		super(mContext);
		this.mContext = mContext;
		this.mListener = mListener;
		Resources res = mContext.getResources();
		this.mdisplayStr = res.getString(mdisplayStr);
		this.positiveBtnText = res.getString(positiveBtnText);
		this.negativeBtnText = res.getString(negativeBtnText);
		isNeutralBtnVisible = false;
	}

	/**
	 * @param context
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 * @param negativeBtnText
	 */
	public PopUp(Context context, Context mContext, PopUpListener mListener, String mdisplayStr, String positiveBtnText, String negativeBtnText)
	{
		super(context);
		this.mContext = mContext;
		this.mListener = mListener;
		this.mdisplayStr = mdisplayStr;
		this.positiveBtnText = positiveBtnText;
		this.negativeBtnText = negativeBtnText;
		isNeutralBtnVisible = false;
	}

	/**
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 * @param neutralBtnText
	 * @param negativeBtnText
	 */
	public PopUp(Context mContext, PopUpListener mListener, int mdisplayStr, int positiveBtnText, int neutralBtnText, int negativeBtnText)
	{
		super(mContext);
		this.mContext = mContext;
		this.mListener = mListener;
		Resources res = mContext.getResources();
		this.mdisplayStr = res.getString(mdisplayStr);
		this.positiveBtnText = res.getString(positiveBtnText);
		this.negativeBtnText = res.getString(negativeBtnText);
		this.neutralBtnText = res.getString(neutralBtnText);
	}

	/**
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 * @param neutralBtnText
	 * @param negativeBtnText
	 */
	public PopUp(Context mContext, PopUpListener mListener, String mdisplayStr, int positiveBtnText, int neutralBtnText, int negativeBtnText)
	{
		super(mContext);
		this.mContext = mContext;
		this.mListener = mListener;
		Resources res = mContext.getResources();
		this.mdisplayStr = mdisplayStr;
		this.positiveBtnText = res.getString(positiveBtnText);
		this.negativeBtnText = res.getString(negativeBtnText);
		this.neutralBtnText = res.getString(neutralBtnText);
	}

	/**
	 * @param context
	 * @param mContext
	 * @param mListener
	 * @param mdisplayStr
	 * @param positiveBtnText
	 * @param neutralBtnText
	 * @param negativeBtnText
	 */
	public PopUp(Context context, Context mContext, PopUpListener mListener, String mdisplayStr, String positiveBtnText, String neutralBtnText,
			String negativeBtnText)
	{
		super(context);
		this.mContext = mContext;
		this.mListener = mListener;
		this.mdisplayStr = mdisplayStr;
		this.positiveBtnText = positiveBtnText;
		this.negativeBtnText = negativeBtnText;
		this.neutralBtnText = neutralBtnText;
	}

	/**
	 * @param context
	 */
	public PopUp(Context context)
	{
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_up);

		mDislayText = (TextView) findViewById(R.id.pop_up_dlg_text);
		neutralButton = (Button) findViewById(R.id.pop_up_neutral_btn);
		neutralButton.setOnClickListener(this);
		positiveButton = (Button) findViewById(R.id.pop_up_positive_btn);
		positiveButton.setOnClickListener(this);
		negativeButton = (Button) findViewById(R.id.pop_up_negative_btn);
		negativeButton.setOnClickListener(this);

		if (isNegativeBtnVisible)
		{
			negativeButton.setVisibility(View.VISIBLE);
		} else
		{
			negativeButton.setVisibility(View.GONE);
		}

		if (isNeutralBtnVisible)
		{
			neutralButton.setVisibility(View.VISIBLE);
		} else
		{
			neutralButton.setVisibility(View.GONE);
		}
		this.setCancelable(false);
		initUI();
	}

	/**
	 * @param message
	 */
	public void setDisplayMessage(String message)
	{
		mdisplayStr = message;
	}

	/**
	 * @param message
	 */
	public void setDisplayMessage(int message)
	{
		mdisplayStr = mContext.getResources().getString(message);
	}

	/**
	 * 
	 */
	private void initUI()
	{
		mDislayText.setText(mdisplayStr);
		positiveButton.setText(positiveBtnText);
		negativeButton.setText(negativeBtnText);
		neutralButton.setText(neutralBtnText);
	}

	/**
	 * @param positiveButton
	 * @param negativeButton
	 */
	public void setButtonTitles(String positiveButton, String negativeButton)
	{
		positiveBtnText = positiveButton;
		negativeBtnText = negativeButton;
	}

	/**
	 * @param positiveButton
	 * @param neutralButton
	 * @param negativeButton
	 */
	public void setButtonTitles(String positiveButton, String neutralButton, String negativeButton)
	{
		positiveBtnText = positiveButton;
		negativeBtnText = negativeButton;
		neutralBtnText = neutralButton;
	}

	/**
	 * @param positiveButton
	 * @param negativeButton
	 */
	public void setButtonTitles(int positiveButton, int negativeButton)
	{
		positiveBtnText = mContext.getResources().getString(positiveButton);
		negativeBtnText = mContext.getResources().getString(negativeButton);
	}

	/**
	 * @param positiveButton
	 * @param neutralButton
	 * @param negativeButton
	 */
	public void setButtonTitles(int positiveButton, int neutralButton, int negativeButton)
	{
		positiveBtnText = mContext.getResources().getString(positiveButton);
		negativeBtnText = mContext.getResources().getString(negativeButton);
		neutralBtnText = mContext.getResources().getString(neutralButton);
	}

	/**
	 * @param title
	 */
	public void setPositiveButtonTitle(String title)
	{
		positiveBtnText = title;
	}

	/**
	 * @param title
	 */
	public void setNegativeButtonTitle(int title)
	{
		negativeBtnText = mContext.getResources().getString(title);
	}

	/**
	 * @param title
	 */
	public void setNegativeButtonTitle(String title)
	{
		negativeBtnText = title;
	}

	/**
	 * @param title
	 */
	public void setPositiveButtonTitle(int title)
	{
		positiveBtnText = mContext.getResources().getString(title);
	}

	/**
	 * @param title
	 */
	public void setNeutralButtonTitle(int title)
	{
		neutralBtnText = mContext.getResources().getString(title);
	}

	/**
	 * @param title
	 */
	public void setNeutralButtonTitle(String title)
	{
		neutralBtnText = title;
	}

	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{
			case R.id.pop_up_positive_btn:
			{
				PopUp.this.dismiss();
				if (null != mListener)
				{
					mListener.onPoisitiveBtnClicked();
				}

			}
				break;
			case R.id.pop_up_negative_btn:
			{
				PopUp.this.dismiss();
				if (null != mListener)
				{
					mListener.onNegativeBtnClicked();
				}

			}
				break;
			case R.id.pop_up_neutral_btn:
			{
				PopUp.this.dismiss();
				if (null != mListener)
				{
					mListener.onNeutralBtnClicked();
				}
			}
			break;
			default:
				break;
		}
	}

	/**
	 * 
	 */
	public void swapPositiveNegative()
	{
		negativeButton = positiveButton;
	}

	/**
	 * @return the positiveButton
	 */
	public Button getPositiveButton()
	{
		if (positiveButton == null)
		{
			positiveButton = (Button) findViewById(R.id.pop_up_positive_btn);
			positiveButton.setOnClickListener(this);
		}

		return positiveButton;
	}

	/**
	 * @return the negativeButton
	 */
	public Button getNegativeButton()
	{
		return negativeButton;
	}

	/**
	 * @return the neutralButton
	 */
	public Button getNeutralButton()
	{
		return neutralButton;
	}

	/**
	 * @return the mDislayText
	 */
	public TextView getmDislayText()
	{
		return mDislayText;
	}
}
