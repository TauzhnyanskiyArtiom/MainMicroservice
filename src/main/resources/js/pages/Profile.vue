<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div class="title mb-3">User profile</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <v-img v-bind:src="srcUserpic(profile)" alt="User image"></v-img>
          </v-flex>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>{{ profile.name }}</v-flex>
              <v-flex>{{ profile.locale }}</v-flex>
              <v-flex>{{ profile.lastVisit }}</v-flex>
              <v-flex>
                {{ profile.subscriptions && profile.subscriptions.length }} subscriptions
              </v-flex>
              <router-link
                  v-if="isMyProfile"
                  :to="`/subscriptions/${profile.id}`"
              >
                {{ profile.subscribers && profile.subscribers.length }} subscribers
              </router-link>
              <v-flex
                  v-else
              >
                {{ profile.subscribers && profile.subscribers.length }} subscribers
              </v-flex>
              <v-flex v-if="isMyProfile">
                <v-btn
                    class="mt-5"
                    @click="handleImageImport"
                >
                  Change Image
                </v-btn>
                <input
                    ref="uploader"
                    class="d-none"
                    type="file"
                    name="image"
                    id="image"
                    accept="image/*"
                    @change="changePhoto">
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-btn
            v-if="!isMyProfile"
            @click="changeSubscription"
            class="mt-5"
        >
          {{ isISubscribed ? 'Unsubscribe' : 'Subscribe' }}
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
      selectedFile: null
    }
  },
  computed: {
    isMyProfile() {
      return !this.$route.params.id ||
          this.$route.params.id === this.oauthUser.id
    },
    isISubscribed() {
      console.log(this.profile.subscribers)
      return this.profile.subscribers &&
          this.profile.subscribers.find(subscription => {
            console.log(subscription)
            return subscription.subscriberId === this.oauthUser.id
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
    srcUserpic(profile) {
      if (profile.userpic.startsWith("https://"))
        return profile.userpic;
      return '/api/profile/' + profile.id + '/avatar';
    },
    handleImageImport() {
      this.isSelecting = true;
      window.addEventListener('focus', () => {
        this.isSelecting = false
      }, {once: true});

      this.$refs.uploader.click();
    },
    changePhoto(e) {
      this.selectedFile = e.target.files[0];

      const formData = new FormData()
      formData.append('image', this.selectedFile, this.selectedFile.name)
      Vue.http.post('/api/profile/change-photo', formData, {}).then((response) => {
        this.$router.go(this.$router.currentRoute)
      })
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