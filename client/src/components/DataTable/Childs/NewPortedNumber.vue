<template>
    <v-layout justify-center>
        <v-btn @click="yeniKayit" small outline color="info">Yeni Kayıt
            <v-icon>add</v-icon>
        </v-btn>
        <v-dialog v-model="dialog" persistent>
            <v-card>
                <v-card-title>
                    <span class="headline">Yeni Kayıt</span>
                </v-card-title>
                <v-card-text>
                    <v-container grid-list-md>
                        <v-layout wrap>
                            <v-flex md4>
                                <v-text-field :error-messages="serviceNumberErrors" v-model="serviceNumber"
                                              label="Hizmet Numarası"></v-text-field>
                            </v-flex>
                            <v-flex md4>
                                <v-text-field :error-messages="portIdErrors" v-model="portId"
                                              label="Port Id"></v-text-field>
                            </v-flex>
                            <v-flex md4>
                                <v-select :error-messages="rnErrors" clearable v-model="rn" :items="rnItems"
                                          label="İşletmeci RN"></v-select>
                            </v-flex>
                            <v-flex md4>

                                <v-text-field :error-messages="activityErrors" v-model="activity"
                                              label="Aktivite Adı"></v-text-field>
                            </v-flex>
                            <v-flex md4>
                                <v-text-field :error-messages="routeNumberErrors" v-model="routeNumber"
                                              label="Route Number"></v-text-field>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn :disabled="submitted" @click="submit" outline small color="success">Ekle</v-btn>
                    <v-btn :disabled="submitted" @click="vazgec" outline small color="warning">Vazgeç</v-btn>
                </v-card-actions>
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
    import {validationMixin} from 'vuelidate'
    import {required} from 'vuelidate/lib/validators'
    import {EventBus} from "../../../main";

    export default {
        name: "NewPortedNumber",
        mixins: [validationMixin],
        validations: {
            serviceNumber: {required},
            portId: {required},
            activity: {required},
            rn: {required},
            routeNumber: {required}

        },
        data: () => ({
            dialog: false,
            serviceNumber: '',
            portId: '',
            rn: '',
            activity: '',
            routeNumber: '',
            rnItems: [],
            submitted: false,
            // snackbar
            snackbar: false,
            text: '',
            timeout: 3000,
            snackbarColor: '',
            updateResultText: '',
        }),
        methods: {
            yeniKayit() {
                this.$http.get("http://localhost:9090/operators/all").then(response => {
                    for (let index in response.data) {
                        this.rnItems.push(response.data[index].rn);
                    }
                    this.rnItems.sort()
                }, response => {

                })
                this.dialog = true
            },
            submit() {
                this.$v.$touch();
                this.submitted = true;
                if (
                    this.serviceNumber !== '' &&
                    this.portId !== '' &&
                    this.rn !== '' &&
                    this.activity !== '' &&
                    this.routeNumber !== '') {
                    this.$http.post("http://localhost:9090/ported-numbers/add-number", {
                        'serviceNumber': this.serviceNumber,
                        'portId': this.portId,
                        'rn': this.rn,
                        'activity': this.activity,
                        'routeNumber': this.routeNumber
                    }).then(response => {
                        this.submitted = true;
                        this.updateResultText = "Yeni kayıt eklendi.";
                        this.snackbar = true;
                        this.snackbarColor = "green lighten-2";
                        this.$http.get('http://localhost:9090/ported-numbers/all').then(response => {
                            EventBus.$emit("tumKayitlar", response.data);

                        });
                        let vm = this;
                        setTimeout(function () {
                            vm.dialog = false;
                            vm.submitted = false;
                            // EventBus.$emit("addedNewNumber",true);
                        }, 1000);
                    }, response => {
                        this.updateResultText = "Kayıt işlemi başarısız oldu.";
                        this.snackbar = true;
                        this.snackbarColor = "red lighten-2";
                        let vmv = this;
                        setTimeout(function () {
                            vmv.submitted = false;

                        }, 1500);

                        console.log("hata")
                    })
                }
            },
            vazgec() {
                this.dialog = false
            }
        },
        computed: {
            serviceNumberErrors() {
                const errors = []
                if (!this.$v.serviceNumber.$dirty) return errors
                !this.$v.serviceNumber.required && errors.push('Gereklidir.');
                return errors
            },
            portIdErrors() {
                const errors = []
                if (!this.$v.portId.$dirty) return errors
                !this.$v.portId.required && errors.push('Gereklidir.')
                return errors
            },
            activityErrors() {
                const errors = []
                if (!this.$v.activity.$dirty) return errors
                !this.$v.activity.required && errors.push('Gereklidir.')
                return errors
            },
            rnErrors() {
                const errors = []
                if (!this.$v.rn.$dirty) return errors
                !this.$v.rn.required && errors.push('Gereklidir.')
                return errors
            },
            routeNumberErrors() {
                const errors = []
                if (!this.$v.routeNumber.$dirty) return errors
                !this.$v.routeNumber.required && errors.push('Gereklidir.')
                return errors
            }
        }
    }
</script>

<style scoped>

</style>