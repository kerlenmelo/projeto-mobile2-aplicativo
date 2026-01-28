package com.mobile2.aconselhamentofinanceiropessoal.ui.reports

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity

@Composable
fun ChartSection(transactions: List<TransactionEntity>) {
    // Melhoria: Considera tudo que NÃO é Receita como despesa para o gráfico
    val expensesByCategory = transactions
        .filter { !it.type.equals("Receita", ignoreCase = true) }
        .groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.value } }

    if (expensesByCategory.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Nenhuma despesa para exibir no gráfico.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        return
    }

    val entries = expensesByCategory.map { PieEntry(it.value.toFloat(), it.key) }

    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                isDrawHoleEnabled = true
                setHoleColor(android.graphics.Color.TRANSPARENT)
                legend.isEnabled = true
                setEntryLabelColor(android.graphics.Color.BLACK)
                animateY(1000)
            }
        },
        modifier = Modifier.fillMaxWidth().height(350.dp),
        update = { chart ->
            val dataSet = PieDataSet(entries, "Categorias de Gastos").apply {
                colors = ColorTemplate.MATERIAL_COLORS.toList()
                valueTextSize = 16f
                valueTextColor = android.graphics.Color.BLACK
            }
            chart.data = PieData(dataSet)
            chart.invalidate()
        }
    )
}