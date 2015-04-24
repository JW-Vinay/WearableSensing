package com.wearables.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wearables.models.BiometricBOModel;
import com.wearables.models.BiometricBPModel;
import com.wearables.models.WithingsWeight;
import com.wearables.networking.NetworkConstants;

public class JSONParser {
	private Context mContext;

	public JSONParser(Context context) throws JSONException {
		this.mContext = context;

	}

	public void parseResponse(String response) {
		try {
			JSONObject jObject = new JSONObject(response);
			SharedPrefs sp = SharedPrefs.getInstance(this.mContext);
			if (jObject != null) {

				String accessToken = (!jObject.isNull("AccessToken")) ? jObject
						.getString("AccessToken") : "";
				sp.setParameters(NetworkConstants.ACCESS_TOKEN, accessToken);
				sp.setLongParameters(NetworkConstants.TIMESTAMP,
						System.currentTimeMillis());
				String userID = (!jObject.isNull("UserID")) ? jObject
						.getString("UserID") : "";
				sp.setParameters(NetworkConstants.USER_ID, userID);
				String refreshToken = (!jObject.isNull("RefreshToken")) ? jObject
						.getString("RefreshToken") : "";
				sp.setParameters(NetworkConstants.REFRESH_TOKEN, refreshToken);
			}
		} catch (JSONException e) {

		}
	}

	public BiometricBOModel parseSP02(String response) {
		try {
			JSONObject jObject = new JSONObject(response);
			if (jObject != null) {
				BiometricBOModel model = null;
				JSONArray boArray = (!jObject.isNull("BODataList")) ? jObject
						.getJSONArray("BODataList") : new JSONArray();
				for (int i = 0; i < boArray.length(); i++) {
					JSONObject currObj = boArray.getJSONObject(i);
					String bloodOxy = (!currObj.isNull("BO")) ? currObj
							.getString("BO") : "";
					String mDate = (!currObj.isNull("MDate")) ? currObj
							.getString("MDate") : "";

					model = new BiometricBOModel(
							Integer.parseInt(bloodOxy),
							Utils.getTotalMillisecondTime(Long.parseLong(mDate)));
					// System.out.println("Blood oxygen " + bloodOxy + ": MDATE"
					// + mDate);
				}
				return model;
			}
		} catch (JSONException e) {

		}
		return null;
	}

	public WithingsWeight parseWithings(String response) {
		// http://wbsapi.withings.net/measure?action=getmeas&oauth_consumer_key=9e993fd9448f18c86128e243d3cc6bffdeed88ce8b079bd090728b5&oauth_nonce=dafd9d0f28c900cb532d7186fd28562c&oauth_signature=tHwR99IwbHTiWH8V9ZkVFOa%2Fxs0%3D&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1429823520&oauth_token=158021847c8bcc43816669b93cfdd038d9f4691870982300cb5c32d840ddd&oauth_version=1.0&userid=7229874
		try {
			JSONObject jObject = new JSONObject(response);
			if (jObject != null) {
				JSONObject body = jObject.getJSONObject("body");
				if (body != null) {
					JSONArray measuregps = body.getJSONArray("measuregrps");
					if (measuregps != null && measuregps.length() > 0) {
						JSONObject measureObject = measuregps.getJSONObject(0);
						long timestamp = measureObject.getLong("date");
						JSONArray measuresAray = measureObject
								.getJSONArray("measures");
						if (measuresAray != null && measuresAray.length() > 0) {
							for (int i = 0; i < measuresAray.length(); i++) {
								JSONObject obj = measuresAray.getJSONObject(i);
								if (obj != null) {
									int type = obj.getInt("type");
									if (type == 1) {
										int val = obj.getInt("value");
										int unit = obj.getInt("unit");
										double kg = val * Math.pow(10, unit);
										WithingsWeight model = new WithingsWeight(
												(int) Math.round(kg),
												(timestamp * 1000));
										return model;
									}
								}
							}
						}
					}
				}

			}
		} catch (JSONException e) {

			e.printStackTrace();
			// LogUtils.LOGE(TA, message);
		}

		return null;
	}

	public BiometricBPModel parseBP(String response) {
		try {
			JSONObject jObject = new JSONObject(response);
			if (jObject != null) {
				// TODE: write JSON parser
				BiometricBPModel model = null;
				// System.out.println(jObject.toString());
				JSONArray bpArray = (!jObject.isNull("BPDataList")) ? jObject
						.getJSONArray("BPDataList") : new JSONArray();

				for (int i = 0; i < bpArray.length(); i++) {
					JSONObject currObj = bpArray.getJSONObject(i);
					String systolic = (!currObj.isNull("HP")) ? currObj
							.getString("HP") : "";
					String dystolic = (!currObj.isNull("LP")) ? currObj
							.getString("LP") : "";
					String pulse = (!currObj.isNull("HR")) ? currObj
							.getString("HR") : "";
					String mDate = (!currObj.isNull("MDate")) ? currObj
							.getString("MDate") : "";
					model = new BiometricBPModel(
							Integer.parseInt(systolic),
							Integer.parseInt(dystolic),
							Integer.parseInt(pulse),
							Utils.getTotalMillisecondTime(Long.parseLong(mDate)));
					// System.out.println("SY " + systolic + ": dys" + dystolic
					// + ": pulse" + pulse + ": MDATE" + mDate);
				}
				return model;
			}
		} catch (JSONException e) {

		}
		return null;
	}

	public void parseRefreshToken(String response) {
		try {
			JSONObject jObject = new JSONObject(response);
			SharedPrefs sp = SharedPrefs.getInstance(this.mContext);
			if (jObject != null) {
				// System.out.println(jObject.toString());
				String accessToken = (!jObject.isNull("AccessToken")) ? jObject
						.getString("AccessToken") : "";
				sp.setParameters(NetworkConstants.ACCESS_TOKEN, accessToken);

				String refreshToken = (!jObject.isNull("RefreshToken")) ? jObject
						.getString("RefreshToken") : "";
				// System.out.println(refreshToken);
				sp.setParameters(NetworkConstants.REFRESH_TOKEN, refreshToken);
				sp.setLongParameters(NetworkConstants.TIMESTAMP,
						System.currentTimeMillis());

			}
		} catch (JSONException e) {

		}
	}
}
