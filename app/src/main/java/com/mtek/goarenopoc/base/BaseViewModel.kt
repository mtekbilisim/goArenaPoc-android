package com.mtek.goarenopoc.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.mtek.goarenopoc.utils.AppKoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import org.koin.core.qualifier.TypeQualifier
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.lang.IllegalStateException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass


abstract class BaseViewModel<out REPO : BaseRepository>(
    repoTypeOne: KClass<out REPO>
) : ViewModel() {
    protected val repository: REPO = ((AppKoin.extGet(TypeQualifier(repoTypeOne)) as BaseRepository) as REPO)
    val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val errMsg: MutableLiveData<BaseErrorModel>? = MutableLiveData()

    protected inline fun sendRequest(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        crossinline block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            showProgress.postValue(true)
            try {
                block()
            } catch (exception: Exception) {
                when (exception) {
                    is TimeoutException -> {
                        errMsg?.postValue(BaseErrorModel(404, Errors(0, "timeException")))
                    }
                    is IllegalStateException -> {
                        errMsg?.postValue(BaseErrorModel(404, Errors(0, "${(exception as IllegalStateException).message}")))
                    }
                    is JsonSyntaxException -> {
                        errMsg?.postValue(BaseErrorModel(404, Errors(0, "${(exception as JsonSyntaxException).message}")))
                    }
                    is UnknownHostException -> {
                        errMsg?.postValue(BaseErrorModel(101, Errors(0, "Internet bağlantısı bulunamadı. Lütfen cihazınızın internete bağlı olduğundan emin olun")))
                    }
                    is InterruptedIOException -> {
                        errMsg?.postValue(BaseErrorModel(404, Errors(0, "${exception.message}")))

                    }
                    is SocketTimeoutException -> {
                        errMsg?.postValue(BaseErrorModel(404, Errors(0, "${exception.message}")))

                    }
                    is IOException -> {
                        errMsg?.postValue(BaseErrorModel(404, Errors(0, "${exception.message}")))

                    }
                    is KotlinNullPointerException -> {

                    }
                    is MalformedJsonException -> {
                        errMsg?.postValue(BaseErrorModel(404, Errors(0, "${exception.message}")))

                    }
                    else -> {
                        var error =

                            GsonBuilder().setPrettyPrinting().create()
                                .fromJson((exception as HttpException).response()?.errorBody()?.charStream(), BaseErrorModel::class.java)
                        error.code = (exception as HttpException).code()
                        errMsg?.postValue(error)
                    }
                }
            } finally {
                showProgress.postValue(false)
            }
        }
    }
}