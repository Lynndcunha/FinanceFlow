package com.financeflow.model

data class BudgetReqModel (var userId: String, var rent: String,
                           var electricityBill: String,
                           var phoneBill: String, var internetBill: String,
                           var studentLoan: String,
                           var grocery: String, var gym: String,
                           var dineOut: String,
                           var savings: String, var subscriptions: String,
                           var others: String,
                          )


data class UpdateBudgetReqModel (var id: String, var rent: String,
                           var electricityBill: String,
                           var phoneBill: String, var internetBill: String,
                           var studentLoan: String,
                           var grocery: String, var gym: String,
                           var dineOut: String,
                           var savings: String, var subscriptions: String,
                           var others: String)

data class CreatetransactionReqModel (var amount: String, var title: String,var users : List<String>,var userId : String,var manual : Boolean)

data class CreatetransactionReqModel1 (var amount: String, var title: String,var users : List<CustomeAmountReqModel>,var userId : String,var manual : Boolean)

data class CustomeAmountReqModel (var id : String,var amount: String)
data class SetteltransactionReqModel (var transactionId : String)

data class CanceltransactionReqModel (var id : String)

data class GoalAmountReqModel (var id : String,var amount: String)