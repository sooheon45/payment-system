
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import PaymentPaymentManager from "./components/listers/PaymentPaymentCards"
import PaymentPaymentDetail from "./components/listers/PaymentPaymentDetail"

import PaymentReceiptView from "./components/PaymentReceiptView"
import PaymentReceiptViewDetail from "./components/PaymentReceiptViewDetail"

export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/payments/payments',
                name: 'PaymentPaymentManager',
                component: PaymentPaymentManager
            },
            {
                path: '/payments/payments/:id',
                name: 'PaymentPaymentDetail',
                component: PaymentPaymentDetail
            },

            {
                path: '/payments/paymentReceipts',
                name: 'PaymentReceiptView',
                component: PaymentReceiptView
            },
            {
                path: '/payments/paymentReceipts/:id',
                name: 'PaymentReceiptViewDetail',
                component: PaymentReceiptViewDetail
            },


    ]
})
