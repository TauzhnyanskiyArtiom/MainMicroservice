<template>
  <v-app>
      <v-app-bar app
                 absolute
                 color="indigo darken-2"
                 dark
      >
        <v-toolbar-title class="text-h5">Continent</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn text
               v-if="profile"
               :disabled="$route.path === '/'"
               @click="showMessages"
               class="ml-5"
        >
          Messages
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn text
               v-if="profile"
               :disabled="$route.path === '/users'"
               @click="showSearch">
          Search
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn text
               v-if="profile"
               :disabled="$route.path === '/user'"
               @click="showProfile">
          {{profile.name}}
        </v-btn>

        <v-btn v-if="profile" icon href="/logout">
          <v-icon>exit_to_app</v-icon>
        </v-btn>
      </v-app-bar>
    <v-main>
      <v-container>
      <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>


export default {
  methods: {
    showMessages() {
      this.$router.push('/')
    },
    showProfile() {
      this.$router.push('/user')
    },
    showSearch(){
      this.$router.push('/users')
    }
  },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.replace('/auth')
    }
  }
}
</script>

<style>

</style>
