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
}
</script>

<style scoped>

</style>