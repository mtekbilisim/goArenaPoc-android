package com.mtek.goarenopoc.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mtek.goarenopoc.R
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException
import java.io.Serializable


fun <T : RequestBody?> T.bodyToString(): String {
    return try {
        val buffer = Buffer()
        if (this != null) this.writeTo(buffer) else return ""
        buffer.readUtf8()
    } catch (e: IOException) {
        "did not work"
    }
}

fun emptyString(): String = ""
fun emptyNumber(): Int = -1
fun emptyDouble(): Double = 0.0
fun nullObject() = null
fun emptyBoolean(): Boolean = false

fun <T> Bundle.put(key: String, value: T) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Short -> putShort(key, value)
        is Long -> putLong(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Float -> putFloat(key, value)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalStateException("")
    }
}

fun loadImage(
    view: ImageView,
    url: String?,
    progressDrawable: CircularProgressDrawable?
) {
    val options: RequestOptions = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(view.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(view)
}

fun getProgressDrawable(context: Context?): CircularProgressDrawable {
    val progressDrawable = CircularProgressDrawable(context!!)
    progressDrawable.strokeWidth = 10f
    progressDrawable.centerRadius = 50f
    progressDrawable.start()
    return progressDrawable
}

fun Any.flag_error(message: String, tag:String = "flag_error") = Log.e(tag,message)
fun Any.flag_debug(message: String, tag:String="flag_debug") = Log.e(tag,message)
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClick = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClick)

}




fun <T : AppCompatActivity> AppCompatActivity.extStartActivity(className: Class<T>, bundle: Bundle) {
    startActivity(Intent(this, className).putExtras(bundle))
}

infix fun <T : AppCompatActivity> AppCompatActivity.extStartActivity(className: Class<T>) {
    startActivity(Intent(this, className))

}

infix fun <T : AppCompatActivity> AppCompatActivity.extLogOutActivity(className: Class<T>) {
    startActivity(
        Intent(this, className).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}

infix fun <T : AppCompatActivity> AppCompatActivity.extClearTopStartActivity(className: Class<T>) {
    startActivity(
        Intent(this, className).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}


infix fun Context.extToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.extStartActivity(intent: Intent) {
    startActivity(intent)
}


/**
 *              openActivity<MyActivity> {
 *                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
 *               putExtra(LaBoxConstants.DEFAULT_LANDING, Default_Landing)
 *               putExtra(HomeActivity.APP_RELAUNCH, AppReLaunched)
 *                    }
 *
 * */

inline fun <reified T : Activity> Context.openActivity(noinline extra: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.extra()
    startActivity(intent)
}




