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
    <media v-if="message.link" :message="message"></media>
    <v-card-actions v-if="oauthUser.id === message.author.id">
      <v-btn value="Edit" @click="edit" small text rounded>Edit</v-btn>
      <v-btn icon @click="del" large>
        <v-icon>delete</v-icon>
      </v-btn>
    </v-card-actions>
    <comment-list
        :comments="message.comments"
        :message-id="message.id"
        :authorId="message.author.id"
    ></comment-list>
  </v-card>
</template>
<script>
import CommentList from "components/comments/CommentList.vue"
import UserLink from "components/UserLink.vue"
import Media from 'components/media/Media.vue'

    export default {
        props: ['message', 'editMessage', 'deleteMessage', 'messages'],
        components: {CommentList, UserLink, Media},
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
