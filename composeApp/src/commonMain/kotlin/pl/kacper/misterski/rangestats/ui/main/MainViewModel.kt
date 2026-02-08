package pl.kacper.misterski.rangestats.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    fun onAction(action: MainAction){
        when(action){
            MainAction.ShowBottomSheet ->{} // TODO K tutaj pokaż sheeta
        }
    }
}