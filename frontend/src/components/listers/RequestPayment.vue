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
                    <h1 class="align-self-center ml-3">결제</h1>
                    <div class="secondary-text-color" style="margin-left:30px;"></div>
                </v-list-item>
            </v-row>
        </v-card>
        <v-col style="margin-bottom:40px;">
            <payment serviceType="pay" :requestInfo="info"></payment>
        </v-col>
    </div>
</template>

<script>
    import PaymentService from './PaymentService.vue';

    export default {
        name: 'RequestPaymentPaymentManager',
        components: {
            'payment': PaymentService
        },
        props: {
            offline: Boolean
        },
        data: () => ({
            info: {}
        }),
        async created() {
            this.info = {
                price : 10000, // 결제 가격
                name : "상품명", // 결제 상품명
                buyerId : "buyerId", // 결제 구매자 아이디
                buyerName : "구매자명", // 결제 구매자 이름
                buyerTel: "010-1234-5678", // 결제 구매자 전화번호
                buyerEmail: "buyerEmail@example.com", // 결제 구매자 이메일
            }
        },
        methods:{

            
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

