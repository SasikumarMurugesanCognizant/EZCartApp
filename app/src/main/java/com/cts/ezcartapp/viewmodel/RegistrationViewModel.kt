package com.cts.ezcartapp.viewmodel


import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.data.entities.UserData
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(val remoteDataSourceRepository: RemoteDataSourceRepository) : ViewModel() {
    val isSignUpCompleted: MutableLiveData<Boolean> = MutableLiveData()
    private fun passwordValidation(passwordStr: String): String {
        if (passwordStr.length < 6) return "Password length should minimum 6"
        if (passwordStr.matches(("(.*[0-9].*)").toRegex())
                .not()
        ) return "Password should contain at-least one number "
        if (passwordStr.matches(("(.*[A-Z].*)").toRegex())
                .not()
        ) return "Password should contain at-least one uppercase "
        if ((passwordStr.matches(("^(?=.*[_.()\$&@]).*\$").toRegex())).not()) return "Password should contain at-least alpha numeric"
        return ""
    }

    private fun mobileNumberValidation(mobileNumber: String): String =
        if (mobileNumber.isNullOrEmpty()
                .not() && mobileNumber.length == 10
        ) "" else "Please enter valid mobile number"

    private fun firstORLastNameValidation(nameStr: String, nameLabel: String): String {
        return if (nameStr.isEmpty()) "Please enter $nameLabel" else ""
    }

    fun doSignUpValidation(
        firstName: String,
        lastName: String,
        userId: String,
        password: String,
        mobileNo: String
    ): String {
        if (firstName.isBlank()) return "Please enter First Name"

        if (lastName.isBlank()) return "Please enter Last Name"

        if (userId.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(userId).matches()
                .not()
        ) return "Enter valid Email address"

        if (passwordValidation(password).isNotEmpty()) return passwordValidation(password)

        if (mobileNumberValidation(mobileNo).isNotEmpty()) return mobileNumberValidation(mobileNo)

        return ""
    }

    fun doSignUp(userData: UserData) {
        viewModelScope.launch {
            remoteDataSourceRepository.insertUserDataIntoLocalStorage(userData)
            isSignUpCompleted.value = true
        }
    }
}