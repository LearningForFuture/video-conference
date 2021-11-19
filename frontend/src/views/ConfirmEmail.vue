<template>
  <h1 class="text-primary">
    Verification success
  </h1>
</template>
<script>
import {mapActions} from 'vuex';

export default {
  name: 'ConfirmEmailRegistration',

  data() {
    return {
        
    };
  },

  // computed: {
  //     ...mapGetters('users', ['getUsername', 'getPassword', 'getEmail'])
  // },

  mounted() {
    this.confirmEmail();
  },

  methods: {
    ...mapActions('users', ['confirmEmailRegistration', 'login']),
    async confirmEmail() {
      try {
        await this.confirmEmailRegistration(this.$route.params.token);
        await this.login({
          username: localStorage.getItem("username"),
          password: localStorage.getItem("password")
        });
        localStorage.removeItem("username");
        localStorage.removeItem("password");
        // this.$router.push({name: 'Teams'});
        window.location.href = "conversations/teams";
      } catch (e) {
        if (e.response.status === 404) {
          this.$toast.error(e.response.data.message, {
            position: "top-right",
            timeout: 5000,
            closeOnClick: true,
            pauseOnFocusLoss: true,
            pauseOnHover: true,
            draggable: true,
            draggablePercent: 0.6,
            showCloseButtonOnHover: false,
            hideProgressBar: true,
            closeButton: "button",
            icon: true,
            rtl: false
          });
        }
      }
    }
  },
};
</script>

<style lang="scss" scoped>

</style>