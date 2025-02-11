<template>
    <div>
        <v-btn 
            class="mx-auto"
            @click="openDialog = true"
            color="primary"
            text
        >
            {{ buttonLabel }}
        </v-btn>
        <v-dialog width="400" v-model="openDialog">
            <v-card>
                <!-- 결제 -->
                <div v-if="serviceType == 'pay'"
                    style="width:100%;"
                >
                    <v-row class="ma-0 pa-4 pb-0">
                        <v-card-title class="pa-0">결제하기</v-card-title>
                        <v-spacer></v-spacer>
                        <v-icon @click="closeDialog()">mdi-close</v-icon>
                    </v-row>
                    <v-card-text class="payment-dialog-scroll">
                        <v-card class="pa-4 mb-2" outlined>
                            <div class="sub-title">상품 정보</div>
                            <h3 v-if="requestInfo.name" class="pb-2">{{ requestInfo.name }}</h3>
                            <v-row class="ma-0 pa-0">
                                <div class="sub-text">
                                    <div>결제 번호 : {{ paymentId }}</div>
                                    <div>주문 번호 : {{ requestInfo.orderId }}</div>
                                </div>
                            </v-row>
                        </v-card>
                        <!-- pay 디테일 -->
                        <v-card v-if="buyerInfoMode" class="pa-4 pb-0" outlined>
                            <div class="sub-title">구매자 정보 입력</div>
                            <v-text-field 
                                dense 
                                outlined 
                                v-model="requestInfo.buyerName" 
                                label="구매자 성명" 
                                :rules="[v => !!v || '구매자 성명을 입력하세요.']"
                            />
                            <v-text-field 
                                dense 
                                outlined 
                                v-model="requestInfo.buyerEmail" 
                                label="구매자 이메일" 
                                :rules="[v => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v) || '유효한 이메일을 입력하세요.']"
                            />
                            <v-text-field 
                                dense 
                                outlined 
                                v-model="requestInfo.buyerId" 
                                label="구매자 아이디" 
                                :rules="[v => !!v || '구매자 아이디를 입력하세요.']"
                            />
                            <v-text-field 
                                dense 
                                outlined 
                                v-model="requestInfo.buyerTel" 
                                label="구매자 전화번호" 
                                :rules="[v => /^(\d{2,3})-?(\d{3,4})-?(\d{4})$/.test(v) || '유효한 전화번호를 입력하세요.']"
                            />
                        </v-card>
                    </v-card-text>
                    <v-row class="sub-title pa-4 pb-0 pt-0 ma-0">
                        <div style="font-size:20px;">결제금액</div>
                        <v-spacer></v-spacer>
                        <div style="font-size:20px;
                        font-weight: 700"
                        >{{ formattedPrice }}원</div>
                    </v-row>
                    <div class="pa-4">
                        <v-btn @click="pay()"
                            color="primary"
                            :disabled="!isFormValid"
                            style="width:100%;"
                        >결제</v-btn>
                    </div>
                </div>

                <!-- 환불 -->
                <div v-if="serviceType == 'refund'">
                    <v-row class="ma-0 pa-4 pb-0">
                        <v-card-title class="pa-0">환불하기</v-card-title>
                        <v-spacer></v-spacer>
                        <v-icon @click="closeDialog()">mdi-close</v-icon>
                    </v-row>
                        <v-card-text class="payment-dialog-scroll">
                            <v-card class="pa-4 mb-2" outlined>
                                <div class="sub-title">환불 정보 입력</div>
                                <v-text-field v-model="requestInfo.paymentId"
                                    dense 
                                    outlined
                                    required 
                                    label="환불 주문번호 입력" 
                                />
                                <v-textarea v-model="requestInfo.reason"
                                    class="delete-input-detail"
                                    dense
                                    outlined
                                    required
                                    label="환불 사유"
                                    auto-grow
                                    :rows="3"
                                />
                        </v-card>
                    </v-card-text>

                    <v-row class="sub-title pa-4 pb-0 pt-0 ma-0">
                        <div style="font-size:20px;">환불 금액</div>
                        <v-spacer></v-spacer>
                        <div style="font-size:20px;
                        font-weight: 700"
                        >{{ formattedPrice }}원</div>
                    </v-row>
                    <div class="pa-4">
                        <v-btn @click="refund()"
                            color="primary"
                            style="width:100%;"
                        >환불</v-btn>
                    </div>
                </div>

                <!-- 영수증 조회 -->
                <div v-if="serviceType == 'receipt'">
                    <v-row class="ma-0 pa-4 pb-0">
                        <v-card-title class="pa-0">영수증 조회</v-card-title>
                        <v-spacer></v-spacer>
                        <v-icon @click="closeDialog()">mdi-close</v-icon>
                    </v-row>
                    <div class="payment-dialog-scroll">
                        <div class="pa-4 pb-0">
                            <v-text-field v-model="requestInfo.paymentId"
                                class="delete-input-detail"
                                label="조회 주문번호 입력"
                                outlined
                                required 
                                dense
                            />
                        </div>
                        <v-card-text v-if="isReceiptCompleted" class="pb-0">
                            <v-card class="pa-4 mb-2" outlined>
                                <v-row class="sub-text ma-0 pa-0">
                                    <div style="font-weight: 500;">상품명</div>
                                    <v-spacer></v-spacer>
                                    <div>{{ receiptInfo.name }}</div>
                                </v-row>
                                <v-row class="sub-text ma-0 pa-0 mt-4">
                                    <div style="font-weight: 500;">가격</div>
                                    <v-spacer></v-spacer>
                                    <div>{{ receiptInfo.price }}</div>
                                </v-row>
                            </v-card>

                            <v-card v-if="buyerInfoMode"
                                class="pa-4"
                                outlined
                            >
                                <v-row class="sub-text ma-0 pa-0">
                                    <div style="font-weight: 500;">구매자 성명</div>
                                    <v-spacer></v-spacer>
                                    <div>{{ receiptInfo.buyerName }}</div>
                                </v-row>
                                <v-row class="sub-text ma-0 pa-0 mt-4">
                                    <div style="font-weight: 500;">구매자 이메일</div>
                                    <v-spacer></v-spacer>
                                    <div>{{ receiptInfo.buyerEmail }}</div>
                                </v-row>
                                <v-row class="sub-text ma-0 pa-0 mt-4">
                                    <div style="font-weight: 500;">구매자 아이디</div>
                                    <v-spacer></v-spacer>
                                    <div>{{ receiptInfo.buyerId }}</div>
                                </v-row>
                                <v-row class="sub-text ma-0 pa-0 mt-4">
                                    <div style="font-weight: 500;">구매자 전화번호</div>
                                    <v-spacer></v-spacer>
                                    <div>{{ receiptInfo.buyerTel }}</div>
                                </v-row>
                            </v-card>
                        </v-card-text>
                    </div>
                    <div class="pa-4">
                        <v-btn @click="search()"
                            color="primary"
                            style="width:100%;"
                        >조회</v-btn>
                    </div>
                </div>   
            </v-card>
        </v-dialog>
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
            },
            buyerInfoMode: Boolean,
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
            isReceiptCompleted: false,
            openDialog: false,
        }),
        async created() {
            // var me = this;
        },
        computed: {
            buttonLabel() {
                switch (this.serviceType) {
                    case 'pay':
                        return '결제';
                    case 'refund':
                        return '환불';
                    case 'receipt':
                        return '영수증 조회';
                    default:
                        return '결제';
                }
            },
            formattedPrice() {
                return this.requestInfo.price.toLocaleString('ko-KR');
            },
            isFormValid() {
                if (!this.buyerInfoMode) {
                    return true; // buyerInfoMode가 false일 때는 항상 true 반환
                }
                return !!this.requestInfo.buyerName &&
                    /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.requestInfo.buyerEmail) &&
                    !!this.requestInfo.buyerId &&
                    /^(\d{2,3})-?(\d{3,4})-?(\d{4})$/.test(this.requestInfo.buyerTel); // 수정된 전화번호 정규식
            },
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
                            status: "SUCCESS",
                            reason: response.message
                        }
                    )
                    alert(`결제 성공`)
                } else {
                    await axios.put(`http://localhost:8088/payments/${me.requestInfo.orderId}/receivepaymentstatus`,
                        {
                            id: me.requestInfo.orderId,
                            paymentId: me.paymentId,
                            status: "SUCCESS",
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
            closeDialog() {
                this.openDialog = false;
            }
        }
    };
</script>
<style>
    .payment-dialog-scroll {
        max-height: 65vh;
        overflow: auto;
    }
    .sub-text {
        font-size: 12px;
        font-weight: 700;
        width: 100%;
        color: #666666;
    }
    .sub-title {
        font-size: 14px;
        font-weight: 700;
        width: 100%;
        margin-bottom:16px;
    }
</style>

