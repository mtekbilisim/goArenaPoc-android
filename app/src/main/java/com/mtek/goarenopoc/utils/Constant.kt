package com.mtek.goarenopoc.utils

import android.content.Context
import java.util.*

object Constants {

    const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    const val HEADER_USER_AGENT = "User-Agent"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_TIME_ZONE = "timezone"
    const val DEVICES_TOKEN = "deviceToken"
    const val DEVICES_NAME: String = "deviceName"
    const val SELECTED_URI: String = "SelectedImageUri"
    var DEVICES_VALUE: String? = emptyString()
    const val PHOTOS_KEY = "easy_image_photos_list"
    const val SAVE_INFORMATION = "saveInformation"
    const val EMAIL = "email"
    const val XPS = "password"
    const val SOCIAL_LOGIN = "social_login"
    const val ONBOARDING = "onBoarding"
    const val MANAGER = "MANAGER"
    const val EMPLOYEE = "EMPLOYEE"

    const val CAMERA_REQUEST = 52
    const val PICK_REQUEST = 53
    const val PERMISSION_CODE_CAMERA = 1001;
    val CHOOSER_PERMISSIONS_REQUEST_CODE = 34961
    val CAMERA_REQUEST_CODE = 7500
    val CAMERA_VIDEO_REQUEST_CODE = 7501
    val GALLERY_REQUEST_CODE = 7502
    val DOCUMENTS_REQUEST_CODE = 7503
    val TURKISH = Locale.forLanguageTag("tr")
    var ACCESS_TOKEN: String? = null
    var CURRENT_CONTEXT: Context? = null
     var filterUriStr = emptyString()







}
