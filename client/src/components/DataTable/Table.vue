/* eslint-disable */
<template name="aaaw" xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <v-toolbar v-if="desserts.length>0" flat color="blue-grey darken-2" height="40px">
            <v-spacer></v-spacer>
            <v-btn small color="primary " dark @click="sonuclariTemizle()">
                Sonuçları Temizle
            </v-btn>
        </v-toolbar>
        <v-dialog>
            <v-card v-model="dialog" max-width="500px">
                <v-card-text>
                    <v-container grid-list-md>
                        <v-layout wrap>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="editedItem.serviceNumber" label="Hizmet No"></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="editedItem.rn" label="RN"></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="editedItem.createdDate" label="Taşıma Tarihi"></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm6 md4>
                                <v-text-field v-model="editedItem.portId" label="Port Id"></v-text-field>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
                    <v-btn color="blue darken-1" flat @click="save">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-data-table :headers="headers" :items="desserts" class="elevation-1">
            <template v-slot:items="props">
                <td class="text-xs-left">{{ props.item.serviceNumber }}</td>
                <td class="text-xs-left" v-if="props.item.rn!=null">{{ props.item.rn}}</td>
                <td v-else>Bilgi Boş</td>
                <td class="text-xs-left">{{ props.item.createdDate | filterDate}}</td>
                <td class="text-xs-left">{{ props.item.portId}}</td>
                <td class="justify-center layout px-0">
                    <v-icon small class="mr-2" @click="editItem(props.item)"> edit</v-icon>
                    <v-icon small @click="deleteItem(props.item)"> delete</v-icon>
                </td>
            </template>
            <template v-slot:no-data>
                <p align="center">Gösterilecek sonuç bulunmuyor</p>
            </template>
        </v-data-table>
        <app-update-number></app-update-number>
    </div>
</template>
<script>
    import {EventBus} from "../../main";
    import UpdateNumber from "./Childs/UpdatePortedNumber"
    import Vue from 'vue'


    import filters from '../../main'

    export default {
        components: {
            appUpdateNumber: UpdateNumber
        },
        filters: {
            filters
        },
        data: () => ({

            dialog: false,
            headers: [
                {text: 'Hizmet No', value: 'serviceNumber'},
                {text: 'RN', value: 'rn'},
                {text: 'Taşıma Tarihi', value: 'createdDate'},
                {text: 'Port Id', value: 'portId'},
                {text: 'Actions', value: 'actionButtons', sortable: false}
            ],
            desserts: [],
            editedIndex: -1,
            numberDataFromIndex: [],
            editedItem: {
                id: '',
                serviceNumber: '',
                rn: 0,
                activity: 0,
                portId: 0,
            },
            defaultItem: {
                id: '',
                serviceNumber: '',
                rn: 0,
                activity: 0,
                portId: 0,
            },


        }),
        watch: {
            dialog(val) {
                val || this.close()
            },
        },
        created() {
            this.initialize();

        },
        methods: {
            sonuclariTemizle() {
                return this.desserts = [];
            },
            initialize() {
                EventBus.$on('filteredPortedNumber', (portedNumbers) => {
                    this.desserts = []; // arraye sürekli pushlamasın diye boşaltıyorum
                    for (let key in portedNumbers) {

                        if (portedNumbers[key].recipientOperator != null) {
                            this.desserts.push({
                                id: portedNumbers[key].id,
                                serviceNumber: portedNumbers[key].serviceNumber,
                                rn: portedNumbers[key].recipientOperator.rn,
                                activity: portedNumbers[key].activity,
                                createdDate: portedNumbers[key].createdDate,
                                portId: portedNumbers[key].portId
                            })
                        } else {
                            this.desserts.push({
                                id: portedNumbers[key].id,
                                serviceNumber: portedNumbers[key].serviceNumber,
                                rn: portedNumbers[key].recipientOperator,
                                activity: portedNumbers[key].activity,
                                createdDate: portedNumbers[key].createdDate,
                                portId: portedNumbers[key].portId
                            })
                        }
                    }
                });
                EventBus.$on('tumKayitlar', (gelen) => {
                    this.desserts = [];  // arraye sürekli pushlamasın diye boşaltıyorum

                    for (let key in gelen) {
                        if (gelen[key].recipientOperator != null) {
                            this.desserts.push({
                                id: gelen[key].id,
                                serviceNumber: gelen[key].serviceNumber,
                                rn: gelen[key].recipientOperator.rn,
                                activity: gelen[key].activity,
                                createdDate: gelen[key].createdDate,
                                portId: gelen[key].portId
                            })
                        } else {
                            this.desserts.push({
                                id: gelen[key].id,
                                serviceNumber: gelen[key].serviceNumber,
                                rn: gelen[key].rn,
                                activity: gelen[key].activity,
                                createdDate: gelen[key].createdDate,
                                portId: gelen[key].portId
                            })
                        }

                    }

                });
                EventBus.$on("numberDataFromIndex", (numberDataFromIndex) => {
                    Vue.set(this.desserts, numberDataFromIndex.index, {
                        'id': numberDataFromIndex.id,
                        'serviceNumber': numberDataFromIndex.serviceNumber,
                        'rn': numberDataFromIndex.rn,
                        'portId': numberDataFromIndex.portId,
                        'activity': numberDataFromIndex.activity,
                        'createdDate': numberDataFromIndex.createdDate,
                    });

                });

            },
            editItem(item) {
                this.editedIndex = this.desserts.indexOf(item);
                this.editedItem = Object.assign({}, item);
                this.dialog = true;
                EventBus.$emit("updateDialog", true);
                EventBus.$emit("selectedIndex", this.editedIndex);
                EventBus.$emit("updatableNumberData", this.editedItem);
            },
            deleteItem(item) {
                const index = this.desserts.indexOf(item);

                if (confirm('Taşınmış numara silme işlemini onaylıyor musunuz?')) {
                    this.$http.post("http://localhost:9090/ported-numbers/delete-number", {
                        'id': this.desserts[index].id
                    }, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });
                    this.desserts.splice(index, 1);
                }

            },
            close() {
                this.dialog = false;
                setTimeout(() => {
                    this.editedItem = Object.assign({}, this.defaultItem);
                    this.editedIndex = -1
                }, 300)
            },
            save() {
                if (this.editedIndex > -1) {
                    Object.assign(this.desserts[this.editedIndex], this.editedItem)
                } else {
                    this.desserts.push(this.editedItem)
                }
                this.close()
            }
        },
        mounted() {
            // this.initialize();
        }
    }
</script>