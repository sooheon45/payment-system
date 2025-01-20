<template>
    <div>
        <v-card v-if="serviceType == 'pay'" style="width: 500px;">
            <v-card-title> 결제하기 </v-card-title>
            <v-card-text>
                <v-text-field disabled v-model="paymentId" label="결제 번호"/>

                <v-text-field disabled v-model="requestInfo.orderId" label="주문 번호" />
                <v-text-field required v-model="requestInfo.name" label="상품명" />
                <v-text-field required v-model="requestInfo.price" label="가격" />
             
                <v-text-field v-model="requestInfo.buyerName" label="구매자 성명" />
                <v-text-field v-model="requestInfo.buyerEmail" label="구매자 이메일" />
                <v-text-field v-model="requestInfo.buyerId" label="구매자 아이디" />
                <v-text-field v-model="requestInfo.buyerTel" label="구매자 전화번호" />
            </v-card-text>
            <v-card-actions>
                <v-btn @click="pay()"> 결제 </v-btn>
                <v-btn @click="close()"> 닫기 </v-btn>
            </v-card-actions>
        </v-card>
        <v-card v-if="serviceType == 'refund'" style="width: 500px;">
            <v-card-title> 환불하기 </v-card-title>
            <v-card-text>
                <v-text-field required v-model="requestInfo.paymentId" label="환불 주문번호 입력" />
                <v-text-field required v-model="requestInfo.price" label="환불 가격" />
                <v-text-field required v-model="requestInfo.reason" label="환불 사유"/>
            </v-card-text>
            <v-card-actions>
                <v-btn @click="refund()"> 환불 </v-btn>
                <v-btn @click="close()"> 닫기 </v-btn>
            </v-card-actions>
        </v-card>
        <v-card v-if="serviceType == 'receipt'" style="width: 500px;">
            <v-card-title> 영수증 조회 </v-card-title>
            <v-card-text>
                <v-text-field required v-model="requestInfo.paymentId" label="조회 주문번호 입력" />
            </v-card-text>
            <v-card-text v-if="isReceiptCompleted">
                <v-text-field disabled v-model="receiptInfo.name" label="상품명"/>
                <v-text-field disabled v-model="receiptInfo.price" label="가격"/>
                <v-text-field disabled v-model="receiptInfo.buyerName" label="구매자 성명" />
                <v-text-field disabled v-model="receiptInfo.buyerEmail" label="구매자 이메일" />
                <v-text-field disabled v-model="receiptInfo.buyerId" label="구매자 아이디" />
                <v-text-field disabled v-model="receiptInfo.buyerTel" label="구매자 전화번호" />
            </v-card-text>
            <v-card-actions>
                <v-btn @click="search()"> 조회 </v-btn>
                <v-btn @click="close()"> 닫기 </v-btn>
            </v-card-actions>
        </v-card>   
    </div>
</template>

<script>
    const axios = require('axios').default;
    // import { v4 as uuidv4 } from 'uuid'; 
    // import $ from 'jquery'; // jQuery를 import
    import * as PortOne from "@portone/browser-sdk";

    export default {
        name: 'payment-service',
        props: {
            serviceType: String,
            requestInfo: {
                type: Object,
                default: () => ({
                    orderId: "",
                    price: 0,
                    name: "",
                    buyerId: "",
                    buyerName: "",
                    buyerTel: "",
                    buyerEmail: "",
                    reason: ""
                })
            }
        },
        data: () => ({
            paymentId: `payment-${crypto.randomUUID()}`,
            receiptInfo: {
                name: "",
                price: "",
                buyerName: "",
                buyerEmail: "",
                buyerId: "",
                buyerTel: ""
            },
            isReceiptCompleted: false
        }),
        async created() {
            // var me = this;
            
        },
        methods:{
            async pay(){
                var me = this
                const response = await PortOne.requestPayment({                    // Store ID 설정
                    storeId: "store-acb53ee1-ef4e-4966-b341-8c6a36d81096",
                    channelKey: "channel-key-c89519b6-bf42-4aac-b23a-cf92d4a28a6b",
                    paymentId: me.paymentId,
                    orderName: me.requestInfo.name,
                    totalAmount: me.requestInfo.price,
                    currency: "CURRENCY_KRW",
                    payMethod: "CARD",
                    buyer: {
                        id: me.requestInfo.buyerId,
                        name: me.requestInfo.buyerName,
                        email: me.requestInfo.buyerEmail,
                        tel: me.requestInfo.buyerTel
                    }
                });


                if (response.success) {
                    await axios.put(`http://localhost:8088/payments/${me.requestInfo.orderId}/receivepaymentstatus`,
                        {
                            id: me.requestInfo.orderId,
                            paymentId: me.paymentId,
                            status: "SUCESS",
                            reason: response.message
                        }
                    )
                    alert(`결제 성공`)
                } else {
                    await axios.put(`http://localhost:8088/payments/${me.requestInfo.orderId}/receivepaymentstatus`,
                        {
                            id: me.requestInfo.orderId,
                            paymentId: me.paymentId,
                            status: "SUCESS",
                            reason: response.message
                        }
                    )
                    alert(`결제 성공`)
                    // await axios.put(`http://localhost:8088/payments/${me.requestInfo.orderId}/receivepaymentstatus`,
                    //     {
                    //         id: me.requestInfo.orderId,
                    //         paymentId: me.paymentId,
                    //         status: "CANCELLED",
                    //         reason: response.message
                    //     }
                    // )
                    // alert(`결제 실패 : ${response.message}`)
                }
            },  
            async refund(){
                var me = this
                try {
                    let response = await axios.post(`https://api.portone.io/payments/${me.requestInfo.paymentId}/cancel`, {'Content-Type': 'application/json'}, {
                        reason: me.requestInfo.reason
                    })
                    if(response.status == 200) {
                        alert("환불 완료")
                    } else {
                        alert("환불 실패")
                    }
                }catch(e) {
                    alert("환불 실패")
                }
            },
            async search(){
                var me = this
                try {
                    me.isReceiptCompleted = false
                    let response = await axios.get(`https://api.portone.io/payments/payments/${me.requestInfo.paymentId}`,{'Content-Type': 'application/json'})
                    if(response.status == 200) {
                        me.receiptInfo.name = response.orderName
                        me.receiptInfo.price = response.amount.paid
                        me.receiptInfo.buyerName = response.customer.name
                        me.receiptInfo.buyerEmail = response.customer.email
                        me.receiptInfo.buyerTel = response.customer.phoneNumber
                        me.isReceiptCompleted = true
                    } else {
                        alert(`조회 실패 : ${response.message}`)
                    }
                   
                }catch(e) {
                    alert("조회 실패")
                }
            },
            close(){
                this.$emit('close');
            }
        }
    };
</script>

