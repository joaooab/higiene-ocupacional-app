package br.com.joaoov

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.joaoov.data.PathState
import br.com.joaoov.data.SyncState
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.show
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val syncViewModel: SyncViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    private val adapterPath by lazy {
        MainPathAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavController()
        setupPath()
        handleObserve()
    }

    private fun handleObserve() {
        observeSyncronizeState()
        observePathState()
    }

    private fun observePathState() {
        mainViewModel.pathState.observe(this, { state ->
            when (state) {
                is PathState.Add -> {
                    adapterPath.add(state.path)
                }
                is PathState.Remove -> {
                    adapterPath.remove()
                }
            }
        })
    }

    private fun observeSyncronizeState() {
        syncViewModel.syncronizeState.observe(this, { state ->
            when (state) {
                is SyncState.Running -> {
                    linearLayoutSync.show()
                    textViewSync.text = state.message
                }
                is SyncState.Completed -> {
                    textViewSync.setText(R.string.sync_complete)
                    Handler(Looper.getMainLooper()).postDelayed({
                        linearLayoutSync.gone()
                    }, 1000)
                }
                is SyncState.Failed -> {
                    linearLayoutSync.gone()
                    showToast(R.string.sync_failed)
                }
            }
        })
    }

    private fun setupPath() {
        recyclerViewPath.adapter = adapterPath
    }

    private fun setupNavController() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.companyListFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavHostFragment.findNavController(nav_host_fragment).navigateUp()
    }

    fun checkPermission(permission: String): Boolean {
        if (hasContainPermission(permission)) {
            return true
        }
        requestPermission(permission)
        return false
    }

    private fun hasContainPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            1
        )
    }

}
