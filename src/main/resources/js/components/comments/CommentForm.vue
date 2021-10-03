<template>
  <v-container>
    <v-layout row class="px-3">
      <v-text-field
          label="Add comment"
          placeholder="Write something"
          v-model="text"
          @keyup.enter="save"
      />
      <v-btn @click="save">
        Add
      </v-btn>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'CommentForm',
  props: ['messageId','comments'],
  data() {
    return {
      text: ''
    }
  },
  methods: {
    save() {
      this.$resource('/api/comments').save({},{text: this.text, message: {id: this.messageId}})
          .then(result =>
          result.json().then(data => {
            this.comments.push(data)
            this.text = ''
          })
      )
      }
  }
}
</script>

<style scoped>
</style>