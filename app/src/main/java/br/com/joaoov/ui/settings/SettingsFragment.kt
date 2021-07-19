package br.com.joaoov.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val syncViewModel: SyncViewModel by sharedViewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(menu = false)
        setupView()
    }

    private fun setupView() {
        textPersonName.text = Session.user?.name.orEmpty()
        textPersonEmail.text = getString(R.string.label_email, Session.user?.username.orEmpty())

        settingChangePassword.setOnClickListener {
            val direction = SettingsFragmentDirections.actionSettingsFragmentToUserUpdateFragment()
            findNavController().navigate(direction)
        }

        settingSync.setOnClickListener {
            syncViewModel.forceSyncronize()
        }

        settingLogout.setOnClickListener {
            (requireActivity() as MainActivity).logout()
        }
    }

}