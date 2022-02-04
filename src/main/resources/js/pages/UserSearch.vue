<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <v-layout row>
        <v-text-field
            label="Name user"
            placeholder="Write something"
            v-model="prefixName"
        />
      </v-layout>
      <search-row v-for="user in filteredUsers" :key="user.name" :user="user"/>
    </v-layout>
  </v-container>
</template>

<script>
import SearchRow from 'components/search/SearchRow.vue'
import Vue from "vue";

export default {
  name: "Users",
  components: {
    SearchRow
  },
  data() {
    return {
      users: [],
      prefixName: ''
    }
  },
  computed: {
    filteredUsers() {
      if (this.prefixName === '')
        return this.users
      return this.users.filter(user => user.name.toLowerCase().includes(this.prefixName.toLowerCase()))
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