<template>
  <div :class="'modal ' + (modal.isShowModal ? 'show-modal': '')">
    <div class="modal-content">
      <span
        class="close-button"
        @click="closeModal"
      >&times;</span>
      <div
        id="container"
        :class="'container ' + (modal.isSignUp ? 'right-panel-active': '')"
      >
        <div class="form-container sign-up-container">
          <form action="">
            <h2>Create Account</h2>
            <div class="social-container">
              <!-- <a href="#" class="social"><font-awesome-icon icon="fa-brands fa-twitter" /></a> -->
              <a
                href="#"
                class="social"
              ><i class="fab fa-google-plus-g" /></a>
              <a
                href="#"
                class="social"
              ><i class="fab fa-linkedin-in" /></a>
            </div>
            <span>or use your email for registration</span>
            <input
              type="text"
              placeholder="Name"
            >
            <input
              type="email"
              placeholder="Email"
            >
            <input
              type="password"
              placeholder="Password"
            >
            <input
              class="btn-submit"
              type="submit"
              value="Sign Up"
            >
            <input
              type="button"
              class="ghost btn-submit"
              value="Sign In"
              @click="tabSignIn"
            >
          </form>
        </div>
        <div class="form-container sign-in-container">
          <form action="">
            <h2>Sign in</h2>
            <div class="social-container">
              <a
                href="#"
                class="social"
              ><i class="fab fa-facebook-f" /></a>
              <a
                href="#"
                class="social"
              ><i class="fab fa-google-plus-g" /></a>
              <a
                href="#"
                class="social"
              ><i class="fab fa-linkedin-in" /></a>
            </div>
            <span>or use your account</span>
            <input
              type="email"
              placeholder="Email"
            >
            <input
              type="password"
              placeholder="Password"
            >
            <a
              class="forgot-pass"
              href="#"
            >Forgot your password?</a>
            <input
              class="btn-submit"
              type="submit"
              value="Sign In"
            >
            <input
              type="button"
              class="ghost btn-submit"
              value="Sign Up"
              @click="tabSignUp"
            >
          </form>
        </div>
        <div class="overlay-container">
          <div class="overlay">
            <div class="overlay-panel overlay-left">
              <h3>Welcome Back!</h3>
              <p class="text-p">
                To keep connected with us please login with your personal info
              </p>
              <button
                class="ghost btn-submit"
                @click="tabSignIn"
              >
                Sign In
              </button>
            </div>
            <div class="overlay-panel overlay-right">
              <h3>Hello, Friend!</h3>
              <p class="text-p">
                Enter your personal details and start journey with us
              </p>
              <button
                class="ghost btn-submit"
                @click="tabSignUp"
              >
                Sign Up
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'ModalSignIn',
    props: {
        modal: {
            type: Object,
            required: true,
        }
    },
    data() {
        return {
            
        }
    },
    methods: {
        tabSignUp: function() {
            this.$emit('changeIsSignUp', true)
        },

        tabSignIn: function() {
            this.$emit('changeIsSignUp', false)
        },

        closeModal: function() {
            this.$emit('closeModal')
        }
    }
}
</script>

<style lang="css">
</style>
