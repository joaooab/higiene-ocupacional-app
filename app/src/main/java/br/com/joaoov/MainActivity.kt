package br.com.joaoov

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val adapterPath by lazy {
        MainPathAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavController()
        setupPath()
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

    fun addPath(path: Path) {
        adapterPath.add(path)
    }

    fun removePath() {
        adapterPath.remove()
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
