package ru.test.points.base.dagger.network

import okhttp3.ResponseBody
import retrofit2.Converter
import ru.test.points.model.common.BaseResponse
import java.io.IOException

class ResponseBodyConverter<T>(private val converter: Converter<ResponseBody, BaseResponse<T>>) :
    Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): T {
        val response = converter.convert(responseBody)
        return if (response.resultCode == "OK") response.payload
        else throw IOException(response.errorMessage)
    }
}