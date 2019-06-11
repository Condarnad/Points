package ru.test.points.model.common

class BaseResponse<T>(
    val resultCode: String,
    val payload: T,
    val errorMessage: String?
)