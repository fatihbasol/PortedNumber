/* eslint-disable */
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout justify-center>
        <v-dialog v-model="dialog" persistent xs12 sm6 md4>
            <v-card>
                <form>
                    <v-card-title>
                        <span class="headline">Güncelleme</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container grid-list-md>
                            <v-layout wrap>
                                <v-flex xs12 sm6 md4>
                                    <v-text-field
                                            v-model="serviceNumber"
                                            :error-messages="serviceNumberErrors"
                                            label="Hizmet Numarası"
                                            required
                                    ></v-text-field>
                                </v-flex>
                                <v-flex xs12 sm6 md4>
                                    <v-text-field disabled
                                                  v-model="rn"
                                                  label="Rn"
                                                  required
                                    ></v-text-field>
                                </v-flex>
                                <v-flex xs12 sm6 md4>
                                    <v-text-field
                                            v-model="portId"
                                            :error-messages="portIdErrors"
                                            label="Port Id"
                                            required
                                    ></v-text-field>
                                </v-flex>
                                <v-flex xs12 sm6 md4 outlined>
                                    <v-text-field
                                            v-model="activity"
                                            :error-messages="activityErrors"
                                            label="Aktivite"
                                            required
                                    ></v-text-field>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="warning darken-1" text @click="vazgec">Vazgeç</v-btn>
                        <v-btn :disabled="submitted" color="success darken-1" text @click="submit">Güncelle</v-btn>
                    </v-card-actions>
                </form>
            </v-card>
        </v-dialog>
        <template>
            <div class="text-center">
                <v-snackbar :color=snackbarColor :top="true" v-model="snackbar" :timeout="timeout">
                    {{ updateResultText }}
                </v-snackbar>
            </div>
        </template>
    </v-layout>
</template>
<script>
    import {EventBus} from "../../../main";
    import Vue from 'vue'
    import {validationMixin} from 'vuelidate'
    import {required} from 'vuelidate/lib/validators'


    export default {
        mixins: [validationMixin],
        validations: {
            serviceNumber: {required},
            portId: {required},
            activity: {required}
        },

        data: () => ({
            dialog: false,
            id: '',
            serviceNumber: '',
            createdDate:'',
            rn: '',
            portId: '',
            activity: '',
            selectedIndex: '',
            updateResult: -1,  // -1 default , 0 update success , 1 update failed
            // snackbar
            snackbar: false,
            text: '',
            timeout: 3000,
            snackbarColor: '',
            updateResultText: '',

            submitted: false,   // gönder butonu disable etmek için

        }),

        computed: {
            serviceNumberErrors() {
                const errors = []
                if (!this.$v.serviceNumber.$dirty) return errors
                !this.$v.serviceNumber.required && errors.push('Gereklidir.')
                return errors
            }
            ,
            portIdErrors() {
                const errors = []
                if (!this.$v.portId.$dirty) return errors
                !this.$v.portId.required && errors.push('Gereklidir.')
                return errors
            }
            ,
            activityErrors() {
                const errors = []
                if (!this.$v.activity.$dirty) return errors
                !this.$v.activity.required && errors.push('Gereklidir.')
                return errors
            }
        },

        methods: {

            submit() {
                this.$v.$touch();
                this.submitted = true;
                if (this.serviceNumber != "" && this.activity != "" && this.portId != "") {
                    this.$http.post("http://localhost:9090/ported-numbers/update-number", {
                        'id': this.id,
                        'rn': this.rn,
                        'serviceNumber': this.serviceNumber,
                        'portId': this.portId,
                        'activity': this.activity
                    }).then(response => {
                        //başarılı
                        this.updateResultText = "Güncelleme işlemi başarılı.";
                        this.snackbar = true;
                        this.updateResult = 1;
                        this.snackbarColor = "green lighten-2";
                        EventBus.$emit("numberDataFromIndex", {
                            'index' : this.selectedIndex,
                            'id':this.id,
                            'rn': this.rn,
                            'portId':this.portId,
                            'activity':this.activity,
                            'serviceNumber':this.serviceNumber,
                            'createdDate': this.createdDate,
                        });
                    }, response => {
                        //başarısız
                        this.updateResultText = "Güncelleme işlemi başarısız.";
                        this.updateResult = 0;
                        this.snackbar = true;
                        this.snackbarColor = "red lighten-2";
                        this.submitted = false;
                    })
                }
            },
            vazgec() {
                this.dialog = false;
                this.updateResultText = '';
                this.updateResult = 0;
            }
        },
        created() {
            EventBus.$on("updateDialog", () => {
                this.dialog = true;
            });
            EventBus.$on("updatableNumberData", (data) => {
                this.id = data.id;
                this.serviceNumber = data.serviceNumber;
                this.createdDate = data.createdDate;
                this.rn = data.rn;
                this.activity = data.activity;
                this.portId = data.portId;
            });
            EventBus.$on("selectedIndex", (selectedIndex) => {
                this.selectedIndex = selectedIndex;
            });
        },
        watch: {
            updateResult() {
                if (this.updateResult == 1) {
                    let vm = this;
                    setTimeout(function () {
                        vm.dialog = false;
                        vm.updateResult = 0;
                        vm.submitted = false;

                    }, 1000);
                }
            }
        }
    }
</script>

<style scoped>

</style>