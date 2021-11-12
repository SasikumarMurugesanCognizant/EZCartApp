package com.cts.ezcartapp.utils

import android.util.Base64
import android.util.Patterns
import java.lang.Exception
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object Extensions
{
    private const val SECRET_KEY = "aesEncryptionKey"
    private const val INIT_VECTOR = "encryptionIntVec"
  fun String.emailValidation():String{
      return if (this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
          ""
      } else "Enter valid Email address"
  }

    fun String.encrypt(): String? {
        try {
            val iv = IvParameterSpec(INIT_VECTOR.toByteArray(Charset.forName("UTF-8")))
            val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charset.forName("UTF-8")), "AES")
            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted: ByteArray = cipher.doFinal(this.toByteArray())
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun String.decrypt(): String? {
        try {
            val iv = IvParameterSpec(INIT_VECTOR.toByteArray(Charset.forName("UTF-8")))
            val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charset.forName("UTF-8")), "AES")
            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original: ByteArray = cipher.doFinal(Base64.decode(this, Base64.DEFAULT))
            return String(original)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}