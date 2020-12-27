/* eslint-disable */
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container>
        <v-layout text-xs-center wrap>
            <template>
                <v-form>
                    <v-container>
                        <v-layout row wrap>
                            <v-flex xs3 md3>
                                <v-text-field clearable v-model="rn" label="RN" required></v-text-field>
                            </v-flex>
                            <v-flex xs3 md3>
                                <v-text-field clearable v-model="hizmetNo" label="Hizmet No" required></v-text-field>
                            </v-flex>
                            <v-flex xs3 md3>
                                <v-text-field clearable v-model="portId" label="Port Id" required></v-text-field>
                            </v-flex>
                            <v-flex xs3 md3>
                                <v-select clearable v-model="aktivite" :items="items" label="Aktivite Adı"></v-select>
                            </v-flex>
                            <v-flex xs2 md2>
                                <!--                             Başlangıç Tarih-->
                            </v-flex>
                            <v-flex xs2 md2>
                                <!--                        Bitiş Tarih-->
                            </v-flex>
                            <v-flex>
                                <v-btn @click="sorgula" outline small color="success">Sorgula
                                    <v-icon dark right>search</v-icon>
                                </v-btn>
                                <v-btn @click="temizle" outline small color="warning">Temizle
                                    <v-icon dark right>delete</v-icon>
                                </v-btn>
                            </v-flex>
                            <v-flex>
                                <app-new-ported-number></app-new-ported-number>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-form>
            </template>
        </v-layout>
        <app-table></app-table>

    </v-container>
</template>

<script>

    import table from './DataTable/Table'
    import newPortedNumber from './DataTable/Childs/NewPortedNumber'
    import {EventBus} from "../main";

    export const globalUrl = "http://localhost:9090";

    export default {
        components: {
            appTable: table,
            appNewPortedNumber: newPortedNumber
        },
        data: () => ({
            rn: null,
            hizmetNo: null,
            portId: null,
            aktivite: null,
            items: [
                'Port',
                'PORT_DISCONNECT'
            ],

        }),

        methods: {

            temizle() {
                this.aktivite = null;
                this.rn = null;
                this.hizmetNo = null;
                this.portId = null;
            },
            sorgula() {

                if (
                    this.aktivite == null &&
                    this.rn == null &&
                    this.hizmetNo == null &&
                    this.portId == null
                ) {
                    this.$http.get(globalUrl + '/ported-numbers/all').then(response => {
                        EventBus.$emit("tumKayitlar", response.data);

                    }, response => {
                        console.log(Error("Get method error."))
                    });
                } else {
                    this.$http.post(globalUrl + '/ported-numbers/filter', {
                        'rn': this.rn,
                        'portId': this.portId,
                        'activity': this.aktivite,
                        'serviceNumber': this.hizmetNo
                    }).then(response => {
                        EventBus.$emit("filteredPortedNumber", response.data)
                    }, response => {
                        console.log(Error("Hata."))
                    });
                }
            },
        },
    }
</script>

<style>

</style>
