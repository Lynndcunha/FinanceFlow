package com.financeflow.model

data class IncomeReqModel (var userId: String, var income: List<IncomeReqModelList>)

data class IncomeReqModelList (var incomeType: String, var incomeAmount: String)

data class UpdateIncomeReqModel (var id: String, var income: List<IncomeReqModelList>)
