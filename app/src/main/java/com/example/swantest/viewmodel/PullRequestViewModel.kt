package com.example.swantest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swantest.model.PullRequestModel
import com.example.swantest.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullRequestViewModel : ViewModel() {
    sealed class State {
        data class GetOpenPRSuccessData(
            val responseCode: Int,
            val data: ArrayList<PullRequestModel>,
            val message: String
        ) : State()

        data class GetOpenPRErrorData(val responseCode: Int, val message: String) : State()

        data class GetClosedPRSuccessData(
            val responseCode: Int,
            val data: ArrayList<PullRequestModel>,
            val message: String
        ) : State()

        data class GetClosedPRErrorData(val responseCode: Int, val message: String) : State()
    }

    sealed class Event {
        data class GetPullRequestData(val state: String) : Event()
    }

    fun onEventReceived(event: Event) {
        when (event) {
            is Event.GetPullRequestData -> {
                getPullRequestData(event.state)
            }
        }
    }

    val state: LiveData<State>
        get() = _state

    private val _state = MutableLiveData<State>()


    private fun getPullRequestData(state: String) {
        RestClient.retrofitCallBack().create(RestClient.NetworkCall::class.java)
            .getPullRequestData(state)
            .enqueue(object : Callback<ArrayList<PullRequestModel>> {
                override fun onResponse(
                    call: Call<ArrayList<PullRequestModel>>,
                    response: Response<ArrayList<PullRequestModel>>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        if (response.code() == 200) {
                            if (state.equals("open", true))
                                _state.value =
                                    State.GetOpenPRSuccessData(response.code(), responseData!!, "")
                            else
                                _state.value = State.GetClosedPRSuccessData(
                                    response.code(),
                                    responseData!!,
                                    ""
                                )
                        } else {
                            if (state.equals("open", true))
                                _state.value =
                                    State.GetOpenPRErrorData(response.code(), response.message())
                            else
                                _state.value =
                                    State.GetClosedPRErrorData(response.code(), response.message())
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<PullRequestModel>>, t: Throwable?) {
                    if (state.equals("open", true))
                        _state.value = State.GetOpenPRErrorData(0, t?.message!!)
                    else
                        _state.value = State.GetClosedPRErrorData(0, t?.message!!)
                }
            })
    }
}