<template>
    <div>
        <v-card
            class="mx-auto"
            outlined
            color="primary"
            style="padding:10px 0px 10px 0px; margin-bottom:40px;"
        >
            <v-row>
                <v-list-item class="d-flex" style="background-color: white;">
                    <h1 class="align-self-center ml-3">Payment</h1>
                    <div class="secondary-text-color" style="margin-left:30px;"></div>
                </v-list-item>
            </v-row>
        </v-card>
        <v-col>
            <div>
                <!-- 모드 종류 : receipt(영수증 조회), pay(결제정보), receipt(환불관련),paymentDetail()  -->
                <Payment
                    :serviceType="'pay'" 
                    :paymentDetail="false"
                    :editMode="false"
                    :requestInfo="requestInfo"
                />
            </div>
        </v-col>
        <v-row>
            <Payment :offline="offline" class="video-card" v-for="(value, index) in values" v-model="values[index]" v-bind:key="index" @delete="remove"/>
        </v-row>
    </div>
</template>

<script>

    const axios = require('axios').default;
    import Payment from './Payment';

    export default {
        name: 'RequestPaymentPaymentManager',
        components: {
            Payment,
        },
        props: {
            offline: Boolean
        },
        data: () => ({
            values: [],
            newValue: {},
            tick : true,
            // paymentDetail : false,
            requestInfo: {
                orderId: '',
                price: 0,
                name: '',
                buyerId: '',
                buyerName: '',
                buyerTel: '',
                buyerEmail: '',
                reason: ''
            },
        }),
        async created() {
            var me = this;
            if(me.offline){
                if(!me.values) me.values = [];
                return;
            } 

            var temp = await axios.get(axios.fixUrl('/payments'))
            me.values = temp.data._embedded.payments;
            
            me.newValue = {
                'amount': 0,
                'issuedDate': '2024-12-30',
                'approvalDate': '2024-12-30',
                'refunedDate': '2024-12-30',
            }
        },
        methods:{
            setRequestInfo() {
                this.requestInfo = {
                    orderId: '2',
                    price: 129000,
                    name: '키보드',
                    buyerId: 'kibum',
                    buyerName: '박기범',
                    buyerTel: '010-0000-0000',
                    buyerEmail: 'kibum0405@gmail.com',
                    reason: ''
                };
            },
            append(value){
                this.tick = false
                this.newValue = {}
                this.values.push(value)
                
                this.$emit('input', this.values);

                this.$nextTick(function(){
                    this.tick=true
                })
            },
            remove(value){
                var where = -1;
                for(var i=0; i<this.values.length; i++){
                    if(this.values[i]._links.self.href == value._links.self.href){
                        where = i;
                        break;
                    }
                }

                if(where > -1){
                    this.values.splice(i, 1);
                    this.$emit('input', this.values);
                }
            },
        }
    };
</script>


<style>
    .video-card {
        width:300px; 
        margin-left:4.5%; 
        margin-top:50px; 
        margin-bottom:50px;
    }
</style>

