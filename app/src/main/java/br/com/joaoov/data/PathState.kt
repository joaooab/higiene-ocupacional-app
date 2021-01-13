package br.com.joaoov.data

import br.com.joaoov.Path

sealed class PathState {
    class Add(val path: Path) : PathState()
    object Remove : PathState()
}