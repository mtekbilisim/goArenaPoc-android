package com.mtek.goarenopoc.data.network.client

import android.os.Build
import com.mtek.goarenopoc.utils.Constants
import com.mtek.goarenopoc.utils.bodyToString
import com.mtek.goarenopoc.utils.flag_error
import com.mtek.goarenopoc.utils.manager.LocalDataManager
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.text.SimpleDateFormat
import java.util.*

private const val TIME_ZONE = 3

class UserTokenHeader : Interceptor {

    private var userAgent: String
    private var timezone: Int = 3

    init {
        val df =
            SimpleDateFormat("Z", Locale.getDefault())
        val formattedTimezone =
            df.format(Calendar.getInstance().time).substring(0, TIME_ZONE)
        this.timezone = formattedTimezone.toInt()
        this.userAgent = String.format(
            Locale.US,
            "%s/%s (Android %s; %s; %s %s; %s)",
            System.getProperty("java.vm.name"),
            System.getProperty("java.vm.version"),
            Build.VERSION.RELEASE,
            Build.MODEL,
            Build.BRAND,
            Build.DEVICE,
            Locale.getDefault().language
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response { //Log.e("User-Agent",userAgent);

        val userAgentRequest = with(chain.request().newBuilder(), {
           // addHeader("Authorization", "Bearer ${LocalDataManager.instance.getSharedPreferenceString(Constants.CURRENT_CONTEXT!!, Constants.ACCESS_TOKEN, "")}")
            addHeader(Constants.DEVICES_NAME,"${Build.BRAND} / ${Build.MODEL}")
            addHeader(Constants.HEADER_TIME_ZONE, TIME_ZONE.toString())
            addHeader(Constants.HEADER_CONTENT_TYPE, "application/json")
            addHeader(Constants.HEADER_USER_AGENT, userAgent)

            build()
        })
        flag_error("Header ${userAgentRequest.headers}")
        flag_error("Request ${userAgentRequest.body?.bodyToString()}")
        val response = chain.proceed(userAgentRequest)
        val rawJson = response.body?.string()
        val x = rawJson?.toResponseBody(response.body?.contentType())

        flag_error("Response $rawJson")
        return response.newBuilder().body(x).build()
    }


    private fun getLangCode(): String {
        val lang = Locale.getDefault().toString()
        val langCode: String
        langCode = if ("tr" == lang) {
            "tr-TR"
        } else {
            "en-US"
        }
        return langCode
    }


}