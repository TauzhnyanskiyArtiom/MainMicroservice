<template>
  <v-layout row>
    <v-text-field
        label="New message"
        placeholder="Write something"
        v-model="text"
        @keyup.enter="save"
    />
    <v-btn @click="save">
      Save
    </v-btn>
  </v-layout>
</template>

<script>

import messagesApi from 'api/messages'

export default {
  props: ['messages', 'messageAttr'],
  data() {
    return {
      text: '',
      id: '',
    }
  },
  watch: {
    messageAttr(newVal, oldVal) {
      if (newVal === null){
        this.text = ''
        this.id = ''
      } else {
        this.text = newVal.text
        this.id = newVal.id
      }
    }
  },
  methods: {
    save() {
      const message = {
        id: this.id,
        text: this.text
      }
      if (this.id) {
        messagesApi.update(message).then(result =>
            result.json().then(data => {
              const index = this.messages.findIndex(item => item.id === data.id)
              this.messages.splice(index, 1, data)
              this.text = ''
              this.id = ''
            })
        )
      } else {
        messagesApi.add(message).then(result =>
            result.json().then(data => {
              const index = this.messages.findIndex(item => item.id === data.id)
              if (index > -1) {
                this.messages.splice(index, 1, data)
              } else {
                this.messages.unshift(data)
              }
            })
        )
        this.text = ''
      }
    }
  }
}
</script>

<style>

</style>
