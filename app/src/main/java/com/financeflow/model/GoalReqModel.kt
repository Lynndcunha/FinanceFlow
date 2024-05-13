package com.financeflow.model

data class GoalReqModel ( var userId: String, var name: String, var amount: String,
                         var date: String,var description: String,
                          )



data class GoalUpdateReqModel ( var id: String, var name: String, var amount: String,
                          var date: String,var description: String,
)



data class ExpenseReqModel ( var userId: String, var category: String, var amount: String, )

data class ExpenseUpdateReqModel ( var id: String, var name: String, var amount: String,)