<template>
    <v-app>
        <v-toolbar app  height="40" >
            <v-toolbar-title class="headline ">
                <span class="font-weight-bolder">Taşınmış Numara Yönetim Paneli</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>
        </v-toolbar>
        <v-content>
            <app-home/>
        </v-content>
    </v-app>
</template>

<script>
    import Home from './components/Home'

    export default {
        name: 'App',
        components: {
            appHome: Home
        },
        data() {
            return {}
        }
    }
</script>
<style>


</style>
