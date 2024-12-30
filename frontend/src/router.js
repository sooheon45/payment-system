
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import RequestPaymentPaymentManager from "./components/listers/RequestPaymentPaymentCards"
import RequestPaymentPaymentDetail from "./components/listers/RequestPaymentPaymentDetail"




export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/requestPayments/payments',
                name: 'RequestPaymentPaymentManager',
                component: RequestPaymentPaymentManager
            },
            {
                path: '/requestPayments/payments/:id',
                name: 'RequestPaymentPaymentDetail',
                component: RequestPaymentPaymentDetail
            },





    ]
})
