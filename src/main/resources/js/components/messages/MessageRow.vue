<template>
  <v-card class="my-2">
    <v-card-text primary-title>
      <user-link
          :user="message.author"
          size="48"
          textStyle="body-1"
      ></user-link>
      <div class="pt-3 text-subtitle-1 font-weight-medium">
        {{ message.text }}
      </div>
    </v-card-text>
    <v-card-actions v-if="oauthUser.name === message.author.name">
      <v-btn value="Edit" @click="edit" small text rounded>Edit</v-btn>
      <v-btn icon @click="del" small>
        <v-icon>delete</v-icon>
      </v-btn>
    </v-card-actions>
    <comment-list
        :comments="message.comments"
        :message-id="message.id"
    ></comment-list>
  </v-card>
</template>
<script>
import CommentList from "components/comments/CommentList.vue";
import UserLink from "components/UserLink.vue";

    export default {
        props: ['message', 'editMessage', 'deleteMessage', 'messages'],
        components: {CommentList, UserLink},
        data() {
          return {
            oauthUser: frontendData.profile
          }
        },
        methods: {
            edit() {
                this.editMessage(this.message)
            },
            del() {
                this.deleteMessage(this.message)
            }
        }
    }
</script>

<style>

</style>
