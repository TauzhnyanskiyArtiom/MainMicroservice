<template>
  <v-layout>
    <v-list>
      <v-list-item>
        <user-link
            :user="this.user"
            size="48"
            textStyle="body-1"
        ></user-link>
      </v-list-item>
    </v-list>
  </v-layout>
</template>

<script>
import UserLink from "components/UserLink.vue"

export default {
  name: "SearchRow",
  props: ['user'],
  components: {UserLink},
}
</script>

<style scoped>

</style>