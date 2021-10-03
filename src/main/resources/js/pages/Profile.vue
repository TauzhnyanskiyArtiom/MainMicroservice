<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div class="title mb-3">User profile</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <v-img :src="profile.userpic"></v-img>
          </v-flex>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>{{profile.name}}</v-flex>
              <v-flex>{{profile.locale}}</v-flex>
              <v-flex>{{profile.gender}}</v-flex>
              <v-flex>{{profile.lastVisit}}</v-flex>
              <v-flex>
                {{profile.subscriptions && profile.subscriptions.length}} subscriptions
              </v-flex>
              <router-link
                  v-if="isMyProfile"
                  :to="`/subscriptions/${profile.id}`"
              >
                {{profile.subscribers && profile.subscribers.length}} subscribers
              </router-link>
              <v-flex
                  v-else
              >
                {{profile.subscribers && profile.subscribers.length}} subscribers
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-btn
            v-if="!isMyProfile"
            @click="changeSubscription"
            class="mt-5"
        >
          {{isISubscribed ? 'Unsubscribe' : 'Subscribe'}}
        </v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import Vue from "vue";

  export default {
    name: 'Profile',
    data() {
      return {
        profile: {},
        oauthUser: frontendData.profile,
      }
    },
    computed: {
      isMyProfile() {
        return !this.$route.params.id ||
            this.$route.params.id === this.oauthUser.id
      },
      isISubscribed() {
        return this.profile.subscribers &&
            this.profile.subscribers.find(subscription => {
              return subscription.subscriber === this.oauthUser.id
            })
      }
    },
    watch: {
      '$route'() {
        this.updateProfile()
      }
    },
    methods: {
      changeSubscription() {
        Vue.http.post(`/api/profile/change-subscription/${this.profile.id}`).then(result =>
            result.json().then(data => {
              this.profile = data
            }))
      },
      updateProfile() {
        const id = this.$route.params.id || this.oauthUser.id
        this.$resource('/api/profile{/id}').get({id: id}).then(result =>
            result.json().then(data => {
              this.profile = data
            }))
        this.$forceUpdate()
      }
    },
    beforeMount() {
      this.updateProfile()
    }
  }
</script>

<style scoped>
img {
  max-width: 100%;
  height: auto;
}
</style>