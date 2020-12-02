package br.com.joaoov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import br.com.joaoov.R
import br.com.joaoov.ext.getString
import kotlinx.android.synthetic.main.dialog_choose_agent.*

class ChooseAgentDialog: DialogFragment() {

    companion object {
        fun newInstance(onClick: (String) -> Unit): ChooseAgentDialog {
            val dialog = ChooseAgentDialog()
            dialog.onClick = onClick
            return dialog
        }
    }

    private lateinit var onClick: (String) -> Unit

    override fun onStart() {
        super.onStart()
        dialog?.setTitle("Agentes")
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
        return inflater.inflate(R.layout.dialog_choose_agent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.agents))
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner_choose_agent.adapter = adapter

        autoCompleteTextViewAgents.setAdapter(
            NoFilterAdapter(
                requireContext(),
                R.layout.drop_down_item,
                resources.getStringArray(R.array.agents)
            )
        )

        button2.setOnClickListener {
            dismiss()
            onClick(autoCompleteTextViewAgents.getString())
        }
    }
}