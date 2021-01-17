package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.departament.Departament

data class DepartamentWithAmbients(
    val departament: Departament,
    val ambientsWithFunctions: List<AmbientWithFunctions>
)
