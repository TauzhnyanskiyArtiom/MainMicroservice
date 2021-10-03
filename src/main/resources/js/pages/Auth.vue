<template>
  <v-container>

    <div class="d-flex align-start flex-column">Необходимо авторизоваться через:
      <v-btn
          href="/oauth2/authorization/google"
          depressed
          color="primary"
          class="mt-2"
      >Google
      </v-btn>
    </div>
  </v-container>
</template>

<script>
export default {
  name: 'Auth',
}
</script>

<style scoped>
</style>