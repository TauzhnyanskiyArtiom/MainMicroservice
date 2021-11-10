<template>
  <v-layout row>
    <v-text-field
        label="Name user"
        placeholder="Write something"
        v-model="text"
        @keyup.enter="search"
    />
    <v-btn @click="search">
      Search
    </v-btn>
  </v-layout>
</template>

<script>
import Vue from "vue";

export default {
  name: "SearchForm",
  props: ['users'],
  data() {
    return {
      text: '',
    }
  },
  methods: {
    search() {
      Vue.http.get('/api/search/users/name',{params: { prefixName:this.text }}).then(result =>
          result.json().then(data => {
            this.users.splice(0, this.users.length)
            this.users.push(...data)
          })
      )
    }
  }
}
</script>

<style scoped>

</style>