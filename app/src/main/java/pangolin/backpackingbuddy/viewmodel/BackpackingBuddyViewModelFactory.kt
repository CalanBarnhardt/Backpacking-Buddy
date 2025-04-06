package pangolin.backpackingbuddy.viewmodel

import BackpackingBuddyRepo
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope

class BackpackingBuddyViewModelFactory(private val context: Context, private val coroutineScope: CoroutineScope) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        private const val LOG_TAG = "448.SamodelkinViewModelFactory"
    }

    fun getViewModelClass() = BackpackingBuddyViewModel::class.java

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(LOG_TAG, "create() called")
        val repo = BackpackingBuddyRepo.getInstance(
            context,
            coroutineScope = coroutineScope
        )
        return BackpackingBuddyViewModel(repo) as T
    }

}