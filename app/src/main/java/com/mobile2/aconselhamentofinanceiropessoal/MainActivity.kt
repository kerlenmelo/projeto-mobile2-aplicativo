package com.mobile2.aconselhamentofinanceiropessoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile2.aconselhamentofinanceiropessoal.ui.home.AddTransactionScreen
import com.mobile2.aconselhamentofinanceiropessoal.ui.home.HomeScreen
import com.mobile2.aconselhamentofinanceiropessoal.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeViewModel = HomeViewModel(applicationContext)

        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "home") {

                        composable("home") {
                            val transactions = homeViewModel.transactions.collectAsState().value
                            val balance = homeViewModel.balance.collectAsState().value

                            HomeScreen(
                                transactions = transactions,
                                balance = balance,
                                onAddTransactionClick = {
                                    navController.navigate("addTransaction")
                                }
                            )
                        }

                        composable("addTransaction") {
                            AddTransactionScreen(
                                onSave = { type, category, description, value ->
                                    homeViewModel.addTransaction(type, category, description, value)
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
