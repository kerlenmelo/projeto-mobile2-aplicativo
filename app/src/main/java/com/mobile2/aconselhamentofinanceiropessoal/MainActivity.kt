package com.mobile2.aconselhamentofinanceiropessoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile2.aconselhamentofinanceiropessoal.data.local.AppDatabase
import com.mobile2.aconselhamentofinanceiropessoal.data.local.SessionManager
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.TransactionRepository
import com.mobile2.aconselhamentofinanceiropessoal.ui.home.AddTransactionScreen
import com.mobile2.aconselhamentofinanceiropessoal.ui.home.HomeScreen
import com.mobile2.aconselhamentofinanceiropessoal.ui.login.LoginScreen
import com.mobile2.aconselhamentofinanceiropessoal.ui.login.RegisterScreen
import com.mobile2.aconselhamentofinanceiropessoal.ui.reports.ReportsScreen
import com.mobile2.aconselhamentofinanceiropessoal.ui.theme.AconselhamentoFinanceiroPessoalTheme
import com.mobile2.aconselhamentofinanceiropessoal.viewmodel.HomeViewModel
import com.mobile2.aconselhamentofinanceiropessoal.viewmodel.ReportsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val transactionRepository = TransactionRepository(database.transactionDao())
        val sessionManager = SessionManager(applicationContext)

        setContent {
            AconselhamentoFinanceiroPessoalTheme {
                val navController = rememberNavController()

                val startDestination = if (sessionManager.isLoggedIn()) "home" else "login"

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = startDestination) {

                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { user ->
                                    sessionManager.saveUserSession(user.id, user.name)
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = {
                                    navController.navigate("register")
                                }
                            )
                        }

                        composable("register") {
                            RegisterScreen(
                                onRegisterSuccess = {
                                    navController.popBackStack()
                                },
                                onNavigateToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("home") {
                            val userId = sessionManager.getUserId()
                            val userName = sessionManager.getUserName() ?: ""
                            val homeViewModel = remember { HomeViewModel(transactionRepository, userId) }

                            val transactions by homeViewModel.transactions.collectAsState()
                            val balance by homeViewModel.balance.collectAsState()

                            HomeScreen(
                                userName = userName,
                                transactions = transactions,
                                balance = balance,
                                onAddTransactionClick = { navController.navigate("addTransaction") },
                                onReportsClick = { navController.navigate("reports") },
                                onEditTransaction = { transaction ->
                                    navController.navigate("editTransaction/${transaction.id}")
                                },
                                onDeleteTransaction = { transaction ->
                                    homeViewModel.deleteTransaction(transaction)
                                },
                                onLogoutClick = {
                                    sessionManager.logout()
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("addTransaction") {
                            val userId = sessionManager.getUserId()
                            val homeViewModel = remember { HomeViewModel(transactionRepository, userId) }

                            AddTransactionScreen(
                                onSave = { type, category, description, value ->
                                    val transaction = TransactionEntity(
                                        userId = userId,
                                        type = type,
                                        category = category,
                                        description = description,
                                        value = value
                                    )
                                    homeViewModel.addTransaction(transaction)
                                    navController.popBackStack()
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("editTransaction/{transactionId}") {
                            val transactionId = it.arguments?.getString("transactionId")?.toIntOrNull()
                            if (transactionId != null) {
                                val userId = sessionManager.getUserId()
                                val homeViewModel = remember { HomeViewModel(transactionRepository, userId) }
                                val transaction = homeViewModel.transactions.collectAsState().value.find { it.id == transactionId }

                                if (transaction != null) {
                                    AddTransactionScreen(
                                        transaction = transaction,
                                        onSave = { type, category, description, value ->
                                            val updatedTransaction = transaction.copy(
                                                type = type,
                                                category = category,
                                                description = description,
                                                value = value
                                            )
                                            homeViewModel.updateTransaction(updatedTransaction)
                                            navController.popBackStack()
                                        },
                                        onBack = { navController.popBackStack() }
                                    )
                                }
                            }
                        }

                        composable("reports") {
                            val userId = sessionManager.getUserId()
                            val reportsViewModel = remember { ReportsViewModel(transactionRepository, userId) }

                            ReportsScreen(
                                viewModel = reportsViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
