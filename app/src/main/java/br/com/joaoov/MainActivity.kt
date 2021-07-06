package br.com.joaoov

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.joaoov.data.PathState
import br.com.joaoov.data.SyncState
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.handle
import br.com.joaoov.ext.show
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val syncViewModel: SyncViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by viewModel()
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private var menu: Menu? = null

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
        observeComponents()
        observePathState()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_export -> {
                navigateToExportCompanyFragment()
            }
            R.id.action_settings -> {
                navController.navigate(R.id.settingsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        Session.logout()
        if (R.id.authFragment != navController.currentDestination?.id) {
            navController.popBackStack(R.id.nav_graph, true)
            navController.navigate(R.id.authFragment)
        }
    }

    private fun navigateToExportCompanyFragment() {
        navController.navigate(R.id.exportSelectCompanyFragment)
    }

    private fun observeComponents() {
        componentViewModel.components.observe(this, {
            if (it.path) {
                recyclerViewPath.show()
            } else {
                recyclerViewPath.gone()
            }
            if (it.toolbar) {
                appBar.show()
            } else {
                appBar.gone()
            }
            menu?.findItem(R.id.action_export)?.isVisible = it.menu
            menu?.findItem(R.id.action_settings)?.isVisible = it.menu
        })
    }

    private fun observePathState() {
        componentViewModel.pathState.observe(this, { state ->
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
                    linearLayoutSync.show()
                    textViewSync.setText(R.string.sync_complete)
                    Handler(Looper.getMainLooper()).postDelayed({
                        linearLayoutSync.gone()
                    }, 1000)
                }
                is SyncState.Failed -> {
                    linearLayoutSync.gone()
                    state.throwable.handle(this)
                }
            }
        })
    }

    private fun setupPath() {
        recyclerViewPath.adapter = adapterPath
    }

    private fun setupNavController() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.companyListFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, _, _ -> }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
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
