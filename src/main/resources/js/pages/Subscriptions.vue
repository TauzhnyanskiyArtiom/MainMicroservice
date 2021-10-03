<template>
  <v-container>
    <v-layout justify-space-around>
      <v-list>
        <v-list-item
            v-for="item in subscriptions"
            :key="item.id"
        >
          <user-link
              :user="item.subscriber"
              size="24"
          ></user-link>

          <v-btn
              @click="changeSubscriptionStatus(item.subscriber.id)"
          >
            {{item.active ? "Dismiss" : "Approve"}}
          </v-btn>
        </v-list-item>
      </v-list>
    </v-layout>
  </v-container>
</template>

<script>
import Vue from "vue";
import UserLink from 'components/UserLink.vue'

export default {
  name: 'Subscriptions',
  components: {UserLink},
  data() {
    return {
      subscriptions: [],
      oauthUser: frontendData.profile
    }
  },
  methods: {
    changeSubscriptionStatus(subscriberId) {
      Vue.http.post(`/api/profile/change-status/${subscriberId}`)

      const subscriptionIndex = this.subscriptions.findIndex(item =>
          item.subscriber.id === subscriberId
      )
      const subscription = this.subscriptions[subscriptionIndex]
      this.subscriptions = [
        ...this.subscriptions.slice(0, subscriptionIndex),
        {
          ...subscription,
          active: !subscription.active
        },
        ...this.subscriptions.slice(subscriptionIndex + 1)
      ]
    }
  },
  beforeMount() {
    this.$resource('/api/profile/get-subscribers{/id}').get({id: this.oauthUser.id}).then(result =>
        result.json().then(data => {
          this.subscriptions = data
        }))
  }
}
</script>

<style scoped>
</style>