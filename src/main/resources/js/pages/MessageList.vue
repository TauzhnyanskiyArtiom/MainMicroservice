<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <message-form :messages="messages" :messageAttr="message"/>
      <message-row v-for="message in sortedMessages" :key="message.id" :message="message" :editMessage="editMessage"
                   :deleteMessage="deleteMessage" :messages="messages"/>
    </v-layout>
  </v-container>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'

    export default {
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message: null,
                messages: frontendData.messages,
            }
        },
        computed: {
          sortedMessages() {
            return (this.messages || []).sort((a, b) => -(a.id - b.id))
          }
        },
        methods: {
            editMessage(message) {
                this.message = message
            },
            deleteMessage(message) {
                this.$resource('/api/messages{/id}').remove({id: message.id}).then(result => {
                    if (result.ok) {
                        this.messages.splice(this.messages.indexOf(message), 1)
                    }
                })
            }
        }
    }
</script>

<style>

</style>
