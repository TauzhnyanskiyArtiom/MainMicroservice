<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <message-form :messages="messages" :messageAttr="message"/>
      <message-row v-for="message in messages" :key="message.id" :message="message" :editMessage="editMessage"
                   :deleteMessage="deleteMessage" :messages="messages"/>
    </v-layout>
  </v-container>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import messagesApi from 'api/messages'

    export default {
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message: null,
                messages: [],
            }
        },
        methods: {
            editMessage(message) {
                this.message = message
            },
            deleteMessage(message) {
                messagesApi.remove(message.id).then(result => {
                    if (result.ok) {
                        const index = this.messages.findIndex(item => item.id === message.id)
                        if (index > -1)
                          this.messages.splice(index, 1)
                    }
                })
                this.message = null
            }
        },
        beforeMount() {
            this.messages = frontendData.messages
        }
    }
</script>

<style>

</style>
