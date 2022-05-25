<template>
  <v-list-item>
    <user-link
        :user="comment.author"
        textStyle="body-2"
    ></user-link>
    <v-list-item-content>
      <v-list-item-title>{{comment.text}}</v-list-item-title>
    </v-list-item-content>

    <v-list-item-action v-if="authorId === oauthUser.id || comment.author.id === oauthUser.id">
      <v-btn icon @click="del" large>
        <v-icon>delete</v-icon>
      </v-btn>
    </v-list-item-action>
  </v-list-item>
</template>

<script>
import UserLink from "components/UserLink.vue";
export default {
  name: 'CommentItem',
  components: {UserLink},
  props: ['comment', 'deleteComment', 'authorId'],

  data() {
    return {
      oauthUser: frontendData.profile
    }
  },

  methods:  {
    del(){
      this.deleteComment(this.comment)
    }
  }
}
</script>

<style scoped>
</style>