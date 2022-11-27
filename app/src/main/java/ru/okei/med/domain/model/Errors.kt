package ru.okei.med.domain.model

sealed class Errors: Throwable() {
    sealed class Auth: Errors(){
        class ErrorInEmail(override val message: String) : Auth()
        class ErrorInPassword(override val message: String) : Auth()
        class ErrorInName(override val message: String):Auth()
        object RefreshTokenNotAvailable: Auth()
        object AccessTokenExpired: Auth()
    }
    sealed class Request: Errors(){
        class NetworkError(override val message: String?): Request()
    }
    class TransformationJson(val type: Any): Errors(){
        override val message: String
            get() = "Can't deserialize value: $type"
    }

}
