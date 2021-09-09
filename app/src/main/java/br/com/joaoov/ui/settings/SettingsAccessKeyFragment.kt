package br.com.joaoov.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.Session
import br.com.joaoov.data.State
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.handle
import br.com.joaoov.ext.show
import br.com.joaoov.ui.component.AlertDialogCustom
import kotlinx.android.synthetic.main.fragment_settings_access_key.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsAccessKeyFragment : Fragment(R.layout.fragment_settings_access_key) {

    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val accessKeyViewModel: AccessKeyViewModel by viewModel()
    private val user by lazy { Session.user }
    private val adapter by lazy {
        SettingsAccessKeyAdapater(onDeleteClick = {
            AlertDialogCustom(requireContext())
                .showDeleteDialog { accessKeyViewModel.deleteLinkedUser(it) }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(menu = false)
        setupView()
        handleObserver()
    }

    private fun handleObserver() {
        accessKeyViewModel.fetchLinkedUsers()
        accessKeyViewModel.linkedUsers.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Loading -> {
                    loading.show()
                    groupContentLinkedUsers.gone()
                }
                is State.Success -> {
                    loading.gone()
                    groupContentLinkedUsers.show()
                    adapter.items = state.data.toMutableList()
                }
                is State.Error -> {
                    loading.gone()
                    groupContentLinkedUsers.show()
                }
            }
        })

        accessKeyViewModel.removeLinkedUser.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Loading -> {
                    loading.show()
                    groupContentLinkedUsers.gone()
                }
                is State.Success -> {
                    accessKeyViewModel.fetchLinkedUsers()
                }
                is State.Error -> {
                    loading.gone()
                    groupContentLinkedUsers.show()
                    state.throwable.handle(requireContext())
                }
            }
        })
    }

    private fun setupView() {
        textAccessKey.text = getString(R.string.label_access_key, user?.accessKey.orEmpty())
        recyclerViewLinkedUsers.adapter = adapter
        setupAddButton()
    }

    private fun setupAddButton() {
        buttonAdd.setOnClickListener {
            AddLinkedUserDialog.newInstance {
                accessKeyViewModel.fetchLinkedUsers()
            }.show(childFragmentManager)
        }
    }

}