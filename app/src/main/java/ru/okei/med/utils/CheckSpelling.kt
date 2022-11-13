package ru.okei.med.utils

object CheckSpelling {
    fun check(text:String, type: Type):Boolean{
        return when(type){
            Type.Email -> isEmail(text)
            Type.Password -> isPassword(text)
            Type.NameAndSecondName -> isName(text)
        }
    }
    private fun isEmail(text: String):Boolean{
        val nameToDomain = text.split("@")
        val domain = nameToDomain.getOrNull(1) ?: return false
        return nameToDomain[0].isNotEmpty() &&
                domain.isNotEmpty() &&
                !domain.split(".").getOrNull(0).isNullOrEmpty() &&
                !domain.split(".").getOrNull(1).isNullOrEmpty()
    }

    private fun isPassword(text: String):Boolean{
        return text.length > 6
    }
    private fun isName(text:String):Boolean{
        val nameAndSecondName = text.split(' ')
        return (nameAndSecondName.size>1) &&
                nameAndSecondName[0].isNotEmpty() &&
                nameAndSecondName[1].isNotEmpty()
    }

    sealed class Type{
        object Email: Type()
        object Password: Type()
        object NameAndSecondName: Type()
    }
}