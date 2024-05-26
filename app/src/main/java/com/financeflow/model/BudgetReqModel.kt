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
                           var others: String,
)


data class CreatetransactionReqModel (var amount: String, var title: String,var users : List<String>,var userId : String)


data class SetteltransactionReqModel (var transactionId : String)