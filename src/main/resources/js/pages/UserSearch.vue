<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <search-form :users="users"/>
      <search-row v-for="user in users" :key="user.name" :user="user"/>
    </v-layout>
  </v-container>
</template>

<script>
import SearchRow from 'components/search/SearchRow.vue'
import SearchForm from "components/search/SearchForm.vue";
import Vue from "vue";

export default {
  name: "Users",
  components: {
    SearchRow,
    SearchForm
  },
  data() {
    return {
      users: [],
    }
  },
  beforeMount() {
    Vue.http.get('/api/search/users').then(result =>
        result.json().then(data => {
          this.users.splice(0, this.users.length)
          this.users.push(...data)
        })
    )
  }
}
</script>

<style scoped>

</style>