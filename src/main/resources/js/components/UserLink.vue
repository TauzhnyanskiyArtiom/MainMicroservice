<template>
  <router-link
      :to="`/user/${user.id}`"
      class="px-3"
      style="text-decoration: none; color: inherit;"
  >
    <v-avatar
        v-if="user && user.userpic"
        :size="`${size || 36}px`"
    >
      <img
          :src="user.userpic"
          :alt="user.name"
      >
    </v-avatar>

    <span :class="`ml-3 text-${textStyle}`">{{ userName }}</span>
  </router-link>
</template>

<script>
export default {
  name: 'UserLink',
  props: ['user', 'size', 'textStyle'],
  computed: {
    userName() {
      return this.user.name
    }
  },
}
</script>

<style scoped>
</style>