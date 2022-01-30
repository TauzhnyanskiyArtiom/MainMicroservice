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
        {{ profile.name }}
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


import {addHandler} from "../util/ws";

export default {
  methods: {
    showMessages() {
      this.$router.push('/')
    },
    showProfile() {
      this.$router.push('/user')
    },
    showSearch() {
      this.$router.push('/users')
    }
  },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  created() {
    addHandler(data => {
      if (data.objectType === 'MESSAGE') {
        const index = this.messages.findIndex(item => item.id === data.body.id)
        switch (data.eventType) {
          case 'CREATE':
          case 'UPDATE':
            if (index > -1) {
              this.messages.splice(index, 1, data.body)
            } else {
              this.messages.push(data.body)
            }
            break
          case 'REMOVE':
            if (index > -1)
              this.messages.splice(index, 1)
            break
          default:
            console.error(`Looks like the event type if unknown "${data.eventType}"`)
        }
      } else if (data.objectType === 'COMMENT' && data.eventType === 'CREATE') {
        const indMessage = this.messages.findIndex(item => item.id === data.body.message.id)
        if (indMessage > -1) {
          const comments = this.messages[indMessage].comments
          const indComment = comments.findIndex(item => item.id === data.body.id)
          console.log(indComment)
          console.log(comments)
          if (indComment > -1) {
            comments.splice(indComment, 1, data.body)
          } else {
            comments.push(data.body)
          }

        } else {
          console.error(`This message doesn\`t exist`)
        }
      } else {
        console.error(`Looks like the object type if unknown "${data.objectType}"`)
      }
    })
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
