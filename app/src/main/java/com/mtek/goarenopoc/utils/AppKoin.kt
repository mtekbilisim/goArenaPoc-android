package com.mtek.goarenopoc.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import kotlin.reflect.KClass

object AppKoin {

    fun extGetKoin() = GlobalContext.get().koin

    inline fun <reified T : Any> extInject(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ) = lazy { extGet<T>(qualifier, parameters) }

    inline fun <reified T : Any> extGet(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ): T = extGetKoin().get(qualifier, parameters)

    inline fun <reified S, reified P> extBind(
        noinline parameters: ParametersDefinition? = null
    ): S = extGetKoin().bind<S, P>(parameters)

    fun <VB : ViewBinding> extGetAdapterViewBinding(
        parent: ViewGroup,
        viewBindingType: KClass<VB>
    ): VB = (extGet(TypeQualifier(viewBindingType)) {
        parametersOf(parent)
    } as ViewBinding) as VB

    fun <VB : ViewBinding> extGetActivityViewBinding(
        layoutInflater: LayoutInflater,
        viewBindingType: KClass<VB>
    ): VB = (extGet(TypeQualifier(viewBindingType)) {
        parametersOf(layoutInflater)
    } as ViewBinding) as VB
}