package br.com.joaoov.ui.component.move


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import br.com.joaoov.R
import br.com.joaoov.data.State
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.setupData
import br.com.joaoov.ext.show
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.dialog_move.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoveDialog : DialogFragment() {

    private val viewModel by viewModel<MoveViewModel>()
    private lateinit var itemToMove: Any

    companion object {
        fun newInstance(itemToMove: Any): MoveDialog {

            return MoveDialog().apply {
                this.itemToMove = itemToMove
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_move, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleObserver()
    }

    private fun handleObserver() {
        viewModel.loadPossibleMoves(itemToMove).observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                showEmpty()
            } else {
                showData(it)
            }
        })
        viewModel.moveState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Loading -> {
                    buttonMove.gone()
                    loading.show()
                }
                is State.Success -> {
                    dismiss()
                }
                is State.Error -> {
                    buttonMove.gone()
                    showToast(R.string.message_error_generic)
                }
            }
        })
    }

    private fun setupView() {
        textInputLayoutMove.hint = getString(getMoveHint())
        buttonMove.setOnClickListener {
            val selectedItem = viewModel.selectedItem.value
            if (selectedItem != null) {
                viewModel.move(itemToMove, selectedItem)
            } else {
                dismiss()
            }
        }
    }

    private fun showEmpty() {
        val hint = getString(getMoveHint())
        textInputLayoutMove.gone()
        val message = requireContext().getString(R.string.message_possible_move_empty, hint)
        textViewMoveEmpty.text = message
        textViewMoveEmpty.show()
        buttonMove.setText(R.string.action_cancel)
    }

    private fun showData(it: List<Any>) {
        textInputLayoutMove.setupData(it)
        autoCompleteTextViewMove.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val item = adapterView.adapter.getItem(position)
                viewModel.changeSelectedItem(item)
            }
    }

    private fun getMoveHint() = when (itemToMove) {
        is Departament -> {
            R.string.company
        }
        is Ambient -> {
            R.string.departament
        }
        is Function -> {
            R.string.ambient
        }
        is Risk -> {
            R.string.function
        }
        else -> {
            throw UnsupportedOperationException("")
        }
    }

}