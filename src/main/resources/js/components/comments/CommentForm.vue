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

import commentsApi from 'api/comments'

export default {
  name: 'CommentForm',
  props: ['messageId', 'comments'],
  data() {
    return {
      text: ''
    }
  },
  methods: {
    save() {
      commentsApi.add({text: this.text, messageId: this.messageId})
          .then(result =>
              result.json().then(data => {
                const index = this.comments.findIndex(item => item.id === data.id)
                if (index > -1)
                  this.comments.splice(index, 1, data)
                else
                  this.comments.push(data)
              }))
      this.text = ''
    }
  }
}
</script>

<style scoped>
</style>