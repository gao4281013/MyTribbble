package com.example.administrator.mydribbble.tools

/**
 * Created by Administrator on 2017/9/5 0005.
 */
class Constant {

    companion object {

        val BUGLY_ID = "ab01adf2e2"
        val  CLIENT_ID: String ="14736e3fe75031a8eb83a3b9e762ec32bc31de4024bfc7cc238e50abaaf8c760"
        val CLIENT_SECRET: String ="ef8eb3102694ad0f8ce55172cb0fcacb4363106542987936437fb54b39f39955"
        val VOICE_CODE = 0x01
        val RX_RETRY_TIME: Long = 1
    }
}


    object singleData {
        var token: String? = null
        var username: String? = null
        var avatar: String? = null
        fun isLogin(): Boolean = !token.isNullOrBlank()
    }
